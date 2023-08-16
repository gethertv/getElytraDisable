package me.gethertv.getelytradisable.data;
import me.gethertv.getelytradisable.listeners.MoveEvent;
import me.gethertv.getelytradisable.utils.ColorFixer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class Cuboid {

    private final World world;
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int minZ;
    private int maxZ;

    private Location first;
    private Location second;

    private List<TakeOffData> takeOffData;

    public Cuboid(Location loc1, Location loc2, List<TakeOffData> takeOffData) {
        this(loc1.getWorld(), loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
        this.first = loc1;
        this.second = loc2;
        this.takeOffData = takeOffData;
    }

    public Cuboid(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.world = world;

        minX = Math.min(x1, x2);
        minY = Math.min(y1, y2);
        minZ = Math.min(z1, z2);
        maxX = Math.max(x1, x2);
        maxY = Math.max(y1, y2);
        maxZ = Math.max(z1, z2);
    }

    public void checkTakeOff(Player player)
    {
        for(TakeOffData data : takeOffData)
        {
            if(!data.isEnable())
                continue;

            boolean any = false;
            for(Material material : data.getMaterials())
            {
                if(hasMaterial(player, data.getType(), material))
                {
                    takeOffItem(player, data.getType());
                    any = true;
                }
            }
            if(any)
                if(!data.getMessage().equals("NONE"))
                    player.sendMessage(ColorFixer.addColors(data.getMessage()));
        }
    }

    public void takeOffItem(Player player, ArmorType type)
    {
        ItemStack itemStack = null;
        if(type==ArmorType.HELMET)
        {
            itemStack = player.getInventory().getHelmet();
            player.getInventory().setHelmet(null);
        }
        if(type==ArmorType.CHESTPLATE)
        {
            itemStack = player.getInventory().getChestplate();
            player.getInventory().setChestplate(null);
        }
        if(type==ArmorType.LEGGINGS)
        {
            itemStack = player.getInventory().getLeggings();
            player.getInventory().setLeggings(null);
        }
        if(type==ArmorType.BOOTS)
        {
            itemStack = player.getInventory().getBoots();
            player.getInventory().setBoots(null);
        }

        if(itemStack!=null)
        {
            if(MoveEvent.isInventoryFull(player))
                player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            else
                player.getInventory().addItem(itemStack);
        }

    }

    public boolean hasMaterial(Player player, ArmorType armorType, Material material)
    {
        ItemStack itemStack = null;
        if(armorType==ArmorType.HELMET)
            itemStack = player.getInventory().getHelmet();

        if(armorType==ArmorType.CHESTPLATE)
            itemStack = player.getInventory().getChestplate();

        if(armorType==ArmorType.LEGGINGS)
            itemStack = player.getInventory().getLeggings();

        if(armorType==ArmorType.BOOTS)
            itemStack = player.getInventory().getBoots();

        if(itemStack!=null && itemStack.getType()==material)
            return true;

        return false;
    }
    public void clearCuboid()
    {
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    if(world.getBlockAt(x, y, z).getType()!= Material.AIR)
                        world.getBlockAt(x, y, z).setType(Material.AIR);
                }
            }
        }
    }
    public World getWorld() {
        return world;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public boolean contains(Cuboid cuboid) {
        return cuboid.getWorld().equals(world) &&
                cuboid.getMinX() >= minX && cuboid.getMaxX() <= maxX &&
                cuboid.getMinY() >= minY && cuboid.getMaxY() <= maxY &&
                cuboid.getMinZ() >= minZ && cuboid.getMaxZ() <= maxZ;
    }

    public boolean contains(Location location) {
        if(location.getWorld().getName().equals(world.getName())) {
            return contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }
        return false;
    }

    public boolean contains(int x, int y, int z) {
        return x >= minX && x <= maxX &&
                y >= minY && y <= maxY &&
                z >= minZ && z <= maxZ;
    }

    public boolean overlaps(Cuboid cuboid) {
        return cuboid.getWorld().equals(world) &&
                !(cuboid.getMinX() > maxX || cuboid.getMinY() > maxY || cuboid.getMinZ() > maxZ ||
                        minZ > cuboid.getMaxX() || minY > cuboid.getMaxY() || minZ > cuboid.getMaxZ());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Cuboid)) {
            return false;
        }
        final Cuboid other = (Cuboid) obj;
        return world.equals(other.world)
                && minX == other.minX
                && minY == other.minY
                && minZ == other.minZ
                && maxX == other.maxX
                && maxY == other.maxY
                && maxZ == other.maxZ;
    }

    @Override
    public String toString() {
        return "Cuboid[world:" + world.getName() +
                ", minX:" + minX +
                ", minY:" + minY +
                ", minZ:" + minZ +
                ", maxX:" + maxX +
                ", maxY:" + maxY +
                ", maxZ:" + maxZ + "]";
    }

    public List<TakeOffData> getTakeOffData() {
        return takeOffData;
    }

    public Location getSecond() {
        return second;
    }

    public Location getFirst() {
        return first;
    }
}
