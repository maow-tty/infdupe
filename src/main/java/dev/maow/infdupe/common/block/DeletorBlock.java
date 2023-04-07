package dev.maow.infdupe.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public final class DeletorBlock extends Block {
    public DeletorBlock() {
        super(Properties
            .of(new Material.Builder(MaterialColor.COLOR_RED).build())
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
        final var item = player.getItemInHand(hand);
        if (!item.isEmpty()) {
            if (!level.isClientSide()) {
                // Remove the item on the logical server
                inventory.removeItem(item);
            } else {
                // Display particles and play sounds on the logical client
                for (int i = 0; i < 5; i++) {
                    level.addParticle(ParticleTypes.SMOKE,
                        pos.getX() + 0.5, pos.getY() + i, pos.getZ() + 0.5,
                        0.0, 0.5, 0.0
                    );
                    level.playSound(player, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.25F, 1.0F);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state,
                                        @NotNull BlockGetter getter,
                                        @NotNull BlockPos pos,
                                        @NotNull CollisionContext context) {
        return Block.box(0f, 0f, 0f, 16.0D, 2.0D, 16.0D);
    }
}