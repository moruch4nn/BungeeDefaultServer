package dev.mr3n.bungeedeafultserver

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor

object BungeeDefaultServerCommand: Command("bungeedefaultserver", "bungeedefaultserver.admin", "bds", "defaultserver"), TabExecutor {

    init {

    }

    override fun execute(sender: CommandSender, args: Array<out String>) {
        try {
            when(args.getOrNull(0)) {
                "set" -> {
                    val serverStr = args.getOrNull(1)?: throw Exception("サーバーを設定してください: /bds set <サーバー名> (全員を転送:true,false)")
                    val sendAll = args.getOrNull(2)?.toBooleanStrictOrNull()?:false
                    val server = ProxyServer.getInstance().servers[serverStr]?:throw Exception("指定されたサーバーが見つかりません(${serverStr})")
                    ProxyServer.getInstance().config.listeners.forEach { listener -> listener.serverPriority[0] = server.name }
                    sender.sendMessage(*TextComponent.fromLegacyText("${ChatColor.GREEN}新しくデフォルトサーバーを設定しました: $${server.name}"))
                    if(sendAll) { ProxyServer.getInstance().players.forEach { player -> player.connect(server) } }
                }
                "now" -> {
                    val defaultServer = BungeeDefaultServer.getPriorities().getOrNull(0)?:throw Exception("リスナーが見つかりません。")
                    sender.sendMessage(*TextComponent.fromLegacyText("${ChatColor.GREEN}現在のデフォルトのサーバー: $defaultServer"))
                }
            }
        } catch(e: Exception) {
            sender.sendMessage(*TextComponent.fromLegacyText("${ChatColor.RED}${e.message}"))
        }
    }

    override fun onTabComplete(sender: CommandSender, args: Array<out String>): MutableIterable<String> {
        when (args.size) {
            1 -> {
                return mutableListOf("set", "now")
            }
            2 -> {
                when(args[0]) {
                    "set" -> {
                        return ProxyServer.getInstance().servers.keys
                    }
                }
            }
            3 -> {
                when(args[0]) {
                    "set" -> {
                        return mutableListOf("true", "false")
                    }
                }
            }
        }
        return mutableListOf()
    }
}