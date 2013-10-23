/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.new_liberty.itemconomy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author simplyianm
 */
public class ICInventory {

    private final Inventory i;

    public ICInventory(Player p) {
        this.i = p.getInventory();
    }

    /**
     * Gets this player's balance.
     *
     * @return
     */
    public int balance() {
        int sum = 0;
        for (ItemStack s : stacks()) {
            // Check for null itemstack
            if (s == null) {
                continue;
            }

            sum += Itemconomy.i().getValue(s.getType()) * s.getAmount();
        }
        return sum;
    }

    /**
     * Gets all pertinent ItemStacks.
     *
     * @return
     */
    public List<ItemStack> stacks() {
        List<ItemStack> r = new ArrayList<ItemStack>();
        for (ItemStack s : i.getContents()) {
            // Check for null itemstack
            if (s == null) {
                continue;
            }

            for (Entry<Material, Integer> e : Itemconomy.i().currencySet()) {
                if (s.getType() == e.getKey()) {
                    r.add(s);
                }
            }
        }
        return r;
    }

    /**
     * Removes an amount of currency from this inventory.
     *
     * @param amt
     */
    public void remove(int amt) {
        // Sort using a tree map
        SortedMap<Integer, Material> s = new TreeMap<Integer, Material>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }

        });

        // Place elements to sort
        for (Entry<Material, Integer> e : Itemconomy.i().currencySet()) {
            s.put(e.getValue(), e.getKey());
        }

        // Iterate backwards -- get rid of the blocks first
        for (Entry<Integer, Material> e : s.entrySet()) {
            int removed = amt / e.getKey();
            int surplus = removed;

            ItemStack[] contents = i.getContents();
            for (int a = 0; a < contents.length; a++) {
                ItemStack t = contents[a];

                if (surplus == 0) {
                    break;
                }

                // Check for null itemstack
                if (t == null) {
                    continue;
                }

                if (t.getType() == e.getValue()) {
                    // Find amt to remove
                    int rem = t.getAmount();
                    if (rem > surplus) {
                        rem = surplus;
                    }

                    // Remove the item
                    if (rem == 0) {
                        i.clear(a);
                    } else {
                        t.setAmount(t.getAmount() - rem);
                    }

                    surplus -= rem;
                }
            }

            // Recalculate amt of items removed
            removed -= surplus;

            amt -= removed * e.getKey();
        }
    }

}
