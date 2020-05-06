package Events;

import Main.Main;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class joinitems
  implements Listener {
  public static FileConfiguration storage = null;
  public static File storageFile = null;
  public static List<String> HidePlayer = new ArrayList();


  
  public static List<JoinItem> items = new ArrayList();

  
  public static void JoinItems(Plugin plugin) {
    storageFile = new File(plugin.getDataFolder(), "joinitems.yml");
    if (!storageFile.exists()) {
      plugin.saveResource("joinitems.yml", false);
      Main.getInstance().getLogger().info("Generating config joinitems.yml");
    } else {
      Main.getInstance().getLogger().info("Loading config joinitems.yml");
    } 
    storage = YamlConfiguration.loadConfiguration(storageFile);
  }

  
  public static void load() {
    items = new ArrayList();
    itemsConfig = loadFile("joinitems.yml");
    Set<String> cs = itemsConfig.getKeys(false);
    for (String item : cs) {
      ConfigurationSection itemNode = itemsConfig.getConfigurationSection(item);
      if (!itemNode.isSet("Name")) {
        Main.getInstance().getLogger().log(Level.INFO, "The item \"{0}\" has no name!", item); continue;
      } 
      if (!itemNode.isSet("ID")) {
        Main.getInstance().getLogger().log(Level.INFO, "The item \"{0}\" has no ID!", item); continue;
      } 
      if (itemNode.getInt("ID") == 0 || Material.getMaterial(itemNode.getInt("id")) == null) {
        Main.getInstance().getLogger().log(Level.INFO, "\" has an invalid item ID: The item \"{0}{1}.", new Object[] { item, Integer.valueOf(itemNode.getInt("ID")) });
        continue;
      } 
      Material material = Material.getMaterial(itemNode.getInt("ID"));
      String command = itemNode.getString("Command");
      String name = itemNode.getString("Name");
      Integer slot = Integer.valueOf(itemNode.getInt("Slot"));
      Short dataValue = Short.valueOf((short)itemNode.getInt("Data-value"));
      JoinItem objetos = new JoinItem(material);
      objetos.setCommands(ItemCommand.arrayFromString(command));
      objetos.setSlot(slot);
      objetos.setCustomName(name);
      objetos.setDataValue(dataValue);
      if (itemNode.isSet("Lore") && itemNode.isList("Lore")) {
        objetos.setLore(itemNode.getStringList("Lore"));
      }
      objetos.setDroppable(itemNode.getBoolean("Allow-drop", false));
      if (itemNode.getInt("cooldown-seconds") > 0) {
        objetos.setUseCooldown(true);
        objetos.setCooldownSeconds(itemNode.getInt("cooldown-seconds"));
      } 
      items.add(objetos);
    } 
  }

  
  public static FileConfiguration loadFile(String path) {
    if (!path.endsWith(".yml")) {
      path = String.valueOf(path) + ".yml";
    }
    File file = new File(Main.getInstance().getDataFolder(), path);
    if (!file.exists()) {
      try {
        Main.getInstance().saveResource(path, false);
      }
      catch (Exception e) {
        Main.getInstance().getLogger().log(Level.WARNING, "Cannot save {0} to disk!", path);
        return null;
      } 
    }
    return YamlConfiguration.loadConfiguration(file);
  }
}
