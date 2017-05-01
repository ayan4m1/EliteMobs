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

package com.magmaguy.elitemobs;

import com.magmaguy.elitemobs.mobscanner.ValidAgressiveMobFilter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MagmaGuy on 26/04/2017.
 */
public class MetadataHandler {

    //if you're wondering why I thought this was needed, it's because I got tired of finding and replacing
    //individual metadata strings and stuff breaking due to it

    //plugin name
    public final static String ELITE_MOBS = "EliteMobs";
    //plugin metadata
    public final static String ELITE_MOB_MD = "EliteMob";
    public final static String PASSIVE_ELITE_MOB_MD = "PassiveEliteMob";
    public final static String NATURAL_MOB_MD = "NaturalMob";
    public final static String MINOR_POWER_AMOUNT_MD = "MinorPowerAmount";
    public final static String VISUAL_EFFECT_MD = "VisualEffect";
    public final static String FORBIDDEN_MD = "Forbidden";
    public final static String CUSTOM_MD = "Custom";
    //Powers
    public final static String ATTACK_BLINDING_MD = "AttackBlinding";
    public final static String ATTACK_CONFUSING_MD = "AttackConfusing";
    public final static String ATTACK_FIRE_MD = "AttackFire";
    public final static String ATTACK_FREEZE_MD = "AttackFreeze";
    public final static String ATTACK_GRAVITY_MD = "AttackGravity";
    public final static String ATTACK_POISON_MD = "AttackPoison";
    public final static String ATTACK_PUSH_MD = "AttackPush";
    public final static String ATTACK_WEB_MD = "AttackWeb";
    public final static String ATTACK_WITHER_MD = "AttackWither";
    public final static String BONUS_LOOT_MD = "BonusLoot";
    public final static String INVULNERABILITY_ARROW_MD = "InvulnerabilityArrow";
    public final static String INVULNERABILITY_FALL_DAMAGE_MD = "InvulnerabilityFallDamage";
    public final static String INVULNERABILITY_FIRE_MD = "InvulnerabilityFire";
    public final static String INVULNERABILITY_KNOCKBACK_MD = "InvulnerabilityKnockback";
    public final static String MOVEMENT_SPEED_MD = "MovementSpeed";
    public final static String INVISIBILITY_MD = "Invisibility";

    //powerEffects
    public final static String FROZEN = "Frozen";
    public final static String FROZEN_COOLDOWN = "FrozenCooldown";

    Plugin plugin = Bukkit.getPluginManager().getPlugin(ELITE_MOBS);

    public List<String> metadataList() {

        List<String> metadataList = new ArrayList<>();

        metadataList.add(ELITE_MOB_MD);
        metadataList.add(PASSIVE_ELITE_MOB_MD);
        metadataList.add(NATURAL_MOB_MD);
        metadataList.add(MINOR_POWER_AMOUNT_MD);
        metadataList.add(VISUAL_EFFECT_MD);
        metadataList.add(FORBIDDEN_MD);
        metadataList.add(ATTACK_CONFUSING_MD);
        metadataList.add(ATTACK_FIRE_MD);
        metadataList.add(ATTACK_FREEZE_MD);
        metadataList.add(ATTACK_GRAVITY_MD);
        metadataList.add(ATTACK_POISON_MD);
        metadataList.add(ATTACK_PUSH_MD);
        metadataList.add(ATTACK_WEB_MD);
        metadataList.add(ATTACK_WITHER_MD);
        metadataList.add(BONUS_LOOT_MD);
        metadataList.add(INVULNERABILITY_ARROW_MD);
        metadataList.add(INVULNERABILITY_FALL_DAMAGE_MD);
        metadataList.add(INVULNERABILITY_FIRE_MD);
        metadataList.add(INVULNERABILITY_KNOCKBACK_MD);
        metadataList.add(MOVEMENT_SPEED_MD);
        metadataList.add(INVISIBILITY_MD);

        metadataList.add(FROZEN);
        metadataList.add(FROZEN_COOLDOWN);

        return metadataList;

    }

    public List<String> minorPowerList(){

        List<String> metadataList = new ArrayList<>();

        metadataList.add(ATTACK_BLINDING_MD);
        metadataList.add(ATTACK_CONFUSING_MD);
        metadataList.add(ATTACK_FIRE_MD);
        metadataList.add(ATTACK_FREEZE_MD);
        metadataList.add(ATTACK_GRAVITY_MD);
        metadataList.add(ATTACK_POISON_MD);
        metadataList.add(ATTACK_PUSH_MD);
        metadataList.add(ATTACK_WEB_MD);
        metadataList.add(ATTACK_WITHER_MD);
        metadataList.add(BONUS_LOOT_MD);
        metadataList.add(INVULNERABILITY_ARROW_MD);
        metadataList.add(INVULNERABILITY_FALL_DAMAGE_MD);
        metadataList.add(INVULNERABILITY_FIRE_MD);
        metadataList.add(INVULNERABILITY_KNOCKBACK_MD);
        metadataList.add(MOVEMENT_SPEED_MD);
        metadataList.add(INVISIBILITY_MD);

        return metadataList;

    }

    public void flushMetadata(Entity entity) {

        for (String string : metadataList()) {

            if (entity.hasMetadata(string)) {

                if (!(entity instanceof IronGolem) && entity.hasMetadata(VISUAL_EFFECT_MD)) {

                    entity.remove();

                }

                entity.removeMetadata(string, plugin);

            }

            if (!(entity instanceof IronGolem) && ValidAgressiveMobFilter.ValidAgressiveMobFilter(entity,
                    plugin.getConfig().getList("Valid aggressive EliteMobs"))) {

                entity.remove();

            }

            if (entity instanceof Player) {


                entity.removeMetadata(string, plugin);

            }

        }

    }

}
