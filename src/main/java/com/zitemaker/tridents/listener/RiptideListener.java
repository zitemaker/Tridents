package com.zitemaker.tridents.listener;

import com.zitemaker.tridents.Tridents;
import me.chancesd.pvpmanager.player.CombatPlayer;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRiptideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.concurrent.ThreadLocalRandom;

public class RiptideListener implements Listener {

    private final Tridents plugin;

    public RiptideListener(Tridents plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerRiptide(PlayerRiptideEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        CombatPlayer combatPlayer = CombatPlayer.get(player);
        if (combatPlayer == null || !combatPlayer.isInCombat()) {
            return;
        }

        ItemStack item = event.getItem();
        if (item == null || !item.hasItemMeta()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof Damageable damageable)) {
            return;
        }

        int riptideLevel = getEnchantmentLevel(item, "riptide");
        int unbreakingLevel = getEnchantmentLevel(item, "unbreaking");

        durabilityDamage(player, item, damageable, riptideLevel, unbreakingLevel);
        removeHunger(player, riptideLevel);
    }

    private int getEnchantmentLevel(ItemStack item, String key) {
        Enchantment enchantment = Registry.ENCHANTMENT.get(NamespacedKey.minecraft(key));
        if (enchantment != null) {
            return item.getEnchantmentLevel(enchantment);
        }
        return 0;
    }

    private void durabilityDamage(Player player, ItemStack item, Damageable damageable, int riptideLevel,
            int unbreakingLevel) {
        double baseDamage = plugin.getConfig().getDouble("durability.damage-per-jump", 2.5);
        double extraDamage = plugin.getConfig().getDouble("durability.extra-damage-per-riptide-level", 0.0)
                * riptideLevel;

        double calculatedCost = baseDamage + extraDamage;
        if (calculatedCost <= 0) {
            return;
        }

        int damageToApply = (int) Math.round(calculatedCost);

        if (plugin.getConfig().getBoolean("durability.unbreaking-prevents-damage", true)) {
            int effectiveDamage = 0;
            for (int i = 0; i < damageToApply; i++) {
                if (shouldTakeDamage(unbreakingLevel)) {
                    effectiveDamage++;
                }
            }
            damageToApply = effectiveDamage;
        }

        if (damageToApply <= 0) {
            return;
        }

        int maxDurability = item.getType().getMaxDurability();
        int currentDamage = damageable.getDamage();
        int resultingDamage = currentDamage + damageToApply;

        if (resultingDamage >= maxDurability) {
            item.setAmount(0);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            player.updateInventory();
        } else {
            damageable.setDamage(resultingDamage);
            item.setItemMeta(damageable);
        }
    }

    private boolean shouldTakeDamage(int unbreakingLevel) {
        if (unbreakingLevel <= 0) {
            return true;
        }
        return ThreadLocalRandom.current().nextInt(unbreakingLevel + 1) == 0;
    }

    private void removeHunger(Player player, int riptideLevel) {
        double baseExhaustion = plugin.getConfig().getDouble("hunger.exhaustion-per-jump", 2.0);
        double extraExhaustion = plugin.getConfig().getDouble("hunger.extra-exhaustion-per-riptide-level", 0.5)
                * riptideLevel;

        double totalExhaustion = baseExhaustion + extraExhaustion;

        if (totalExhaustion > 0) {
            float currentExhaustion = player.getExhaustion();
            player.setExhaustion(currentExhaustion + (float) totalExhaustion);
        }
    }
}
