package org.raduking.mobsinfo.keybinds;

import org.lwjgl.glfw.GLFW;
import org.raduking.mobsinfo.config.ConfigScreen;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

public class ConfigKeyHandler {

	private final KeyBinding keyBinding;

	public ConfigKeyHandler() {
	    // Bind the '0' (zero) default key for configuration menu.
		keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"mobsinfo.key.config",
				GLFW.GLFW_KEY_0,
				"mobsinfo.category.keys"
		));
	}

	public void onEndTick(final MinecraftClient client) {
		while (keyBinding.wasPressed()) {
			if (null != client.player) {
				client.setScreen(new ConfigScreen(client.currentScreen));
			}
		}
	}

}
