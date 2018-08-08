package com.lseg.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class CumulativeReturnCalculator {
	// this method calculates the cumulative return for a given range of values
	public static double calculateReturn(Map<Date, Double> rangeMap) {

		BigDecimal result = new BigDecimal(1);
		for (Double cumulative : rangeMap.values()) {
			result = result.multiply(new BigDecimal(cumulative).add(new BigDecimal(1)));
		}

		return format((result.subtract(new BigDecimal(1))));
	}

	private static double format(BigDecimal decimal) {
		return decimal.setScale(8, BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}

}
