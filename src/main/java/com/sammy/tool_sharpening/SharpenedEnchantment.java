package com.sammy.tool_sharpening;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.extensions.IForgeEnchantment;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class SharpenedEnchantment extends Enchantment implements IForgeEnchantment
{
    public static EnchantmentCategory NONE = EnchantmentCategory.create(ToolSharpeningMod.MODID + ":none", i -> false);
    protected SharpenedEnchantment()
    {
        super(Rarity.COMMON, NONE, new EquipmentSlot[]{});
    }

    @Override
    protected boolean checkCompatibility(Enchantment ench)
    {
        if (ench.equals(Enchantments.SHARPNESS))
        {
            return false;
        }
        return super.checkCompatibility(ench);
    }

    @Override
    public float getDamageBonus(int level, MobType creatureType, ItemStack stack)
    {
        return 3;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return false;
    }
}
