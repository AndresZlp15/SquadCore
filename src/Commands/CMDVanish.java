package Commands;

import Events.FlyOnJoin;
import Utils.LangManager;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class CMDVanish
  implements CommandExecutor, Listener {
  public static ArrayList<Player> vanished = new ArrayList();

  
  public boolean onCommand(CommandSender sd, Command cmd, String string, String[] args) {
    if (cmd.getName().equalsIgnoreCase("vanish") || cmd.getName().equalsIgnoreCase("v")) {
      Player p = (Player)sd;
      if (!p.hasPermission("squad.staff")) {
        p.sendMessage(FlyOnJoin.tk(LangManager.storage.getString("No-Permissions")));
        return true;
      } 
      if (!vanished.contains(p)) {
        vanished.add(p);
        for (Player pls : Bukkit.getOnlinePlayers()) {
          pls.hidePlayer(p);
          p.hidePlayer(p);
        } 
        p.sendMessage(FlyOnJoin.tk(LangManager.storage.getString("VanishActive")));
        return true;
      } 
      vanished.remove(p);
      for (Player pls : Bukkit.getOnlinePlayers()) {
        pls.showPlayer(p);
        p.showPlayer(p);
      } 
      p.sendMessage(FlyOnJoin.tk(LangManager.storage.getString("VanishDesactive")));
      return true;
    } 
    
    return false;
  }
  
  @EventHandler
  public void onQuit(PlayerQuitEvent e) {
    Player p = e.getPlayer();
    if (vanished.contains(p)) {
      vanished.remove(p);
      for (Player pls : Bukkit.getOnlinePlayers()) {
        pls.showPlayer(p);
        p.showPlayer(p);
      } 
    } 
  }
}
