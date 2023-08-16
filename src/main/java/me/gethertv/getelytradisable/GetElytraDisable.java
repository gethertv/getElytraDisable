package me.gethertv.getelytradisable;

import me.gethertv.getelytradisable.cmd.GetElytraCmd;
import me.gethertv.getelytradisable.data.ArmorType;
import me.gethertv.getelytradisable.data.Cuboid;
import me.gethertv.getelytradisable.data.ElytraLevelType;
import me.gethertv.getelytradisable.data.TakeOffData;
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

    private boolean elytraLevelUse = false;
    private int elytraLevelHeight = 0;
    private ElytraLevelType elytraLevelType;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();

        loadItemSelector();
        loadRegions();
        loadDisableElytraLevel();

        getCommand("getelytra").setExecutor(new GetElytraCmd());
        getCommand("getelytra").setTabCompleter(new GetElytraCmd());

        getServer().getPluginManager().registerEvents(new MoveEvent(this), this);
        getServer().getPluginManager().registerEvents(new ClickListener(), this);

    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll(this);
    }

    private void loadDisableElytraLevel()
    {
        elytraLevelUse = getConfig().getBoolean("elytra-level.enable");
        elytraLevelHeight = getConfig().getInt("elytra-level.y");
        elytraLevelType = ElytraLevelType.valueOf(getConfig().getString("elytra-level.type").toUpperCase());

    }

    private void loadItemSelector() {
        selector = new ItemStack(Material.STICK);
        ItemMeta itemMeta = selector.getItemMeta();
        itemMeta.setDisplayName(ColorFixer.addColors("&c&l# Selector #"));
        selector.setItemMeta(itemMeta);
    }

    public void reloadRegions()
    {
        reloadConfig();
        elytraDisableRegion.clear();
        loadRegions();
    }
    private void loadRegions() {
        if(!getConfig().isSet("regions"))
            return;

        for(String key : getConfig().getConfigurationSection("regions").getKeys(false))
        {
            List<TakeOffData> takeOffData = new ArrayList<>();

            // HELMET
            {
                List<Material> materials = new ArrayList<>();
                getConfig().getStringList("regions." + key + ".take-off.helmet.material").forEach(nameMaterial -> {
                    materials.add(Material.valueOf(nameMaterial.toUpperCase()));
                });

                takeOffData.add(new TakeOffData(
                        getConfig().getBoolean("regions." + key + ".take-off.helmet.enable"),
                        ArmorType.HELMET,
                        new ArrayList<>(materials),
                        getConfig().getString("regions." + key + ".take-off.helmet.message")
                ));
            }
            // CHESTPLATE
            {
                List<Material> materials = new ArrayList<>();
                getConfig().getStringList("regions." + key + ".take-off.chestplate.material").forEach(nameMaterial -> {
                    materials.add(Material.valueOf(nameMaterial.toUpperCase()));
                });

                takeOffData.add(new TakeOffData(
                        getConfig().getBoolean("regions." + key + ".take-off.chestplate.enable"),
                        ArmorType.CHESTPLATE,
                        new ArrayList<>(materials),
                        getConfig().getString("regions." + key + ".take-off.chestplate.message")
                ));
            }
            // LEGGINGS
            {
                List<Material> materials = new ArrayList<>();
                getConfig().getStringList("regions." + key + ".take-off.leggings.material").forEach(nameMaterial -> {
                    materials.add(Material.valueOf(nameMaterial.toUpperCase()));
                });

                takeOffData.add(new TakeOffData(
                        getConfig().getBoolean("regions." + key + ".take-off.leggings.enable"),
                        ArmorType.LEGGINGS,
                        new ArrayList<>(materials),
                        getConfig().getString("regions." + key + ".take-off.leggings.message")
                ));
            }
            // BOOTS
            {
                List<Material> materials = new ArrayList<>();
                getConfig().getStringList("regions." + key + ".take-off.boots.material").forEach(nameMaterial -> {
                    materials.add(Material.valueOf(nameMaterial.toUpperCase()));
                });

                takeOffData.add(new TakeOffData(
                        getConfig().getBoolean("regions." + key + ".take-off.boots.enable"),
                        ArmorType.BOOTS,
                        new ArrayList<>(materials),
                        getConfig().getString("regions." + key + ".take-off.boots.message")
                ));
            }

            elytraDisableRegion.add(new Cuboid(
                    getConfig().getLocation("regions."+key+".first"),
                    getConfig().getLocation("regions."+key+".second"),
                    takeOffData
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

    public ElytraLevelType getElytraLevelType() {
        return elytraLevelType;
    }

    public int getElytraLevelHeight() {
        return elytraLevelHeight;
    }

    public boolean isElytraLevelUse() {
        return elytraLevelUse;
    }

    public ItemStack getSelector() {
        return selector;
    }
}
