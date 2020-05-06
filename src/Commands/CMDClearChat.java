package Commands;

import Events.FlyOnJoin;
import Utils.LangManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class CMDClearChat
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("cc")) {
      Player p = (Player)sender;
      if (!p.hasPermission("squad.clearchat")) {
        p.sendMessage(FlyOnJoin.tk(LangManager.storage.getString("No-Permissions")));
        return true;
      } 
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(" ");
      Bukkit.broadcastMessage(FlyOnJoin.tk(LangManager.storage.getString("ChatClear").replace("%player%", p.getName())));
      return true;
    } 
    return false;
  }
}
