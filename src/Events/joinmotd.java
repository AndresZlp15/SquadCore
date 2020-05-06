package Events;

import Main.Main;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class joinmotd
  implements Listener {
  private Main plugin;
  
  public joinmotd(Main plugin) { this.plugin = plugin; }

  
  @EventHandler
  public void PlayerJoinMotd(PlayerJoinEvent e) {
    if (this.plugin.getConfig().getBoolean("JoinMotdEnable") == true) {
      Player p = e.getPlayer();
      List<String> JoinMotd = this.plugin.getConfig().getStringList(ChatColor.translateAlternateColorCodes('&', "JoinMotd"));
      JoinMotd.forEach(message -> {
            String servername = this.plugin.getConfig().getString("ServerName");
            String ServerIP = this.plugin.getConfig().getString("ServerIP");
            int online1 = this.plugin.getServer().getOnlinePlayers().size();
            String online2 = Integer.toString(online1);
            int max = this.plugin.getServer().getMaxPlayers();
            String max2 = Integer.toString(max);
            String ip_adress = p.getAddress().getAddress().toString();
            ip_adress = ip_adress.replaceAll("/", "");
            int heal = (int)e.getPlayer().getHealth();
            String health = Integer.toString(heal);
            int maxhh = (int)e.getPlayer().getMaxHealth();
            String maxh = Integer.toString(maxhh);
            p.sendMessage(Main.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', message)
                  .replace("%player%", e.getPlayer().getName())
                  .replace("%display_name%", e.getPlayer().getDisplayName())
                  .replace("%server_name%", servername)
                  .replaceAll("%server_ip%", ServerIP)
                  .replace("%online-players%", online2)
                  .replace("%max-online%", max2)
                  .replace("%ip-adress%", ip_adress)
                  .replace("%health%", health)
                  .replace("%max-health%", maxh)));
          });
    } 
  }
}
