package net.abyss.abyssmainplugin.Manager;

import dev.lone.itemsadder.api.CustomStack;
import net.abyss.abyssmainplugin.AbyssMainPlugin;
import net.abyss.abyssmainplugin.PlayerData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemManager
{
    private static ItemManager instance;

    public static ItemManager getInstance()
    {
        if(instance == null)
        {
            synchronized (ItemManager.class)
            {
                instance = new ItemManager();
            }
        }

        return instance;
    }
    private static AbyssMainPlugin plugin;
    public static void setPlugin(AbyssMainPlugin MainPlugin)
    {
        plugin = MainPlugin;
    }
    public AbyssMainPlugin getPlugin()
    {
        return plugin;
    }

    public static final ItemStack guiGrayGlassPane = buildItem(Material.GRAY_STAINED_GLASS_PANE, 1
            , Component.text().color(TextColor.color(0, 0, 0)).content("").build()
            , Component.text().color(TextColor.color(0, 0, 0)).content("").build());

    public static ItemStack buildItem(Material type, int amount, TextComponent displayName, TextComponent... lore)
    {
        ItemStack stack = new ItemStack(type, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(displayName);
        meta.lore(Arrays.asList(lore));
        stack.setItemMeta(meta);

        return stack;
    }

    public static CustomStack buildItemByCustomItem(CustomStack customStack, TextComponent displayName, TextComponent... lore)
    {
        ItemMeta meta = customStack.getItemStack().getItemMeta();
        meta.displayName(displayName);
        meta.lore(Arrays.asList(lore));
        customStack.getItemStack().setItemMeta(meta);

        return customStack;
    }

    public CustomStack playerHealthStatGUI(PlayerData playerData)
    {
        if(playerData.getStatHealth() == PlayerManager.getInstance().getMaxStatHealth())
        {
            // 최대치일때 스텟 초월
        }
        return ItemManager.buildItemByCustomItem(CustomStack.getInstance("custom_aether:health_aether")
                , Component.text().color(TextColor.fromHexString("#33e633")).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("체력").build()
                , Component.text().color(TextColor.fromHexString("#33e633")).decoration(TextDecoration.ITALIC, false).content("최대체력을 증가시킨다.").build()
                , Component.text().color(TextColor.fromHexString("#33e633")).decoration(TextDecoration.ITALIC, false).content("현재 스텟: " + playerData.getStatHealth()).build()
                , Component.text().color(TextColor.fromHexString("#33e633")).decoration(TextDecoration.ITALIC, false).content("증가한 체력: " + playerData.getStatHealth()).build());
    }

    public CustomStack playerStrengthStatGUI(PlayerData playerData)
    {
        if(playerData.getStatHealth() == PlayerManager.getInstance().getMaxStatHealth())
        {
            // 최대치일때 스텟 초월
        }
        return ItemManager.buildItemByCustomItem(CustomStack.getInstance("custom_aether:strength_aether")
                , Component.text().color(TextColor.fromHexString("#eb2e33")).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("힘").build()
                , Component.text().color(TextColor.fromHexString("#eb2e33")).decoration(TextDecoration.ITALIC, false).content("공격력을 증가시킨다.").build()
                , Component.text().color(TextColor.fromHexString("#eb2e33")).decoration(TextDecoration.ITALIC, false).content("현재 스텟: " + playerData.getStatStrength()).build()
                , Component.text().color(TextColor.fromHexString("#eb2e33")).decoration(TextDecoration.ITALIC, false).content("증가한 공격력: " + playerData.getStatStrength()).build());
    }

    public CustomStack playerRapidStatGUI(PlayerData playerData)
    {
        if(playerData.getStatRapid() == PlayerManager.getInstance().getMaxStatRapid())
        {
            // 최대치일때 연속 공격이 3타로 증가 + 연속 공격에 치명타 적용
            return ItemManager.buildItemByCustomItem(CustomStack.getInstance("custom_aether:rapid_aether")
                    , Component.text().color(TextColor.fromHexString("#fbd633")).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("신속(극)").build()
                    , Component.text().color(TextColor.fromHexString("#fbd633")).decoration(TextDecoration.ITALIC, false).content("연속으로 적을 공격할 확률을 증가시킨다.").build()
                    , Component.text().color(TextColor.fromHexString("#fbd633")).decoration(TextDecoration.ITALIC, false).content("연속으로 적을 3번 공격한다.").build()
                    , Component.text().color(TextColor.fromHexString("#fbd633")).decoration(TextDecoration.ITALIC, false).content("연속 공격에 치명타가 적용된다.").build()
                    , Component.text().color(TextColor.fromHexString("#fbd633")).decoration(TextDecoration.ITALIC, false).content("현재 스텟: " + playerData.getStatRapid() + " (최대)").build()
                    , Component.text().color(TextColor.fromHexString("#fbd633")).decoration(TextDecoration.ITALIC, false).content("연속타격 확률: " + (Math.round(((double) playerData.getStatRapid() / 125.0) * 100)) + "%").build());
        }
        return ItemManager.buildItemByCustomItem(CustomStack.getInstance("custom_aether:rapid_aether")
                , Component.text().color(TextColor.fromHexString("#fbd633")).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("신속").build()
                , Component.text().color(TextColor.fromHexString("#fbd633")).decoration(TextDecoration.ITALIC, false).content("연속으로 적을 공격할 확률을 증가시킨다.").build()
                , Component.text().color(TextColor.fromHexString("#fbd633")).decoration(TextDecoration.ITALIC, false).content("현재 스텟: " + playerData.getStatRapid()).build()
                , Component.text().color(TextColor.fromHexString("#fbd633")).decoration(TextDecoration.ITALIC, false).content("연속타격 확률: " + (Math.round(((double) playerData.getStatRapid() / 125.0) * 100)) + "%").build());
    }
    public CustomStack playerAccelStatGUI(PlayerData playerData)
    {
        if(playerData.getStatAccel() == PlayerManager.getInstance().getMaxStatAccel())
        {
            // 최대치일때 스텟 초월
        }
        return ItemManager.buildItemByCustomItem(CustomStack.getInstance("custom_aether:accel_aether")
                , Component.text().color(TextColor.fromHexString("#70f6da")).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("가속").build()
                , Component.text().color(TextColor.fromHexString("#70f6da")).decoration(TextDecoration.ITALIC, false).content("이동속도를 증가시킨다.").build()
                , Component.text().color(TextColor.fromHexString("#70f6da")).decoration(TextDecoration.ITALIC, false).content("현재 스텟: " + playerData.getStatAccel()).build()
                , Component.text().color(TextColor.fromHexString("#70f6da")).decoration(TextDecoration.ITALIC, false).content("증가한 이동속도: " + (Math.round(((double) playerData.getStatAccel() / 25.0) * 100)) + "%").build());
    }
    public CustomStack playerIntuitionStatGUI(PlayerData playerData)
    {
        if(playerData.getStatIntuition() == PlayerManager.getInstance().getMaxStatIntuition())
        {
            // 최대치일때 회피확률 추가 증가 + 회피시 공격력의 500%의 카운터 데미지 적용
        }
        return ItemManager.buildItemByCustomItem(CustomStack.getInstance("custom_aether:intuition_aether")
                , Component.text().color(TextColor.fromHexString("#dbdbdb")).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("직감").build()
                , Component.text().color(TextColor.fromHexString("#dbdbdb")).decoration(TextDecoration.ITALIC, false).content("회피확률을 증가시킨다.").build()
                , Component.text().color(TextColor.fromHexString("#dbdbdb")).decoration(TextDecoration.ITALIC, false).content("현재 스텟: " + playerData.getStatIntuition()).build()
                , Component.text().color(TextColor.fromHexString("#dbdbdb")).decoration(TextDecoration.ITALIC, false).content("현재 회피확률: " + (playerData.getStatIntuition() * 0.2)).build());
    }
    public CustomStack playerLuckStatGUI(PlayerData playerData)
    {
        if(playerData.getStatLuck() == PlayerManager.getInstance().getMaxStatLuck())
        {
            // 최대치일때 스텟 초월
        }
        return ItemManager.buildItemByCustomItem(CustomStack.getInstance("custom_aether:luck_aether")
                , Component.text().color(TextColor.fromHexString("#912cce")).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).content("행운").build()
                , Component.text().color(TextColor.fromHexString("#912cce")).decoration(TextDecoration.ITALIC, false).content("행운을 증가시킨다.").build()
                , Component.text().color(TextColor.fromHexString("#912cce")).decoration(TextDecoration.ITALIC, false).content("현재 스텟: " + playerData.getStatLuck()).build());
    }
}
