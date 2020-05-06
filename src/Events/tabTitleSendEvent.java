package Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class tabTitleSendEvent extends Event {
  private Player player;
  private static HandlerList handlers = new HandlerList();
  private String header;
  
  public tabTitleSendEvent(Player player, String header, String footer) {
    this.cancelled = false;

    
    this.player = player;
    this.header = header;
    this.footer = footer;
  }
  private String footer;
  private boolean cancelled;
  
  public HandlerList getHandlers() { return handlers; }


  
  public static HandlerList getHandlerList() { return handlers; }


  
  public Player getPlayer() { return this.player; }


  
  public String getHeader() { return this.header; }


  
  public void setHeader(String header) { this.header = header; }


  
  public String getFooter() { return this.footer; }


  
  public void setFooter(String footer) { this.footer = footer; }


  
  public boolean isCancelled() { return this.cancelled; }


  
  public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }
}

