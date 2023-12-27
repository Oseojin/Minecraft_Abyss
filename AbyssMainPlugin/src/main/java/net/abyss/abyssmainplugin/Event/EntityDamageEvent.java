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
            player.sendMessage("회피 랜덤: " + randNum + " / 현재 회피 확률: " + (playerData.getStatIntuition() * 0.2) + "%");
            if(randNum <= playerData.getStatIntuition() * 0.2)
            {
                event.setDamage(0);
                player.sendMessage("회피!");
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
            player.sendMessage("크리티컬 랜덤: " + randNum + " / 현재 치명타 확률: " + (playerData.getStatLuck() * 5) + "%");
            if(randNum <= playerData.getStatLuck() * 5)
            {
                event.setDamage(event.getDamage() * 2);
                player.sendMessage("크리티컬!");
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

        if(rapidStat == 100)
        {
            cnt = 2;
            isMax = true;
        }
        else if(rapidStat > 20)
        {
            cnt = 1;
        }
        else
        {
            return;
        }

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int randNum = random.nextInt(125) + 1;

        player.sendMessage("연속타격 랜덤: " + randNum + " / 현재 연속타격 확률: " + rapidStat + "%");

        if(randNum > (rapidStat-20))
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
                        player.sendMessage("크리티컬 랜덤: " + randNum + " / 현재 치명타 확률: " + (PlayerManager.getInstance().getPlayerData(player).getStatLuck() * 5) + "%");
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
