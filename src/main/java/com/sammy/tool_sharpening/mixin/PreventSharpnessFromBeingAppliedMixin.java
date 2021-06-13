package com.sammy.tool_sharpening.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class PreventSharpnessFromBeingAppliedMixin
{
	@Inject(at = @At(value = "HEAD"), method = "canApplyAtEnchantingTable", cancellable = true, remap = false)
    private void canApplyAtEnchantingTable(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if (this.equals(Enchantments.SHARPNESS))
        {
            cir.setReturnValue(false);
        }
    }
}
