package net.abyss.abyssmainplugin.Event;

import dev.lone.itemsadder.api.CustomStack;
import net.abyss.abyssmainplugin.Inventory.CharacterStatGUI;
import net.abyss.abyssmainplugin.Manager.PlayerManager;
import net.abyss.abyssmainplugin.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CharacterStatInvClickEvent implements Listener
{
    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
        if(event.getClickedInventory() == null)
        {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        PlayerData playerData = PlayerManager.getInstance().getPlayerData(player);

        if(event.getView().getOriginalTitle().equalsIgnoreCase("STATGUI"))
        {
            CustomStack stack = CustomStack.byItemStack(event.getCurrentItem());
            if(stack == null)
            {
                event.setCancelled(true);
                return;
            }
            if(event.getClickedInventory().equals(player.getInventory()))
            {
                switch (stack.getNamespace())
                {
                    case "custom_weapon":
                        if(playerData.getWeapon() != null)
                        {
                            playerData.takeOfWeapon();
                        }
                        playerData.equipWeapon(stack);
                        break;
                    case "custom_helmet":
                        if(playerData.getHelmet() != null)
                        {
                            playerData.takeOfHelmet();
                        }
                        playerData.equipHelmet(stack);
                        break;
                    case "custom_chest":
                        if(playerData.getChest() != null)
                        {
                            playerData.takeOfChest();
                        }
                        playerData.equipChest(stack);
                        break;
                    case "custom_leggings":
                        if(playerData.getLeggings() != null)
                        {
                            playerData.takeOfLeggings();
                        }
                        playerData.equipLeggings(stack);
                        break;
                    case "custom_boots":
                        if(playerData.getBoots() != null)
                        {
                            playerData.takeOfBoots();
                        }
                        playerData.equipBoots(stack);
                        break;
                    default:
                        event.setCancelled(true);
                }
            }
            else
            {
                if(player.getInventory().firstEmpty() == -1)
                {
                    player.sendMessage("인벤토리가 가득 차 장비를 해제할 수 없습니다.");
                    return;
                }
                switch (event.getSlot())
                {
                    case 22:
                        playerData.takeOfWeapon();
                        break;
                    case 15:
                        playerData.takeOfHelmet();
                        break;
                    case 24:
                        playerData.takeOfChest();
                        break;
                    case 33:
                        playerData.takeOfLeggings();
                        break;
                    case 42:
                        playerData.takeOfBoots();
                        break;
                    default:
                        event.setCancelled(true);
                }
            }

            event.setCancelled(true);
            event.getClickedInventory().close();
            new CharacterStatGUI(player).open();
        }
    }
}
