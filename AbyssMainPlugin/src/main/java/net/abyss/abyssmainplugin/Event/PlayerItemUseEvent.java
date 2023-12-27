package net.abyss.abyssmainplugin.Event;

import com.destroystokyo.paper.event.player.PlayerAttackEntityCooldownResetEvent;
import dev.lone.itemsadder.api.CustomStack;
import net.abyss.abyssmainplugin.Manager.PlayerManager;
import net.abyss.abyssmainplugin.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerItemUseEvent implements Listener
{
    @EventHandler
    public void eatItem(PlayerItemConsumeEvent event)
    {
        Player player = event.getPlayer();
        ItemStack ateItem = event.getItem();

        CustomStack stack = CustomStack.byItemStack(ateItem);

        if(stack != null)
        {
            PlayerData playerData = PlayerManager.getInstance().getPlayerData(player);
            switch (stack.getId())
            {
                case "health_aether":
                    if(playerData.getStatHealth() >= PlayerManager.getInstance().getMaxStatHealth())
                    {
                        player.sendMessage("체력 스텟이 최대치에 도달했습니다.");
                        event.setCancelled(true);
                    }
                    playerData.addStatHealth(1);
                    break;
                case "strength_aether":
                    if(playerData.getStatStrength() >= PlayerManager.getInstance().getMaxStatStrength())
                    {
                        player.sendMessage("힘 스텟이 최대치에 도달했습니다.");
                        event.setCancelled(true);
                    }
                    playerData.addStatStrength(1);
                    break;
                case "rapid_aether":
                    if(playerData.getStatRapid() >= PlayerManager.getInstance().getMaxStatRapid())
                    {
                        player.sendMessage("신속 스텟이 최대치에 도달했습니다.");
                        event.setCancelled(true);
                    }
                    playerData.addStatRapid(1);
                    break;
                case "accel_aether":
                    if(playerData.getStatAccel() >= PlayerManager.getInstance().getMaxStatAccel())
                    {
                        player.sendMessage("가속 스텟이 최대치에 도달했습니다.");
                        event.setCancelled(true);
                    }
                    playerData.addStatAccel(1);
                    break;
                case "intuition_aether":
                    if(playerData.getStatIntuition() >= PlayerManager.getInstance().getMaxStatIntuition())
                    {
                        player.sendMessage("직감 스텟이 최대치에 도달했습니다.");
                        event.setCancelled(true);
                    }
                    playerData.addStatIntuition(1);
                    break;
                case "luck_aether":
                    if(playerData.getStatLuck() >= PlayerManager.getInstance().getMaxStatLuck())
                    {
                        player.sendMessage("행운 스텟이 최대치에 도달했습니다.");
                        event.setCancelled(true);
                    }
                    playerData.addStatLuck(1);
                    break;
            }
            player.setFoodLevel(20);
        }
    }
    @EventHandler
    public void useItem(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if(action == Action.RIGHT_CLICK_AIR)
        {
            if(player.getInventory().getItemInMainHand().getType().isEdible())
            {
                player.setFoodLevel(player.getFoodLevel()-1);
            }
        }
    }
}
