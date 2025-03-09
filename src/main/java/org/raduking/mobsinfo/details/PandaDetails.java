package org.raduking.mobsinfo.details;

import org.raduking.mobsinfo.hud.DisplayedInfo;

import net.minecraft.entity.passive.PandaEntity;

public class PandaDetails implements Details<PandaEntity> {

	public void populate(final PandaEntity panda, final DisplayedInfo displayedInfo) {
		displayedInfo.add("mobsinfo.hud.panda.main_gene", panda.getMainGene());
		displayedInfo.add("mobsinfo.hud.panda.hidden_gene", panda.getHiddenGene());
	}

}
