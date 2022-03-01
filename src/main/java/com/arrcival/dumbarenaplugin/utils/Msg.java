package com.arrcival.dumbarenaplugin.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Msg {

    public static void send(CommandSender sender, String message) {
        send(sender, message, Consts.CHAT_PREFIX);
    }

    public static void send(CommandSender sender, String message, String prefix) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
    }

    public static String color(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
