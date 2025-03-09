package org.raduking.mobsinfo.utils;

public class MobsInfoMath {

	public static final int DECIMALS = 2;

	private static final double TEN_FACTOR = Math.pow(10, DECIMALS);

	public static double round(final double x) {
		return Math.round(x * TEN_FACTOR) / TEN_FACTOR;
	}

}
