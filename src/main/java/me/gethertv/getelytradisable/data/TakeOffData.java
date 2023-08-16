package me.gethertv.getelytradisable.data;

import org.bukkit.Material;

import java.util.List;

public class TakeOffData {

    private boolean enable;
    private ArmorType type;
    private List<Material> materials;

    private String message;

    public TakeOffData(boolean enable, ArmorType type, List<Material> materials, String message) {
        this.enable = enable;
        this.type = type;
        this.materials = materials;
        this.message = message;
    }

    public ArmorType getType() {
        return type;
    }

    public boolean isEnable() {
        return enable;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public String getMessage() {
        return message;
    }
}
