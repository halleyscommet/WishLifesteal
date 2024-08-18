package us.dingl.wishlifesteal;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WishGUIManager {
    private final List<Inventory> pages = new ArrayList<>();
    private static final int PAGE_SIZE = 45; // 54 slots - 9 for navigation

    public WishGUIManager() {
    }

    public void openWishGUI(Player player) {
        createPages();
        player.openInventory(pages.getFirst());
    }

    private void createPages() {
        // Clear existing pages
        pages.clear();

        // Create items for the GUI
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.DIAMOND_BLOCK, 2));
        items.add(new ItemStack(Material.EMERALD_BLOCK, 8));
        items.add(new ItemStack(Material.GOLD_BLOCK, 4));
        items.add(new ItemStack(Material.IRON_BLOCK, 16));
        items.add(new ItemStack(Material.LAPIS_BLOCK, 32));

        items.add(new ItemStack(Material.DIAMOND_SWORD));
        items.add(new ItemStack(Material.TRIDENT));
        items.add(new ItemStack(Material.BOW));
        items.add(new ItemStack(Material.SHIELD));
        items.add(new ItemStack(Material.DIAMOND_AXE));
        items.add(new ItemStack(Material.DIAMOND_PICKAXE));
        items.add(new ItemStack(Material.DIAMOND_HELMET));
        items.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
        items.add(new ItemStack(Material.DIAMOND_LEGGINGS));
        items.add(new ItemStack(Material.DIAMOND_BOOTS));
        items.add(new ItemStack(Material.WATER_BUCKET));
        items.add(new ItemStack(Material.LAVA_BUCKET));
        items.add(new ItemStack(Material.COBWEB, 64));

        items.add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
        items.add(new ItemStack(Material.GOLDEN_APPLE, 32));
        items.add(new ItemStack(Material.GOLDEN_CARROT, 64));

        items.add(new ItemStack(Material.ENDER_PEARL, 16));
        items.add(new ItemStack(Material.ENDER_CHEST, 4));
        items.add(new ItemStack(Material.BLAZE_ROD, 16));

        items.add(new ItemStack(Material.ANVIL, 4));
        items.add(new ItemStack(Material.ENCHANTING_TABLE));
        items.add(new ItemStack(Material.BREWING_STAND, 2));
        items.add(new ItemStack(Material.BARREL, 16));
        items.add(new ItemStack(Material.BOOKSHELF, 15));
        items.add(new ItemStack(Material.BEACON));

        items.add(new ItemStack(Material.RECOVERY_COMPASS));


        int totalPages = (int) Math.ceil((double) items.size() / PAGE_SIZE);

        for (int i = 0; i < totalPages; i++) {
            Inventory page = Bukkit.createInventory(null, 54, Component.text("Wish Page " + (i + 1)));
            int start = i * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, items.size());

            for (int j = start; j < end; j++) {
                page.setItem(j - start, items.get(j));
            }

            // Add navigation items
            if (i > 0) {
                page.setItem(45, createNavigationItem("Previous Page"));
            }
            if (i < totalPages - 1) {
                page.setItem(53, createNavigationItem("Next Page"));
            }

            pages.add(page);
        }
    }

    private ItemStack createNavigationItem(String name) {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name));
        item.setItemMeta(meta);
        return item;
    }

    public boolean handleNavigation(Player player, ItemStack item, int currentPage) {
        if (Objects.equals(item.getItemMeta().displayName(), Component.text("Next Page"))) {
            player.openInventory(pages.get(currentPage + 1));
            return true;
        } else if (Objects.equals(item.getItemMeta().displayName(), Component.text("Previous Page"))) {
            player.openInventory(pages.get(currentPage - 1));
            return true;
        }
        return false;
    }
}