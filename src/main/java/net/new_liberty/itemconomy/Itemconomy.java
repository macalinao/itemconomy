/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.new_liberty.itemconomy;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author simplyianm
 */
public class Itemconomy extends JavaPlugin {

    private static Itemconomy _instance;

    private Map<Material, Integer> currency = new EnumMap<Material, Integer>(Material.class);

    @Override
    public void onEnable() {
        _instance = this;

        // Our currency - TODO config
        currency.put(Material.EMERALD, 1);
        currency.put(Material.EMERALD_BLOCK, 9);
    }

    @Override
    public void onDisable() {
        _instance = null;
    }

    /**
     * Gets the Itemconomy instance.
     *
     * @return
     */
    public static Itemconomy i() {
        return _instance;
    }

    /**
     * Gets a set of all the currency.
     *
     * @return
     */
    public Set<Entry<Material, Integer>> currencySet() {
        return currency.entrySet();
    }

    /**
     * Gets the value of a certain material in this economy.
     *
     * @param m
     * @return
     */
    public int getValue(Material m) {
        return currency.get(m);
    }

}
