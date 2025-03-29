package org.raduking.mobsinfo.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {

	private final Screen parent;

	private final MobsInfoOptions options;

	public ConfigScreen(final Screen parent) {
		super(Text.literal("MobsInfo Configuration"));
		this.parent = parent;
		this.options = MobsInfoOptions.load();
	}

	@Override
	protected void init() {
		CheckboxWidget featureWidget = CheckboxWidget.builder(Text.of("Enable Feature"), textRenderer)
				.pos(10, 10)
				.callback((checkbox, value) -> options.enableFeature = value)
				.build();
		this.addDrawableChild(featureWidget);

		this.addDrawableChild(
				ButtonWidget.builder(Text.of("Save"), button -> options.save(this, button))
						.position(10, 10 + featureWidget.getHeight())
						.build()
		);
	}

	public Screen getParent() {
		return parent;
	}
}
