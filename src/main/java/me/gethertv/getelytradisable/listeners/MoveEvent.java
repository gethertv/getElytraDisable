package me.gethertv.getelytradisable.listeners;

import me.gethertv.getelytradisable.GetElytraDisable;
import me.gethertv.getelytradisable.data.Cuboid;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class MoveEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        if(player.hasPermission("getelytra.bypass"))
            return;

        for(Cuboid cuboid : GetElytraDisable.getInstance().getElytraDisableRegion())
        {
            if(cuboid.contains(player.getLocation()))
            {
                ItemStack itemStack = player.getInventory().getChestplate();
                if(itemStack!=null && itemStack.getType()== Material.ELYTRA)
                {
                    player.getInventory().setChestplate(null);
                    if(isInventoryFull(player))
                        player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                    else
                        player.getInventory().addItem(itemStack);
                }
            }
        }
    }

    public boolean isInventoryFull(Player p)
    {
        return p.getInventory().firstEmpty() == -1;
    }
}
