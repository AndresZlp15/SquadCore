package Events;

import Main.Main;
import Utils.Utils;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;






public class JoinItem
{
  private Material material;
  private ItemCommand[] commands;
  private Short dataValue;
  private String customName;
  private String permission;
  private Integer slot;
  private boolean onlyOnFirstJoin;
  private boolean blockMovement;
  
  public JoinItem(Material mat) {
    this.dataValue = null;
    this.customName = null;
    this.permission = null;
    this.slot = null;
    this.onlyOnFirstJoin = false;
    this.blockMovement = false;
    this.droppable = false;
    this.giveOnRespawn = true;
    this.cooldownSeconds = 0;
    this.useCooldown = false;
    this.playersInCooldown = new ArrayList();
    this.removeInWorlds = new ArrayList();
    this.material = mat;
  }
  private boolean droppable; private boolean giveOnRespawn; private List<String> lore; private int cooldownSeconds; private boolean useCooldown; private List<String> playersInCooldown; private boolean giveOnWorldChange; private List<String> removeInWorlds;
  
  public String getCustomName() { return this.customName; }

  
  public void setCustomName(String customName) {
    if (customName == null || customName.length() == 0) {
      this.customName = null;
      return;
    } 
    this.customName = Utils.addDefaultColor(Utils.color(customName));
  }
  
  public void setLore(List<String> lore) {
    if (lore == null || lore.isEmpty()) {
      this.lore = null;
      return;
    } 
    this.lore = new ArrayList();
    for (String s : lore) {
      s = Utils.color(s);
      s = Utils.addDefaultColor(s);
      this.lore.add(s);
    } 
  }
  
  public void setSlot(Integer slot) {
    if (slot == null || slot.intValue() == 0) {
      this.slot = null;
      return;
    } 
    if (slot.intValue() < 1) {
      slot = Integer.valueOf(1);
    }
    if (slot.intValue() > 9) {
      slot = Integer.valueOf(9);
    }
    slot = Integer.valueOf(slot.intValue() - 1);
    this.slot = slot;
  }
  
  public void setPerm(String permission) {
    if (permission == null || permission.length() == 0) {
      this.permission = null;
    } else {
      
      this.permission = permission;
    } 
  }
  
  public boolean isSimilar(ItemStack item) {
    if (item == null) {
      return false;
    }
    if (item.getType() != this.material) {
      return false;
    }
    ItemMeta meta = item.getItemMeta();
    if (this.customName == null) {
      if (meta.hasDisplayName()) {
        return false;
      }
    } else {
      
      if (!meta.hasDisplayName()) {
        return false;
      }
      if (!meta.getDisplayName().equals(this.customName)) {
        return false;
      }
    } 
    return (this.dataValue == null || this.dataValue.shortValue() == item.getDurability());
  }
  
  public void removeFrom(Player player) {
    PlayerInventory inv = player.getInventory();
    ItemStack[] contents = inv.getContents();
    for (int i = 0; i < contents.length; i++) {
      if (isSimilar(contents[i])) {
        inv.setItem(i, new ItemStack(Material.AIR));
      }
    } 
    if (isSimilar(inv.getHelmet())) {
      inv.setHelmet(new ItemStack(Material.AIR));
    }
    if (isSimilar(inv.getChestplate())) {
      inv.setChestplate(new ItemStack(Material.AIR));
    }
    if (isSimilar(inv.getLeggings())) {
      inv.setLeggings(new ItemStack(Material.AIR));
    }
    if (isSimilar(inv.getBoots())) {
      inv.setBoots(new ItemStack(Material.AIR));
    }
  }
  
  public void giveTo(Player player, boolean notifyFailure) {
    PlayerInventory inv = player.getInventory();
    if (inv.firstEmpty() == -1) {
      if (notifyFailure) {
        player.sendMessage("�cYour inventory is full.");
      }
      return;
    } 
    ItemStack[] contents;
    for (int length = contents = inv.getContents().length, i = 0; i < length; i++) {
      ItemStack itemStack = contents[i];
      if (isSimilar(itemStack)) {
        if (notifyFailure) {
          player.sendMessage("�cYour already own this item.");
        }
        return;
      } 
    } 
    ItemStack[] armorContents;
    for (int length2 = armorContents = inv.getArmorContents().length, j = 0; j < length2; j++) {
      ItemStack itemStack = armorContents[j];
      if (isSimilar(itemStack)) {
        if (notifyFailure) {
          player.sendMessage("�cYour already own this item.");
        }
        return;
      } 
    } 
    ItemStack item = new ItemStack(this.material);
    if (this.dataValue != null) {
      item.setDurability(this.dataValue.shortValue());
    }
    ItemMeta meta = item.getItemMeta();
    if (this.customName != null) {
      meta.setDisplayName(this.customName);
    }
    if (this.lore != null) {
      meta.setLore(this.lore);
    }
    item.setItemMeta(meta);
    if (this.slot != null) {
      ItemStack previous = inv.getItem(this.slot.intValue());
      inv.setItem(this.slot.intValue(), item);
      if (previous != null) {
        inv.addItem(new ItemStack[] { previous });
      }
    } else {
      
      inv.addItem(new ItemStack[] { item });
    } 
  }
  
  public void executeCommands(Player player) {
    if (this.commands != null && this.commands.length > 0) {
      if (this.useCooldown) {
        if (this.playersInCooldown.contains(player.getName().toLowerCase())) {
          player.sendMessage("�cPlease wait before clicking again.");
          return;
        } 
        addCooldown(player);
      } 
      ItemCommand[] comandos;
      for (int length = comandos = this.commands.length, i = 0; i < length; i++) {
        ItemCommand itemCommand = comandos[i];
        itemCommand.execute(player);
      } 
    } 
  }

  
  public boolean hasPerm(Player player) { return (this.permission == null || player.hasPermission(this.permission)); }


  
  public boolean isOnlyOnFirstJoin() { return this.onlyOnFirstJoin; }

  
  public void setOnlyOnFirstJoin(boolean onlyOnFirstJoin) {
    this.onlyOnFirstJoin = onlyOnFirstJoin;
    if (onlyOnFirstJoin && this.giveOnRespawn) {
      this.giveOnRespawn = false;
    }
  }
  
  public void setDataValue(Short dataValue) {
    if (dataValue == null || dataValue.shortValue() == 0) {
      this.dataValue = null;
      return;
    } 
    this.dataValue = dataValue;
  }

  
  public boolean isDroppable() { return this.droppable; }


  
  public void setDroppable(boolean droppable) { this.droppable = droppable; }


  
  public boolean isGiveOnRespawn() { return this.giveOnRespawn; }

  
  public void setGiveOnRespawn(boolean giveOnRespawn) {
    if (this.onlyOnFirstJoin) {
      return;
    }
    this.giveOnRespawn = giveOnRespawn;
    isGiveOnRespawn();
  }

  
  public void setCommands(ItemCommand[] commands) { this.commands = commands; }


  
  public void setBlockMovement(boolean block) { this.blockMovement = block; }


  
  public boolean isMovementBlocked() { return this.blockMovement; }


  
  public void setUseCooldown(boolean use) { this.useCooldown = use; }


  
  public void setCooldownSeconds(int seconds) { this.cooldownSeconds = seconds; }


  
  public boolean usesCooldown() { return this.useCooldown; }


  
  public void setGiveOnWorldChange(boolean b) { this.giveOnWorldChange = b; }


  
  public boolean isGiveOnWorldChange() { return this.giveOnWorldChange; }


  
  public boolean isAllowedInWorld(String s) { return !this.removeInWorlds.contains(s); }

  
  public void setDisabledWorlds(List<String> list) {
    if (list != null) {
      this.removeInWorlds = list;
    }
  }
  
  public void addCooldown(Player player) {
    final String name = player.getName().toLowerCase();
    if (this.playersInCooldown.contains(name)) {
      return;
    }
    this.playersInCooldown.add(name);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable()
        {
          public void run() {
            JoinItem.this.playersInCooldown.remove(name);
          }
        },  (this.cooldownSeconds * 20));
  }
}
