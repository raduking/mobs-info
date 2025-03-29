package org.raduking.mobsinfo.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.raduking.mobsinfo.MobsInfo;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.widget.ButtonWidget;

import com.google.gson.Gson;

public class MobsInfoOptions {

	private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("mobsinfo.json");
	private static final Path DEFAULT_CONFIG_PATH = Path.of("/assets/mobsinfo/default.options.json");

	public boolean enableFeature;

	public static MobsInfoOptions load() {
		try {
			return new Gson().fromJson(Files.readString(CONFIG_PATH), MobsInfoOptions.class);
		} catch (IOException e) {
			return loadDefault();
		}
	}

	public static MobsInfoOptions loadDefault() {
		try {
			return new Gson().fromJson(readTextFile(DEFAULT_CONFIG_PATH), MobsInfoOptions.class);
		} catch (IOException e) {
			return new MobsInfoOptions();
		}
	}

	public void save(final ConfigScreen screen, final ButtonWidget button) {
		save();
		screen.close();
	}

	public void save() {
		try {
			Files.writeString(CONFIG_PATH, new Gson().toJson(this));
		} catch (IOException e) {
			MobsInfo.LOGGER.error("Failed to save config", e);
		}
	}

	public static String readTextFile(final Path path) throws IOException {
		try (InputStream inputStream = MobsInfo.class.getResourceAsStream(path.toString())) {
			if (null == inputStream) {
				throw new IOException("Failed to load default options from: " + path);
			}
			return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
		}
	}

}
