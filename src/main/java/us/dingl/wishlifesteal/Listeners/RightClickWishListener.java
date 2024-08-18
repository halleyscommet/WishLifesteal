package us.dingl.wishlifesteal.Listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import us.dingl.wishlifesteal.WishGUIManager;

import java.util.Objects;

public class RightClickWishListener implements Listener {

    private final WishGUIManager guiManager;

    public RightClickWishListener(WishGUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction().isRightClick()) {
            ItemStack item = player.getInventory().getItemInMainHand();

            if (isWishItem(item)) {
                guiManager.openWishGUI(player);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().title().toString().startsWith("Wish Page")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();

            if (item != null && item.hasItemMeta()) {
                int currentPage = Integer.parseInt(event.getView().title().toString().split(" ")[2]) - 1;
                guiManager.handleNavigation(player, item, currentPage);
            }
        }
    }

    private boolean isWishItem(ItemStack item) {
        return item != null && item.hasItemMeta() && Objects.equals(item.getItemMeta().displayName(), Component.text("Wish")) && item.getItemMeta().hasLore();
    }
}