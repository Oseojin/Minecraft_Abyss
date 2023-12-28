package net.abyss.abyssmainplugin.Event;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import dev.lone.itemsadder.api.CustomStack;
import net.abyss.abyssmainplugin.Manager.ItemManager;
import net.abyss.abyssmainplugin.Manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInvEvent implements Listener
{
    @EventHandler
    public void playerEquipmentChange(PlayerArmorChangeEvent event)
    {
        Player player = event.getPlayer();
        ItemStack equipment = event.getNewItem();

        switch (event.getSlotType())
        {
            case HEAD:
                player.getInventory().setHelmet(null);
                break;
            case CHEST:
                player.getInventory().setChestplate(null);
                break;
            case LEGS:
                player.getInventory().setLeggings(null);
                break;
            case FEET:
                player.getInventory().setBoots(null);
                break;
        }
        Bukkit.getScheduler().runTaskLater(ItemManager.getInstance().getPlugin(), () ->
        {
            player.getInventory().addItem(equipment);
        }, 20);
    }

    @EventHandler
    public void playerDropItem(PlayerDropItemEvent event)
    {
        ItemStack stack = event.getItemDrop().getItemStack();
        CustomStack playerWeapon = PlayerManager.getInstance().getPlayerData(event.getPlayer()).getWeapon();

        if(stack.getType().equals(Material.GRAY_STAINED_GLASS_PANE))
        {
            event.setCancelled(true);
        }

        if(CustomStack.byItemStack(stack) == null || playerWeapon == null)
        {
            return;
        }
        if(stack.equals(playerWeapon.getItemStack()))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerClickGlassItem(InventoryClickEvent event)
    {
        if(event.getCurrentItem() == null)
        {
            return;
        }
        if(event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE))
        {
            event.setCancelled(true);
        }
        if(event.getClickedInventory().equals(event.getWhoClicked().getInventory()) && event.getSlot() == 0)
        {
            event.setCancelled(true);
        }
    }
}
