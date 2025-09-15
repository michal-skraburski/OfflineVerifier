package eu.maikeru;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NullMarked;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

@NullMarked
public class LoginCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        if (args.length != 1) {
            //the password is missing
            source.getSender().sendMessage(Component.text("You need to supply an argument.", NamedTextColor.RED));
        }
        LoginManager loginMan = JavaPlugin.getPlugin(ExamplePlugin.class).getLoginManager();
        List<Login> logs = loginMan.getLogins();
        List<LockedPlayer> lockedPlayers = JavaPlugin.getPlugin(ExamplePlugin.class).getLockedPlayers();
        
        boolean pred = logs.stream().anyMatch(login -> login.getHashPass().equals(Login.hash(args[0])));

        if (!pred) {
            source.getSender().sendMessage(Component.text("Wrong password.", NamedTextColor.RED));
        }else {
            LockedPlayer player = lockedPlayers.stream()
               .filter(lply -> (lply.getPlayer().getName().equals(source.getSender().getName())))
               .findFirst()
               .orElse(null);
            
            if (player != null) {
                source.getSender().sendMessage(Component.text("Success! Correct password.", NamedTextColor.GREEN));
                Bukkit.broadcast(Component.text(player.getPlayer().getName()+" has joined the server.", NamedTextColor.YELLOW));
                player.release();
                lockedPlayers.remove(player);
            }else {
                source.getSender().sendMessage(Component.text("Success, but you're already logged in...", NamedTextColor.GOLD));
            }
            

        }
    }

}