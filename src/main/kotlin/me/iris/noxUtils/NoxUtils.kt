package me.iris.noxUtils

import com.noxcrew.noxesium.api.qib.QibDefinition
import com.noxcrew.noxesium.paper.api.EntityRuleManager
import com.noxcrew.noxesium.paper.api.NoxesiumManager
import com.noxcrew.noxesium.paper.api.rule.EntityRules
import com.noxcrew.noxesium.paper.api.rule.ServerRules
import fr.skytasul.glowingentities.GlowingBlocks
import fr.skytasul.glowingentities.GlowingEntities
import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class NoxUtils : JavaPlugin() {

    public companion object {
        public var Logger: Logger = LoggerFactory.getLogger("NoxUtils")
        public val qibDefinitions: MutableMap<String, QibDefinition> = mutableMapOf()
        public lateinit var instance: NoxUtils
        public lateinit var noxesiumManager: NoxesiumManager
        public lateinit var entityRuleManager: EntityRuleManager
        public lateinit var glowingEntities: GlowingEntities
        public lateinit var glowingBlocks: GlowingBlocks
    }

    override fun onLoad() {
        // registerCommands()
    }

    override fun onEnable() {
        instance = this

        // Enable glowing APIs
        glowingEntities = GlowingEntities(this)
        glowingBlocks = GlowingBlocks(this)

        // Registers all Noxesium related managers
        noxesiumManager = Manager(this, Logger)
        noxesiumManager.register()
        entityRuleManager = EntityRuleManager(noxesiumManager)
        entityRuleManager.register()

        // Registers all rules
        ServerRules(noxesiumManager)
        EntityRules(noxesiumManager)

        Logger.info("NoxUtils has been enabled!")
    }

    public fun getInstance(): NoxUtils {
        return instance
    }

    public fun getEntityGlow(): GlowingEntities {
        return glowingEntities
    }

    public fun getBlockGlow(): GlowingBlocks {
        return glowingBlocks
    }

    public fun getManager(): NoxesiumManager {
        return noxesiumManager
    }

    override fun onDisable() {
        noxesiumManager.unregister()
        entityRuleManager.unregister()
        Logger.info("NoxUtils has been disabled!")
    }
}
