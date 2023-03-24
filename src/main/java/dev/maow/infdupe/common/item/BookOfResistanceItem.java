package dev.maow.infdupe.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public class BookOfResistanceItem extends LoredItem {
    public BookOfResistanceItem() {
        super(
            new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC),
            Component
                .translatable("item.infdupe.book_of_resistance.lore")
                .withStyle(ChatFormatting.GRAY)
                .withStyle(ChatFormatting.ITALIC)
        );
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }
}
