package dev.maow.infdupe;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(InfiniteDuplication.MODID)
public class InfiniteDuplication {
    public static final String MODID = "infdupe";

    public InfiniteDuplication() {
        final var eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        InfiniteDuplicationBlocks.register(eventBus);
        InfiniteDuplicationItems.register(eventBus);
    }
}