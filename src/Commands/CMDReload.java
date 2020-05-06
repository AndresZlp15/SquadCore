package Commands;

import Events.FlyOnJoin;
import Main.Main;
import Scoreboard.BoardTask;
import Scoreboard.TablistTask;
import Utils.LangManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CMDReload
  implements CommandExecutor
{
  public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {
    if (cmd.getName().equalsIgnoreCase("squadreload")) {
      Player p = (Player)cs;
      if (cs instanceof Player) {
        if (p.isOp()) {
          Reload();
          p.sendMessage(FlyOnJoin.tk("&7[&6SquadCore&7] &aReloaded configuration!"));
          return true;
        } 
        p.sendMessage(FlyOnJoin.tk(LangManager.storage.getString("No-Permissions")));
      } 
      
      if (cs instanceof org.bukkit.command.ConsoleCommandSender) {
        Reload();
        cs.sendMessage("[SquadCore] Reloaded configuration!");
        return true;
      } 
    } 
    return false;
  }
  
  public void Reload() {
    Main.get().getServer().getPluginManager().disablePlugin(Main.get());
    Main.get().getServer().getPluginManager().enablePlugin(Main.get());
    for (Player p : Bukkit.getOnlinePlayers()) {
      BoardTask.contentBoard(p);
      TablistTask.Send(p);
    } 
  }
}

