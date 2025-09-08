package eu.maikeru;

import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NullMarked;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

@NullMarked
public class RegisterCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        if (args.length != 1) {
            //the password is missing
            source.getSender().sendMessage(Component.text("You need to supply an argument.", NamedTextColor.RED));
        }
        LoginManager loginMan = JavaPlugin.getPlugin(ExamplePlugin.class).getLoginManager();
        List<Login> logs = loginMan.getLogins();
        
        boolean pred = logs.stream().anyMatch(login -> login.getName() == source.getExecutor().getName());

        if (!pred) {
            loginMan.addLogin(source.getExecutor().getName(), args[0]);
            source.getSender().sendMessage(Component.text("Success! You have been registered.", NamedTextColor.GREEN));
        }else {
            source.getSender().sendMessage(Component.text("You can't register more than once!", NamedTextColor.RED));
        }
    }

}
