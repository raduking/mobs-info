package org.raduking.mobsinfo;

import org.lwjgl.glfw.GLFW;
import org.raduking.mobsinfo.hud.HudRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

public class MobsInfo implements ModInitializer {

	public static final String MOD_ID = "mobsinfo";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final HudRenderer HUD_RENDERER = new HudRenderer();

	private static KeyBinding toggleKeyBinding;
	private static boolean modEnabled = true;

	@Override
	public void onInitialize() {
		HudLayerRegistrationCallback.EVENT.register(layeredDrawer ->
				layeredDrawer.attachLayerBefore(IdentifiedLayer.CHAT, HudRenderer.MOBS_INFO_LAYER, HUD_RENDERER::onHudRender));

		// Bind the 'I' default key for enable/disable the mod.
		toggleKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"mobsinfo.key.toggle",
				InputUtil.Type.KEYSYM, // Type of key (keyboard key)
				GLFW.GLFW_KEY_I, // Default key ('I' key)
				"mobsinfo.category.keys"
		));
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleKeyBinding.wasPressed()) {
				modEnabled = !modEnabled;
				if (null != client.player) {
					Text text = Text.translatable("mobsinfo.category.keys." + (modEnabled ? "enabled" : "disabled"));
					client.player.sendMessage(text, false);
				}
			}
		});
	}

	public static boolean isModDisabled() {
		return !modEnabled;
	}
}