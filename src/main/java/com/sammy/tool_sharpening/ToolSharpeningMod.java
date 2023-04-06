package com.sammy.tool_sharpening;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GrindstoneBlock;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Random;

@Mod("tool_sharpening")
public class ToolSharpeningMod
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Random RANDOM = new Random();
    public static final String MODID = "tool_sharpening";

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MODID);
    public static final RegistryObject<Enchantment> SHARPENED = ENCHANTMENTS.register("sharpened", SharpenedEnchantment::new);

    public ToolSharpeningMod()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ENCHANTMENTS.register(modBus);
    }


    public static boolean canSharpen(ItemStack stack)
    {
        if (stack == null)
        {
            return false;
        }
        return stack.getItem().getAttributeModifiers(EquipmentSlot.MAINHAND, stack).containsKey(Attributes.ATTACK_DAMAGE);
    }
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class RuntimeEvents
    {
        @SubscribeEvent
        public static void attributeModifier(ItemAttributeModifierEvent event)
        {
            if (event.getSlotType().equals(EquipmentSlot.MAINHAND))
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
            Level level = event.getLevel();
            BlockPos pos = event.getPos();
            Player player = event.getEntity();

            if (event.getHand() == InteractionHand.MAIN_HAND)
            {
                if (player.isShiftKeyDown())
                {
                    if (level.getBlockState(pos).getBlock() instanceof GrindstoneBlock)
                    {
                        ItemStack stack = event.getItemStack();
                        if (canSharpen(stack))
                        {
                            if (player.experienceLevel >= 5 || player.isCreative())
                            {
                                if (!player.isCreative())
                                {
                                    player.giveExperienceLevels(-5);
                                }
                                int sharpened = EnchantmentHelper.getTagEnchantmentLevel(SHARPENED.get(), stack);
                                int sharpness = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.SHARPNESS, stack);
                                if (sharpened == 0 && sharpness == 0)
                                {
                                    Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
                                    enchantments.put(SHARPENED.get(), 1);
                                    EnchantmentHelper.setEnchantments(enchantments, stack);

                                    event.getEntity().swing(InteractionHand.MAIN_HAND, true);
                                    level.playSound(null, pos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1, 1);
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