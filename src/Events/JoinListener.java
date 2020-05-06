package Events;


import Commands.CMDVanish;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;





public class JoinListener
  implements Listener
{
  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    p.setPlayerListName("�e" + p.getName());
    for (Player v : Bukkit.getOnlinePlayers()) {
      if (!p.hasPermission("squad.staff") && CMDVanish.vanished.contains(v))
        p.hidePlayer(v); 
    } 
  }
}
