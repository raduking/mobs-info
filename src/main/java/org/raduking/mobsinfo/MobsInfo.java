package org.raduking.mobsinfo;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;

public class MobsInfo implements ModInitializer {

	public static final String MOD_ID = "mobsinfo";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final AtomicBoolean MOD_ENABLED = new AtomicBoolean(true);

	@Override
	public void onInitialize() {
		MobsInfo.LOGGER.info("Called MobsInfo.onInitialize");
	}

	public static boolean isModDisabled() {
		return !getModEnabled();
	}

	public static boolean getModEnabled() {
		return MOD_ENABLED.get();
	}

	public static void setModEnabled(final boolean modEnabled) {
		MobsInfo.MOD_ENABLED.set(modEnabled);
	}
}
