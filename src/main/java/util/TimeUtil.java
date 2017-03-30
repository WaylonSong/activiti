package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by song on 16/8/20.
 */
public class TimeUtil {
    static Calendar now = Calendar.getInstance();

    public static Date stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
//        res = simpleDateFormat.format(date);
//        return res;
        return date;
    }

    public static String getYearMonth(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(d);
    }

    public static String[] getMonthsWithinYear(int yearsBefore){
        String[] months = new String[12];
        now.get(Calendar.YEAR);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        for(int i =0 ; i < 12 ; i++){
            months[i] = sdf.format(now.getTime());
            now.add(Calendar.MONTH, -1);
        }
        return months;
    }

    public static String getMonthByShift(int year, int month){
        now.add(Calendar.YEAR, year);
        now.add(Calendar.MONTH, month);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(now.getTime());
    }

    public static String getFirstMonthYearBefore(int year){
        now.add(Calendar.YEAR, -year);
        now.set(now.get(Calendar.YEAR), 1, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(now.getTime());
    }

    public static String now(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(now.getTime());
    }
}
