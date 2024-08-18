package us.dingl.wishlifesteal.Listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.dingl.wishlifesteal.WishLifesteal;

import java.util.ArrayList;
import java.util.List;

public class DeathListener implements Listener {

    private final WishLifesteal plugin;

    public DeathListener(WishLifesteal plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();

        if (victim.getKiller() instanceof Player killer) {
            if (killer == victim) {
                return;
            }

            ItemStack shard = new ItemStack(Material.ECHO_SHARD);
            ItemMeta meta = shard.getItemMeta();
            List<Component> lore = new ArrayList<>();

            meta.displayName(Component.text("Wish Shard"));
            meta.setRarity(ItemRarity.RARE);

            lore.add(Component.text("A shard of a wish,"));
            lore.add(Component.text("4 are needed to make a wish."));

            meta.lore(lore);
            shard.setItemMeta(meta);

            killer.getInventory().addItem(shard);

            int currentHeartsVictim = plugin.getPlayerHearts(victim);
            plugin.setPlayerHearts(victim, currentHeartsVictim - 2);
            victim.setHealth(currentHeartsVictim - 2);
            victim.sendHealthUpdate();

            int currentHeartsKiller = plugin.getPlayerHearts(killer);
            plugin.setPlayerHearts(killer, currentHeartsKiller + 2);
            killer.setHealth(currentHeartsKiller + 2);
            killer.sendHealthUpdate();
        }
    }
}
