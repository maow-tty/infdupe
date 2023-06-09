package dev.maow.infdupe.client.event;

import dev.maow.infdupe.InfiniteDuplication;
import dev.maow.infdupe.client.render.block.DuplicatorBlockEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = InfiniteDuplication.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public final class ClientModBusEvents {
    private ClientModBusEvents() {}

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
            InfiniteDuplication.BlockEntities.DUPLICATOR.get(),
            DuplicatorBlockEntityRenderer::new
        );
    }
}