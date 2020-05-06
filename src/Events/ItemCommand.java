package Events;

import Main.Main;
import Utils.Utils;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class ItemCommand
{
  private String command;
  private Type type;
  
  private ItemCommand(String command, Type type) {
    this.command = command;
    this.type = type;
  }

  
  public String getCommand() { return this.command; }

  
  public static ItemCommand[] arrayFromString(String input) {
    if (input == null || input.length() == 0) {
      return new ItemCommand[] { new ItemCommand("", Type.DEFAULT) };
    }
    String[] commandStrings = input.split(";");
    ItemCommand[] commands = new ItemCommand[commandStrings.length];
    for (int i = 0; i < commandStrings.length; i++) {
      commands[i] = fromString(commandStrings[i].trim());
    }
    return commands;
  }
  
  public static ItemCommand fromString(String input) {
    if (input == null || input.length() == 0) {
      return new ItemCommand("", Type.DEFAULT);
    }
    input = input.trim();
    Type type = Type.DEFAULT;
    if (input.startsWith("console:")) {
      input = input.substring(8);
      type = Type.CONSOLE;
    } 
    if (input.startsWith("op:")) {
      input = input.substring(3);
      type = Type.OP;
    } 
    if (input.startsWith("server:")) {
      input = input.substring(7);
      type = Type.SERVER;
    } 
    if (input.startsWith("tell:")) {
      input = input.substring(5);
      type = Type.TELL;
    } 
    input = input.trim();
    input = Utils.color(input);
    return new ItemCommand(input, type);
  }
  public void execute(Player player) {
    boolean isOp;
    if (this.command == null || this.command.length() == 0) {
      return;
    }
    if (this.command.contains("%player%")) {
      this.command = this.command.replace("%player%", player.getName());
    }
    if (this.command.contains("%world%")) {
      this.command = this.command.replace("%world%", player.getWorld().getName());
    }
    switch (this.type) {
      case CONSOLE:
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.command);
        return;
      
      case OP:
        isOp = player.isOp();
        player.setOp(true);
        try {
          player.chat("/" + this.command);
        }
        catch (Exception exception) {}
        try {
          player.setOp(isOp);
        }
        catch (Exception danger) {
          player.setOp(false);
          Main.getInstance().getLogger().log(Level.SEVERE, "An exception has occurred while removing {0} from OPs, while executing a command. OP or not, he was removed from OPs!", player.getName());
        } 
        return;
      
      case SERVER:
        Utils.connectToBungeeServer(player, this.command);
        return;
      
      case TELL:
        player.sendMessage(this.command);
        return;
    } 
    
    player.chat("/" + this.command);
  }



  
  public enum Type
  {
    DEFAULT("DEFAULT", 0),
    CONSOLE("CONSOLE", 1),
    OP("OP", 2),
    SERVER("SERVER", 3),
    TELL("TELL", 4);
  }
}
