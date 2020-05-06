package Commands;

import Main.Main;
import Main.MessengerUser;
import Utils.LangManager;
import Utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;


public class CMDMsg
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("msg")) {
      if (sender instanceof Player) {
        Player p = (Player)sender;
        if (args.length >= 2) {
          if (Bukkit.getPlayer(args[false]) != null) {
            Player p2 = Bukkit.getPlayer(args[0]);
            if (Main.getInstance().maySendMessage(p, p2)) {
              StringBuilder sb = new StringBuilder();
              for (int i = 1; i < args.length; i++) {
                sb.append(" ").append(args[i]);
              }
              String message = sb.toString().substring(1);
              if ((Utils.containsIP(message) || Utils.containsLink(message)) && !p.hasPermission("squad.sendLinks")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.mayNotSendLinks")));
                return true;
              } 
              if (p.hasPermission("squad.color")) {
                message = ChatColor.translateAlternateColorCodes('&', message);
              }
              p.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.meTo").replace("%displayname%", p2.getDisplayName()).replace("%message%", message).replace("%name%", p2.getName())));
              p2.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.toMe").replace("%displayname%", p.getDisplayName()).replace("%message%", message).replace("%name%", p.getName())));
              for (MessengerUser spy : Main.USER_STORAGE) {
                if (spy.getPlayer() != null && spy.getPlayer().isOnline() && spy.isSocialSpyActive() && spy.getPlayer() != p && spy.getPlayer() != p2) {
                  spy.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.socialspy.msg").replace("%player1%", p.getDisplayName()).replace("%player2%", p2.getDisplayName()).replace("%message%", message).replace("%name1%", p.getName()).replace("%name2%", p2.getName())));
                }
              } 
              for (CommandSender spy2 : Main.SOCIAL_SPY) {
                if (spy2 != p && spy2 != p2) {
                  spy2.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.socialspy.msg").replace("%player1%", p.getDisplayName()).replace("%player2%", p2.getDisplayName()).replace("%message%", message).replace("%name1%", p.getName()).replace("%name2%", p2.getName())));
                }
              } 
              if (Main.REPLY.containsKey(p2)) {
                Main.REPLY.remove(p2);
              }
              Main.REPLY.put(p2, p);
            } else {
              
              p.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.blockingMessages").replace("%player%", args[0])));
            } 
          } else {
            
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("NotOnline").replace("%player%", args[0])));
          } 
        } else {
          
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.usage")).replace("%label%", label));
        }
      
      } else if (sender instanceof CommandBlock) {
        CommandBlock block = (CommandBlock)sender;
        if (args.length >= 2 && Bukkit.getPlayer(args[false]) != null) {
          Player p2 = Bukkit.getPlayer(args[0]);
          StringBuilder sb = new StringBuilder();
          for (int i = 1; i < args.length; i++) {
            sb.append(" ").append(args[i]);
          }
          String message = ChatColor.translateAlternateColorCodes('&', sb.toString().substring(1));
          p2.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.toMe").replace("%displayname%", "&c[CommandBlock] &r" + block.getName()).replace("%message%", message)));
        }
      
      } else if (sender instanceof ConsoleCommandSender) {
        ConsoleCommandSender console = (ConsoleCommandSender)sender;
        if (args.length >= 2) {
          if (Bukkit.getPlayer(args[false]) != null) {
            Player p2 = Bukkit.getPlayer(args[0]);
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
              sb.append(" ").append(args[i]);
            }
            String message = ChatColor.translateAlternateColorCodes('&', sb.toString().substring(1));
            console.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.meTo").replace("%displayname%", p2.getDisplayName()).replace("%message%", message).replace("%name%", p2.getName())));
            p2.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.toMe").replace("%displayname%", "&dCONSOLE").replace("%message%", message).replace("%name%", "CONSOLE")));
          } else {
            
            console.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("NotOnline").replace("%player%", args[0])));
          } 
        } else {
          
          console.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.usage")).replace("%label%", label));
        } 
      } else {
        
        sender.sendMessage(ChatColor.RED + "Invalid type.");
      } 
    }
    if (cmd.getName().equalsIgnoreCase("socialspy")) {
      if (sender.hasPermission("squad.socialspy")) {
        if (sender instanceof Player) {
          MessengerUser u = MessengerUser.getUser((Player)sender);
          if (u.isSocialSpyActive()) {
            u.setSocialSpyActive(false);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.socialspy.off")));
          } else {
            
            u.setSocialSpyActive(true);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.socialspy.on")));
          }
        
        } else if (Main.SOCIAL_SPY.contains(sender)) {
          Main.SOCIAL_SPY.remove(sender);
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.socialspy.off")));
        } else {
          
          Main.SOCIAL_SPY.add(sender);
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.socialspy.on")));
        } 
      } else {
        
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("No-Permissions")));
      } 
    }
    if (cmd.getName().equalsIgnoreCase("reply")) {
      Player p = (Player)sender;
      if (p.hasPermission("squad.msg")) {
        if (args.length == 0) {
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.reply.usage")).replace("%label%", label));
        }
        else if (Main.REPLY.containsKey(p)) {
          String p3 = ((Player)Main.REPLY.get(p)).getName();
          if (Bukkit.getPlayer(p3) != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
              sb.append(" ").append(args[i]);
            }
            String message = sb.toString().substring(1);
            if ((Utils.containsIP(message) || Utils.containsLink(message)) && !p.hasPermission("squad.sendLinks")) {
              p.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.mayNotSendLinks")));
              return true;
            } 
            p.performCommand("msg " + p3 + " " + message);
          } else {
            
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("NotOnline").replace("%player%", p3)));
          } 
        } else {
          
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.reply.noMessageSent")));
        } 
      } else {
        
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("No-Permissions")));
      } 
    } 
    if (cmd.getName().equalsIgnoreCase("blockmsg")) {
      if (sender instanceof Player) {
        Player p = (Player)sender;
        if (p.hasPermission("squad.msg")) {
          if (Main.BLOCK_MSG.contains(p)) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.blockmsg.deactivated")));
            Main.BLOCK_MSG.remove(p);
          } else {
            
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("Msg.blockmsg.activated")));
            Main.BLOCK_MSG.add(p);
          } 
        } else {
          
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', LangManager.storage.getString("No-Permissions")));
        } 
      } else {
        
        sender.sendMessage(ChatColor.RED + "Invalid type.");
      } 
    }
    return true;
  }
}
