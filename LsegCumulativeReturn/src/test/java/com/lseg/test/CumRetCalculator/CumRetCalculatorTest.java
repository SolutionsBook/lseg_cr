package com.lseg.test.CumRetCalculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.Date;
import java.util.TreeMap;
import org.junit.Before;
import org.junit.Test;
import com.lseg.util.DateUtil;

public class CumRetCalculatorTest
{
    private final CumRetCalculator cumRetCalculator;
    private final String baseDate = "2015-02-01";
    private static TreeMap<Date, Double> dailyReturnMap;
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public CumRetCalculatorTest()
    {
        dailyReturnMap = new TreeMap<>();
        cumRetCalculator = new CumRetCalculator(dailyReturnMap);
    }

    @Before
    public void populate_map()
    {
        dailyReturnMap.put(DateUtil.toDate("2015-01-10", DATE_FORMAT), 0.10);
        dailyReturnMap.put(DateUtil.toDate("2015-02-10", DATE_FORMAT), 0.05);
        dailyReturnMap.put(DateUtil.toDate("2015-04-10", DATE_FORMAT), 0.15);
        dailyReturnMap.put(DateUtil.toDate("2015-04-15", DATE_FORMAT), -0.10);
        dailyReturnMap.put(DateUtil.toDate("2015-06-10", DATE_FORMAT), -0.12);

    }

    @Test
    public void test_cumulative_return()
    {
        // this will not return null as specified in requirement - returns NaN
        // instead as we are not returning string
        assertEquals(getCumulativeReturn("2015-01-31", baseDate), Double.NaN, 0);

        double test1 = getCumulativeReturn("2015-02-28", baseDate);
        assertEquals(0.05, test1, 0);

        double cacheTest = getCumulativeReturn("2015-02-28", baseDate);
        assertEquals(0.05, cacheTest, 0);

        double test2 = getCumulativeReturn("2015-03-13", baseDate);
        assertEquals(0.05, test2, 0);

        double cacheTest2 = getCumulativeReturn("2015-03-13", baseDate);
        assertEquals(0.05, cacheTest2, 0);

        double test3 = getCumulativeReturn("2015-04-30", baseDate);
        assertEquals(0.08675, test3, 0);

        double test4 = getCumulativeReturn("2015-05-08", baseDate);
        assertEquals(0.08675, test4, 0);

        double test5 = getCumulativeReturn("2015-06-30", baseDate);
        assertEquals(-0.04366, test5, 0);
    }

    @Test
    public void test_cumulative_return_negative()
    {

        double test1 = getCumulativeReturn("2015-02-28", baseDate);
        if (test1 != 0.05) {
            fail("Should equal to 0.05");
        }
        double test0 = getCumulativeReturn("2015-01-31", baseDate);
        if (!Double.isNaN(test0)) {
            fail("Should be NaN");
        }

        double test2 = getCumulativeReturn("2015-03-13", baseDate);
        if (test2 != 0.05) {
            fail("Should equal to 0.05");
        }

        double test3 = getCumulativeReturn("2015-04-30", baseDate);
        if (test3 != 0.08675) {
            fail("Should equal to 0.08675");
        }

        double test4 = getCumulativeReturn("2015-05-08", baseDate);
        if (test4 != 0.08675) {
            fail("Should equal to 0.08675");
        }

        double test5 = getCumulativeReturn("2015-06-30", baseDate);
        if (test5 != -0.04366) {
            fail("Should equal to -0.04366");
        }

    }

    private double getCumulativeReturn(String date, String base)
    {
        Date asOfDate = DateUtil.toDate(date, DATE_FORMAT);
        Date baseDate = DateUtil.toDate(base, DATE_FORMAT);
        return cumRetCalculator.findCumulativeReturn(asOfDate, baseDate);
    }

}
