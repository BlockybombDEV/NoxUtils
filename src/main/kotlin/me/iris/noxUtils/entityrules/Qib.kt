package me.iris.noxUtils.entityrules

import com.noxcrew.noxesium.api.protocol.rule.EntityRuleIndices
import com.noxcrew.noxesium.api.protocol.rule.ServerRuleIndices
import com.noxcrew.noxesium.api.qib.QibDefinition
import com.noxcrew.noxesium.api.qib.QibEffect
import com.noxcrew.noxesium.paper.api.rule.RemoteServerRule
import me.iris.noxUtils.NoxUtils
import me.iris.noxUtils.NoxUtils.Companion.qibDefinitions
import org.bukkit.entity.Interaction
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

public class Qib {
    // Qibs are only usable on minecraft:interaction entities
    private val manager = NoxUtils.noxesiumManager
    private val entityManager = NoxUtils.entityRuleManager

    /**
     * Define and add a definition to list of Qib definitions
     */
    public fun addDefinition(id: String, onEnter: QibEffect?, onLeave: QibEffect?, whileInside: QibEffect?, onJump: QibEffect?, trigger: Boolean) {
        val definition = QibDefinition(onEnter, onLeave, whileInside, onJump, trigger)
        qibDefinitions[id] = definition
    }

    /**
     * Remove a definition from the list of Qib definitions
     */
    public fun removeDefinition(id: String) {
        qibDefinitions.remove(id)
    }

    /**
     * Send all Qib definitions to a [Player]
     */
    public fun sendDefinitions(player: Player) {
        val rule: RemoteServerRule<Any>? = manager.getServerRule(player, ServerRuleIndices.QIB_BEHAVIORS)
        rule!!.reset()
        rule!!.value = qibDefinitions
        manager.updateServerRules(player)

    }

    /**
     * Set the Qib behavior for an [Interaction]
     */
    public fun setBehavior(entity: Interaction, id: String) {
        val rule: RemoteServerRule<Any>? = entityManager.getEntityRule(entity, EntityRuleIndices.QIB_BEHAVIOR)
        rule!!.value = id
    }

    /**
     * Set the width z-axis for an [Interaction]
     */
    public fun setWidthZ(entity: Interaction, value: Double) {
        val rule: RemoteServerRule<Any>? = entityManager.getEntityRule(entity, EntityRuleIndices.QIB_WIDTH_Z)
        rule!!.value = value
    }

}