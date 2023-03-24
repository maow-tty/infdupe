package dev.maow.infdupe;

import dev.maow.infdupe.common.block.DeletorBlock;
import dev.maow.infdupe.common.block.DuplicatorBlock;
import dev.maow.infdupe.common.block.entity.DuplicatorBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class InfiniteDuplicationBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
        ForgeRegistries.BLOCKS,
        InfiniteDuplication.MODID
    );
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(
        ForgeRegistries.BLOCK_ENTITY_TYPES,
        InfiniteDuplication.MODID
    );

    public static final RegistryObject<DuplicatorBlock> DUPLICATOR = BLOCKS.register("duplicator", DuplicatorBlock::new);
    public static final RegistryObject<DeletorBlock> DELETOR = BLOCKS.register("deletor", DeletorBlock::new);
    public static final RegistryObject<BlockEntityType<DuplicatorBlockEntity>> DUPLICATOR_ENTITY = BLOCK_ENTITY_TYPES.register(
        "duplicator_entity",
        () -> BlockEntityType.Builder.of(DuplicatorBlockEntity::new, DUPLICATOR.get()).build(null)
    );

    static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}