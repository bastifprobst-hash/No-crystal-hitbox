package com.bastifprobst.nocrystalhitbox;

import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexRendering;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

/**
 * Draws a box outline around every End Crystal in the world while the feature is enabled.
 *
 * <p>This is the only version-sensitive part of the mod. The two calls most likely to be
 * renamed in a future Minecraft version are {@link RenderLayers#lines()} and
 * {@link VertexRendering#drawOutline}; if a version bump fails to compile, fix them here.</p>
 */
public final class CrystalHitboxRenderer {

	/** Outline colour as packed ARGB (opaque white). */
	private static final int COLOR = 0xFFFFFFFF;

	/** Outline line width. */
	private static final float LINE_WIDTH = 1.0f;

	private CrystalHitboxRenderer() {
	}

	public static void render(WorldRenderContext context) {
		if (!NoCrystalHitboxClient.areHitboxesShown()) {
			return;
		}

		MinecraftClient client = MinecraftClient.getInstance();
		ClientWorld world = client.world;
		MatrixStack matrices = context.matrices();
		VertexConsumerProvider consumers = context.consumers();
		if (world == null || matrices == null || consumers == null) {
			return;
		}

		Vec3d cam = client.gameRenderer.getCamera().getCameraPos();
		VertexConsumer lines = consumers.getBuffer(RenderLayers.lines());

		for (Entity entity : world.getEntities()) {
			if (entity instanceof EndCrystalEntity) {
				Box box = entity.getBoundingBox();
				VoxelShape shape = VoxelShapes.cuboidUnchecked(
						box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
				// Offset by the negative camera position so the world-space box lands correctly.
				VertexRendering.drawOutline(matrices, lines, shape,
						-cam.x, -cam.y, -cam.z, COLOR, LINE_WIDTH);
			}
		}
	}
}
