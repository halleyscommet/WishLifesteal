package us.dingl.wishlifesteal.Commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GiveWishShard implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if (player.hasPermission("wishlifesteal.give")) {
                ItemStack wishShard = new ItemStack(Material.ECHO_SHARD);
                ItemMeta wishShardMeta = wishShard.getItemMeta();
                List<Component> wishShardLore = new ArrayList<>();

                wishShardMeta.displayName(Component.text("Wish Shard"));
                wishShardMeta.setRarity(ItemRarity.RARE);

                wishShardLore.add(Component.text("A shard of a wish,"));
                wishShardLore.add(Component.text("4 are needed to make a wish."));

                wishShardMeta.lore(wishShardLore);
                wishShard.setItemMeta(wishShardMeta);

                player.getInventory().addItem(wishShard);
                return true;
            } else {
                player.sendMessage("You do not have permission to use this command.");
                return false;
            }
        } else {
            commandSender.sendMessage("You must be a player to use this command.");
            return false;
        }
    }
}