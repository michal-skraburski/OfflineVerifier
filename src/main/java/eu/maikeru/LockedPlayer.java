package eu.maikeru;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class LockedPlayer {
    private final Player ply;
    private boolean locked = false;
    private final boolean wasFlying;
    private final GameMode previousGameMode;
    private final boolean wasOp;
    Entity armorStand;

    public LockedPlayer(Player player){
        this.ply = player;
        this.wasOp = player.isOp();
        this.previousGameMode = player.getGameMode();
        this.wasFlying = player.isFlying();
    }

    public void lock() {
        if (locked) {
            return;
        }
        locked = true;
        ply.setOp(false);
        ply.setGameMode(GameMode.SPECTATOR);
        ply.setAllowFlight(true);
        ply.setFlying(true);
        ply.setFlySpeed(0.0f);

        Entity AS = ply.getWorld().spawn(ply.getLocation(),EntityType.ARMOR_STAND.getEntityClass());
        AS.setGravity(false);
        AS.setInvisible(true);
        AS.setInvulnerable(true);

        ply.setSpectatorTarget(AS);
        this.armorStand = AS;
    }

    public void release() {
        if (!locked) {
            return;
        }
        locked = false;
        armorStand.remove();
        ply.setOp(wasOp);
        ply.setGameMode(previousGameMode);
        ply.setFlying(wasFlying);
        ply.setFlySpeed(0.5f);
    }
    public Player getPlayer() {
        return ply;
    }
}
