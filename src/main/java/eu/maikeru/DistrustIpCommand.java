package eu.maikeru;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NullMarked;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

@NullMarked
public class DistrustIpCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        LoginManager loginMan = JavaPlugin.getPlugin(ExamplePlugin.class).getLoginManager();
        List<LockedPlayer> lockedPlayers = JavaPlugin.getPlugin(ExamplePlugin.class).getLockedPlayers();
        List<Login> logs = loginMan.getLogins();
        Player player;
        
        if (source.getExecutor() instanceof Player player1) {
            player = player1;
        }else {
            source.getExecutor().sendMessage(Component.text("Only players may execute this command.", NamedTextColor.RED));
            return;
        }

        Login pred = logs.stream()
                .filter(login -> login.getName().equals(source.getSender().getName()))
                .findFirst()
                .orElse(null);
        LockedPlayer lply = lockedPlayers.stream()
                .filter(ply -> ply.getPlayer().getName().equals(player.getName()))
                .findFirst()
                .orElse(null);

        if (pred != null && lply == null) {
                if (pred.getIpsCopy().contains(player.getAddress().getHostString())) {
                    pred.distrustIp(player.getAddress().getHostString());
                    source.getSender().sendMessage(Component.text("Success! You have distrusted this ip address.", NamedTextColor.GREEN));
                }else {
                    source.getExecutor().sendMessage(Component.text("Your ip is distrusted already!", NamedTextColor.RED));
                }
        }else {
            source.getSender().sendMessage(Component.text("You need to register and login first!", NamedTextColor.RED));
        }
    }

}
