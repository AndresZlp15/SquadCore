package Main;

import Events.FlyOnJoin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.weather.WeatherChangeEvent;


public class LobbyProtection
  implements Listener
{
  @EventHandler
  public void food(FoodLevelChangeEvent e) { e.setCancelled(true); }

  
  @EventHandler
  public void onChange(PlayerChangedWorldEvent e) {
    Player p = e.getPlayer();
    if (Main.worlds.contains(p.getWorld().getName())) {
      if (p.getWorld().hasStorm()) {
        p.getWorld().setStorm(false);
      }
      for (World worlds : Bukkit.getWorlds()) {
        if (worlds.hasStorm()) {
          worlds.setStorm(false);
        }
      } 
    } 
  }

  
  @EventHandler
  public void onBurn(BlockBurnEvent e) { e.setCancelled(true); }


  
  @EventHandler
  public void onDamageBlock(BlockDamageEvent e) { e.setCancelled(true); }

  
  @EventHandler
  public void onExplode(EntityExplodeEvent e) {
    if (e.getEntity().getLocation().getWorld().getName().equals("lobby")) {
      e.blockList().clear();
    }
  }

  
  @EventHandler
  public void onFade(BlockFadeEvent e) { e.setCancelled(true); }


  
  @EventHandler
  public void onFrom(BlockFormEvent e) { e.setCancelled(true); }

  
  @EventHandler
  public void onIgnite(BlockIgniteEvent e) {
    if (e.getCause().equals(BlockIgniteEvent.IgniteCause.EXPLOSION)) {
      e.setCancelled(true);
    } else {
      e.setCancelled(true);
    } 
  }

  
  @EventHandler
  public void onLeaves(LeavesDecayEvent e) { e.setCancelled(true); }


  
  @EventHandler
  public void onSpread(BlockSpreadEvent e) { e.setCancelled(true); }
  
  @EventHandler
  public void onWater(WeatherChangeEvent e) {
    if (!e.getWorld().hasStorm())
      e.setCancelled(true); 
  }
  
  @EventHandler
  public void onJoinHeal(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    p.setHealth(20.0D);
    p.setMaxHealth(20.0D);
    p.setFoodLevel(20);
    p.setGameMode(GameMode.valueOf(Main.getInstance().getConfig().getString("JoinGameMode").replace("ADVENTURE", GameMode.ADVENTURE.toString()).replace("CREATIVE", GameMode.CREATIVE.toString()).replace("SURVIVAL", GameMode.SURVIVAL.toString())));
    if (p.getLocation().getWorld().hasStorm());
  }


  
  @EventHandler(priority = EventPriority.HIGH)
  public void onPlayerLogin(PlayerLoginEvent event) {
    PlayerLoginEvent.Result result = event.getResult();
    if (result == PlayerLoginEvent.Result.KICK_FULL) {
      String message = Main.getInstance().getConfig().getString("ServerFull");
      message = FlyOnJoin.tk(message);
      event.setKickMessage(message);
    } 
    if (result == PlayerLoginEvent.Result.KICK_WHITELIST) {
      String message = Main.getInstance().getConfig().getString("Whitelist");
      message = FlyOnJoin.tk(message);
      event.setKickMessage(message);
    } 
  }
}

