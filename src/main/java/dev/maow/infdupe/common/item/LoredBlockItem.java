package dev.maow.infdupe.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LoredBlockItem extends BlockItem {
    private final Component component;

    public LoredBlockItem(Block block, Properties properties, Component component) {
        super(block, properties);
        this.component = component;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack,
                                @Nullable Level level,
                                @NotNull List<Component> components,
                                @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
        components.add(component);
    }
}