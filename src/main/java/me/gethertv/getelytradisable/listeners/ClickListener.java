package me.gethertv.getelytradisable.listeners;

import me.gethertv.getelytradisable.GetElytraDisable;
import me.gethertv.getelytradisable.utils.ColorFixer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ClickListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();


        if(!player.hasPermission("getelytra.admin"))
            return;

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (player.getInventory().getItemInMainHand().isSimilar(GetElytraDisable.getInstance().getSelector())) {

                if (event.getClickedBlock() == null)
                    return;

                if (event.getHand() == EquipmentSlot.OFF_HAND) {
                    return;
                }
                event.setCancelled(true);
                GetElytraDisable.getInstance().setFirst(player, event.getClickedBlock().getLocation());
                player.sendMessage(ColorFixer.addColors("&aPomyslnie zaznaczono pierwszy blok."));
                return;
            }
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (player.getInventory().getItemInMainHand().isSimilar(GetElytraDisable.getInstance().getSelector())) {

                if (event.getClickedBlock() == null)
                    return;

                if (event.getHand() == EquipmentSlot.OFF_HAND) {
                    return;
                }
                event.setCancelled(true);
                GetElytraDisable.getInstance().setSecond(player, event.getClickedBlock().getLocation());
                player.sendMessage(ColorFixer.addColors("&aPomyslnie zaznaczono drugi blok."));
                return;
            }
        }
    }
}
