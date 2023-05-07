package me.gethertv.getelytradisable.listeners;

import me.gethertv.getelytradisable.GetElytraDisable;
import me.gethertv.getelytradisable.data.Cuboid;
import me.gethertv.getelytradisable.data.ElytraLevelType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class MoveEvent implements Listener {

    GetElytraDisable plugin;

    public MoveEvent(GetElytraDisable plugin)
    {
        this.plugin = plugin;
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        if(player.hasPermission("getelytra.bypass"))
            return;

        if(plugin.isElytraLevelUse())
        {
            if(plugin.getElytraLevelType()== ElytraLevelType.ABOVE)
            {
                if(player.getLocation().getY()>plugin.getElytraLevelHeight()) {
                    if (hasElytra(player))
                        takeoffElytra(player);

                }
                return;
            }
            if(plugin.getElytraLevelType()== ElytraLevelType.BELOW)
            {
                if(player.getLocation().getY()<plugin.getElytraLevelHeight())
                {
                    if(hasElytra(player))
                        takeoffElytra(player);

                }
                return;
            }
        }

        for(Cuboid cuboid : plugin.getElytraDisableRegion())
        {
            if(cuboid.contains(player.getLocation()))
            {

                if(!hasElytra(player))
                    return;

                takeoffElytra(player);
                return;

            }
        }
    }

    public void takeoffElytra(Player player)
    {
        ItemStack itemStack = player.getInventory().getChestplate();
        player.getInventory().setChestplate(null);
        if(isInventoryFull(player))
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
        else
            player.getInventory().addItem(itemStack);
    }
    public boolean hasElytra(Player player)
    {
        ItemStack itemStack = player.getInventory().getChestplate();
        if(itemStack!=null && itemStack.getType()== Material.ELYTRA)
        {
            return true;
        }
        return false;
    }

    public boolean isInventoryFull(Player p)
    {
        return p.getInventory().firstEmpty() == -1;
    }
}
