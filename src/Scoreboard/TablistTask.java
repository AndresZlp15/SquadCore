package Scoreboard;

import Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;



public class TablistTask
  extends BukkitRunnable
{
  public void run() { Send(); }

  
  public static void Send() {
    for (Player p : Bukkit.getOnlinePlayers()) {
      Send(p);
    }
  }

  
  public static void Send(Player p) { Main.get().updateTab(p); }
}