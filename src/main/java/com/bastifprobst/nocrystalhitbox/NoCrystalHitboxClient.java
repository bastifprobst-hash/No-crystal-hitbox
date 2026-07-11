package com.bastifprobst.nocrystalhitbox;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

/**
 * No Crystal Hitbox — a client-side Fabric mod.
 *
 * <p>Toggles the rendering of End Crystal hitbox outlines with a key bind (default: H).
 * By default the crystal hitboxes are hidden. Whenever the key is pressed a short status
 * message is shown above the hotbar for 5 seconds.</p>
 */
public class NoCrystalHitboxClient implements ClientModInitializer {

	public static final String MOD_ID = "nocrystalhitbox";

	/** How long the status message stays on screen after toggling. */
	private static final long MESSAGE_DURATION_MS = 5000L;

	/**
	 * Whether End Crystal hitboxes are currently shown.
	 * {@code false} = hidden (this is the default / "hide active" state).
	 */
	private static boolean showCrystalHitboxes = false;

	/** Epoch millis until which the status message should be displayed. */
	private static long messageUntilMs = 0L;

	private static KeyBinding toggleKey;

	@Override
	public void onInitializeClient() {
		toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.nocrystalhitbox.toggle",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_H,
				KeyBinding.Category.create(Identifier.of(MOD_ID, "general"))
		));

		// Detect key presses each client tick.
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleKey.wasPressed()) {
				showCrystalHitboxes = !showCrystalHitboxes;
				messageUntilMs = System.currentTimeMillis() + MESSAGE_DURATION_MS;
			}
		});

		// Draw the crystal hitbox outlines (only while enabled).
		WorldRenderEvents.AFTER_ENTITIES.register(CrystalHitboxRenderer::render);

		// Draw the status message above the hotbar.
		HudRenderCallback.EVENT.register(HitboxHud::render);
	}

	public static boolean areHitboxesShown() {
		return showCrystalHitboxes;
	}

	public static boolean isMessageVisible() {
		return System.currentTimeMillis() < messageUntilMs;
	}
}
