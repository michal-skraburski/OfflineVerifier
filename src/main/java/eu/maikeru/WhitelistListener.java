package eu.maikeru;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import net.kyori.adventure.text.Component;

public class WhitelistListener implements Listener {

    @EventHandler
    public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event){
        Set<OfflinePlayer> players = Bukkit.getWhitelistedPlayers();
        players.forEach(action -> {
            System.out.println(action.getName());
        });
        boolean flag = players.stream().anyMatch(player -> player.getName() == event.getName());
        if (flag) {
            event.allow();
        }else {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Component.text("You are not whitelisted!"));
        }

    }
    
}
