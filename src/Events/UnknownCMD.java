package Events;

import Utils.LangManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

public class UnknownCMD
  implements Listener
{
  @EventHandler
  public void OnCommando(PlayerCommandPreprocessEvent e) {
    if (!e.isCancelled()) {
      Player p = e.getPlayer();
      String message = e.getMessage().split(" ")[0];
      HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(message);
      if (topic == null) {
        p.sendMessage(LangManager.storage.getString("UnknownCommand").replaceAll("&", "�"));
        e.setCancelled(true);
      } 
    } 
  }
}
