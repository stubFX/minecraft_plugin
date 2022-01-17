package com.stubfx.plugin.chatreactor.commands.impl

import com.stubfx.plugin.chatreactor.commands.Command
import com.stubfx.plugin.chatreactor.commands.CommandRunner
import com.stubfx.plugin.chatreactor.commands.CommandType
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity

object Spawn : Command() {

    override fun commandType(): CommandType = CommandType.SPAWN

    override fun options(): List<String> {
        return EntityType.values().map { it.toString() }
    }

    override fun behavior(playerName: String, options: String?) {
        // this is what the function is supposed to do.
        val blacklist = listOf(EntityType.WITHER, EntityType.ENDER_DRAGON, EntityType.ENDER_CRYSTAL)
        EntityType.GLOW_SQUID
        var mobToSpawn = EntityType.CREEPER
        try {
            mobToSpawn = EntityType.valueOf(options?.uppercase() ?: "CREEPER")
        } catch (_: Exception) {
        }
        if (blacklist.contains(mobToSpawn)) {
            return
        }
        CommandRunner.forEachPlayer {
            val entity = it.world.spawnEntity(it.location, mobToSpawn)
            entity.customName = playerName
            entity.isCustomNameVisible = true
        }
    }

}