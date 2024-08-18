package us.dingl.wishlifesteal.Listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.dingl.wishlifesteal.WishGUIManager;

import java.util.ArrayList;
import java.util.List;

public class WishGUIListener implements Listener {

    private final WishGUIManager guiManager;

    public WishGUIListener(WishGUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack wish = new ItemStack(Material.SKULL_BANNER_PATTERN);
        ItemMeta wishMeta = wish.getItemMeta();
        List<Component> wishLore = new ArrayList<>();

        wishMeta.displayName(Component.text("Wish"));
        wishMeta.setRarity(ItemRarity.EPIC);

        wishLore.add(Component.text("A wish to be granted."));
        wishLore.add(Component.text("Right click with wish in hand to grant an item."));

        wishMeta.lore(wishLore);
        wish.setItemMeta(wishMeta);

        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) return;

        if (event.getView().getTitle().startsWith("Wish Page")) {
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null) return;

            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            boolean isNavigationItem = guiManager.handleNavigation(player, clickedItem, getCurrentPage(event.getView().getTitle()));
            if (!isNavigationItem) {
                player.getWorld().dropItemNaturally(player.getLocation(), clickedItem);
                player.closeInventory();
                player.getInventory().remove(wish);
            }
        }
    }

    private int getCurrentPage(String title) {
        // Assuming the title is in the format "Wish Page X"
        String[] parts = title.split(" ");
        return Integer.parseInt(parts[2]) - 1; // Convert to zero-based index
    }
}