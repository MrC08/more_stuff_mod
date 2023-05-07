
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.essorant.morestuff.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;

import com.essorant.morestuff.item.BambooSpearItem;
import com.essorant.morestuff.MoreStuffMod;

public class MoreStuffModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MoreStuffMod.MODID);
	public static final RegistryObject<Item> BAMBOO_SPEAR = REGISTRY.register("bamboo_spear", () -> new BambooSpearItem());
}
