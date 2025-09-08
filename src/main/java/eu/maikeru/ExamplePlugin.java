package eu.maikeru;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

public class ExamplePlugin extends JavaPlugin implements Listener {
  private ComponentLogger logger;
  private LoginManager loginMan;
  private List<LockedPlayer> lockedPlayers = new ArrayList<>();

  @Override
  public void onEnable() {
    logger = this.getComponentLogger();
    loginMan = new LoginManager();
    Bukkit.getPluginManager().registerEvents(this, this);
    Bukkit.getPluginManager().registerEvents(new PlayerRestrictListener(), this);
    Bukkit.getPluginManager().registerEvents(new WhitelistListener(), this);
    registerCommand("register", new RegisterCommand());
    registerCommand("login", new LoginCommand());
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));
  }

  public ComponentLogger getLog(){
    return logger;
  }

  public LoginManager getLoginManager () {
    return loginMan;
  }

    public List<LockedPlayer> getLockedPlayers() {
        return lockedPlayers;
    }
}