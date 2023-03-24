package dev.maow.infdupe.common.block;

import dev.maow.infdupe.InfiniteDuplicationItems;
import dev.maow.infdupe.api.capability.DamageMultiplier;
import dev.maow.infdupe.common.block.entity.DuplicatorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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

@SuppressWarnings("deprecation")
public final class DuplicatorBlock extends Block implements EntityBlock {
    private static final float ADDED_DAMAGE = 0.003125F;

    public DuplicatorBlock() {
        super(
            BlockBehaviour.Properties
                .of(new Material.Builder(MaterialColor.COLOR_PURPLE).build())
                .noOcclusion()
        );
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state,
                                          @NotNull Level level,
                                          @NotNull BlockPos pos,
                                          @NotNull Player player,
                                          @NotNull InteractionHand hand,
                                          @NotNull BlockHitResult result) {
        final var inventory = player.getInventory();
        final var entity = (DuplicatorBlockEntity) level.getBlockEntity(pos);
        final var item = player.getItemInHand(hand);

        if (item.is(InfiniteDuplicationItems.BOOK_OF_RESISTANCE.get())) {
            return InteractionResult.FAIL;
        }

        final var shouldSet = !item.isEmpty() && !item.sameItem(entity.getTargetItem());
        if (!level.isClientSide()) {
            if (shouldSet) {
                entity.setTargetItem(item.copyWithCount(1), level);
            } else if (!entity.isEmpty()) {
                final var capability = player.getCapability(DamageMultiplier.CAPABILITY);
                final var targetItem = entity.getTargetItem();
                final var crouched = player.isCrouching();

                inventory.add(targetItem.copyWithCount(
                    crouched
                        ? targetItem.getMaxStackSize()
                        : 1
                ));

                if (!player.isCreative()) {
                    capability.ifPresent(multiplier ->
                        multiplier.addValue(
                            crouched
                                ? ADDED_DAMAGE * 64
                                : ADDED_DAMAGE
                        ));
                }
            }
        } else if (shouldSet) {
            level.playSound(player, pos, SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.BLOCKS, 0.25F, 1.0F);
        }
        return InteractionResult.SUCCESS;
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