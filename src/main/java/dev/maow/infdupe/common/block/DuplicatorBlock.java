package dev.maow.infdupe.common.block;

import dev.maow.infdupe.InfiniteDuplication;
import dev.maow.infdupe.common.block.entity.DuplicatorBlockEntity;
import dev.maow.infdupe.util.Lazy;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@SuppressWarnings("deprecation")
public final class DuplicatorBlock extends Block implements EntityBlock {
    private static final Lazy<Set<Item>> BLACKLIST = new Lazy<>(() -> Set.of(
        InfiniteDuplication.Items.BOOK_OF_RESISTANCE.get(),
        InfiniteDuplication.Items.DUPLICATOR.get(),
        InfiniteDuplication.Items.DELETOR.get()
    ));

    public DuplicatorBlock() {
        super(
            BlockBehaviour.Properties
                .of(new Material.Builder(MaterialColor.COLOR_PURPLE).build())
                .noOcclusion()
        );
    }

    private static boolean isBlacklisted(ItemStack stack) {
        return BLACKLIST.get().contains(stack.getItem());
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state,
                                          @NotNull Level level,
                                          @NotNull BlockPos pos,
                                          @NotNull Player player,
                                          @NotNull InteractionHand hand,
                                          @NotNull BlockHitResult result) {
        final var stack = player.getItemInHand(hand);
        final var entity = (DuplicatorBlockEntity) level.getBlockEntity(pos);
        if (entity == null) return InteractionResult.FAIL;

        if (isBlacklisted(stack)) {
            return InteractionResult.FAIL;
        } else if (!entity.matchesTargetItem(stack) && !stack.isEmpty()) {
            // Set target item to the one in the player's hand as long as they don't match
            setItem(player, pos, entity, stack, level);
        } else if (!entity.isEmpty()) {
            // Acquire target item if it isn't empty. Crouching gives you the max stack size of that item.
            acquireItem(player, entity, level);
        }
        return InteractionResult.SUCCESS;
    }

    private void setItem(Player player, BlockPos pos, DuplicatorBlockEntity entity, ItemStack stack, Level level) {
        if (!level.isClientSide()) {
            entity.setTargetItem(stack.copyWithCount(1), level);
        } else {
            level.playSound(player, pos, SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.BLOCKS, 0.25F, 1.0F);
        }
    }

    private void acquireItem(Player player, DuplicatorBlockEntity entity, Level level) {
        if (!level.isClientSide()) {
            final var targetItem = entity.getTargetItem();

            player.getInventory().add(targetItem.copyWithCount(
                player.isCrouching()
                    ? targetItem.getMaxStackSize()
                    : 1
            ));
        }
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new DuplicatorBlockEntity(pos, state);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state,
                                        @NotNull BlockGetter getter,
                                        @NotNull BlockPos pos,
                                        @NotNull CollisionContext context) {
        return Block.box(0f, 0f, 0f, 16.0D, 2.0D, 16.0D);
    }
}