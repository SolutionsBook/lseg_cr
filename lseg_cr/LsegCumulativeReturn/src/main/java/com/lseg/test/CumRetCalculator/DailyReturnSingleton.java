package com.lseg.test.CumRetCalculator;

import java.util.Date;
import java.util.TreeMap;

public class DailyReturnSingleton
{
    // return singleton instance
    // instance marked as volatile because of double check singleton pattern
	
    private volatile static DailyReturnSingleton _instance;
   
    // TreeMap gives natural order by date
    // Performance is O(log n) as it has to maintain the order
    // if we have to maintain the insertion order, we could have used LinkedHashMap
    private final TreeMap<Date, Double> dailyReturnTreeMap;
    
    // use object to lock
    private static final Object lock = new Object();
    
    // initialize map
    private DailyReturnSingleton(TreeMap<Date, Double> dailyReturnMap)
    {
        dailyReturnTreeMap = dailyReturnMap;
    }

    /* 
     double check singleton pattern implementation for performance gain
    */
    public static DailyReturnSingleton getInstance(TreeMap<Date, Double> dailyReturnMap)
    {
        if (_instance == null) {
            synchronized (lock) {
                if (_instance == null) {
                    _instance = new DailyReturnSingleton(dailyReturnMap);
                }
            }
        }
        return _instance;
    }

    public TreeMap<Date, Double> getDailyReturn()
    {
        return dailyReturnTreeMap;
    }
}
