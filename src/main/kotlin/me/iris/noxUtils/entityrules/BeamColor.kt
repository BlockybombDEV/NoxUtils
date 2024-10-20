package me.iris.noxUtils.entityrules

import com.noxcrew.noxesium.api.protocol.rule.EntityRuleIndices
import com.noxcrew.noxesium.paper.api.rule.RemoteServerRule
import me.iris.noxUtils.NoxUtils
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Guardian
import org.bukkit.entity.EnderCrystal
import java.awt.Color
import java.util.*

public class BeamColor {
    // Only works for minecraft:guardian, minecraft:elder_guardian and minecraft:end_crystal
    private val entityManager = NoxUtils.entityRuleManager

    // Preset colors for Skript
    public companion object {
        public val RED: Optional<Color> = Optional.of(Color.RED)
        public val ORANGE: Optional<Color> = Optional.of(Color(255, 157, 0))
        public val YELLOW: Optional<Color> = Optional.of(Color.YELLOW)
        public val LIME: Optional<Color> = Optional.of(Color.GREEN)
        public val GREEN: Optional<Color> = Optional.of(Color(0, 100, 0))
        public val CYAN: Optional<Color> = Optional.of(Color(0, 170, 170))
        public val AQUA: Optional<Color> = Optional.of(Color.CYAN)
        public val BLUE: Optional<Color> = Optional.of(Color.BLUE)
        public val PURPLE: Optional<Color> = Optional.of(Color(100, 0, 200))
        public val PINK: Optional<Color> = Optional.of(Color.MAGENTA)
        public val WHITE: Optional<Color> = Optional.of(Color.WHITE)
        public val BLACK: Optional<Color> = Optional.of(Color.BLACK)
        public val GRAY: Optional<Color> = Optional.of(Color.GRAY)
        public val LIGHT_GRAY: Optional<Color> = Optional.of(Color.LIGHT_GRAY)
        public val DARK_GRAY: Optional<Color> = Optional.of(Color.DARK_GRAY)
    }

    /**
     * Set the beam color for a [Guardian] or [EnderCrystal] (also disables the bubbles on guardian beams)
     */
    public fun setBeamColor(entity: Entity, color: Optional<Color>) {
        if (entity.type != EntityType.GUARDIAN && entity.type != EntityType.END_CRYSTAL) return
        var rule: RemoteServerRule<Any>? = entityManager.getEntityRule(entity, EntityRuleIndices.BEAM_COLOR)
        rule!!.setValue(color)

        // Hide the beam bubbles for Guardians
        if (entity.type != EntityType.GUARDIAN) return
        rule = entityManager.getEntityRule(entity, EntityRuleIndices.DISABLE_BUBBLES)
        rule!!.setValue(true)
    }

    /**
     * Set the beam fade/gradient for a [Guardian] or [EnderCrystal]
     */
    public fun setBeamFade(entity: Entity, color: Optional<Color>) {
        if (entity.type != EntityType.GUARDIAN && entity.type != EntityType.END_CRYSTAL) return
        val rule: RemoteServerRule<Any>? = entityManager.getEntityRule(entity, EntityRuleIndices.BEAM_COLOR_FADE)
        rule!!.setValue(color)
    }

    /**
     * Reset the beam color for a [Guardian] or [EnderCrystal]
     */
    public fun resetBeamColor(entity: Entity) {
        if (entity.type != EntityType.GUARDIAN && entity.type != EntityType.END_CRYSTAL) return
        val rule: RemoteServerRule<Any>? = entityManager.getEntityRule(entity, EntityRuleIndices.BEAM_COLOR)
        rule!!.reset()
    }

    /**
     * Reset the beam fade/gradient for a [Guardian] or [EnderCrystal]
     */
    public fun resetBeamFade(entity: Entity) {
        if (entity.type != EntityType.GUARDIAN && entity.type != EntityType.END_CRYSTAL) return
        val rule: RemoteServerRule<Any>? = entityManager.getEntityRule(entity, EntityRuleIndices.BEAM_COLOR_FADE)
        rule!!.reset()
    }

}