package net.abyss.abyssmainplugin;

import dev.lone.itemsadder.api.CustomStack;
import net.abyss.abyssmainplugin.Db.db_connect;
import net.abyss.abyssmainplugin.Manager.ItemManager;
import net.abyss.abyssmainplugin.Manager.PlayerManager;
import net.abyss.abyssmainplugin.Manager.TitleManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerData
{
    private Player player;
    private int playerNo;
    private String playerName;
    private String uuid;
    private int totalStat;
    private int statHealth;
    private int statStrength;
    private int statRapid;
    private int statAccel;
    private int statIntuition;
    private int statLuck;
    private double armorPoint;
    private boolean isLobby = true;
    private String participatedGameCode = "";
    private CustomStack weapon;
    private CustomStack weaponArtifact;
    private CustomStack helmet;
    private CustomStack helmetArtifact;
    private CustomStack chest;
    private CustomStack chestArtifact;
    private CustomStack leggings;
    private CustomStack leggingsArtifact;
    private CustomStack boots;
    private CustomStack bootsArtifact;
    public void setIsLobby(boolean _isLobby)
    {
        isLobby = _isLobby;
    }
    public boolean getIsLobby()
    {
        return isLobby;
    }

    public Player getPlayer()
    {
        return player;
    }
    public void initData(Player _player)
    {
        player = _player;
        armorPoint = 0;
        isLobby = true;
    }
    public void setCode(String gameCode)
    {
        participatedGameCode = gameCode;
    }
    public String getCode()
    {
        return participatedGameCode;
    }
    public void takeOfPlayerEquipment()
    {
        Inventory inv = player.getInventory();
        if(helmet != null)
        {
            inv.setItem(9, helmet.getItemStack());
        }
        if(chest != null)
        {
            inv.setItem(10, chest.getItemStack());
        }
        if(leggings != null)
        {
            inv.setItem(11, leggings.getItemStack());
        }
        if (boots != null)
        {
            inv.setItem(12, boots.getItemStack());
        }
    }
    public void loadPlayerEquipment()
    {
        Inventory inv = player.getInventory();
        ItemStack stack = inv.getItem(0);
        if(stack == null)
        {
            inv.setItem(0, ItemManager.guiGrayGlassPane);
            stack = inv.getItem(0);
            weapon = null;
        }
        if(!stack.getType().equals(Material.GRAY_STAINED_GLASS_PANE))
        {
            equipWeapon(CustomStack.byItemStack(stack));
        }
        else
        {
            weapon = null;
        }

        stack = inv.getItem(9);
        if(stack == null)
        {
            inv.setItem(9, ItemManager.guiGrayGlassPane);
            stack = inv.getItem(9);
            helmet = null;
        }
        if(!stack.getType().equals(Material.GRAY_STAINED_GLASS_PANE))
        {
            equipHelmet(CustomStack.byItemStack(stack));
        }
        else
        {
            helmet = null;
        }

        stack = inv.getItem(10);
        if(stack == null)
        {
            inv.setItem(10, ItemManager.guiGrayGlassPane);
            stack = inv.getItem(10);
            chest = null;
        }
        if(!stack.getType().equals(Material.GRAY_STAINED_GLASS_PANE))
        {
            equipChest(CustomStack.byItemStack(stack));
        }
        else
        {
            chest = null;
        }

        stack = inv.getItem(11);
        if(stack == null)
        {
            inv.setItem(11, ItemManager.guiGrayGlassPane);
            stack = inv.getItem(11);
            leggings = null;
        }
        if(!stack.getType().equals(Material.GRAY_STAINED_GLASS_PANE))
        {
            equipLeggings(CustomStack.byItemStack(stack));
        }
        else
        {
            leggings = null;
        }

        stack = inv.getItem(12);
        if(stack == null)
        {
            inv.setItem(12, ItemManager.guiGrayGlassPane);
            stack = inv.getItem(12);
            boots = null;
        }
        if(!stack.getType().equals(Material.GRAY_STAINED_GLASS_PANE))
        {
            equipBoots(CustomStack.byItemStack(stack));
        }
        else
        {
            boots = null;
        }

        for(int i = 9; i <= 17; i++)
        {
            inv.setItem(i, ItemManager.guiGrayGlassPane);
        }
    }
    public int getPlayerNo()
    {
        return playerNo;
    }
    public void setPlayerNo(int _no)
    {
        playerNo = _no;
    }
    public String getUUID()
    {
        return uuid;
    }
    public void setUUID(String _uuid)
    {
        uuid = _uuid;
    }
    public String getPlayerName()
    {
        return playerName;
    }
    public void setPlayerName(String _playerName)
    {
        playerName = _playerName;
    }

    public double getArmorPoint()
    {
        return armorPoint;
    }


    public int getTotalStat()
    {
        return totalStat;
    }
    public int getStatHealth()
    {
        return statHealth;
    } // 초록 체력
    public int getStatStrength()
    {
        return statStrength;
    } // 빨강 힘
    public int getStatRapid()
    {
        return statRapid;
    } // 노랑 신속
    public int getStatAccel()
    {
        return statAccel;
    } // 하늘 가속
    public int getStatIntuition()
    {
        return statIntuition;
    } // 흰 기감
    public int getStatLuck()
    {
        return statLuck;
    } // 보라 행운

    public void calcTotalStat()
    {
        totalStat = 0;
        totalStat += statHealth + statStrength + statRapid + statAccel + statIntuition + statLuck;
    }
    public void setStatHealth(int value)
    {
        int diff = value - statHealth;
        statHealth = value;
        totalStat += diff;
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20 + statHealth);
    }
    public void setStatStrength(int value)
    {
        int diff = value - statStrength;
        statStrength = value;
        totalStat += diff;
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1.0 + statStrength);
    }
    public void setStatRapid(int value)
    {
        int diff = value - statRapid;
        statRapid = value;
        totalStat += diff;
    }
    public void setStatAccel(int value)
    {
        int diff = value - statAccel;
        statAccel = value;
        totalStat += diff;
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1 * (1 + ((double) statAccel / 25.0)));
    }
    public void setStatIntuition(int value)
    {
        int diff = value - statIntuition;
        statIntuition = value;
        totalStat += diff;
    }
    public void setStatLuck(int value)
    {
        int diff = value - statLuck;
        statLuck = value;
        totalStat += diff;
    }

    public void addStatHealth(int value)
    {
        statHealth += value;
        totalStat += value;
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20 + statHealth);
        db_connect.getInstance().SetStat(player, "health", statHealth);
    }
    public void addStatStrength(int value)
    {
        statStrength += value;
        totalStat += value;
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1.0 + statStrength);
        db_connect.getInstance().SetStat(player, "strength", statStrength);
    }
    public void addStatRapid(int value)
    {
        statRapid += value;
        totalStat += value;

        if(statRapid == PlayerManager.getInstance().getMaxStatRapid())
        {
            TextComponent titleMessage = Component.text().color(TextColor.fromHexString("#fbd633")).content("신속이 극에 달했습니다.").build();
            TextComponent subTitleMessage = Component.text().color(TextColor.color(255, 235, 189)).content("신속 에테르의 힘을 온전히 사용할 수 있습니다.").build();
            TitleManager.getInstance().printTitleToPlayer(titleMessage, subTitleMessage, player);
        }
        db_connect.getInstance().SetStat(player, "rapid", statRapid);
    }
    public void addStatAccel(int value)
    {
        statAccel += value;
        totalStat += value;
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1 * (1 + ((double) statAccel / 25.0)));
        db_connect.getInstance().SetStat(player, "accel", statAccel);
    }
    public void addStatIntuition(int value)
    {
        statIntuition += value;
        totalStat += value;
        db_connect.getInstance().SetStat(player, "intuition", statIntuition);
    }
    public void addStatLuck(int value)
    {
        statLuck += value;
        totalStat += value;
        db_connect.getInstance().SetStat(player, "luck", statLuck);
    }



    // 장비
    public CustomStack getWeapon()
    {
        return weapon;
    }
    public CustomStack getWeaponArtifact()
    {
        return weaponArtifact;
    }
    public CustomStack getHelmet()
    {
        return helmet;
    }
    public CustomStack getHelmetArtifact()
    {
        return helmetArtifact;
    }
    public CustomStack getChest()
    {
        return chest;
    }
    public CustomStack getChestArtifact()
    {
        return chestArtifact;
    }
    public CustomStack getLeggings()
    {
        return leggings;
    }
    public CustomStack getLeggingsArtifact()
    {
        return leggingsArtifact;
    }
    public CustomStack getBoots()
    {
        return boots;
    }
    public CustomStack getBootsArtifact()
    {
        return bootsArtifact;
    }

    private void takeOfInv(CustomStack stack)
    {
        if(stack == null)
        {
            return;
        }
        player.getInventory().addItem(stack.getItemStack());
        if(stack.getItemStack().getType().equals(Material.LEATHER_HORSE_ARMOR))
        {
            String lore = ChatColor.stripColor(stack.getItemStack().getLore().get(0));
            armorPoint -= Double.parseDouble(lore.replace("방어력: ", ""));
        }
    }
    private void equipInv(CustomStack stack)
    {
        if(stack == null)
        {
            return;
        }
        player.getInventory().remove(stack.getItemStack());
        if(stack.getItemStack().getType().equals(Material.LEATHER_HORSE_ARMOR))
        {
            String lore = ChatColor.stripColor(stack.getItemStack().getLore().get(0));
            armorPoint += Double.parseDouble(lore.replace("방어력: ", ""));
        }
    }

    public void equipWeapon(CustomStack _weapon)
    {
        weapon = _weapon;
        player.getInventory().removeItem(weapon.getItemStack());
        player.getInventory().setItem(0, weapon.getItemStack());
    }
    public void takeOfWeapon()
    {
        player.getInventory().setItem(0, ItemManager.guiGrayGlassPane);
        player.getInventory().addItem(weapon.getItemStack());
        weapon = null;
    }
    public void equipWeaponArtifact(CustomStack _weaponArtifact)
    {
        weaponArtifact = _weaponArtifact;
    }
    public void takeOfWeaponArtifact()
    {
        weaponArtifact = null;
    }
    public void equipHelmet(CustomStack _helmet)
    {
        helmet = _helmet;
        equipInv(helmet);
    }
    public void takeOfHelmet()
    {
        takeOfInv(helmet);
        helmet = null;
    }
    public void equipHelmetArtifact(CustomStack _helmetArtifact)
    {
        helmetArtifact = _helmetArtifact;
    }
    public void takeOfHelmetArtifact()
    {
        helmetArtifact = null;
    }
    public void equipChest(CustomStack _chest)
    {
        chest = _chest;
        equipInv(chest);
    }
    public void takeOfChest()
    {
        takeOfInv(chest);
        chest = null;
    }
    public void equipChestArtifact(CustomStack _chestArtifact)
    {
        chestArtifact = _chestArtifact;
    }
    public void takeOfChestArtifact()
    {
        chestArtifact = null;
    }
    public void equipLeggings(CustomStack _leggings)
    {
        leggings = _leggings;
        equipInv(leggings);
    }
    public void takeOfLeggings()
    {
        takeOfInv(leggings);
        leggings = null;
    }
    public void equipLeggingsArtifact(CustomStack _leggingsArtifact)
    {
        leggingsArtifact = _leggingsArtifact;
    }
    public void takeOfLeggingsArtifact()
    {
        leggingsArtifact = null;
    }
    public void equipBoots(CustomStack _boots)
    {
        boots = _boots;
        equipInv(boots);
    }
    public void takeOfBoots()
    {
        takeOfInv(boots);
        boots = null;
    }
    public void equipBootsArtifact(CustomStack _bootsArtifact)
    {
        bootsArtifact = _bootsArtifact;
    }
    public void takeOfBootsArtifact()
    {
        bootsArtifact = null;
    }
}
