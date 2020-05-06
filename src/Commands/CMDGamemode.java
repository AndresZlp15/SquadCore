package Commands;

import Events.FlyOnJoin;
import Main.Main;
import Utils.LangManager;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class CMDGamemode
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    String command = cmd.getName();
    if (command.equalsIgnoreCase("gamemode") || command.equalsIgnoreCase("gm")) {
      if (sender instanceof Player) {
        Player player = (Player)sender;
        if (player.hasPermission("squad.gamemode")) {
          if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Too less More Arguments");
            player.sendMessage(ChatColor.AQUA + "Usage: /Gamemode [Gamemode] <Player>");
            player.sendMessage(ChatColor.AQUA + "(Alias: /gm)");
            return true;
          }  if (args.length == 1) {
            if (Main.getInstance().getConfig().getBoolean("BuilderModeRequired") == true) {
              if (CMDBuilder.builders.contains(player)) {
                if (isInt(args[0])) {
                  int gm = Integer.parseInt(args[0]);
                  if (gm >= 0 && gm <= 3) {
                    player.setGameMode(GameMode.getByValue(gm));
                    player.sendMessage(LangManager.storage.getString("GameModeChanged").replace("&", "�").replace("%gamemode%", WordUtils.capitalizeFully(player.getGameMode().toString())));
                    return true;
                  } 
                  player.sendMessage(LangManager.storage.getString("GameModeNull").replace("&", "�"));
                  return true;
                } 
                
                try {
                  player.setGameMode(GameMode.valueOf(args[0].toUpperCase()));
                  player.sendMessage(LangManager.storage.getString("GameModeChanged").replace("&", "�").replace("%gamemode%", WordUtils.capitalizeFully(player.getGameMode().toString())));
                  return true;
                } catch (NullPointerException e) {
                  player.sendMessage(LangManager.storage.getString("GameModeNull").replace("&", "�"));
                  return true;
                } 
              } 
              
              player.sendMessage(ChatColor.RED + "You are not in Builder Mode, Run in console " + ChatColor.GREEN + "/buildadd " + player.getName() + ChatColor.RED + " to activate Builder Mode");
              return true;
            } 
            
            if (isInt(args[0])) {
              int gm = Integer.parseInt(args[0]);
              if (gm >= 0 && gm <= 3) {
                player.setGameMode(GameMode.getByValue(gm));
                player.sendMessage(LangManager.storage.getString("GameModeChanged").replace("&", "�").replace("%gamemode%", WordUtils.capitalizeFully(player.getGameMode().toString())));
                return true;
              } 
              player.sendMessage(LangManager.storage.getString("GameModeNull").replace("&", "�"));
              return true;
            } 
            
            try {
              player.setGameMode(GameMode.valueOf(args[0].toUpperCase()));
              player.sendMessage(LangManager.storage.getString("GameModeChanged").replace("&", "�").replace("%gamemode%", WordUtils.capitalizeFully(player.getGameMode().toString())));
              return true;
            } catch (NullPointerException e) {
              player.sendMessage(LangManager.storage.getString("GameModeNull").replace("&", "�"));
              return true;
            } 
          } 
          
          if (args.length == 2) {
            String name = args[1];
            if (Bukkit.getServer().getPlayer(name) != null) {
              Player target = Bukkit.getServer().getPlayer(name);
              if (CMDBuilder.builders.contains(target)) {
                if (isInt(args[0])) {
                  int gm = Integer.parseInt(args[0]);
                  if (gm >= 0 && gm <= 3) {
                    target.setGameMode(GameMode.getByValue(gm));
                    target.sendMessage(LangManager.storage.getString("GameModeChanged").replace("&", "�").replace("%gamemode%", WordUtils.capitalizeFully(target.getGameMode().toString())));
                    player.sendMessage(LangManager.storage.getString("GameModeChangedOther").replace("%player%", target.getName()).replace("&", "�").replace("%gamemode%", WordUtils.capitalizeFully(target.getGameMode().toString())));
                  } else {
                    player.sendMessage(LangManager.storage.getString("GameModeNull").replace("&", "�"));
                    return true;
                  } 
                } else {
                  try {
                    target.setGameMode(GameMode.valueOf(args[0].toUpperCase()));
                    target.sendMessage(LangManager.storage.getString("GameModeChanged").replace("&", "�").replace("%gamemode%", WordUtils.capitalizeFully(target.getGameMode().toString())));
                    player.sendMessage(LangManager.storage.getString("GameModeChangedOther").replace("%player%", target.getName()).replace("&", "�").replace("%gamemode%", WordUtils.capitalizeFully(target.getGameMode().toString())));
                  } catch (NullPointerException e) {
                    player.sendMessage(LangManager.storage.getString("GameModeNull").replace("&", "�"));
                    return true;
                  } 
                } 
              } else {
                player.sendMessage(ChatColor.RED + "This player is not in Builder mode, Run " + ChatColor.GREEN + "/buildadd " + target.getName() + ChatColor.RED + " to activate the Builder mode in this player");
              } 
            } 
          } 
        } else {
          player.sendMessage(FlyOnJoin.tk(LangManager.storage.getString("No-Permissions")));
          return true;
        } 
      } else if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
        if (args.length == 0 || args.length == 1) {
          Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Too less More Arguments");
          Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Usage: /Gamemode [Gamemode] [Player]");
          Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "(Alias: /gm)");
        } else if (args.length == 2) {
          String name = args[1];
          if (Bukkit.getServer().getPlayer(name) != null) {
            Player target = Bukkit.getServer().getPlayer(name);
            if (isInt(args[0])) {
              int gm = Integer.parseInt(args[0]);
              if (gm >= 0 && gm <= 3) {
                target.setGameMode(GameMode.getByValue(gm));
                target.sendMessage(LangManager.storage.getString("GameModeChanged").replace("&", "�").replace("%gamemode%", WordUtils.capitalizeFully(target.getGameMode().toString())));
                Bukkit.getConsoleSender().sendMessage(LangManager.storage.getString("GameModeChangedOther").replace("%player%", target.getName()).replace("&", "�").replace("%gamemode%", WordUtils.capitalizeFully(target.getGameMode().toString())));
              } else {
                Bukkit.getConsoleSender().sendMessage(LangManager.storage.getString("GameModeNull").replace("&", "�"));
                return true;
              } 
            } else {
              try {
                target.setGameMode(GameMode.valueOf(args[0].toUpperCase()));
                target.sendMessage(LangManager.storage.getString("GameModeChanged").replace("&", "�").replace("%gamemode%", WordUtils.capitalizeFully(target.getGameMode().toString())));
                Bukkit.getConsoleSender().sendMessage(LangManager.storage.getString("GameModeChangedOther").replace("%player%", target.getName()).replace("&", "�").replace("%gamemode%", WordUtils.capitalizeFully(target.getGameMode().toString())));
              } catch (NullPointerException e) {
                Bukkit.getConsoleSender().sendMessage(LangManager.storage.getString("GameModeNull").replace("&", "�"));
                return true;
              } 
            } 
          } 
        } 
      } 
    }
    return false;
  }
  
  public boolean isInt(String str) {
    try {
      Integer.parseInt(str);
    } catch (NumberFormatException e) {
      return false;
    } 
    return true;
  }
}
