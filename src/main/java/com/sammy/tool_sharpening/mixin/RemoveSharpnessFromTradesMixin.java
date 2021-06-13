package com.sammy.tool_sharpening.mixin;

import com.sammy.tool_sharpening.ToolSharpeningMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(targets = "net.minecraft.entity.merchant.villager.VillagerTrades$EnchantedBookForEmeraldsTrade")
public class RemoveSharpnessFromTradesMixin
{
    @ModifyVariable(method = "getOffer", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"), remap = false, ordinal = 0)
    private Enchantment filter(Enchantment old)
    {
        List<Enchantment> filtered = Registry.ENCHANTMENT.stream().filter(p -> !p.equals(Enchantments.SHARPNESS)).collect(Collectors.toList());
        return filtered.get(ToolSharpeningMod.RANDOM.nextInt(filtered.size()));
    }
}