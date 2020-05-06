package Events;

import Events.FlyOnJoin;
import Main.Main;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class tablist
  implements Listener {
  private Main plugin;
  public static FileConfiguration storage = null;
  public static File storageFile = null;

  
  public tablist(Main plugin) { this.plugin = plugin; }

  
  public static void load(Plugin plugin) {
    storageFile = new File(plugin.getDataFolder(), "tablist.yml");
    if (!storageFile.exists()) {
      plugin.saveResource("tablist.yml", false);
      Main.getInstance().getLogger().info("Generating config tablist.yml");
    } else {
      Main.getInstance().getLogger().info("Loading config tablist.yml");
    } 
    storage = YamlConfiguration.loadConfiguration(storageFile);
  }

  
  public tablist(Player player, String header, String footer) {}
  
  @EventHandler
  public void onTablist(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    if (storage.getBoolean("Use-Tablist") == true) {
      this.plugin.updateTab(p);
      String servername = this.plugin.getConfig().getString("ServerName");
      String ServerIP = this.plugin.getConfig().getString("ServerIP");
      int online1 = this.plugin.getServer().getOnlinePlayers().size();
      String online2 = Integer.toString(online1);
      int max = this.plugin.getServer().getMaxPlayers();
      String max2 = Integer.toString(max);
      Main.setPlaceholders(p, FlyOnJoin.tk(storage.getString("Header")
            .replace("%player%", e.getPlayer().getName())
            .replace("%display_name%", e.getPlayer().getDisplayName())
            .replace("%server_name%", servername)
            .replaceAll("%server_ip%", ServerIP)
            .replace("%online-players%", online2)
            .replace("%max-online%", max2)));
      Main.setPlaceholders(p, FlyOnJoin.tk(storage.getString("Footer")
            .replace("%player%", e.getPlayer().getName())
            .replace("%display_name%", e.getPlayer().getDisplayName())
            .replace("%server_name%", servername)
            .replaceAll("%server_ip%", ServerIP)
            .replace("%online-players%", online2)
            .replace("%max-online%", max2)));
      Main.sendPacket(e.getPlayer(), p);
    } 
  }
  
  public static void onTablist() {}
}
