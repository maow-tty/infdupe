package dev.maow.infdupe.common.event;

import dev.maow.infdupe.InfiniteDuplicationItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import static dev.maow.infdupe.InfiniteDuplication.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Bus.MOD)
public final class CommonModBusEvents {
    @SubscribeEvent
    public static void registerCreativeTab(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(
            new ResourceLocation(MODID, "tab"),
            builder -> builder
                .title(Component.translatable("item_group." + MODID + ".tab"))
                .icon(() -> new ItemStack(InfiniteDuplicationItems.DUPLICATOR.get()))
                .displayItems((parameters, output) -> {
                    output.accept(InfiniteDuplicationItems.DUPLICATOR.get());
                    output.accept(InfiniteDuplicationItems.DELETOR.get());
                    output.accept(InfiniteDuplicationItems.RITUAL_IRON.get());
                    output.accept(InfiniteDuplicationItems.CREATION_SIGIL.get());
                    output.accept(InfiniteDuplicationItems.DELETION_SIGIL.get());
                    output.accept(InfiniteDuplicationItems.BOOK_OF_RESISTANCE.get());
                })
        );
    }
}
