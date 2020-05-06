package Commands;

import Events.FlyOnJoin;
import Utils.LangManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class CMDChat
  implements CommandExecutor, Listener {
  private static boolean Desactivo = false;
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (label.equalsIgnoreCase("chat")) {
      Player p = (Player)sender;
      if (!p.hasPermission("squad.chat")) {
        p.sendMessage(FlyOnJoin.tk(LangManager.storage.getString("No-Permissions")));
        return true;
      } 
      if (args.length == 1) {
        if (args[0].equalsIgnoreCase("on")) {
          Desactivo = false;
          Bukkit.broadcastMessage(LangManager.storage.getString("ChatEnable").replace("&", "�"));
          return true;
        } 
        if (args[0].equalsIgnoreCase("off")) {
          Desactivo = true;
          Bukkit.broadcastMessage(LangManager.storage.getString("ChatDisable").replace("&", "�"));
          return true;
        } 
      } 
      
      return true;
    } 
    return false;
  }
  
  @EventHandler
  public void Chat(AsyncPlayerChatEvent e) {
    Player p = e.getPlayer();
    if (!p.hasPermission("squad.chat.bypass") && Desactivo)
      e.setCancelled(true); 
  }
}
