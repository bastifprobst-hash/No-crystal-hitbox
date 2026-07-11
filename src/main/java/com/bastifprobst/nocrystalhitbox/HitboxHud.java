package com.bastifprobst.nocrystalhitbox;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;

/**
 * Renders the short status message above the hotbar for a few seconds after the key is pressed.
 */
public final class HitboxHud {

	// Minecraft chat colours: green (§a) and red (§c), fully opaque (0xFF alpha).
	private static final int COLOR_GREEN = 0xFF55FF55;
	private static final int COLOR_RED = 0xFFFF5555;

	private static final Text MESSAGE_HIDE_ACTIVE = Text.literal("Crystal Hitbox Hide Active");
	private static final Text MESSAGE_NOT_ACTIVE = Text.literal("Crystal Hitbox Not active");

	private HitboxHud() {
	}

	public static void render(DrawContext context, RenderTickCounter tickCounter) {
		if (!NoCrystalHitboxClient.isMessageVisible()) {
			return;
		}

		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player == null || client.options.hudHidden) {
			return;
		}

		// Hitboxes hidden (default) -> "Hide Active" in green.
		// Hitboxes shown            -> "Not active" in red.
		boolean shown = NoCrystalHitboxClient.areHitboxesShown();
		Text message = shown ? MESSAGE_NOT_ACTIVE : MESSAGE_HIDE_ACTIVE;
		int color = shown ? COLOR_RED : COLOR_GREEN;

		TextRenderer textRenderer = client.textRenderer;
		int screenWidth = context.getScaledWindowWidth();
		int screenHeight = context.getScaledWindowHeight();

		int x = (screenWidth - textRenderer.getWidth(message)) / 2;
		// Just above the hotbar (the hotbar sits ~22px tall at the bottom of the screen).
		int y = screenHeight - 59;

		context.drawTextWithShadow(textRenderer, message, x, y, color);
	}
}
