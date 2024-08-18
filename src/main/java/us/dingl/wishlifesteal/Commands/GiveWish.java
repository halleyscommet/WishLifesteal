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

public class GiveWish implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if (player.hasPermission("wishlifesteal.give")) {
                ItemStack wish = new ItemStack(Material.SKULL_BANNER_PATTERN);
                ItemMeta wishMeta = wish.getItemMeta();
                List<Component> wishLore = new ArrayList<>();

                wishMeta.displayName(Component.text("Wish"));
                wishMeta.setRarity(ItemRarity.EPIC);

                wishLore.add(Component.text("A wish to be granted."));
                wishLore.add(Component.text("Right click with wish in hand to grant an item."));

                wishMeta.lore(wishLore);
                wish.setItemMeta(wishMeta);

                player.getInventory().addItem(wish);
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
