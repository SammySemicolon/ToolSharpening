package com.sammy.tool_sharpening;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class SharpenedModifier extends AttributeModifier
{
    ItemStack stack;
    public SharpenedModifier(ItemStack stack)
    {
        super(UUID.fromString("00d9dba8-3d2a-4bd2-87b6-36a378e89da0"), "tool_sharpening:sharpened", 0, Operation.ADDITION);
        this.stack = stack;
    }

    @Override
    public double getAmount()
    {
        return stack.getOrCreateTag().getInt("tool_sharpening:sharpened");
    }
}
