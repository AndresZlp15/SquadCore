package Events;

import Main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class Damageall
  implements Listener {
  @EventHandler
  public void onDamage(EntityDamageEvent e) {
    if (!Main.getInstance().getConfig().getBoolean("Allow-Damage-all"))
      e.setCancelled(true); 
  }
}
