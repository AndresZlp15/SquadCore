package LobbySpawn;

import Main.Main;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;




public class VoidSpawn
  implements Listener
{
  @EventHandler
  public void onDamage(EntityDamageEvent e) {
    if (Main.getInstance().getConfig().getBoolean("VoidSpawn") == true && 
      e.getEntity() instanceof Player && e.getEntity().getType().equals(EntityType.PLAYER))
      if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
        Player p = (Player)e.getEntity();
        CmdSpawn.teleportJoinSpawn(p);
        e.setDamage(0.0D);
        e.setCancelled(true);
      } else {
        
        e.setCancelled(true);
      }  
  }
}
