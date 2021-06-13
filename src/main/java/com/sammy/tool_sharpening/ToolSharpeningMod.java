package com.sammy.tool_sharpening;

import net.minecraft.block.Block;
import net.minecraft.block.GrindstoneBlock;
import net.minecraft.command.TranslatableExceptionProvider;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TieredItem;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

@Mod("tool_sharpening")
public class ToolSharpeningMod
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Random RANDOM = new Random();

    public ToolSharpeningMod()
    {

    }

    public static boolean canSharpen(ItemStack stack)
    {
        if (stack == null)
        {
            return false;
        }
        return stack.getItem().getAttributeModifiers(EquipmentSlotType.MAINHAND, stack).containsKey(Attributes.ATTACK_DAMAGE);
    }
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class RuntimeEvents
    {
        @SubscribeEvent
        public static void attributeModifier(ItemAttributeModifierEvent event)
        {
            if (event.getSlotType().equals(EquipmentSlotType.MAINHAND))
            {
                if (canSharpen(event.getItemStack()))
                {
                    event.addModifier(Attributes.ATTACK_DAMAGE, new SharpenedModifier(event.getItemStack()));
                }
            }
        }
        @SubscribeEvent
        public static void onBlockRightClick(PlayerInteractEvent.RightClickBlock event)
        {
            if (event.getEntityLiving() instanceof PlayerEntity)
            {
                if (event.getHand() == Hand.MAIN_HAND)
                {
                    World world = event.getWorld();
                    BlockPos pos = event.getPos();
                    PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
                    if (playerEntity.isSneaking())
                    {
                        if (world.getBlockState(pos).getBlock() instanceof GrindstoneBlock)
                        {
                            ItemStack stack = event.getItemStack();
                            if (canSharpen(stack))
                            {
                                if (playerEntity.experienceLevel >= 5 || playerEntity.isCreative())
                                {
                                    if (!playerEntity.isCreative())
                                    {
                                        playerEntity.addExperienceLevel(-5);
                                    }
                                    CompoundNBT nbt = stack.getOrCreateTag();
                                    int sharpened = nbt.getInt("tool_sharpening:sharpened");
                                    if (sharpened < 3)
                                    {
                                        nbt.putInt("tool_sharpening:sharpened", sharpened + 1);
                                        event.getEntityLiving().swing(Hand.MAIN_HAND, true);
                                        world.playSound(null, pos, SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 1, 1);
                                        event.setCanceled(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}