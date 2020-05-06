package Main;

import com.earth2me.essentials.Essentials;
import Commands.CMDBuilder;
import Commands.CMDChat;
import Commands.CMDClearChat;
import Commands.CMDGamemode;
import Commands.CMDMsg;
import Commands.CMDReload;
import Commands.CMDVanish;
import Events.AntiSpam;
import Events.Damageall;
import Events.JoinFullBypass;
import Events.JoinListener;
import Events.PlayerListener;
import Events.UnknownCMD;
import Events.VanishListener;
import Events.joinitems;
import Events.joinmotd;
import Events.CustomJoinsMessages;
import Events.FlyOnJoin;
import Events.tabTitleSendEvent;
import Events.tablist;
import Events.titlejoin;
import LobbySpawn.CmdSpawn;
import LobbySpawn.VoidSpawn;
import Scoreboard.BoardTask;
import Scoreboard.TablistTask;
import Utils.LangManager;
import com.onarandombox.MultiverseCore.MultiverseCore;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;










public class Main
  extends JavaPlugin
  implements Listener
{
  public static ArrayList<Player> BLOCK_MSG = new ArrayList<Player>();
  public static HashMap<Player, Player> REPLY = new HashMap<Player, Player>();
  public static ArrayList<CommandSender> SOCIAL_SPY = new ArrayList<CommandSender>();
  public static ArrayList<MessengerUser> USER_STORAGE = new ArrayList<MessengerUser>();








  
  private Boolean papi = Boolean.valueOf(getConfig().getBoolean("PlaceholderAPI"));
  private Boolean mvdw = Boolean.valueOf(getConfig().getBoolean("MVdWPlaceholderAPI"));
  public Chat chat = null;
  public Economy econ = null;
  public MultiverseCore mv = null;
  public Essentials essentials = null;
  private static Main instance;
  public static Permission perms;
  
  public static Main getInstance() { return instance; }
  
  public static boolean mysql;
  
  public void onEnable() {
    instance = this;
    getLogger().info("Plugin has been started!");
    configura(this);
    CmdSpawn.load(this);
    BoardTask.load(this);
    CustomJoinsMessages.load(this);
    joinitems.JoinItems(this);
    joinitems.load();
    tablist.load(this);
    LangManager.load(this);
    getServer().getPluginManager().registerEvents(this, this);
    getServer().getPluginManager().registerEvents(new tablist(this), this);
    getServer().getPluginManager().registerEvents(new Damageall(), this);
    getServer().getPluginManager().registerEvents(new joinmotd(this), this);
    getServer().getPluginManager().registerEvents(new titlejoin(this), this);
    getServer().getPluginManager().registerEvents(new LobbyProtection(), this);
    getServer().getPluginManager().registerEvents(new CMDChat(), this);
    getServer().getPluginManager().registerEvents(new CMDBuilder(), this);
    getServer().getPluginManager().registerEvents(new FlyOnJoin(), this);
    getServer().getPluginManager().registerEvents(new joinitems(), this);
    getServer().getPluginManager().registerEvents(new CmdSpawn(), this);
    getServer().getPluginManager().registerEvents(new CMDVanish(), this);
    getServer().getPluginManager().registerEvents(new UnknownCMD(), this);
    getServer().getPluginManager().registerEvents(new JoinFullBypass(this), this);
    getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    getServer().getPluginManager().registerEvents(new CustomJoinsMessages(), this);
    getServer().getPluginManager().registerEvents(new JoinListener(), this);
    getServer().getPluginManager().registerEvents(new VanishListener(), this);
    getServer().getPluginManager().registerEvents(new VoidSpawn(), this);
    getServer().getPluginManager().registerEvents(new AntiSpam(), this);
    setupPermissions();
    setupEssentials();
    setupEconomy();
    checkrain();
    getCommand("gamemode").setExecutor(new CMDGamemode());
    getCommand("gm").setExecutor(new CMDGamemode());
    getCommand("setspawn").setExecutor(new CmdSpawn());
    getCommand("spawn").setExecutor(new CmdSpawn());
    getCommand("Chat").setExecutor(new CMDChat());
    getCommand("cc").setExecutor(new CMDClearChat());
    getCommand("buildadd").setExecutor(new CMDBuilder());
    getCommand("buildremove").setExecutor(new CMDBuilder());
    getCommand("fly").setExecutor(new FlyOnJoin());
    getCommand("vanish").setExecutor(new CMDVanish());
    getCommand("msg").setExecutor(new CMDMsg());
    getCommand("reply").setExecutor(new CMDMsg());
    getCommand("blockmsg").setExecutor(new CMDMsg());
    getCommand("socialspy").setExecutor(new CMDMsg());
    getCommand("squadreload").setExecutor(new CMDReload());
    TablistTask tt = new TablistTask();
    tt.runTaskTimerAsynchronously(get(), (tablist.storage.getInt("Time-Update") * 20), (tablist.storage.getInt("Time-Update") * 20));
    (new BukkitRunnable()
      {
        public void run() {
          for (Player p : Bukkit.getOnlinePlayers()) {
            BoardTask.contentBoard(p);
          }
        }
      }).runTaskTimerAsynchronously(this, 0L, (20 * BoardTask.storage.getInt("Time-Update")));
    getLogger().log(Level.INFO, "Version: {0} Activated", getInstance().getDescription().getVersion());
    if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
      Bukkit.getServer().getLogger().log(Level.INFO, "Vault Hooked version: {0}", Bukkit.getPluginManager().getPlugin("Vault").getDescription().getVersion());
    } else {
      
      Bukkit.getServer().getLogger().log(Level.INFO, "This Plugin requires Vault");
      Bukkit.getServer().getLogger().log(Level.INFO, "Vault Download: https://dev.bukkit.org/projects/vault");
    } 
    if (this.papi.booleanValue()) {
      if (Bukkit.getPluginManager().isPluginEnabled("PlaceHolderAPI")) {
        Bukkit.getServer().getLogger().log(Level.INFO, "Hooked PlaceholderAPI version: {0}", PlaceholderAPIPlugin.getInstance().getDescription().getVersion());
      } else {
        
        Bukkit.getServer().getLogger().log(Level.INFO, "[SquadCore]PlaceholderAPI Download: https://www.spigotmc.org/resources/placeholderapi.6245/");
      } 
    } else {
      
      Bukkit.getServer().getLogger().log(Level.INFO, "PlaceholderAPI desactived in config.yml");
    } 
    if (this.mvdw.booleanValue()) {
      if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
        Bukkit.getServer().getLogger().log(Level.INFO, "Hooked MVdWPlaceholderAPI version {0}", Bukkit.getPluginManager().getPlugin("MVdWPlaceholderAPI").getDescription().getVersion());
      }
    } else {
      
      Bukkit.getServer().getLogger().log(Level.INFO, "MVdWPlaceholderAPI desactived in config.yml");
    } 
    getConfig().options().copyDefaults(true);
    saveDefaultConfig();
    reloadConfig();
  }
  public static List<String> worlds; PluginManager pm;
  public static void configura(Plugin plugin) {
    if (!getInstance().getConfig().getString("Version").equals(getInstance().getDescription().getVersion())) {
      File storageFile1 = null;
      storageFile1 = new File(plugin.getDataFolder(), "config.yml");
      storageFile1.delete();
      
      getInstance().getLogger().info("this version is Old, Generating new config config.yml to version 1.0");
      plugin.saveResource("config.yml", false);
      getInstance().getLogger().info("Version 1.0 update");
    } else {
      getInstance().getLogger().info("Version 1.0 update");
    } 
  }


  
  public boolean maySendMessage(Player p, Player p2) {
    boolean b = true;
    if (!p.hasPermission("squad.msgblock.exempt") && BLOCK_MSG.contains(p2)) {
      b = false;
    }
    return b;
  }
  
  public static String setPlaceholders(Player p, String s) {
    s = PlaceholderAPI.setPlaceholders(p, s);
    s = s.replace("%player%", p.getName());
    return s.replace("%player-displayname%", p.getDisplayName());
  }

  
  public boolean setupPermissions() {
    RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
    perms = (Permission)rsp.getProvider();
    return (perms != null);
  }
  
  public boolean setupEssentials() {
    Essentials ess = (Essentials)Bukkit.getServer().getPluginManager().getPlugin("Essentials");
    this.essentials = ess;
    return (this.essentials != null);
  }
  
  public void checkrain() {
    for (World worlds : Bukkit.getWorlds()) {
      if (worlds.hasStorm()) {
        worlds.setStorm(false);
      }
    } 
  }

  
  public boolean setupEconomy() {
    if (getServer().getPluginManager().getPlugin("Vault") == null) {
      return false;
    }
    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp == null) {
      return false;
    }
    this.econ = (Economy)rsp.getProvider();
    return (this.econ != null);
  }
  
  public void updateTab(Player p) {
    String headline = setPlaceholders(p, tablist.storage.getString("Header").replace("&", "�").replace("%player%", p.getName()));
    String footer = setPlaceholders(p, tablist.storage.getString("Footer").replace("&", "�").replace("%player%", p.getName()));
    sendTabTitle(p, headline, footer);
  }
  
  public static void sendTabTitle(Player player, String header, String footer) {
    if (header == null) {
      header = "";
    }
    header = FlyOnJoin.tk(header);
    if (footer == null) {
      footer = "";
    }
    footer = FlyOnJoin.tk(footer);
    tabTitleSendEvent tabTitleSendEvent = new tabTitleSendEvent(player, header, footer);
    Bukkit.getPluginManager().callEvent(tabTitleSendEvent);
    if (tabTitleSendEvent.isCancelled()) {
      return;
    }
    try {
      Object tabHeader = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + header + "\"}" });
      Object tabFooter = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + footer + "\"}" });
      Constructor<?> titleConstructor = getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor((Class[])new Class[0]);
      Object packet = titleConstructor.newInstance(new Object[0]);
      Field aField = packet.getClass().getDeclaredField("a");
      aField.setAccessible(true);
      aField.set(packet, tabHeader);
      Field bField = packet.getClass().getDeclaredField("b");
      bField.setAccessible(true);
      bField.set(packet, tabFooter);
      sendPacket(player, packet);
    }
    catch (IllegalAccessException|IllegalArgumentException|InstantiationException|NoSuchFieldException|NoSuchMethodException|SecurityException|java.lang.reflect.InvocationTargetException illegalAccessException) {}
  }
  
  public static Class<?> getNMSClass(String name) {
    String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    try {
      return Class.forName("net.minecraft.server." + version + "." + name);
    }
    catch (ClassNotFoundException ex) {
      return null;
    } 
  }
  
  public static void sendPacket(Player player, Object packet) {
    try {
      Object handle = getNMSPlayer(player);
      Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
      playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(playerConnection, new Object[] { packet });
    }
    catch (IllegalAccessException|IllegalArgumentException|NoSuchFieldException|NoSuchMethodException|SecurityException|java.lang.reflect.InvocationTargetException illegalAccessException) {}
  }
  
  private static Object getNMSPlayer(Player p) {
    try {
      return p.getClass().getMethod("getHandle", (Class[])new Class[0]).invoke(p, new Object[0]);
    }
    catch (IllegalAccessException|IllegalArgumentException|NoSuchMethodException|SecurityException|java.lang.reflect.InvocationTargetException ex) {
      return null;
    } 
  }

  
  public void onDisable() {
    getLogger().info("Plugin has been disabled!");
    saveDefaultConfig();
  }

  
  public static Main get() { return getInstance(); }

  
  static  {
    perms = null;
    worlds = new ArrayList<String>();
  }
}