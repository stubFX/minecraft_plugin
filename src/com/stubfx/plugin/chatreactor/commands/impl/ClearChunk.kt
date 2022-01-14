package com.stubfx.plugin.chatreactor.commands.impl

import com.stubfx.plugin.BlockReplacer
import com.stubfx.plugin.chatreactor.commands.Command
import com.stubfx.plugin.chatreactor.commands.CommandRunner
import com.stubfx.plugin.chatreactor.commands.CommandType
import org.bukkit.Material

object ClearChunk : Command() {

    override fun commandName(): CommandType = CommandType.NOCHUNKNOPARTY

    override fun defaultCoolDown(): Long {
        return 600 * 1000 // 10 secs coolDown
    }

    override fun behavior(playerName: String, options: String?) {
        CommandRunner.forEachPlayer {
            val chunk = it.getTargetBlockExact(100)?.chunk ?: return@forEachPlayer
            BlockReplacer.chunkReplace(chunk, Material.AIR)
        }
    }

}