package Scoreboard;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import Main.Main;
import java.io.File;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class BoardTask
{
  public static FileConfiguration storage = null;
  private static File storageFile = null;
  
  public static void load(Plugin plugin) {
    storageFile = new File(plugin.getDataFolder(), "scoreboard.yml");
    if (!storageFile.exists()) {
      plugin.saveResource("scoreboard.yml", false);
      Main.getInstance().getLogger().info("Generating config scoreboard.yml");
    } else {
      Main.getInstance().getLogger().info("Loading config scoreboard.yml");
    } 
    storage = YamlConfiguration.loadConfiguration(storageFile);
  }
  
  public static void contentBoard(Player p) {
    if (null == p) {
      return;
    }
    HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
    if (!storage.getStringList("disabledWorlds").contains(p.getPlayer().getWorld().getName())) {
      String translateAlternateColorCodes2 = ChatColor.translateAlternateColorCodes('&', storage.getString("Title"));
      int size = storage.getStringList("scoreboard-lines").size();
      for (String replaceText : storage.getStringList("scoreboard-lines")) {
        
        replaceText = replaceText.replace("%player%", p.getName());
        replaceText = replaceText.replace("%player-displayname%", p.getDisplayName());
        
        String World = p.getWorld().getName();
        replaceText = replaceText.replaceAll("%world%", World);
        
        replaceText = replaceVault(p, replaceText);
        
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
          replaceText = Main.setPlaceholders(p, replaceText);
        }
        
        replaceText = replaceText.replaceAll("%empty%", " ");
        replaceText = Color(replaceText);
        
        if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
          replaceText = PlaceholderAPI.replacePlaceholders(p, replaceText);
        }
        hashMap.put(fixDuplicates(hashMap, ChatColor.translateAlternateColorCodes('&', replaceText)), Integer.valueOf(size));
        size--;
      } 
      BoardManager.scoredSidebar(p, translateAlternateColorCodes2, hashMap);
    } 
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    contentBoard(p);
  }
  
  public static String replaceVault(Player p, String message) {
    String rank = null;
    rank = Main.perms.getPrimaryGroup(p);
    return message.replace("%group%", rank);
  }

  
  private static String fixDuplicates(HashMap<String, Integer> hashMap, String s) {
    while (hashMap.containsKey(s)) {
      s = s + "�r";
    }
    if (s.length() > 40) {
      s = s.substring(0, 39);
    }
    return s;
  }

  
  public static String Color(String a) { return ChatColor.translateAlternateColorCodes('&', a); }
}
