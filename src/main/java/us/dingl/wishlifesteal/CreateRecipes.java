package us.dingl.wishlifesteal;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.addRecipe;

public class CreateRecipes {

    public static void registerRecipes(JavaPlugin plugin) {
        // Create the custom Wish Shard item
        ItemStack wishShard = new ItemStack(Material.ECHO_SHARD);
        ItemMeta wishShardMeta = wishShard.getItemMeta();
        List<Component> wishShardLore = new ArrayList<>();

        wishShardMeta.displayName(Component.text("Wish Shard"));
        wishShardMeta.setRarity(ItemRarity.RARE);

        wishShardLore.add(Component.text("A shard of a wish,"));
        wishShardLore.add(Component.text("4 are needed to make a wish."));

        wishShardMeta.lore(wishShardLore);
        wishShard.setItemMeta(wishShardMeta);

        // Create the custom Wish item
        ItemStack wish = new ItemStack(Material.SKULL_BANNER_PATTERN);
        ItemMeta wishMeta = wish.getItemMeta();
        List<Component> wishLore = new ArrayList<>();

        wishMeta.displayName(Component.text("Wish"));
        wishMeta.setRarity(ItemRarity.EPIC);

        wishLore.add(Component.text("A wish to be granted."));
        wishLore.add(Component.text("Right click with wish in hand to grant an item."));

        wishMeta.lore(wishLore);
        wish.setItemMeta(wishMeta);

        // Define the recipe for the Wish item using the custom Wish Shard
        NamespacedKey key = new NamespacedKey(plugin, "wish");
        ShapedRecipe wishRecipe = new ShapedRecipe(key, wish);
        wishRecipe.shape("bSb", "SNS", "bSb");
        wishRecipe.setIngredient('S', wishShard);
        wishRecipe.setIngredient('N', Material.NETHER_STAR);
        wishRecipe.setIngredient('b', Material.DIAMOND_BLOCK);

        // Register the recipe
        addRecipe(wishRecipe);
    }
}