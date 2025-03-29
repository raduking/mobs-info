package org.raduking.mobsinfo;

import org.lwjgl.glfw.GLFW;
import org.raduking.mobsinfo.hud.HudRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

public class MobsInfoClient implements ClientModInitializer {

	private static final HudRenderer HUD_RENDERER = new HudRenderer();

	/**
	 * Bind the 'I' default key for enable/disable the mod.
	 */
	private static final KeyBinding TOGGKE_KEY_BINDING = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"mobsinfo.key.toggle",
			InputUtil.Type.KEYSYM, // Type of key (keyboard key)
			GLFW.GLFW_KEY_I, // Default key ('I' key)
			"mobsinfo.category.keys"
	));

	@Override
	public void onInitializeClient() {
		HudLayerRegistrationCallback.EVENT.register(layeredDrawer ->
				layeredDrawer.attachLayerBefore(IdentifiedLayer.CHAT, HudRenderer.MOBS_INFO_LAYER, HUD_RENDERER::onHudRender));

		// Bind the 'I' default key for enable/disable the mod.
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (TOGGKE_KEY_BINDING.wasPressed()) {
				MobsInfo.setModEnabled(!MobsInfo.getModEnabled());
				if (null != client.player) {
					Text text = Text.translatable("mobsinfo.category.keys." + (MobsInfo.getModEnabled() ? "enabled" : "disabled"));
					client.player.sendMessage(text, false);
				}
			}
		});
	}

}
