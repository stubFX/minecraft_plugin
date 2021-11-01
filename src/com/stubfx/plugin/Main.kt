package com.stubfx.plugin

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

class Main : JavaPlugin() {

    private val DRILL_OFFSET = 2
    private var player: Player? = null
    private var myTask: BukkitTask? = null

    override fun onEnable() {
        getCommand("clearchunk")?.tabCompleter = MyMaterialTabCompleter()
        server.pluginManager.registerEvents(PlayerListener(this), this)
    }

    override fun onDisable() {
        clearTask()
    }

    private fun clearTask() {
        myTask?.cancel()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        clearTask()
        player = sender as Player
        if (player == null) {
            return false
        }
        // in this case we found me.
        checkCommand(command, args)
        return false
    }

    private fun checkCommand(command: Command, args: Array<String>) {
        when (command.name.lowercase()) {
            "draw" -> draw()
            "jesus" -> jesus()
            "drill" -> drill()
            "clearchunk" -> clearchunk(player!!.location, args)
            "stopcommand" -> clearTask()
        }
    }

    private fun getPlayerLocation(): Location? {
        return player?.location
    }

    fun clearChunkListener(location: Location, args: Array<String>?, clearFullChunk: Boolean) {
        clearchunk(location, args, clearFullChunk)
    }

    private fun clearchunk(location: Location, args: Array<String>?, clearFullChunk: Boolean = false) {
        val playerHeight = if (!clearFullChunk) location.y else 255
        val chunk = location.chunk
        myTask = object : BukkitRunnable() {
            override fun run() {
                val materialsToExclude = mutableListOf<Material>()
                if (args != null) {
                    for (arg in args) {
                        materialsToExclude.add(Material.valueOf(arg))
                    }
                }
                for (x in 0..15) {
                    for (z in 0..15) {
                        for (y in playerHeight.toInt() downTo 1) {
                            // this runs for any selected block of the chunk
                            // we need to convert to air, only if they are not ores
                            val block = chunk.getBlock(x, y, z)
                            // check if block type is not contained in the 'exclude list'
                            if (!materialsToExclude.contains(block.type)) {
                                // here if the block is not what we are looking for
                                // so we convert that into air
                                block.type = Material.AIR
                            }
                        }
                    }
                }
            }
        }.runTask(this)
    }

    private fun drill() {
        myTask = object : BukkitRunnable() {
            override fun run() {
                val location = getPlayerLocation()
                for (z in -DRILL_OFFSET..DRILL_OFFSET) {
                    for (x in -DRILL_OFFSET..DRILL_OFFSET) {
                        for (y in 0..DRILL_OFFSET + 2) {
                            location?.clone()?.add(x.toDouble(), y.toDouble(), z.toDouble())?.block?.type = Material.AIR
                        }
                    }
                }
                location?.block?.type = Material.TORCH
            }
        }.runTaskTimer(this, 1, 1)
    }

    private fun jesus() {
        myTask = object : BukkitRunnable() {
            override fun run() {
                getPlayerLocation()?.add(0.toDouble(), (-1).toDouble(), 0.toDouble())?.block?.type = Material.STONE
            }
        }.runTaskTimer(this, 1, 1)
    }

    private fun draw() {
        // in this case we want to activate the command.
        myTask = object : BukkitRunnable() {
            override fun run() {
                val targetBlockExact = player?.getTargetBlockExact(20)
                targetBlockExact?.type = player?.inventory?.itemInMainHand?.type ?: Material.AIR
            }
        }.runTaskTimer(this, 1, 1)
    }


}