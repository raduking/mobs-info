package org.raduking.mobsinfo.details;

import org.raduking.mobsinfo.MobsInfo;
import org.raduking.mobsinfo.hud.DisplayedInfo;
import org.raduking.mobsinfo.utils.MobsInfoMath;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.HorseEntity;

public class HorseDetails implements Details<HorseEntity> {

	@Override
	public void populate(final HorseEntity horse, final DisplayedInfo displayedInfo) {
		displayedInfo.add("mobsinfo.hud.horse.speed", MobsInfoMath.round(calculateSpeed(horse)));
		displayedInfo.add("mobsinfo.hud.horse.jump_height", MobsInfoMath.round(calculateJumpHeight(horse)));
	}

	public static double calculateJumpHeight(final HorseEntity horse) {
		double jumpStrength = horse.getAttributeValue(EntityAttributes.JUMP_STRENGTH);
		MobsInfo.LOGGER.debug("EntityAttributes.JUMP_STRENGTH: {}", jumpStrength);
		double jumpHeight = 5 * Math.pow(jumpStrength, 2) + 0.5 * jumpStrength;
		MobsInfo.LOGGER.debug("Jump Height: {}", jumpHeight);
		return jumpHeight;
	}

	public static double calculateSpeed(final HorseEntity horse) {
		double movementSpeed = horse.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);
		MobsInfo.LOGGER.debug("EntityAttributes.MOVEMENT_SPEED: {}", movementSpeed);
		double speed = movementSpeed * 43.17;
		MobsInfo.LOGGER.debug("Speed: {}", speed);
		return speed;
	}
}
