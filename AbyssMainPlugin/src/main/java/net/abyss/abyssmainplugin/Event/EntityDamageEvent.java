package net.abyss.abyssmainplugin.Event;

import dev.lone.itemsadder.api.CustomStack;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import net.abyss.abyssmainplugin.Manager.MonsterManager;
import net.abyss.abyssmainplugin.Manager.PlayerManager;
import net.abyss.abyssmainplugin.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class EntityDamageEvent implements Listener
{
    private final double intuitionValue = 0.2;
    private final int criticalValue = 5;
    @EventHandler
    public void PlayerPreAttack(PrePlayerAttackEntityEvent event)
    {
        Player player = event.getPlayer();
        CustomStack stack = CustomStack.byItemStack(player.getInventory().getItemInMainHand());
        if(stack != null && PlayerManager.getInstance().getPlayerData(player).getWeapon() != null)
        {
            if(!stack.getItemStack().equals(PlayerManager.getInstance().getPlayerData(player).getWeapon().getItemStack()))
            {
                event.setCancelled(true);
            }
            if(player.getAttackCooldown() != 1.0)
            {
                event.setCancelled(true);
            }
        }
        else
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityHit(EntityDamageByEntityEvent event)
    {
        if(event.getEntityType().equals(EntityType.PLAYER))
        {
            Player player = (Player)event.getEntity();
            PlayerData playerData = PlayerManager.getInstance().getPlayerData(player);
            double armorPoint = playerData.getArmorPoint();

            double realDamage = event.getDamage() - armorPoint;
            if(realDamage <= 0)
            {
                realDamage = 1;
            }

            Random random = new Random();
            random.setSeed(System.currentTimeMillis());
            int randNum = random.nextInt(100) + 1;
            if(randNum <= playerData.getStatIntuition() * intuitionValue)
            {
                event.setDamage(0);
            }

            event.setDamage(realDamage);
        }
        else if(event.getDamager().getType().equals(EntityType.PLAYER))
        {
            if(!(event.getEntity() instanceof LivingEntity))
            {
                return;
            }

            Player player = (Player)event.getDamager();

            double damage = event.getFinalDamage();
            PlayerData playerData = PlayerManager.getInstance().getPlayerData(player);
            Random random = new Random();
            random.setSeed(System.currentTimeMillis());
            int randNum = random.nextInt(100) + 1;
            if(randNum <= playerData.getStatLuck() * criticalValue)
            {
                event.setDamage(event.getDamage() * 2);
            }

            LivingEntity entity = (LivingEntity) event.getEntity();
            Bukkit.getScheduler().runTaskLaterAsynchronously(MonsterManager.getInstance().getPlugin(), () ->
            {
                entity.setNoDamageTicks(0);
            }, 2L);
            rapidAttack(entity, damage, playerData.getStatRapid(), player);
        }
    }

    private void rapidAttack(LivingEntity entity, double damage, int rapidStat, Player player)
    {
        int cnt;
        boolean isMax = false;

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int randNum = random.nextInt(125) + 1;

        if(rapidStat == 100)
        {
            cnt = 2;
            isMax = true;
        }
        else if(rapidStat > 0)
        {
            cnt = 1;
        }
        else
        {
            return;
        }

        if(randNum > rapidStat)
        {
            return;
        }


        for(int i = 1; i <= cnt; i++)
        {
            Bukkit.getScheduler().runTaskLater(MonsterManager.getInstance().getPlugin(), () ->
            {
                if(!entity.isDead())
                {
                    if(PlayerManager.getInstance().getMaxStatRapid() == PlayerManager.getInstance().getPlayerData(player).getStatRapid())
                    {
                        int luckrandom = random.nextInt(100) + 1;
                        if(luckrandom <= PlayerManager.getInstance().getPlayerData(player).getStatLuck() * 5)
                        {
                            entity.damage(damage * 2);
                        }
                        else
                        {
                            entity.damage(damage);
                        }
                    }
                    else
                    {
                        entity.damage(damage);
                    }

                    entity.playHurtAnimation(0);
                    entity.getWorld().playSound(entity, entity.getHurtSound(), 1F, 1F);

                    Bukkit.getScheduler().runTaskLaterAsynchronously(MonsterManager.getInstance().getPlugin(), () ->
                    {
                        entity.setNoDamageTicks(0);
                    }, 2L);
                }
            }, 5 * i);
        }
    }
}
