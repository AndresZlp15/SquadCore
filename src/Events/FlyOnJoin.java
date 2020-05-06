package Events;

import Utils.LangManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FlyOnJoin
  implements CommandExecutor, Listener
{
  public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
    if (cmd.getName().equalsIgnoreCase("fly")) {
      Player p = (Player)sender;
      if (!p.hasPermission("squad.donator")) {
        p.sendMessage(tk(LangManager.storage.getString("No-Permissions")));
        return true;
      } 
      if (p.hasPermission("squad.donator") || p.isOp()) {
        if (p.getAllowFlight() == true) {
          p.setAllowFlight(false);
          p.setFlying(false);
          p.sendMessage(tk(LangManager.storage.getString("Messages.FlyDesactived").replace("%player%", p.getName())));
          return true;
        } 
        p.setAllowFlight(true);
        p.setFlying(true);
        p.sendMessage(tk(LangManager.storage.getString("Messages.FlyActived").replace("%player%", p.getName())));
        return true;
      } 
      
      return true;
    } 
    return false;
  }

  
  public static String tk(String a) { return a.replaceAll("&", "�"); }

  
  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    if (p.hasPermission("squad.donator") || p.isOp()) {
      p.setAllowFlight(true);
      p.setFlying(true);
    } else {
      p.setAllowFlight(false);
      p.setFlying(false);
    } 
  }
}
