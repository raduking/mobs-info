package org.raduking.mobsinfo.details;

import org.raduking.mobsinfo.hud.DisplayedInfo;

import net.minecraft.entity.passive.LlamaEntity;

public class LlamaDetails implements Details<LlamaEntity> {

	@Override
	public void populate(final LlamaEntity llama, final DisplayedInfo displayedInfo) {
		displayedInfo.add("mobsinfo.hud.llama.variant", llama.getVariant());
	}

}
