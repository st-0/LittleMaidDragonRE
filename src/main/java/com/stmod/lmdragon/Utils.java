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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author Yamato
 *
 */
public class Utils {
	private Utils() {
		;
	}

	public static boolean tryUseItems(EntityPlayer player, Item item, boolean consumed) {
		return tryUseItems(player, item, -1, consumed);
	}

	public static boolean tryUseItems(EntityPlayer player, Item item, int damage, boolean consumed) {
		ItemStack curItem = player.getHeldItemMainhand();
		if (curItem != null && curItem.getItem() == item && (damage < 0 || curItem.getItemDamage() == damage)) {
			if (consumed && !player.capabilities.isCreativeMode) {
				curItem.stackSize--;
				if (curItem.stackSize <= 0) {
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
				}
			}
			return true;
		}
		return false;
	}
}
