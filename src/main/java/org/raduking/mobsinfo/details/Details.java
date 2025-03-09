package org.raduking.mobsinfo.details;

import org.raduking.mobsinfo.hud.DisplayedInfo;

import net.minecraft.entity.LivingEntity;

public interface Details<T extends LivingEntity> {

	void populate(T livingEntity, DisplayedInfo displayedInfo);

}
