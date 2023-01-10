package dev.mr3n.bungeedeafultserver

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin

class BungeeDefaultServer: Plugin() {
    override fun onEnable() {
        ProxyServer.getInstance().pluginManager.registerCommand(this, BungeeDefaultServerCommand)
    }

    companion object {
        fun getPriorities(): List<String> {
            return ProxyServer.getInstance().config.listeners.map { it.serverPriority }.getOrNull(0)?:listOf()
        }
    }
}