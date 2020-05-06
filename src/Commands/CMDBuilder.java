package Commands;

import Utils.LangManager;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class CMDBuilder
  implements CommandExecutor, Listener
{
  public static ArrayList<Player> builders = new ArrayList();

  
  public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
    if (cmd.getName().equalsIgnoreCase("buildadd")) {
      if (!(sender instanceof Player)) {
        if (args.length == 1) {
          try {
            Player obj = Bukkit.getPlayer(args[0]);
            if (obj.isOnline()) {
              builders.add(obj);
              sender.sendMessage(LangManager.storage.getString("BuildMode.Actived").replace("%player%", obj.getName()).replace("&", "�"));
              Bukkit.getPlayer(args[0]).setGameMode(GameMode.CREATIVE);
              return true;
            } 
            sender.sendMessage("�cTarget is not online");
            return true;
          }
          catch (Exception e) {
            System.out.println();
          } 
        }
      } else {
        sender.sendMessage("You must be the Console");
        return true;
      } 
      return true;
    } 
    if (cmd.getName().equalsIgnoreCase("buildremove")) {
      if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
        if (args.length == 1) {
          try {
            Player obj = Bukkit.getPlayer(args[0]);
            if (builders.contains(obj)) {
              builders.remove(obj);
              sender.sendMessage(LangManager.storage.getString("BuildMode.Desable").replace("%player%", obj.getName()).replace("&", "�"));
              Bukkit.getPlayer(args[0]).setGameMode(GameMode.SURVIVAL);
              return true;
            } 
          } catch (Exception exception) {}
        }
      } else {
        sender.sendMessage("You must be the Console");
        return true;
      } 
      return true;
    } 
    return false;
  }
  
  @EventHandler
  public void onBreaking(BlockBreakEvent e) {
    if (!builders.contains(e.getPlayer())) {
      e.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onPlace(BlockPlaceEvent e) {
    if (!builders.contains(e.getPlayer())) {
      e.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onBucket(PlayerBucketEmptyEvent e) {
    if (!builders.contains(e.getPlayer())) {
      e.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onBucketFill(PlayerBucketFillEvent e) {
    if (!builders.contains(e.getPlayer())) {
      e.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onDrop(PlayerDropItemEvent e) {
    Player p = e.getPlayer();
    if (!builders.contains(p)) {
      e.setCancelled(true);
    }
  }
  
  @EventHandler(priority = EventPriority.HIGH)
  public void onInvClick(InventoryClickEvent e) {
    Player p = (Player)e.getWhoClicked();
    if (!builders.contains(p))
      e.setCancelled(true); 
  }
}
