package Events;

import Main.Main;
import Utils.TitleAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class titlejoin
  implements Listener {
  private Main plugin;
  
  public titlejoin(Main plugin) { this.plugin = plugin; }

  
  @EventHandler
  public void onJoinTitle(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    if (Main.getInstance().getConfig().getBoolean("EnableJoinTitle") == true) {
      
      String title = this.plugin.getConfig().getString("JoinTitle").replace("%player%", e.getPlayer().getDisplayName()).replace("%player%", p.getName());
      
      String subtitle = this.plugin.getConfig().getString("SubTitle").replace("%player%", p.getName());
      String servername = this.plugin.getConfig().getString("ServerName");
      TitleAPI.sendTitle(p, Main.setPlaceholders(p, title).replace("&", "�").replace("&", "�").replace("%server_name%", servername));
      TitleAPI.sendSubTitle(p, Main.setPlaceholders(p, subtitle));
    } 
  }
}
