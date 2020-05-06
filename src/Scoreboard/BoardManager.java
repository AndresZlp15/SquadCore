package Scoreboard;

import Main.Main;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

public class BoardManager
{
  public static void scoredSidebar(Player p, String title, HashMap<String, Integer> elements) {
    if (title == null) {
      title = "Unamed";
    }
    if (title.length() > 32) {
      title = title.substring(0, 32);
    }
    String finalTitle = title;
    while (elements.size() > 16) {
      String minimumKey = (String)elements.keySet().toArray()[0];
      int minimum = ((Integer)elements.get(minimumKey)).intValue();
      
      for (String string : elements.keySet()) {
        if (((Integer)elements.get(string)).intValue() < minimum || (((Integer)elements.get(string)).intValue() == minimum && string.compareTo(minimumKey) < 0)) {
          minimumKey = string;
          minimum = ((Integer)elements.get(string)).intValue();
        } 
      } 
      elements.remove(minimumKey);
    } 
    
    Bukkit.getScheduler().runTask(Main.get(), () -> {
          if (p == null || !p.isOnline()) {
            return;
          }
          if (Bukkit.getScoreboardManager().getMainScoreboard() != null && 
            Bukkit.getScoreboardManager().getMainScoreboard() == p.getScoreboard()) {
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
          }
          
          if (p.getScoreboard() == null) {
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
          }
          
          Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), ());
        });
  }
}
