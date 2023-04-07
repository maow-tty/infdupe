package dev.maow.infdupe.common.block.entity;

import dev.maow.infdupe.InfiniteDuplication;
import dev.maow.infdupe.util.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public final class DuplicatorBlockEntity extends BlockEntity {
    private ItemStack targetItem = ItemStack.EMPTY;

    public DuplicatorBlockEntity(BlockPos pos, BlockState state) {
        super(InfiniteDuplication.BlockEntities.DUPLICATOR.get(), pos, state);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("targetItem", targetItem.serializeNBT());
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.targetItem = ItemStack.of(tag.getCompound("targetItem"));
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public ItemStack getTargetItem() {
        return targetItem;
    }

    public void setTargetItem(ItemStack targetItem, Level level) {
        this.targetItem = targetItem;
        final var state = getBlockState();
        level.sendBlockUpdated(getBlockPos(), state, state, Block.UPDATE_CLIENTS);
    }

    public boolean matchesTargetItem(ItemStack stack) {
        return ItemUtils.compare(stack, targetItem);
    }

    public boolean isEmpty() {
        return targetItem.isEmpty();
    }
}