package Main;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessengerUser
{
  private String uuid;
  private boolean socialSpyActive;
  
  public static MessengerUser getUser(Player p) {
    for (MessengerUser u : Main.USER_STORAGE) {
      if (u.uuid.equals(p.getUniqueId().toString())) {
        return u;
      }
    } 
    MessengerUser u2 = new MessengerUser(p);
    Main.USER_STORAGE.add(u2);
    return u2;
  }


  
  public MessengerUser(Player p) { this.uuid = p.getUniqueId().toString(); }


  
  public Player getPlayer() { return Bukkit.getPlayer(UUID.fromString(this.uuid)); }


  
  public boolean isSocialSpyActive() { return this.socialSpyActive; }


  
  public void setSocialSpyActive(boolean socialSpyActive) { this.socialSpyActive = socialSpyActive; }
}
