package org.raduking.mobsinfo.details;

import java.util.TreeMap;
import java.util.function.Supplier;

import org.raduking.mobsinfo.MobsInfo;
import org.raduking.mobsinfo.hud.DisplayedInfo;
import org.raduking.mobsinfo.utils.MobsInfoMath;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.HorseEntity;

public class HorseDetails implements Details<HorseEntity> {

	public static final double MIN_JUMP_HEIGHT = 1.1093;
	public static final double MAX_JUMP_HEIGHT = 5.29997;

	// TODO: make this configurable
	private final JumpHeightFormula jumpHeightFormula = JumpHeightFormula.INTERPOLATED;

	@Override
	public void populate(final HorseEntity horse, final DisplayedInfo displayedInfo) {
		displayedInfo.add("mobsinfo.hud.horse.speed", MobsInfoMath.round(calculateSpeed(horse)));
		displayedInfo.add("mobsinfo.hud.horse.jump_height", MobsInfoMath.round(calculateJumpHeight(horse)));
	}

	public double calculateSpeed(final HorseEntity horse) {
		double movementSpeed = horse.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);
		MobsInfo.LOGGER.debug("EntityAttributes.MOVEMENT_SPEED: {}", movementSpeed);
		double speed = movementSpeed * 43.17;
		MobsInfo.LOGGER.debug("Speed: {}", speed);
		return speed;
	}

	public double calculateJumpHeight(final HorseEntity horse) {
		double jumpStrength = horse.getAttributeValue(EntityAttributes.JUMP_STRENGTH);
		MobsInfo.LOGGER.debug("EntityAttributes.JUMP_STRENGTH: {}", jumpStrength);
		double jumpHeight = jumpHeightFormula.calculate(horse);
		MobsInfo.LOGGER.debug("Jump Height: {}", jumpHeight);
		return jumpHeight;
	}

	enum JumpHeightFormula {

		SIMPLE(SimpleJumpHeightCalculator::new),
		COMMUNITY(CommunityJumpHeightCalculator::new),
		INTERPOLATED(InterpolatedJumpHeightCalculator::new);

		private final JumpHeightCalculator jumpHeightCalculator;

		JumpHeightFormula(final Supplier<JumpHeightCalculator> jumpHeightCalculatorSupplier) {
			this.jumpHeightCalculator = jumpHeightCalculatorSupplier.get();
		}

		public double calculate(final HorseEntity horse) {
			return jumpHeightCalculator.calculate(horse);
		}
	}

	interface JumpHeightCalculator {
		double calculate(HorseEntity horse);
	}

	static class SimpleJumpHeightCalculator implements JumpHeightCalculator {

		@Override
		public double calculate(final HorseEntity horse) {
			double jumpStrength = horse.getAttributeValue(EntityAttributes.JUMP_STRENGTH);
			return 5.0 * jumpStrength * jumpStrength
					+ 0.57 * jumpStrength;
		}
	}

	static class CommunityJumpHeightCalculator implements JumpHeightCalculator {

		@Override
		public double calculate(final HorseEntity horse) {
			double jumpStrength = horse.getAttributeValue(EntityAttributes.JUMP_STRENGTH);
			return -0.1817584952 * jumpStrength * jumpStrength * jumpStrength
					+ 3.689713992 * jumpStrength * jumpStrength
					+ 2.128599134 * jumpStrength
					- 0.343930367;
		}
	}

	static class InterpolatedJumpHeightCalculator implements JumpHeightCalculator {

		private static final TreeMap<Double, Double> JUMP_DATA = new TreeMap<>();

		static {
			JUMP_DATA.put(0.4, MIN_JUMP_HEIGHT);
			JUMP_DATA.put(0.5, 1.6248);
			JUMP_DATA.put(0.6, 2.2216);
			JUMP_DATA.put(0.7, 2.8933);
			JUMP_DATA.put(0.8, 3.6339);
			JUMP_DATA.put(0.9, 4.4379);
			JUMP_DATA.put(1.0, MAX_JUMP_HEIGHT);
		}

		@Override
		public double calculate(final HorseEntity horse) {
			double jumpStrength = horse.getAttributeValue(EntityAttributes.JUMP_STRENGTH);
			if (jumpStrength < 0.4) {
				return MIN_JUMP_HEIGHT;
			}
			if (jumpStrength > 1.0) {
				return MAX_JUMP_HEIGHT;
			}

			Double x1 = JUMP_DATA.floorKey(jumpStrength);
			Double x2 = JUMP_DATA.ceilingKey(jumpStrength);
			if (x1.equals(x2)) {
				return JUMP_DATA.get(x1);
			}

			double y1 = JUMP_DATA.get(x1);
			double y2 = JUMP_DATA.get(x2);

			// Perform linear interpolation
			return y1 + ((jumpStrength - x1) / (x2 - x1)) * (y2 - y1);
		}

	}
}
