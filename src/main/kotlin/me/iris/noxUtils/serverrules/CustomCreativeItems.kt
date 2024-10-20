package me.iris.noxUtils.serverrules

import me.iris.noxUtils.NoxUtils.Companion.customCreativeItems
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Manager for the custom creative tab
 */
public class CustomCreativeItems {

    /**
     * Add an [ItemStack] to the custom creative tab
     */
    public fun addItem(item: ItemStack) {
        customCreativeItems.add(item)
    }

    /**
     * Remove an [ItemStack] to the custom creative tab
     */
    public fun removeItem(item: ItemStack) {
        customCreativeItems.remove(item)
    }

    /**
     * Sends/updates the custom creative tab
     */
    public fun update() {
        for (player in Bukkit.getOnlinePlayers()) {
            SetRules(player).customCreativeItems()
        }
    }

}