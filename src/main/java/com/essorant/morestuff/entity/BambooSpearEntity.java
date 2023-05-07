
package com.essorant.morestuff.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.Packet;

import java.util.Random;

import com.essorant.morestuff.procedures.BambooSpearHitProcedure;
import com.essorant.morestuff.init.MoreStuffModItems;
import com.essorant.morestuff.init.MoreStuffModEntities;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class BambooSpearEntity extends AbstractArrow implements ItemSupplier {
	public BambooSpearEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(MoreStuffModEntities.BAMBOO_SPEAR.get(), world);
	}

	public BambooSpearEntity(EntityType<? extends BambooSpearEntity> type, Level world) {
		super(type, world);
	}

	public BambooSpearEntity(EntityType<? extends BambooSpearEntity> type, double x, double y, double z, Level world) {
		super(type, x, y, z, world);
	}

	public BambooSpearEntity(EntityType<? extends BambooSpearEntity> type, LivingEntity entity, Level world) {
		super(type, entity, world);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
		return new ItemStack(MoreStuffModItems.BAMBOO_SPEAR.get());
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}

	@Override
	protected void doPostHurtEffects(LivingEntity entity) {
		super.doPostHurtEffects(entity);
		entity.setArrowCount(entity.getArrowCount() - 1);
	}

	@Override
	public void playerTouch(Player entity) {
		super.playerTouch(entity);
		BambooSpearHitProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), entity);
	}

	@Override
	public void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		BambooSpearHitProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), entityHitResult.getEntity());
	}

	@Override
	public void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		BambooSpearHitProcedure.execute(this.level, blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ(), this.getOwner());
	}

	@Override
	public void tick() {
		super.tick();
		if (this.inGround)
			this.discard();
	}

	public static BambooSpearEntity shoot(Level world, LivingEntity entity, Random random, float power, double damage, int knockback) {
		BambooSpearEntity entityarrow = new BambooSpearEntity(MoreStuffModEntities.BAMBOO_SPEAR.get(), entity, world);
		entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
		entityarrow.setSilent(true);
		entityarrow.setCritArrow(false);
		entityarrow.setBaseDamage(damage);
		entityarrow.setKnockback(knockback);
		world.addFreshEntity(entityarrow);
		world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.bamboo.hit")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
		return entityarrow;
	}

	public static BambooSpearEntity shoot(LivingEntity entity, LivingEntity target) {
		BambooSpearEntity entityarrow = new BambooSpearEntity(MoreStuffModEntities.BAMBOO_SPEAR.get(), entity, entity.level);
		double dx = target.getX() - entity.getX();
		double dy = target.getY() + target.getEyeHeight() - 1.1;
		double dz = target.getZ() - entity.getZ();
		entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1f * 2, 12.0F);
		entityarrow.setSilent(true);
		entityarrow.setBaseDamage(2);
		entityarrow.setKnockback(5);
		entityarrow.setCritArrow(false);
		entity.level.addFreshEntity(entityarrow);
		entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.bamboo.hit")), SoundSource.PLAYERS, 1, 1f / (new Random().nextFloat() * 0.5f + 1));
		return entityarrow;
	}
}