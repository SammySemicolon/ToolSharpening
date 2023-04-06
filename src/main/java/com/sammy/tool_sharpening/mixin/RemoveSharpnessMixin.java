package com.sammy.tool_sharpening.mixin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageEnchantment.class)
public class RemoveSharpnessMixin extends Enchantment {

    @Shadow @Final public int type;

    protected RemoveSharpnessMixin(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public boolean isTradeable() {
        if (this.type == 0) {
            return false;
        }
        return super.isTradeable();
    }

    @Override
    public boolean isDiscoverable() {
        if (this.type == 0) {
            return false;
        }
        return super.isDiscoverable();
    }

    @Inject(method = "canEnchant(Lnet/minecraft/world/item/ItemStack;)Z", at = @At(value = "HEAD"), cancellable = true)
    private void canEnchant(ItemStack pStack, CallbackInfoReturnable<Boolean> cir) {
        if (this.type == 0) {
            cir.setReturnValue(false);
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (this.type == 0) {
            return false;
        }
        return super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        if (this.type == 0) {
            return false;
        }
        return super.isAllowedOnBooks();
    }
}
