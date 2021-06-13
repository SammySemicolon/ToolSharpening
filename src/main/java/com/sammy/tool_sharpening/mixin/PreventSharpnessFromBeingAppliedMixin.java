package com.sammy.tool_sharpening.mixin;

import com.sammy.tool_sharpening.ToolSharpeningMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.EnchantRandomly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(Enchantment.class)
public class PreventSharpnessFromBeingAppliedMixin
{

	@Inject(at = @At(value = "HEAD"), method = "canApplyAtEnchantingTable", cancellable = true)
    private void canApplyAtEnchantingTable(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if (this.equals(Enchantments.SHARPNESS))
        {
            cir.setReturnValue(false);
        }
    }
}
