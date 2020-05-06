package Events;

import Main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class JoinFullBypass
  implements Listener
{
  private Main plugin;
  
  public JoinFullBypass(Main plugin) { this.plugin = plugin; }


  
  @EventHandler
  public void onLogin(PlayerLoginEvent e) {
    if (e.getResult().equals(PlayerLoginEvent.Result.KICK_FULL) && e.getPlayer().hasPermission(this.plugin.getConfig().getString("ServerFullPermissions")))
      e.allow(); 
  }
}

