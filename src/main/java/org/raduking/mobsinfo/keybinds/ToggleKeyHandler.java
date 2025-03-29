package org.raduking.mobsinfo.keybinds;

import org.lwjgl.glfw.GLFW;
import org.raduking.mobsinfo.MobsInfo;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;

public class ToggleKeyHandler {

	private final KeyBinding keyBinding;

	public ToggleKeyHandler() {
	    // Bind the 'I' default key for enable/disable the mod.
		keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"mobsinfo.key.toggle",
				GLFW.GLFW_KEY_I,
				"mobsinfo.category.keys"
		));
	}

	public void onEndTick(final MinecraftClient client) {
		while (keyBinding.wasPressed()) {
			MobsInfo.setModEnabled(!MobsInfo.getModEnabled());
			if (null != client.player) {
				Text text = Text.translatable("mobsinfo.category.keys." + (MobsInfo.getModEnabled() ? "enabled" : "disabled"));
				client.player.sendMessage(text, false);
			}
		}
	}

}
