/**
 * Copyright 2015 Yamato
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stmod.lmdragon;

import info.ata4.minecraft.dragon.server.entity.EntityTameableDragon;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(
	modid = LittleMaidDragonRE.MODID,
	name = LittleMaidDragonRE.NAME,
	version = LittleMaidDragonRE.VERSION,
	dependencies = LittleMaidDragonRE.DEPENDENCIES,
	acceptedMinecraftVersions = LittleMaidDragonRE.ACCEPTED_MCVERSIONS)
public class LittleMaidDragonRE {
	public static final String MODID = "lmdragon";
	public static final String NAME = "LittleMaidDragonRE";
	public static final String VERSION = "1.9-1.0";
	public static final String DEPENDENCIES = "required-after:DragonMounts;required-after:lmreengaged";
	public static final String ACCEPTED_MCVERSIONS = "[1.9]";
	private static final String MAIDSAN_STRING = "DragonMaidsan";
	private static final String DRAGON_STRING = "DragonMount";

	@EventHandler
	public void onInit(FMLInitializationEvent event) {
		// ドラゴン追加
		EntityRegistry.registerModEntity(EntityLmdDragon.class, DRAGON_STRING, 1, this, 80, 3, true);
		// STRING が被るように追加すると、ワールドロード時に差し替えが発生する

		// メイドさん追加
		EntityRegistry.registerModEntity(EntityLmdMaidsan.class, MAIDSAN_STRING, 0, this, 80, 3, true);

		// イベントバスに追加
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onSpawnEntity(EntityJoinWorldEvent event) {
		if (!event.getWorld().isRemote) {
			if (event.getEntity().getClass() == EntityTameableDragon.class) {
				// 差し替え
				NBTTagCompound tag = new NBTTagCompound();
				event.getEntity().writeToNBT(tag);
				EntityLmdDragon dragon = new EntityLmdDragon(event.getWorld());
				dragon.readFromNBT(tag);
				event.setCanceled(true);
				event.getWorld().spawnEntityInWorld(dragon);
			}
		}
	}
}
