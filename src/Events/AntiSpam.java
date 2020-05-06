package Events;

import Main.Main;
import Utils.LangManager;
import Utils.Utils;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AntiSpam
  implements Listener {
  public static ArrayList<Player> count = new ArrayList();
  
  @EventHandler
  public void onChat(AsyncPlayerChatEvent e) {
    final Player p = e.getPlayer();
    if (count.contains(p)) {
      p.sendMessage(LangManager.storage.getString("AntiSpam").replace("%player%", p.getName()).replace("&", "�"));
      e.setCancelled(true);
      return;
    } 
    if (Utils.containsIP(e.getMessage())) {
      p.sendMessage(LangManager.storage.getString("Msg.mayNotSendLinks").replace("%player%", p.getName()).replace("&", "�"));
      e.setCancelled(true);
      return;
    } 
    if (Utils.containsIP(e.getMessage())) {
      p.sendMessage(LangManager.storage.getString("Msg.mayNotSendLinks").replace("%player%", p.getName()).replace("&", "�"));
      e.setCancelled(true);
      return;
    } 
    if (!p.hasPermission("squad.staff")) {
      count.add(p);
      Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.get(), new Runnable()
          {
            public void run() {
              AntiSpam.count.remove(p);
            }
          }, 
          (Main.get().getConfig().getInt("AntiSpam") * 20));
    } 
  }
}