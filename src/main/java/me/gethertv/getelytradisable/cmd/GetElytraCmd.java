package me.gethertv.getelytradisable.cmd;

import me.gethertv.getelytradisable.GetElytraDisable;
import me.gethertv.getelytradisable.data.Cuboid;
import me.gethertv.getelytradisable.data.TakeOffData;
import me.gethertv.getelytradisable.utils.ColorFixer;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GetElytraCmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("getelytra.admin"))
            return false;

        if(args.length==1)
        {
            if(args[0].equalsIgnoreCase("selector"))
            {
                if(sender instanceof Player)
                {
                    ((Player) sender).getInventory().addItem(GetElytraDisable.getInstance().getSelector().clone());
                    sender.sendMessage(ColorFixer.addColors("&aPomyslnie nadano selector!"));
                    return true;
                }
            }
            if(args[0].equalsIgnoreCase("reload"))
            {
                GetElytraDisable.getInstance().reloadRegions();
                sender.sendMessage(ColorFixer.addColors("&aPomyslnie przeladowano plugin!"));
                return true;
            }
        }
        if(args.length==2)
        {
            if(!(sender instanceof Player))
                return false;

            Player player = (Player) sender;
            if(args[0].equalsIgnoreCase("create"))
            {
                String name = args[1].toLowerCase();
                Location first = GetElytraDisable.getInstance().getFirstData().get(player.getUniqueId());
                Location second = GetElytraDisable.getInstance().getSecondData().get(player.getUniqueId());
                if(first == null || second == null)
                {
                    player.sendMessage(ColorFixer.addColors("&cMusisz zaznaczyc dwie lokalizacje!"));
                    return false;
                }
                FileConfiguration config = GetElytraDisable.getInstance().getConfig();
                config.set("regions."+name+".first", first);
                config.set("regions."+name+".second", second);
                config.set("regions."+name+".take-off.helmet.enable", false);
                config.set("regions."+name+".take-off.helmet.material", new ArrayList<>());
                config.set("regions."+name+".take-off.helmet.message", "&cNie mozesz tego tutaj uzywac!");
                config.set("regions."+name+".take-off.chestplate.enable", false);
                config.set("regions."+name+".take-off.chestplate.material", new ArrayList<>());
                config.set("regions."+name+".take-off.chestplate.message", "&cNie mozesz tego tutaj uzywac!");
                config.set("regions."+name+".take-off.leggings.enable", false);
                config.set("regions."+name+".take-off.leggings.material", new ArrayList<>());
                config.set("regions."+name+".take-off.leggings.message", "&cNie mozesz tego tutaj uzywac!");
                config.set("regions."+name+".take-off.boots.enable", false);
                config.set("regions."+name+".take-off.boots.material", new ArrayList<>());
                config.set("regions."+name+".take-off.boots.message", "&cNie mozesz tego tutaj uzywac!");

                GetElytraDisable.getInstance().saveConfig();
                player.sendMessage(ColorFixer.addColors("&aPomyslnie stworzono region! Ustaw w configu przedmioty oraz wpisz /getelytra reload"));
                return true;
            }
            if(args[0].equalsIgnoreCase("remove"))
            {
                String name = args[1].toLowerCase();
                FileConfiguration config = GetElytraDisable.getInstance().getConfig();
                if(config.isSet("regions."+name))
                {
                    Location first = config.getLocation("regions."+name+".first");
                    Location second = config.getLocation("regions."+name+".second");
                    Cuboid removeCuboid = null;
                    for(Cuboid cuboid : GetElytraDisable.getInstance().getElytraDisableRegion())
                    {
                        if(cuboid.getFirst().equals(first) && cuboid.getSecond().equals(second))
                            removeCuboid = cuboid;
                    }
                    if(removeCuboid!=null)
                    {
                        GetElytraDisable.getInstance().getElytraDisableRegion().remove(removeCuboid);
                        player.sendMessage(ColorFixer.addColors("&aPomyslnie usunieto cuboid!"));
                        config.set("regions."+name, null);
                        GetElytraDisable.getInstance().saveConfig();
                        return true;
                    }
                } else {
                    player.sendMessage(ColorFixer.addColors("&cPodany region nie istnieje!"));
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length==1)
        {
            List<String> ar = new ArrayList<>();
            ar.add("selector");
            ar.add("reload");
            ar.add("create");
            ar.add("remove");
            return ar;
        }
        return null;
    }
}
