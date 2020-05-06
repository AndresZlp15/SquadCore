package Utils;

import java.lang.reflect.Constructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TitleAPI {
  public static void sendTitle(Player player, String title) {
    try {
      title = ChatColor.translateAlternateColorCodes('&', title);
      Object packetTitle = getMcClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
      Object objectTitle = getMcClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
      Constructor<?> cTitle = getMcClass("PacketPlayOutTitle").getConstructor(new Class[] { getMcClass("PacketPlayOutTitle").getDeclaredClasses()[0], getMcClass("IChatBaseComponent") });
      sendPacket(player, cTitle.newInstance(new Object[] { packetTitle, objectTitle }));
    }
    catch (IllegalAccessException|IllegalArgumentException|InstantiationException|NoSuchFieldException|NoSuchMethodException|SecurityException|java.lang.reflect.InvocationTargetException illegalAccessException) {}
  }

  
  public static void sendSubTitle(Player player, String subtitle) {
    try {
      subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
      Object packetTitle = getMcClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
      Object objectTitle = getMcClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + subtitle + "\"}" });
      Constructor<?> cTitle = getMcClass("PacketPlayOutTitle").getConstructor(new Class[] { getMcClass("PacketPlayOutTitle").getDeclaredClasses()[0], getMcClass("IChatBaseComponent") });
      sendPacket(player, cTitle.newInstance(new Object[] { packetTitle, objectTitle }));
    }
    catch (IllegalAccessException|IllegalArgumentException|InstantiationException|NoSuchFieldException|NoSuchMethodException|SecurityException|java.lang.reflect.InvocationTargetException illegalAccessException) {}
  }

  
  public static void clearTitle(Player player) {
    sendTitle(player, "");
    sendSubTitle(player, "");
  }
  
  private static Class<?> getMcClass(String name) {
    String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    try {
      return Class.forName("net.minecraft.server." + version + "." + name);
    }
    catch (ClassNotFoundException ex) {
      return null;
    } 
  }
  
  private static void sendPacket(Player player, Object packet) {
    try {
      Object handle = player.getClass().getMethod("getHandle", (Class[])new Class[0]).invoke(player, new Object[0]);
      Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
      playerConnection.getClass().getMethod("sendPacket", new Class[] { getMcClass("Packet") }).invoke(playerConnection, new Object[] { packet });
    }
    catch (IllegalAccessException|IllegalArgumentException|NoSuchFieldException|NoSuchMethodException|SecurityException|java.lang.reflect.InvocationTargetException illegalAccessException) {}
  }
}
