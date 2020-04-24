package com.cinderellavip;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        System.out.println(getDaysOfMonth(2020,5));
    }

    public int getDaysOfMonth(int year, int month) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(year + "-" + month + "-01"));
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            return 0;
        }

    }
}