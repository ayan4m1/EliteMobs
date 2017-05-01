/*
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.magmaguy.elitemobs.elitedrops;

import com.magmaguy.elitemobs.EliteMobs;
import com.magmaguy.elitemobs.MetadataHandler;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.bukkit.Bukkit.getLogger;

/**
 * Created by MagmaGuy on 29/11/2016.
 */
public class EliteDropsHandler implements Listener {

    public static List<ItemStack> lootList = new ArrayList();
    public static HashMap<ItemStack, List<PotionEffect>> potionEffectItemList = new HashMap();
    private EliteMobs plugin;

    public EliteDropsHandler(Plugin plugin) {

        this.plugin = (EliteMobs) plugin;

    }

    public void superDropParser() {

        List<String> lootCount = lootCounter();

        for (String lootEntry : lootCount) {

            StringBuilder path = new StringBuilder();

            path.append(lootEntry);

            String previousPath = path.toString();

            String itemType = itemTypeHandler(previousPath);
            String itemName = itemNameHandler(previousPath);
            List itemLore = itemLoreHandler(previousPath);
            List itemEnchantments = itemEnchantmentHandler(previousPath);
            List potionEffects = itemPotionEffectHandler(previousPath);

            ItemStack itemStack = new ItemStack(Material.getMaterial(itemType), 1);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(itemName);
            itemMeta.setLore(itemLore);

            if (itemEnchantments != null) {

                for (Object object : itemEnchantments) {

                    String string = object.toString();

                    String[] parsedString = string.split(",");

                    String enchantmentName = parsedString[0];
                    Enchantment enchantmentType = Enchantment.getByName(enchantmentName);

                    int enchantmentLevel = Integer.parseInt(parsedString[1]);

                    itemMeta.addEnchant(enchantmentType, enchantmentLevel, true);

                }

            }

            itemStack.setItemMeta(itemMeta);

            lootList.add(itemStack);

            List<PotionEffect> parsedPotionEffect = new ArrayList();

            //Add potion effects to a separate list to reduce i/o operations
            if (potionEffects != null) {

                for (Object object : potionEffects) {

                    String string = object.toString();

                    String[] parsedString = string.split(",");

                    String potionEffectTypeString = parsedString[0];
                    PotionEffectType potionEffectType = PotionEffectType.getByName(potionEffectTypeString);

                    //this is a really bad way of doing things, two wrongs make a right
                    if (parsedString.length % 2 != 0) {

                        getLogger().info("Your item " + itemName + " has a problematic potions effect entry.");

                    }

                    int potionEffectAmplifier = Integer.parseInt(parsedString[1]);

                    PotionEffect potionEffect = new PotionEffect(potionEffectType, 40, potionEffectAmplifier);

                    parsedPotionEffect.add(potionEffect);

                }

                potionEffectItemList.put(itemStack, parsedPotionEffect);

            }

        }

    }


    public List<String> lootCounter() {

        List<String> lootCount = new ArrayList();

        for (String configIterator : plugin.getCustomConfig().getKeys(true)) {

            int dotCount = 0;

            if (configIterator.contains("Loot.")) {

                for (int i = 0; i < configIterator.length(); i++) {

                    if (configIterator.charAt(i) == '.') {

                        dotCount++;

                    }

                }

                if (dotCount == 1) {

                    lootCount.add(configIterator);


                }

            }

        }

        return lootCount;

    }

    public String automatedStringBuilder(String previousPath, String append) {

        StringBuilder automatedStringBuilder = new StringBuilder();

        automatedStringBuilder.append(previousPath);
        automatedStringBuilder.append(".");
        automatedStringBuilder.append(append);

        String path = automatedStringBuilder.toString();

        return path;

    }

    public String itemTypeHandler(String previousPath) {

        String path = automatedStringBuilder(previousPath, "Item Type");

        String itemType = plugin.getCustomConfig().getString(path);

        return itemType;

    }

    public String itemNameHandler(String previousPath) {

        String path = automatedStringBuilder(previousPath, "Item Name");

        String itemName = plugin.getCustomConfig().getString(path);

        return itemName;

    }

    public List itemLoreHandler(String previousPath) {

        String path = automatedStringBuilder(previousPath, "Item Lore");

        List itemLore = plugin.getCustomConfig().getList(path);

        return itemLore;

    }

    public List itemEnchantmentHandler(String previousPath) {

        String path = automatedStringBuilder(previousPath, "Enchantments");

        List enchantments = plugin.getCustomConfig().getList(path);

        return enchantments;

    }

    public List itemPotionEffectHandler(String previousPath) {

        String path = automatedStringBuilder(previousPath, "Potion Effects");

        List potionEffects = plugin.getCustomConfig().getList(path);

        return potionEffects;

    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {

        Entity entity = event.getEntity();

        Random random = new Random();

        if (entity.hasMetadata(MetadataHandler.NATURAL_MOB_MD) &&
                entity.hasMetadata(MetadataHandler.ELITE_MOB_MD) &&
                entity.getMetadata(MetadataHandler.ELITE_MOB_MD).get(0).asInt() > 4) {


            if (random.nextDouble() < plugin.getConfig().getDouble("Aggressive EliteMobs flat loot drop rate %") / 100) {

                int randomDrop = random.nextInt(lootList.size());

                entity.getWorld().dropItem(entity.getLocation(), lootList.get(randomDrop));

            }

            //double drops
            if (plugin.getConfig().getBoolean("Aggressive EliteMobs can drop additional loot with drop % based on EliteMob level (higher is more likely)") &&
                    random.nextDouble() < (entity.getMetadata(MetadataHandler.ELITE_MOB_MD).get(0).asInt() / 100)) {

                int randomDrop = random.nextInt(lootList.size());

                entity.getWorld().dropItem(entity.getLocation(), lootList.get(randomDrop));

            }

            entity.removeMetadata(MetadataHandler.ELITE_MOB_MD, plugin);

            if (entity.hasMetadata(MetadataHandler.NATURAL_MOB_MD)) {

                entity.removeMetadata(MetadataHandler.NATURAL_MOB_MD, plugin);

            }

        }

    }

}
