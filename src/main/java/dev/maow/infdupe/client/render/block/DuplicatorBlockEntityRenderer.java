package dev.maow.infdupe.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.maow.infdupe.common.block.entity.DuplicatorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;

public class DuplicatorBlockEntityRenderer implements BlockEntityRenderer<DuplicatorBlockEntity> {
    private final ItemRenderer itemRenderer;

    public DuplicatorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(@NotNull DuplicatorBlockEntity entity,
                       float partialTicks,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource bufferSource,
                       int combinedLight,
                       int combinedOverlay) {
        final var time = entity.getLevel().getGameTime() + partialTicks;
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.scale(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(time * 4));
        itemRenderer.renderStatic(
            entity.getTargetItem(), ItemDisplayContext.FIXED,
            combinedLight, combinedOverlay,
            poseStack, bufferSource,
            entity.getLevel(), (int) entity.getBlockPos().asLong()
        );
        poseStack.popPose();
    }
}
