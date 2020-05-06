package Events;

import Commands.CMDVanish;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;




public class VanishListener
  implements Listener
{
  @EventHandler
  public void onQuit(PlayerQuitEvent e) {
    Player p = e.getPlayer();
    if (CMDVanish.vanished.contains(p))
      CMDVanish.vanished.remove(p); 
  }
}