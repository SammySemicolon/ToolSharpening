package com.sammy.tool_sharpening.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.EnchantRandomly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Mixin(EnchantRandomly.class)
public class RemoveSharpnessFromLootMixin
{
    @Mutable
    @Shadow
    @Final
    private List<Enchantment> enchantments;


    @SuppressWarnings("all")
    @Inject(method = "<init>",at = @At("RETURN"))
    private void filter(ILootCondition[] conditions, Collection<Enchantment> enchantments, CallbackInfo ci){
        this.enchantments = enchantments.stream().filter(p -> p.equals(Enchantments.SHARPNESS)).collect(Collectors.toList());
    }
}
