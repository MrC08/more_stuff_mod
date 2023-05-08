package com.essorant.morestuff.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;

import com.essorant.morestuff.init.MoreStuffModEntities;
import com.essorant.morestuff.entity.BambooSpearEntity;

public class BambooSpearExtOnDispenseAttemptWithResultProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Direction direction) {
		if (direction == null)
			return;
		if (world instanceof ServerLevel projectileLevel) {
			Projectile _entityToSpawn = new Object() {
				public Projectile getArrow(Level level, float damage, int knockback, byte piercing) {
					AbstractArrow entityToSpawn = new BambooSpearEntity(MoreStuffModEntities.BAMBOO_SPEAR.get(), level);
					entityToSpawn.setBaseDamage(damage);
					entityToSpawn.setKnockback(knockback);
					entityToSpawn.setSilent(true);
					entityToSpawn.setPierceLevel(piercing);
					entityToSpawn.pickup = AbstractArrow.Pickup.ALLOWED;
					return entityToSpawn;
				}
			}.getArrow(projectileLevel, 2, 1, (byte) 16);
			_entityToSpawn.setPos((x + direction.getStepX() / 2), (y + direction.getStepY() / 2), (z + direction.getStepZ() / 2));
			_entityToSpawn.shoot((direction.getStepX()), (direction.getStepY()), (direction.getStepZ()), 1, 0);
			projectileLevel.addFreshEntity(_entityToSpawn);
		}
	}
}
