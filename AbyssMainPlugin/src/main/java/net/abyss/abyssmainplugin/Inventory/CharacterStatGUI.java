package net.abyss.abyssmainplugin.Inventory;

import dev.lone.itemsadder.api.CustomStack;
import net.abyss.abyssmainplugin.Manager.ItemManager;
import net.abyss.abyssmainplugin.Manager.PlayerManager;
import net.abyss.abyssmainplugin.PlayerData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CharacterStatGUI
{
    private final Inventory inv;
    private Player player;
    private ItemStack guiTotalStat;
    private CustomStack guiHealthStat;
    private CustomStack guiStrengthStat;
    private CustomStack guiRapidStat;
    private CustomStack guiAccelStat;
    private CustomStack guiIntuitionStat;
    private CustomStack guiLuckStat;
    private ItemStack guiWeaponSlot;
    private ItemStack guiWeaponArtifactSlot;
    private ItemStack guiHelmetSlot;
    private ItemStack guiHelmetArtifactSlot;
    private ItemStack guiChestSlot;
    private ItemStack guiChestArtifactSlot;
    private ItemStack guiLeggingsSlot;
    private ItemStack guiLeggingsArtifactSlot;
    private ItemStack guiBootsSlot;
    private ItemStack guiBootsArtifactSlot;
    private void initItemSetting()
    {
        PlayerData playerData = PlayerManager.getInstance().getPlayerData(player);

        guiTotalStat = ItemManager.buildItem(Material.NETHER_STAR, 1
                , Component.text().color(TextColor.color(255,255,255)).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("스텟 총합: " + playerData.getTotalStat()).build()
                , Component.text().color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false).content("").build());

        guiHealthStat = ItemManager.getInstance().playerHealthStatGUI(playerData);

        guiStrengthStat = ItemManager.getInstance().playerStrengthStatGUI(playerData);
        guiRapidStat = ItemManager.getInstance().playerRapidStatGUI(playerData);
        guiAccelStat = ItemManager.getInstance().playerAccelStatGUI(playerData);
        guiIntuitionStat = ItemManager.getInstance().playerIntuitionStatGUI(playerData);
        guiLuckStat = ItemManager.getInstance().playerLuckStatGUI(playerData);

        // 무기
        if(playerData.getWeapon() == null)
        {
            guiWeaponSlot = ItemManager.buildItem(Material.IRON_SWORD, 1
                    , Component.text().color(TextColor.color(0, 0, 0)).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("무기").build()
                    , Component.text().color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false).content("무기를 클릭해 장착하세요.").build());
        }
        else
        {
            guiWeaponSlot = playerData.getWeapon().getItemStack();
        }
        if(playerData.getWeaponArtifact() == null)
        {
            guiWeaponArtifactSlot = ItemManager.buildItem(Material.END_CRYSTAL, 1
                    , Component.text().color(TextColor.color(156, 39, 176)).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("무기 장신구").build()
                    , Component.text().color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false).content("무기 슬롯 장신구를 클릭해 장착하세요.(미구현)").build());
        }
        else
        {
            guiWeaponArtifactSlot = playerData.getWeaponArtifact().getItemStack();
        }

        // 헬멧
        if(playerData.getHelmet() == null)
        {
            guiHelmetSlot = ItemManager.buildItem(Material.IRON_HELMET, 1
                    , Component.text().color(TextColor.color(0, 0, 0)).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("모자").build()
                    , Component.text().color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false).content("모자를 클릭해 장착하세요.").build());
        }
        else
        {
            guiHelmetSlot = playerData.getHelmet().getItemStack();
        }
        if(playerData.getHelmetArtifact() == null)
        {
            guiHelmetArtifactSlot = ItemManager.buildItem(Material.END_CRYSTAL, 1
                    , Component.text().color(TextColor.color(156, 39, 176)).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("모자 장신구").build()
                    , Component.text().color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false).content("모자 슬롯 장신구를 클릭해 장착하세요.(미구현)").build());
        }
        else
        {
            guiHelmetArtifactSlot = playerData.getHelmetArtifact().getItemStack();
        }

        // 흉갑
        if(playerData.getChest() == null)
        {
            guiChestSlot = ItemManager.buildItem(Material.IRON_CHESTPLATE, 1
                    , Component.text().color(TextColor.color(0, 0, 0)).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("흉갑").build()
                    , Component.text().color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false).content("흉갑을 클릭해 장착하세요.").build());
        }
        else
        {
            guiChestSlot = playerData.getChest().getItemStack();
        }
        if(playerData.getChestArtifact() == null)
        {
            guiChestArtifactSlot = ItemManager.buildItem(Material.END_CRYSTAL, 1
                    , Component.text().color(TextColor.color(156, 39, 176)).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("흉갑 장신구").build()
                    , Component.text().color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false).content("흉갑 슬롯 장신구를 클릭해 장착하세요.(미구현)").build());
        }
        else
        {
            guiChestArtifactSlot = playerData.getChestArtifact().getItemStack();
        }

        // 레깅스
        if(playerData.getLeggings() == null)
        {
            guiLeggingsSlot = ItemManager.buildItem(Material.IRON_LEGGINGS, 1
                    , Component.text().color(TextColor.color(0, 0, 0)).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("레깅스").build()
                    , Component.text().color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false).content("레깅스를 클릭해 장착하세요.").build());
        }
        else
        {
            guiLeggingsSlot = playerData.getLeggings().getItemStack();
        }
        if(playerData.getLeggingsArtifact() == null)
        {
            guiLeggingsArtifactSlot = ItemManager.buildItem(Material.END_CRYSTAL, 1
                    , Component.text().color(TextColor.color(156, 39, 176)).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("레깅스 장신구").build()
                    , Component.text().color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false).content("레깅스 슬롯 장신구를 클릭해 장착하세요.(미구현)").build());
        }
        else
        {
            guiLeggingsArtifactSlot = playerData.getLeggingsArtifact().getItemStack();
        }

        // 부츠
        if(playerData.getBoots() == null)
        {
            guiBootsSlot = ItemManager.buildItem(Material.IRON_BOOTS, 1
                    , Component.text().color(TextColor.color(0, 0, 0)).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("신발").build()
                    , Component.text().color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false).content("신발을 클릭해 장착하세요.").build());
        }
        else
        {
            guiBootsSlot = playerData.getBoots().getItemStack();
        }
        if(playerData.getBootsArtifact() == null)
        {
            guiBootsArtifactSlot = ItemManager.buildItem(Material.END_CRYSTAL, 1
                    , Component.text().color(TextColor.color(156, 39, 176)).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("신발 장신구").build()
                    , Component.text().color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false).content("신발 슬롯 장신구를 클릭해 장착하세요.(미구현)").build());
        }
        else
        {
            guiBootsArtifactSlot = playerData.getBootsArtifact().getItemStack();
        }

        String[][] placeArray = {
                {"all", "health", "e", "e", "e", "e", "e", "e", "e"},
                {"e", "strength", "e", "e", "e", "e", "helmet", "helmet_artifact", "e"},
                {"e", "rapid", "e", "e", "weapon", "e", "chest", "chest_artifact", "e"},
                {"e", "accel", "e", "e", "weapon_artifact", "e", "leggings", "leggings_artifact", "e"},
                {"e", "intuition", "e", "e", "e", "e", "boots", "boots_artifact", "e"},
                {"e", "luck", "e", "e", "e", "e", "e", "e", "e"},
        };

        int colLen = placeArray.length;
        int rowLen = placeArray[0].length;

        for(int column = 0; column < colLen; column++)
        {
            for(int row = 0; row < rowLen; row++)
            {
                ItemStack stack;
                switch (placeArray[column][row])
                {
                    case "all":
                        stack = guiTotalStat;
                        break;
                    case "health":
                        stack = guiHealthStat.getItemStack();
                        break;
                    case "strength":
                        stack = guiStrengthStat.getItemStack();
                        break;
                    case "rapid":
                        stack = guiRapidStat.getItemStack();
                        break;
                    case "accel":
                        stack = guiAccelStat.getItemStack();
                        break;
                    case "intuition":
                        stack = guiIntuitionStat.getItemStack();
                        break;
                    case "luck":
                        stack = guiLuckStat.getItemStack();
                        break;
                    case "weapon":
                        stack = guiWeaponSlot;
                        break;
                    case "weapon_artifact":
                        stack = guiWeaponArtifactSlot;
                        break;
                    case "helmet":
                        stack = guiHelmetSlot;
                        break;
                    case "helmet_artifact":
                        stack = guiHelmetArtifactSlot;
                        break;
                    case "chest":
                        stack = guiChestSlot;
                        break;
                    case "chest_artifact":
                        stack = guiChestArtifactSlot;
                        break;
                    case "leggings":
                        stack = guiLeggingsSlot;
                        break;
                    case "leggings_artifact":
                        stack = guiLeggingsArtifactSlot;
                        break;
                    case "boots":
                        stack = guiBootsSlot;
                        break;
                    case "boots_artifact":
                        stack = guiBootsArtifactSlot;
                        break;
                    default:
                        stack = ItemManager.guiGrayGlassPane;
                }
                inv.setItem(column * rowLen + row, stack);
            }
        }
    }

    public CharacterStatGUI(Player _player)
    {
        this.inv = Bukkit.createInventory(null, 54, "STATGUI");
        player = _player;
        initItemSetting();
    }

    public void open()
    {
        player.openInventory(inv);
    }
}
