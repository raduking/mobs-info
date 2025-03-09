package org.raduking.mobsinfo;

import java.rmi.registry.Registry;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

import org.lwjgl.glfw.GLFW;
import org.raduking.mobsinfo.hud.MobsInfoEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobsInfo implements ModInitializer {

	public static final String MOD_ID = "mobsinfo";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final MobsInfoEvents mobsInfoEvents = new MobsInfoEvents();

	private static KeyBinding toggleKeyBinding;
	private static boolean modEnabled = true;

	@Override
	public void onInitialize() {
		HudRenderCallback.EVENT.register(mobsInfoEvents::onHudRender);

		// Bind the 'I' default key for enable/disable the mod.
		toggleKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"mobsinfo.key.toggle",
				InputUtil.Type.KEYSYM, // Type of key (keyboard key)
				GLFW.GLFW_KEY_I, // Default key (I key)
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