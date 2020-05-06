package Utils;

import Main.Main;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;

public class Utils {
  public static String color(String input) {
    if (input == null) {
      return null;
    }
    return ChatColor.translateAlternateColorCodes('&', input);
  }
  
  public static String addDefaultColor(String input) {
    if (input == null) {
      return null;
    }
    if (!input.startsWith("�")) {
      input = "�f" + input;
    }
    return input;
  }

  
  public static boolean containsLink(String message) { return Pattern.compile("^((https?|ftp):\\/\\/|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([\\/?].*)?$").matcher(message).find(); }


  
  public static boolean containsIP(String message) { return Pattern.compile("(?i)(((([a-zA-Z0-9-]+\\.)+(de|eu|com|net|to|es|ga|us|nu|tk|io|co|org|gs|xyz|me|info|biz|tv))|([0-9]{1,3}\\.){3}[0-9]{1,3})(\\:[0-9]{2,5})?)").matcher(message).find(); }

  
  public static boolean connectToBungeeServer(Player player, String server) {
    try {
      Messenger messenger = Bukkit.getMessenger();
      if (!messenger.isOutgoingChannelRegistered(Main.getInstance(), "BungeeCord")) {
        messenger.registerOutgoingPluginChannel(Main.getInstance(), "BungeeCord");
      }
      if (server.length() == 0) {
        player.sendMessage("�cTarget server was \"\" (empty string) cannot connect to it.");
        return false;
      } 
      ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(byteArray);
      out.writeUTF("Connect");
      out.writeUTF(server);
      player.sendPluginMessage(Main.getInstance(), "BungeeCord", byteArray.toByteArray());
    }
    catch (IOException ex) {
      ex.printStackTrace();
      player.sendMessage("�cAn exception has occurred. Please notify OPs about this. (They should look at the console).");
      Main.getInstance().getLogger().log(Level.WARNING, "Could not handle BungeeCord command from {0}: tried to connect to \"{1}\".", new Object[] { player.getName(), server });
      return false;
    } 
    return true;
  }
}
