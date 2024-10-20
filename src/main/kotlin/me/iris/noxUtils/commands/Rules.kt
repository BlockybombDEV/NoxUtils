package me.iris.noxUtils.commands

import com.noxcrew.noxesium.api.protocol.NoxesiumFeature
import com.noxcrew.noxesium.api.protocol.rule.ServerRuleIndices
import com.noxcrew.noxesium.paper.api.rule.RemoteServerRule
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.*
import me.iris.noxUtils.NoxUtils.Companion.customCreativeItems
import me.iris.noxUtils.NoxUtils.Companion.noxesiumManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

public class Rules {

    public companion object {
        public var RuleCommands: MutableList<CommandAPICommand> = mutableListOf()
    }

    private var booleanServerRules: MutableMap<String, Int> = mutableMapOf(
        "disableSpinAttackCollision" to ServerRuleIndices.DISABLE_SPIN_ATTACK_COLLISIONS,
        "cameraLocked" to ServerRuleIndices.CAMERA_LOCKED,
        "disableVanillaMusic" to ServerRuleIndices.DISABLE_VANILLA_MUSIC,
        "disableBoatCollisions" to ServerRuleIndices.DISABLE_BOAT_COLLISIONS,
        "disableUiOptimizations" to ServerRuleIndices.DISABLE_UI_OPTIMIZATIONS,
        "showMapInUi" to ServerRuleIndices.SHOW_MAP_IN_UI,
        "disableDeferredChunkUpdates" to ServerRuleIndices.DISABLE_DEFERRED_CHUNK_UPDATES,
        "enableSmootherClientTrident" to ServerRuleIndices.ENABLE_SMOOTHER_CLIENT_TRIDENT,
        "disableMapUi" to ServerRuleIndices.DISABLE_MAP_UI
    )

    private var integerServerRules: MutableMap<String, Int> = mutableMapOf(
        "heldItemNameOffset" to ServerRuleIndices.HELD_ITEM_NAME_OFFSET,
        "riptideCoyoteTime" to ServerRuleIndices.RIPTIDE_COYOTE_TIME
    )

    private var itemStackServerRules: MutableMap<String, Int> = mutableMapOf(
        "handItemOverride" to ServerRuleIndices.HAND_ITEM_OVERRIDE,
        "customCreativeItems" to ServerRuleIndices.CUSTOM_CREATIVE_ITEMS
    )

    private var allRules: MutableMap<String, Int> = mutableMapOf()

    public fun registerCommands() {
        allRules.putAll(booleanServerRules)
        allRules.putAll(integerServerRules)
        allRules.putAll(itemStackServerRules)

        // Boolean server rules
        for (rule in booleanServerRules) {
            RuleCommands.add(
                subcommand(rule.key) {
                    entitySelectorArgumentManyPlayers("players", false, false)
                    booleanArgument("enabled", false)
                    playerExecutor { sender, commandArguments ->
                        val players = commandArguments["players"] as Collection<Player>
                        val value = commandArguments["enabled"] as Boolean
                        var affected = 0
                        for (player in players) {
                            if (!noxesiumManager.isUsingNoxesium(player, NoxesiumFeature.API_V2)) continue
                            val rule: RemoteServerRule<Any>? = noxesiumManager.getServerRule(player, rule.value)
                            rule!!.value = value
                            affected++
                        }
                        if (sender != null) sender.sendMessage(Component.text(affected).color(NamedTextColor.DARK_GREEN).append(Component.text(" player(s) affected").color(NamedTextColor.GREEN)))
                    }
                }
            )
        }

        // Integer server rules
        for (rule in integerServerRules) {
            RuleCommands.add(
                subcommand(rule.key) {
                    entitySelectorArgumentManyPlayers("players", false, false)
                    integerArgument("value")
                    playerExecutor { sender, commandArguments ->
                        val players = commandArguments["players"] as Collection<Player>
                        val value = commandArguments["value"] as Int
                        var affected = 0
                        for (player in players) {
                            if (!noxesiumManager.isUsingNoxesium(player, NoxesiumFeature.API_V2)) continue
                            val rule: RemoteServerRule<Any>? = noxesiumManager.getServerRule(player, rule.value)
                            rule!!.value = value
                            affected++
                        }
                        if (sender != null) sender.sendMessage(Component.text(affected).color(NamedTextColor.DARK_GREEN).append(Component.text(" player(s) affected").color(NamedTextColor.GREEN)))
                    }
                }
            )
        }

        // Item stack server rules
        for (rule in itemStackServerRules) {
            RuleCommands.add(
                subcommand(rule.key) {
                    entitySelectorArgumentManyPlayers("players", false, false)
                    itemStackArgument("value")
                    playerExecutor { sender, commandArguments ->
                        val players = commandArguments["players"] as Collection<Player>
                        val value = commandArguments["value"] as ItemStack
                        var affected = 0
                        if (rule.value == ServerRuleIndices.CUSTOM_CREATIVE_ITEMS) customCreativeItems.add(value)
                        for (player in players) {
                            if (!noxesiumManager.isUsingNoxesium(player, NoxesiumFeature.API_V2)) continue
                            val rules: RemoteServerRule<Any>? = noxesiumManager.getServerRule(player, rule.value)
                            if (rule.value != ServerRuleIndices.CUSTOM_CREATIVE_ITEMS) {
                                rules!!.value = value
                            } else {
                                rules!!.value = customCreativeItems
                            }
                            affected++
                        }
                        if (sender != null) sender.sendMessage(Component.text(affected).color(NamedTextColor.DARK_GREEN).append(Component.text(" player(s) affected").color(NamedTextColor.GREEN)))
                    }
                }
            )
        }

        // Reset server rules
        RuleCommands.add(
            subcommand("reset") {
                entitySelectorArgumentManyPlayers("players", false, false)
                playerExecutor { sender, commandArguments ->
                    val players = commandArguments["players"] as Collection<Player>
                    var affected = 0
                    for (player in players) {
                        if (!noxesiumManager.isUsingNoxesium(player, NoxesiumFeature.API_V2)) continue
                        for (rule in allRules) {
                            val rule: RemoteServerRule<Any>? = noxesiumManager.getServerRule(player, rule.value)
                            rule!!.reset()
                            affected++
                        }
                    }
                    if (sender != null) sender.sendMessage(Component.text(affected).color(NamedTextColor.DARK_GREEN).append(Component.text(" player(s) affected").color(NamedTextColor.GREEN)))
                }
            }
        )

    }

}