package org.raduking.mobsinfo;

import org.raduking.mobsinfo.hud.HudRenderer;
import org.raduking.mobsinfo.keybinds.ConfigKeyHandler;
import org.raduking.mobsinfo.keybinds.ToggleKeyHandler;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;

public class MobsInfoClient implements ClientModInitializer {

	private static final HudRenderer HUD_RENDERER = new HudRenderer();

	private static final ToggleKeyHandler MOD_TOGGLE = new ToggleKeyHandler();
	private static final ConfigKeyHandler MOD_CONFIG = new ConfigKeyHandler();

	@Override
	public void onInitializeClient() {
		HudLayerRegistrationCallback.EVENT.register(layeredDrawer ->
				layeredDrawer.attachLayerBefore(IdentifiedLayer.CHAT, HudRenderer.MOBS_INFO_LAYER, HUD_RENDERER::onHudRender));

		ClientTickEvents.END_CLIENT_TICK.register(MOD_TOGGLE::onEndTick);
		ClientTickEvents.END_CLIENT_TICK.register(MOD_CONFIG::onEndTick);
	}

}
