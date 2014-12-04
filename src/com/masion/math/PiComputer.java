package com.masion.math;

public class PiComputer {

	public static double GetPiValue(long n) {
		double s = 0.0;
		for (long i = 1; i <= n; i++) {
			s += 1.0 / (i * i);
		}
		return Math.sqrt(s*6.0);
	}

}
