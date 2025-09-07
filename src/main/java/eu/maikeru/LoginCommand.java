package eu.maikeru;

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
            source.getSender().sendMessage(Component.text("You need to supply  argument.", NamedTextColor.RED));
        }

    }

}