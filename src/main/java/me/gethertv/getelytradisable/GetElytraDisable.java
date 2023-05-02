package me.gethertv.getelytradisable;

import me.gethertv.getelytradisable.cmd.GetElytraCmd;
import me.gethertv.getelytradisable.data.Cuboid;
import me.gethertv.getelytradisable.listeners.ClickListener;
import me.gethertv.getelytradisable.listeners.MoveEvent;
import me.gethertv.getelytradisable.utils.ColorFixer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class GetElytraDisable extends JavaPlugin {

    private static GetElytraDisable instance;

    private List<Cuboid> elytraDisableRegion = new ArrayList<>();
    private ItemStack selector;
    private HashMap<UUID, Location> firstData = new HashMap<>();
    private HashMap<UUID, Location> secondData = new HashMap<>();
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();

        loadItemSelector();
        loadRegions();

        getCommand("getelytra").setExecutor(new GetElytraCmd());
        getCommand("getelytra").setTabCompleter(new GetElytraCmd());

        getServer().getPluginManager().registerEvents(new MoveEvent(), this);
        getServer().getPluginManager().registerEvents(new ClickListener(), this);

    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll(this);
    }

    private void loadItemSelector() {
        selector = new ItemStack(Material.STICK);
        ItemMeta itemMeta = selector.getItemMeta();
        itemMeta.setDisplayName(ColorFixer.addColors("&c&l# Selector #"));
        selector.setItemMeta(itemMeta);
    }

    public void reloadRegions()
    {
        elytraDisableRegion.clear();
        loadRegions();
    }
    private void loadRegions() {
        if(!getConfig().isSet("regions"))
            return;

        for(String key : getConfig().getConfigurationSection("regions").getKeys(false))
        {
            elytraDisableRegion.add(new Cuboid(
                    getConfig().getLocation("regions."+key+".first"),
                    getConfig().getLocation("regions."+key+".second")
                    ));
        }
    }

    public List<Cuboid> getElytraDisableRegion() {
        return elytraDisableRegion;
    }

    public static GetElytraDisable getInstance() {
        return instance;
    }

    public HashMap<UUID, Location> getFirstData() {
        return firstData;
    }

    public HashMap<UUID, Location> getSecondData() {
        return secondData;
    }
    public void setFirst(Player player, Location location)
    {
        firstData.put(player.getUniqueId(), location);
    }
    public void setSecond(Player player, Location location)
    {
        secondData.put(player.getUniqueId(), location);
    }

    public ItemStack getSelector() {
        return selector;
    }
}
