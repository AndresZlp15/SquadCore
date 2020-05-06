package Events;

import Main.Main;
import java.io.File;
import java.util.Random;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;

public class CustomJoinsMessages
  implements Listener
{
  private static FileConfiguration storage = null;
  private static File storageFile = null;
  
  public static void load(Plugin plugin) {
    storageFile = new File(plugin.getDataFolder(), "customjoinsmessages.yml");
    if (!storageFile.exists()) {
      plugin.saveResource("customjoinsmessages.yml", false);
      Main.getInstance().getLogger().info("Generating config customjoinsmessages.yml");
    } else {
      Main.getInstance().getLogger().info("Loading config customjoinsmessages.yml");
    } 
    storage = YamlConfiguration.loadConfiguration(storageFile);
  }


  
  public boolean EnableOP(Boolean bole, Player p) { return p.isOp(); }

  
  private Color getColor(int i) {
    Color c = null;
    if (i == 1) {
      c = Color.AQUA;
    }
    if (i == 2) {
      c = Color.BLACK;
    }
    if (i == 3) {
      c = Color.BLUE;
    }
    if (i == 4) {
      c = Color.FUCHSIA;
    }
    if (i == 5) {
      c = Color.GRAY;
    }
    if (i == 6) {
      c = Color.GREEN;
    }
    if (i == 7) {
      c = Color.LIME;
    }
    if (i == 8) {
      c = Color.MAROON;
    }
    if (i == 9) {
      c = Color.NAVY;
    }
    if (i == 10) {
      c = Color.OLIVE;
    }
    if (i == 11) {
      c = Color.ORANGE;
    }
    if (i == 12) {
      c = Color.PURPLE;
    }
    if (i == 13) {
      c = Color.RED;
    }
    if (i == 14) {
      c = Color.SILVER;
    }
    if (i == 15) {
      c = Color.TEAL;
    }
    if (i == 16) {
      c = Color.WHITE;
    }
    if (i == 17) {
      c = Color.YELLOW;
    }
    return c;
  }
  
  @EventHandler
  public void onJoinMessage(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    if (Main.getInstance().getConfig().getBoolean("CustomJoins") == true) {
      e.setJoinMessage(null);
      Set<String> cs = storage.getConfigurationSection("CustomJoins").getKeys(false);
      for (String s : cs) {
        if (!storage.isSet("CustomJoins." + s + ".Permission") || !storage.isSet("CustomJoins." + s + ".isOP")) {
          Main.getInstance().getLogger().info("This Permission is not set.");
          return;
        } 
        if (storage.isSet("CustomJoins." + s + ".isOP")) {
          if (EnableOP(Boolean.valueOf(storage.getBoolean("CustomJoins." + s + ".isOP")), p) == true) {
            if (storage.isSet("CustomJoins." + s + ".JoinMessage")) {
              p.getServer().broadcastMessage(storage.getString("CustomJoins." + s + ".JoinMessage").replace("%player%", p.getName()).replace('&', '�'));
              if (storage.getBoolean("CustomJoins." + s + ".SpawnFirewors") == true) {
                Firework fw = (Firework)p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
                FireworkMeta fwm = fw.getFireworkMeta();
                
                Random r = new Random();
                int rt = r.nextInt(5) + 1;
                FireworkEffect.Type type = FireworkEffect.Type.BALL;
                if (rt == 1) type = FireworkEffect.Type.BALL; 
                if (rt == 2) type = FireworkEffect.Type.BALL_LARGE; 
                if (rt == 3) type = FireworkEffect.Type.BURST; 
                if (rt == 4) type = FireworkEffect.Type.CREEPER; 
                if (rt == 5) type = FireworkEffect.Type.STAR;
                
                int color1 = r.nextInt(17) + 1;
                int color2 = r.nextInt(17) + 1;
                Color c1 = getColor(color1);
                Color c2 = getColor(color2);
                
                FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
                fwm.addEffect(effect);
                int rp = r.nextInt(2) + 1;
                fwm.setPower(rp);
                fw.setFireworkMeta(fwm);
              }  return;
            }  continue;
          } 
          if (EnableOP(Boolean.valueOf(storage.getBoolean("CustomJoins." + s + ".isOP")), p) || storage.isSet("CustomJoins." + s + ".Permission"));
          
          continue;
        } 
        if (!storage.isSet("CustomJoins." + s + ".Permission")) {
          Bukkit.getConsoleSender().sendMessage("&cPermissions is not set!".replace('&', '�'));
          return;
        } 
        if (p.hasPermission(storage.getString("CustomJoins." + s + ".Permission")) && 
          storage.isSet("CustomJoins." + s + ".JoinMessage")) {
          p.getServer().broadcastMessage(storage.getString("CustomJoins." + s + ".JoinMessage").replace("%player%", p.getName()).replace('&', '�'));
          if (storage.getBoolean("CustomJoins." + s + ".SpawnFirewors") == true) {
            Firework fw = (Firework)p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();
            
            Random r = new Random();
            int rt = r.nextInt(5) + 1;
            FireworkEffect.Type type = FireworkEffect.Type.BALL;
            if (rt == 1) type = FireworkEffect.Type.BALL; 
            if (rt == 2) type = FireworkEffect.Type.BALL_LARGE; 
            if (rt == 3) type = FireworkEffect.Type.BURST; 
            if (rt == 4) type = FireworkEffect.Type.CREEPER; 
            if (rt == 5) type = FireworkEffect.Type.STAR;
            
            int color1 = r.nextInt(17) + 1;
            int color2 = r.nextInt(17) + 1;
            Color c1 = getColor(color1);
            Color c2 = getColor(color2);
            
            FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
            fwm.addEffect(effect);
            int rp = r.nextInt(2) + 1;
            fwm.setPower(rp);
            fw.setFireworkMeta(fwm);
          } 
          return;
        } 
      } 
    } 
  }


  
  @EventHandler
  public void onQuitMessage(PlayerQuitEvent e) {
    Player p = e.getPlayer();
    if (Main.getInstance().getConfig().getBoolean("CustomJoins") == true) {
      e.setQuitMessage(null);
      Set<String> cs = storage.getConfigurationSection("CustomJoins").getKeys(false);
      for (String s : cs) {
        if (!storage.isSet("CustomJoins." + s + ".Permission") || !storage.isSet("CustomJoins." + s + ".isOP")) {
          Main.getInstance().getLogger().info("This Permission is not set.");
          return;
        } 
        if (storage.isSet("CustomJoins." + s + ".isOP")) {
          if (EnableOP(Boolean.valueOf(storage.getBoolean("CustomJoins." + s + ".isOP")), p) == true) {
            if (storage.isSet("CustomJoins." + s + ".QuitMessage")) {
              p.getServer().broadcastMessage(storage.getString("CustomJoins." + s + ".QuitMessage").replace("%player%", p.getName()).replace('&', '�')); return;
            }  continue;
          } 
          if (!EnableOP(Boolean.valueOf(storage.getBoolean("CustomJoins." + s + ".isOP")), p) && storage.isSet("CustomJoins." + s + ".Permission") && 
            p.hasPermission(storage.getString("CustomJoins." + s + ".Permission")) && 
            storage.isSet("CustomJoins." + s + ".QuitMessage")) {
            p.getServer().broadcastMessage(storage.getString("CustomJoins." + s + ".QuitMessage").replace("%player%", p.getName()).replace('&', '�'));
            return;
          } 
        } 
      } 
    } 
  }
}
