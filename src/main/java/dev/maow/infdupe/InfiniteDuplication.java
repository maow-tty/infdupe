package dev.maow.infdupe;

import dev.maow.infdupe.common.block.DeletorBlock;
import dev.maow.infdupe.common.block.DuplicatorBlock;
import dev.maow.infdupe.common.block.entity.DuplicatorBlockEntity;
import dev.maow.infdupe.common.item.LoredBlockItem;
import dev.maow.infdupe.common.item.LoredItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;

import static net.minecraftforge.registries.ForgeRegistries.*;

@Mod(InfiniteDuplication.MODID)
public class InfiniteDuplication {
    public static final String MODID = "infdupe";

    public InfiniteDuplication() {
        final var eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Blocks.REGISTER.register(eventBus);
        BlockEntities.REGISTER.register(eventBus);
        Items.REGISTER.register(eventBus);
    }

    private static <T> DeferredRegister<T> createRegister(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, InfiniteDuplication.MODID);
    }

    public static final class Blocks {
        private static final DeferredRegister<Block> REGISTER = createRegister(BLOCKS);

        public static final RegistryObject<DuplicatorBlock> DUPLICATOR = REGISTER.register("duplicator", DuplicatorBlock::new);
        public static final RegistryObject<DeletorBlock> DELETOR = REGISTER.register("deletor", DeletorBlock::new);

        private Blocks() {}
    }

    public static final class BlockEntities {
        private static final DeferredRegister<BlockEntityType<?>> REGISTER = createRegister(BLOCK_ENTITY_TYPES);

        public static final RegistryObject<BlockEntityType<DuplicatorBlockEntity>> DUPLICATOR = REGISTER.register(
            "duplicator_entity",
            () -> create(DuplicatorBlockEntity::new, Blocks.DUPLICATOR.get())
        );

        private BlockEntities() {}

        private static <T extends BlockEntity> BlockEntityType<T> create(BlockEntitySupplier<T> blockEntity,
                                                                         Block block) {
            return BlockEntityType.Builder.of(blockEntity, block).build(null);
        }
    }

    public static final class Items {
        private static final DeferredRegister<Item> REGISTER = createRegister(ITEMS);

        public static final RegistryObject<BlockItem> DUPLICATOR = REGISTER.register("duplicator",
            () -> new LoredBlockItem(
                Blocks.DUPLICATOR.get(),
                new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant(),
                Component
                    .translatable("item." + InfiniteDuplication.MODID + ".duplicator.lore")
                    .withStyle(ChatFormatting.GRAY)
                    .withStyle(ChatFormatting.ITALIC)
            )
        );
        public static final RegistryObject<BlockItem> DELETOR = REGISTER.register("deletor",
            () -> new LoredBlockItem(
                Blocks.DELETOR.get(),
                new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant(),
                Component
                    .translatable("item." + InfiniteDuplication.MODID + ".deletor.lore")
                    .withStyle(ChatFormatting.GRAY)
                    .withStyle(ChatFormatting.ITALIC)
            )
        );
        public static final RegistryObject<Item> RITUAL_IRON = REGISTER.register("ritual_iron",
            () -> new LoredItem(
                new Item.Properties().fireResistant(),
                Component
                    .translatable("item." + InfiniteDuplication.MODID + ".ritual_iron.lore")
                    .withStyle(ChatFormatting.GRAY)
                    .withStyle(ChatFormatting.ITALIC)
            )
        );
        public static final RegistryObject<Item> CREATION_SIGIL = registerPlain("creation_sigil");
        public static final RegistryObject<Item> DELETION_SIGIL = registerPlain("deletion_sigil");

        private Items() {}

        private static RegistryObject<Item> registerPlain(String name) {
            return REGISTER.register(name, () -> new Item(new Item.Properties()));
        }

        public static Collection<RegistryObject<Item>> getAll() {
            return REGISTER.getEntries();
        }
    }
}