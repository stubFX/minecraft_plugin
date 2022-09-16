package com.stubfx.plugin.chatreactor.commands

import com.stubfx.plugin.chatreactor.commands.impl.*

object CommandFactory {

    private val commandList = listOf(
        Help, Spawn, DropIt, Levitate, Anvil,
        Fire, Diamonds, Chickens, Knock,
        PanicSound, TreeCage, Speedy, Heal,
        Hungry, Feed, WallHack, Superman,
        Normalman, Water, Woollify, RandomBlock,
        NeverFall, Armored, ToTheNether, ToTheOverworld,
        Bob, NukeMobs, Dinnerbone, CraftingTable,
        IHaveIt, Paint, GoingDown, ClearChunk,
        ThatIsTNT, TunnelTime, OpenSpace, UpsideDown, OnTheMoon,
        Cookies, SuperTools, Milk, Potion, Lava, Slowness, Bees, EndFrame, WaterIsLava
    )

    fun getAvailableCommands(): List<Command> {
        return commandList
    }

    private val commandMap: Map<String, Command> = commandList.associateBy { it.commandName().lowercase() }


    fun getCommandOptions(commandName: String): List<String> {
        return getCommand(commandName)?.tabCompleterOptions() ?: StubCommand.tabCompleterOptions()
    }

    fun run(commandName: String, playerName: String, options: String?): CommandResultWrapper {
        return getCommand(commandName)!!.run(playerName, options)
    }

    fun forceRun(commandName: String, playerName: String, options: String?): CommandResultWrapper {
        return getCommand(commandName)!!.forceRun(playerName, options)
    }

    private fun getCommand(commandName: String) = commandMap[commandName.lowercase()]

}