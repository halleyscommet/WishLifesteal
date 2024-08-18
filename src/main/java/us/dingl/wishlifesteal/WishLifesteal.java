package us.dingl.wishlifesteal;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import us.dingl.wishlifesteal.Commands.GiveWish;
import us.dingl.wishlifesteal.Commands.GiveWishShard;
import us.dingl.wishlifesteal.Listeners.DeathListener;
import us.dingl.wishlifesteal.Listeners.RightClickWishListener;
import us.dingl.wishlifesteal.Listeners.WishGUIListener;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public final class WishLifesteal extends JavaPlugin {

    private File playersFile;
    private FileConfiguration playersConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("WishLifesteal has been enabled!");

        // Create plugin data folder
        createPluginDataFolder();

        // Load players config
        loadPlayersConfig();

        // Register commands
        registerCommands();

        // Register listeners
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);

        // Register recipes
        CreateRecipes.registerRecipes(this);

        // Initialize and register GUI manager
        WishGUIManager guiManager = new WishGUIManager();
        getServer().getPluginManager().registerEvents(new RightClickWishListener(guiManager), this);
        getServer().getPluginManager().registerEvents(new WishGUIListener(guiManager), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("WishLifesteal has been disabled!");
    }

    private void createPluginDataFolder() {
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            if (dataFolder.mkdirs()) {
                getLogger().info("Plugin data folder created successfully.");
                writeFileInDataFolder();
            } else {
                getLogger().severe("Failed to create plugin data folder.");
            }
        }
    }

    private void writeFileInDataFolder() {
        playersFile = new File(getDataFolder(), "hearts.yml");
        if (!playersFile.exists()) {
            try {
                if (playersFile.createNewFile()) {
                    getLogger().info("hearts.yml file created successfully.");
                } else {
                    getLogger().severe("Failed to create hearts.yml file.");
                }
                playersConfig = YamlConfiguration.loadConfiguration(playersFile);
                playersConfig.set("hearts", List.of());
                playersConfig.save(playersFile);
            } catch (IOException e) {
                getLogger().severe("Failed to write file: " + e.getMessage());
            }
        }
    }

    private void loadPlayersConfig() {
        playersFile = new File(getDataFolder(), "hearts.yml");
        if (!playersFile.exists()) {
            writeFileInDataFolder();
        } else {
            playersConfig = YamlConfiguration.loadConfiguration(playersFile);
        }
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("givewish")).setExecutor(new GiveWish());
        Objects.requireNonNull(getCommand("givewishshard")).setExecutor(new GiveWishShard());
    }

    public int getPlayerHearts(Player player) {
        return playersConfig.getInt("hearts." + player.getUniqueId(), 0);
    }

    public void setPlayerHearts(Player player, int hearts) {
        playersConfig.set("hearts." + player.getUniqueId(), hearts);
        try {
            playersConfig.save(playersFile);
        } catch (IOException e) {
            getLogger().severe("Failed to save hearts.yml: " + e.getMessage());
        }
    }
}
