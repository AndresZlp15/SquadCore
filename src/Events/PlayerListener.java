package Events;

import Main.Main;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    String world = player.getWorld().getName();
    if (Main.getInstance().getConfig().getBoolean("Join-Items") == true) {
      if (clearInventoryOnJoin) {
        player.getInventory().clear();
        player.getInventory().setArmorContents((ItemStack[])null);
      } 
      for (JoinItem item : joinitems.items) {
        if (!item.hasPerm(player) || !item.isAllowedInWorld(world) || (
          item.isOnlyOnFirstJoin() && player.hasPlayedBefore())) {
          continue;
        }
        item.giveTo(player, false);
      } 
    } else {
      return;
    } 
  }
  
  public static boolean clearInventoryOnJoin = true;
  
  @EventHandler
  public void onDeath(PlayerDeathEvent event) { if (Main.getInstance().getConfig().getBoolean("Join-Items") == true) {
      Iterator<ItemStack> iter = event.getDrops().iterator();
      while (iter.hasNext()) {
        ItemStack next = (ItemStack)iter.next();
        for (JoinItem item : joinitems.items) {
          if (item.isSimilar(next)) {
            iter.remove();
          }
        } 
      } 
    } else {
      return;
    }  }

  
  @EventHandler
  public void onRespawn(PlayerRespawnEvent event) {
    final Player player = event.getPlayer();
    if (Main.getInstance().getConfig().getBoolean("Join-Items") == true) {
      Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable()
          {
            public void run() {
              if (!player.isOnline()) {
                return;
              }
              String world = player.getWorld().getName();
              for (JoinItem item : joinitems.items) {
                if (item.hasPerm(player) && item.isGiveOnRespawn() && item.isAllowedInWorld(world)) {
                  item.giveTo(player, false);
                }
              } 
            }
          }1L);
    }
  }
  
  @EventHandler(ignoreCancelled = true)
  public void onDrop(PlayerDropItemEvent event) {
    ItemStack drop = event.getItemDrop().getItemStack();
    if (Main.getInstance().getConfig().getBoolean("Join-Items") == true) {
      for (JoinItem item : joinitems.items) {
        if (item.isSimilar(drop) && !item.isDroppable()) {
          event.setCancelled(true);
          final Player player = event.getPlayer();
          Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable()
              {
                public void run() {
                  player.updateInventory();
                }
              },  1L);
        } 
      } 
    } else {
      return;
    } 
  }
  
  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    if (Main.getInstance().getConfig().getBoolean("Join-Items") == true && (
      event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
      ItemStack itemInHand = event.getItem();
      for (JoinItem item : joinitems.items) {
        if (item.isSimilar(itemInHand)) {
          event.setCancelled(true);
          item.executeCommands(event.getPlayer());
          event.getPlayer().updateInventory();
        } 
      } 
    } 
  }

  
  @EventHandler
  public void changeWorld(PlayerChangedWorldEvent event) {
    final Player whoSwitched = event.getPlayer();
    if (Main.getInstance().getConfig().getBoolean("Join-Items") == true) {
      Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable()
          {
            public void run() {
              if (!whoSwitched.isOnline()) {
                return;
              }
              String world = whoSwitched.getWorld().getName();
              for (JoinItem item : joinitems.items) {
                if (!item.isAllowedInWorld(world)) {
                  item.removeFrom(whoSwitched); continue;
                } 
                if (!item.isGiveOnWorldChange() || !item.hasPerm(whoSwitched)) {
                  continue;
                }
                item.giveTo(whoSwitched, false);
              } 
            }
          }1L);
    }
  }

  
  @EventHandler
  public void inventoryClick(InventoryClickEvent event) {
    if (Main.getInstance().getConfig().getBoolean("Join-Items") == true) {
      ItemStack item = event.getCurrentItem();
      if (item == null) {
        return;
      }
      for (JoinItem joinItem : joinitems.items) {
        if (joinItem.isSimilar(item) && joinItem.isMovementBlocked())
          event.setCancelled(true); 
      } 
    } 
  }
}
