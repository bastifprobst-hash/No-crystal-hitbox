package com.bastifprobst.nocrystalhitbox.mixin;

import com.bastifprobst.nocrystalhitbox.NoCrystalHitboxClient;
import net.minecraft.client.render.debug.EntityHitboxDebugRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Suppresses the vanilla debug (F3 + B) hitbox rendering for End Crystals while the
 * "hide" state is active. This is the core of the mod: when you have hitboxes turned on
 * with F3 + B, End Crystal hitboxes are hidden by default and can be toggled back on with
 * the key bind.
 *
 * <p>Version note: this targets {@code EntityHitboxDebugRenderer#drawHitbox}, the method
 * vanilla calls once per entity to draw its hitbox. If a future version renames it, this
 * is the single spot to update.</p>
 */
@Mixin(EntityHitboxDebugRenderer.class)
public class EntityHitboxDebugRendererMixin {

	@Inject(method = "drawHitbox", at = @At("HEAD"), cancellable = true)
	private void nocrystalhitbox$hideCrystalHitbox(Entity entity, float tickProgress, boolean inLocalServer, CallbackInfo ci) {
		// Skip drawing the whole hitbox for End Crystals while they are hidden.
		if (entity instanceof EndCrystalEntity && !NoCrystalHitboxClient.areHitboxesShown()) {
			ci.cancel();
		}
	}
}
