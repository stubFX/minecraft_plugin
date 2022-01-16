package com.stubfx.plugin

import com.stubfx.plugin.chatreactor.ChatReactor
import com.stubfx.plugin.chatreactor.commands.CommandRunner
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin


class Main : JavaPlugin() {
    private var chatReactor = ChatReactor(this)

    override fun onEnable() {
//        getCommand("clearchunk")?.tabCompleter = MaterialTabCompleter()
//        getCommand("sectionreplace")?.tabCompleter = MaterialTabCompleter()
//        getCommand("chunkreplace")?.tabCompleter = MaterialTabCompleter()
//        server.pluginManager.registerEvents(PlayerListener(this), this)
//        server.pluginManager.registerEvents(EntityListener(this), this)
//        server.pluginManager.registerEvents(ProjectileListener(this), this)
        CommandRunner.setMainRef(this)
        BlockReplacer.setMainRef(this)
        PluginUtils.setMainRef(this)
    }

    fun getTicks(): Int {
        return 20
    }

    override fun onDisable() {
        chatReactor.onDisable()
        ConfigManager.onDisable()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        return ChatCommandExecutor.onCommand(sender, command, label, args)
    }

}