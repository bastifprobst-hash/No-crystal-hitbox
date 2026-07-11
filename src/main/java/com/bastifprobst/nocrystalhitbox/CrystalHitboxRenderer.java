package com.bastifprobst.nocrystalhitbox;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.math.Box;

/**
 * Draws a box outline around every End Crystal in the world while the feature is enabled.
 *
 * <p>This is the only version-sensitive part of the mod. If a future Minecraft version
 * renames {@link DebugRenderer#drawBox}, this is the single spot that needs adjusting.</p>
 */
public final class CrystalHitboxRenderer {

	// Outline colour (R, G, B, A) — a bright, easy-to-see white.
	private static final float R = 1.0f;
	private static final float G = 1.0f;
	private static final float B = 1.0f;
	private static final float A = 1.0f;

	private CrystalHitboxRenderer() {
	}

	public static void render(WorldRenderContext context) {
		if (!NoCrystalHitboxClient.areHitboxesShown()) {
			return;
		}

		ClientWorld world = context.world();
		MatrixStack matrices = context.matrixStack();
		VertexConsumerProvider consumers = context.consumers();
		if (world == null || matrices == null || consumers == null) {
			return;
		}

		for (Entity entity : world.getEntities()) {
			if (entity instanceof EndCrystalEntity) {
				Box box = entity.getBoundingBox();
				// DebugRenderer#drawBox expects world coordinates and offsets by the camera itself.
				DebugRenderer.drawBox(matrices, consumers, box, R, G, B, A);
			}
		}
	}
}
