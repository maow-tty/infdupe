package dev.maow.infdupe.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

public final class ItemUtils {
    private ItemUtils() {}

    public static boolean compare(ItemLike item, ItemLike other) {
        return item.asItem() == other.asItem();
    }

    public static boolean compare(ItemLike item, ItemStack other) {
        return !other.isEmpty() && item.asItem() == other.getItem();
    }

    public static boolean compare(ItemStack item, ItemStack other) {
        if (item.isEmpty()) {
            // If both items are empty, return true
            return other.isEmpty();
        }
        // Otherwise, compare
        return item.getItem() == other.getItem();
    }

    public static boolean compare(ItemLike item, RegistryObject<Item> other) {
        return item.asItem() == other.get();
    }

    public static boolean compare(ItemStack item, RegistryObject<Item> other) {
        return item.getItem() == other.get();
    }
}