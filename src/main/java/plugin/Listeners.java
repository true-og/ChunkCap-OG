// This is free and unencumbered software released into the public domain.
// Author: NotAlexNoyle.
package plugin;

import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;

public class Listeners implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onHangingPlace(HangingPlaceEvent event) {

        final FileConfiguration cfg = ChunkCapOG.config();
        final Chunk chunk = event.getEntity().getLocation().getChunk();
        final List<String> exemptWorlds = cfg.getStringList("exemptWorlds");
        if (exemptWorlds.contains(chunk.getWorld().getName())) {

            return;

        }

        final int limit = cfg.getInt("perChunkLimits.hangings", 20);
        final EntityType type = event.getEntity().getType();
        int count = 0;

        for (Entity ent : chunk.getEntities()) {

            if (isHanging(ent.getType()) && (!cfg.getBoolean("splitByType", true) || ent.getType() == type)) {

                count++;

            }

        }

        if (count >= limit) {

            event.setCancelled(true);

        }

    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onVehicleCreate(VehicleCreateEvent event) {

        final FileConfiguration cfg = ChunkCapOG.config();
        final Chunk chunk = event.getVehicle().getLocation().getChunk();
        final List<String> exemptWorlds = cfg.getStringList("exemptWorlds");
        if (exemptWorlds.contains(chunk.getWorld().getName())) {

            return;

        }

        final int limit = cfg.getInt("perChunkLimits.vehicles", 10);
        final EntityType type = event.getVehicle().getType();
        int count = 0;

        for (Entity ent : chunk.getEntities()) {

            if (isVehicle(ent.getType()) && (!cfg.getBoolean("splitByType", true) || ent.getType() == type)) {

                count++;

            }

        }

        if (count >= limit) {

            event.getVehicle().remove();

        }

    }

    private static boolean isHanging(EntityType type) {

        switch (type) {

            case ITEM_FRAME:
            case GLOW_ITEM_FRAME:
            case PAINTING:
            case LEASH_HITCH:
                return true;
            default:
                return false;

        }

    }

    private static boolean isVehicle(EntityType type) {

        switch (type) {

            case BOAT:
            case CHEST_BOAT:
            case MINECART:
            case MINECART_CHEST:
            case MINECART_FURNACE:
            case MINECART_HOPPER:
            case MINECART_TNT:
            case MINECART_COMMAND:
            case MINECART_MOB_SPAWNER:
                return true;
            default:
                return false;

        }

    }

}
