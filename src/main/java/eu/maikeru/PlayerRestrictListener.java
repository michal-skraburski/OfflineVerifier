package eu.maikeru;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;


public class PlayerRestrictListener implements Listener {

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event){
       Player ply = event.getPlayer();
       String name = ply.getName();
       LoginManager loginMan = JavaPlugin.getPlugin(ExamplePlugin.class).getLoginManager();
       List<LockedPlayer> lockedPlayers = JavaPlugin.getPlugin(ExamplePlugin.class).getLockedPlayers();
       LockedPlayer lply = new LockedPlayer(ply);
       lply.lock();
       lockedPlayers.add(lply);
       event.setJoinMessage(null);

       if (loginMan.loginExistsFor(name)) {
            ply.sendMessage(Component.text("Use /login <password> to log into the server.", NamedTextColor.GOLD));
       }else {
            ply.sendMessage(Component.text("Use /register <password> to create a login!", NamedTextColor.RED));
       }
    }

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent event) {
          Player ply = event.getPlayer();
          List<LockedPlayer> lockedPlayers = JavaPlugin.getPlugin(ExamplePlugin.class).getLockedPlayers();

          LockedPlayer pred = lockedPlayers.stream()
               .filter(lply -> (lply.getPlayer().getName().equals(ply.getName())))
               .findFirst()
               .orElse(null);

          if (pred != null) {
               lockedPlayers.remove(pred);
               pred.release();
          }
    }

    @EventHandler
    public void PlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
          Player ply = event.getPlayer();
          List<LockedPlayer> lockedPlayers = JavaPlugin.getPlugin(ExamplePlugin.class).getLockedPlayers();

          LockedPlayer pred = lockedPlayers.stream()
               .filter(lply -> (lply.getPlayer().getName().equals(ply.getName())))
               .findFirst()
               .orElse(null);

          if (pred != null) {
               event.setCancelled(true);
          }
    }
    
}

