package org.raduking.mobsinfo.details;

import org.raduking.mobsinfo.hud.DisplayedInfo;

import net.minecraft.entity.passive.TraderLlamaEntity;

public class TraderLlamaDetails implements Details<TraderLlamaEntity> {

	@Override
	public void populate(final TraderLlamaEntity llama, final DisplayedInfo displayedInfo) {
		displayedInfo.add("mobsinfo.hud.trader_llama.variant", llama.getVariant());
	}

}
