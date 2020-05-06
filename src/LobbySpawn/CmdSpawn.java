package LobbySpawn;

import Events.FlyOnJoin;
import Main.Main;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class CmdSpawn
  implements CommandExecutor, Listener {
  public static FileConfiguration storage = null;
  public static File storageFile = null;
  
  public static void load(Plugin plugin) {
    storageFile = new File(plugin.getDataFolder(), "spawn.yml");
    if (!storageFile.exists()) {
      plugin.saveResource("spawn.yml", false);
      Main.getInstance().getLogger().info("Generating config spawn.yml");
    } else {
      Main.getInstance().getLogger().info("Loading config spawn.yml");
    } 
    storage = YamlConfiguration.loadConfiguration(storageFile);
  }
  
  public static void teleportJoinSpawn(Player p) {
    World w = Bukkit.getServer().getWorld(storage.getString("Spawn.world"));
    float yaw = (float)storage.getDouble("Spawn.yaw");
    float pitch = (float)storage.getDouble("Spawn.pitch");
    double x = storage.getDouble("Spawn.x");
    double y = storage.getDouble("Spawn.y");
    double z = storage.getDouble("Spawn.z");
    Location l = new Location(w, x, y, z);
    l.setYaw(yaw);
    p.getLocation().setPitch(pitch);
    p.teleport(l);
  }


  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("setspawn")) {
      Player p = (Player)sender;
      if (sender.hasPermission("squad.founder")) {
        storage.set("Spawn.pitch", Double.valueOf(p.getLocation().getPitch()));
        storage.set("Spawn.yaw", Double.valueOf(p.getLocation().getYaw()));
        storage.set("Spawn.x", Double.valueOf(p.getLocation().getX()));
        storage.set("Spawn.y", Double.valueOf(p.getLocation().getY()));
        storage.set("Spawn.z", Double.valueOf(p.getLocation().getZ()));
        storage.set("Spawn.world", p.getWorld().getName());
        try {
          storage.save(storageFile);
        } catch (IOException ex) {
          Logger.getLogger(CmdSpawn.class.getName()).log(Level.SEVERE, null, ex);
        } 
        p.sendMessage(storage.getString("SetSpawnMsj").replace("&", "�"));
        return true;
      } 
      p.sendMessage(FlyOnJoin.tk(Main.getInstance().getConfig().getString("No-Permissions")));
      
      return true;
    } 
    if (cmd.getName().equalsIgnoreCase("spawn")) {
      Player p = (Player)sender;
      if (p.hasPermission("squad.user")) {
        float yaw = (float)storage.getDouble("Spawn.yaw");
        float pitch = (float)storage.getDouble("Spawn.pitch");
        double x = storage.getDouble("Spawn.x");
        double y = storage.getDouble("Spawn.y");
        double z = storage.getDouble("Spawn.z");
        World w = Bukkit.getServer().getWorld(storage.getString("Spawn.world"));
        Location l = new Location(w, x, y, z);
        l.setYaw(yaw);
        p.getLocation().setPitch(pitch);
        p.teleport(l);
        p.sendMessage(storage.getString("EntrySpawn").replace("&", "�"));
      } 
      return true;
    } 
    return false;
  }
  
  @EventHandler
  public void JoinSpawn(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    if (storage.getDouble("Spawn.yaw") == 0.0D && 
      storage.getDouble("Spawn.pitch") == 0.0D && 
      storage.getDouble("Spawn.x") == 0.0D && 
      storage.getDouble("Spawn.y") == 0.0D && 
      storage.getDouble("Spawn.z") == 0.0D && 
      storage.getString("Spawn.world") == null) {
      Main.getInstance().getLogger().info("Spawn is not set!");


      
      return;
    } 

    
    if (storage.getBoolean("JoinSpawn") == true)
      teleportJoinSpawn(p); 
  }
}
