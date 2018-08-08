package com.lseg.test.CumRetCalculator;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import com.lseg.util.CumulativeReturnCalculator;

class CumRetCalculator
{
    // define map
    private final TreeMap<Date, Double> dailyReturnTreeMap;
    private Map<String, Double> cacheMap = new ConcurrentHashMap<>();

    // initialize map and populate map
    // load this on application init

    public CumRetCalculator(TreeMap<Date, Double> dailyReturns)
    {
        this.dailyReturnTreeMap = DailyReturnSingleton.getInstance(dailyReturns).getDailyReturn();
    }

    public double findCumulativeReturn(Date asOf, Date base) throws Exception
    {
        if(asOf == null || base == null){
            throw new IllegalArgumentException("As Of Date and Base Date cannot be null");
        }
        // since return type is double, we cannot return null so returning NaN
        if (asOf.before(base)) {
            return Double.NaN;
        }

        String cacheKey = asOf + "~" + base;

        if (cacheMap.containsKey(cacheKey)) {
            // no logging yet so using sysout
            System.out.println("[CumRetCalculator][findCumulativeReturn] cache hit-> fetching from cache for " + cacheKey);
            return cacheMap.get(cacheKey);
        } else {
            Map<Date, Double> rangeMap = this.dailyReturnTreeMap.subMap(base, false, asOf, true);
            final double result = CumulativeReturnCalculator.calculateReturn(rangeMap);
            cacheMap.putIfAbsent(cacheKey, result);
            return result;
        }

    }

}
