package dev.maow.infdupe.common.event;

import dev.maow.infdupe.InfiniteDuplication;
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
                .icon(() -> new ItemStack(InfiniteDuplication.Items.DUPLICATOR.get()))
                .displayItems((parameters, output) -> {
                    for (var item : InfiniteDuplication.Items.getAll()) {
                        output.accept(item.get());
                    }
                })
        );
    }
}
