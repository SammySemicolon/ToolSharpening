package com.sammy.tool_sharpening;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.inventory.EquipmentSlotType;

public class SharpenedEnchantment extends Enchantment
{
    public static EnchantmentType NONE = EnchantmentType.create(ToolSharpeningMod.MODID + ":none", i -> false);
    protected SharpenedEnchantment()
    {
        super(Rarity.COMMON, NONE, new EquipmentSlotType[]{});
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench)
    {
        if (ench.equals(Enchantments.SHARPNESS))
        {
            return false;
        }
        return super.canApplyTogether(ench);
    }

    @Override
    public float calcDamageByCreature(int level, CreatureAttribute creatureType)
    {
        return 3;
    }
}
