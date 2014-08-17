package com.merikmc.chat;


import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * created by codermason on 8/17/14
 */
public class MErikChat extends JavaPlugin implements Listener {

    private String CHAT_FORMAT = "(PREFIX)(NAME) &8» &7(MESSAGE)";
    private Chat chat;

    public void onEnable() {

        if(!setupChat()) {
            Bukkit.getLogger().severe("Could not hook into Vault! Shutting down...");
            return;
        }

        this.getServer().getPluginManager().registerEvents(this, this);

    }

    public void onDisable() {}

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }
        return (chat != null);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String temp = CHAT_FORMAT.replace("(PREFIX)", chat.getPlayerPrefix(e.getPlayer()));
        temp = temp.replace("(NAME)", e.getPlayer().getName());
        temp = temp.replace("(MESSAGE)", e.getMessage());
        e.setFormat(ChatColor.translateAlternateColorCodes('&', temp));
    }

}
