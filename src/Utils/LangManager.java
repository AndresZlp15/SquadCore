package Utils;

import Main.Main;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class LangManager
{
  public static FileConfiguration storage = null;
  public static File storageFile = null;
  
  public static void load(Plugin plugin) {
    storageFile = new File(plugin.getDataFolder(), "lang.yml");
    if (!storageFile.exists()) {
      plugin.saveResource("lang.yml", false);
      Main.getInstance().getLogger().info("Generating config lang.yml");
    } else {
      Main.getInstance().getLogger().info("Loading config lang.yml");
    } 
    storage = YamlConfiguration.loadConfiguration(storageFile);
  }
}
