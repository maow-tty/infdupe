package dev.maow.infdupe;

import dev.maow.infdupe.common.item.BookOfResistanceItem;
import dev.maow.infdupe.common.item.LoredBlockItem;
import dev.maow.infdupe.common.item.LoredItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class InfiniteDuplicationItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, InfiniteDuplication.MODID);

    public static final RegistryObject<BlockItem> DUPLICATOR = ITEMS.register("duplicator",
        () -> new LoredBlockItem(
            InfiniteDuplicationBlocks.DUPLICATOR.get(),
            new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant(),
            Component
                .translatable("item." + InfiniteDuplication.MODID + ".duplicator.lore")
                .withStyle(ChatFormatting.GRAY)
                .withStyle(ChatFormatting.ITALIC)
        )
    );
    public static final RegistryObject<BlockItem> DELETOR = ITEMS.register("deletor",
        () -> new LoredBlockItem(
            InfiniteDuplicationBlocks.DELETOR.get(),
            new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant(),
            Component
                .translatable("item." + InfiniteDuplication.MODID + ".deletor.lore")
                .withStyle(ChatFormatting.GRAY)
                .withStyle(ChatFormatting.ITALIC)
        )
    );
    public static final RegistryObject<Item> RITUAL_IRON = ITEMS.register("ritual_iron",
        () -> new LoredItem(
            new Item.Properties().fireResistant(),
            Component
                .translatable("item." + InfiniteDuplication.MODID + ".ritual_iron.lore")
                .withStyle(ChatFormatting.GRAY)
                .withStyle(ChatFormatting.ITALIC)
        )
    );
    public static final RegistryObject<Item> CREATION_SIGIL = ITEMS.register("creation_sigil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DELETION_SIGIL = ITEMS.register("deletion_sigil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BOOK_OF_RESISTANCE = ITEMS.register("book_of_resistance", BookOfResistanceItem::new);

    static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}