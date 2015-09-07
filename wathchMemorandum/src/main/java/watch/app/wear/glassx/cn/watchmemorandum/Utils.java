package watch.app.wear.glassx.cn.watchmemorandum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.text.format.Time;

public class Utils
{

    private static ArrayList<HashMap<String, String>> arraylist = new ArrayList();
    private static ArrayList<HashMap<String, String>> showlist = new ArrayList();
    private static Long[] tempTimeMillis = null;

    public Utils()
    {
    }

    public static void put(HashMap<String, String> map)
    {
        arraylist.add(map);
    }

    public static ArrayList<HashMap<String, String>> getList()
    {
        return arraylist;
    }

    public static HashMap<String, String> getItem(int position)
    {
        return (HashMap) arraylist.get(position);
    }


    public static void sort()
    {
        int size = arraylist.size();

        for (int i = size - 1; i >= 0; --i)
        {
            for (int j = 0; j < i; ++j)
            {
                if (Long.parseLong((String) ((HashMap) arraylist.get(j)).get("datetime")) < Long.parseLong((String) ((HashMap) arraylist.get(j + 1)).get("datetime")))
                {
                    HashMap temp = (HashMap) arraylist.get(j);
                    arraylist.set(j, (HashMap) arraylist.get(j + 1));
                    arraylist.set(j + 1, temp);
                }
            }
        }

    }

    public static void MillisToDate(ArrayList<HashMap<String, String>> arraylist)
    {
        int size = arraylist.size();
        tempTimeMillis = new Long[size];

        for (int i = 0; i < size; ++i)
        {
            String temp = timeTransfer(i);
            ((HashMap) arraylist.get(i)).remove("datetime");
            ((HashMap) arraylist.get(i)).put("datetime", temp);
        }

    }

    public static void DateToMillis(ArrayList<HashMap<String, String>> arraylist)
    {
        int size = arraylist.size();

        for (int i = 0; i < size; ++i)
        {
            String temp = String.valueOf(tempTimeMillis[i]);
            ((HashMap) arraylist.get(i)).remove("datetime");
            ((HashMap) arraylist.get(i)).put("datetime", temp);
        }

    }

    public static String format(int hourOfDay)
    {
        String str = "" + hourOfDay;
        if (str.length() == 1)
        {
            str = "0" + str;
        }

        return str;
    }

    public static String toDateString(Calendar calendar)
    {
        String day = null;
        switch (calendar.get(Calendar.DAY_OF_WEEK))
        {
            case 1:
                day = "日";
                break;
            case 2:
                day = "一";
                break;
            case 3:
                day = "二";
                break;
            case 4:
                day = "三";
                break;
            case 5:
                day = "四";
                break;
            case 6:
                day = "五";
                break;
            case 7:
                day = "六";
        }
        return calendar.get(Calendar.YEAR) + "年" + format(calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_WEEK) + "日" + "  星期" + day;
    }

    public static String timeTransfer(int i)
    {
        Time time = new Time();
        long tempLong = Long.parseLong((String) ((HashMap) arraylist.get(i)).get("datetime"));
        tempTimeMillis[i] = Long.valueOf(tempLong);
        time.setToNow();
        String dateNow = time.toString().substring(0, 8);
        String timeNow = time.toString().substring(9, 13);
        time.set(tempLong);
        String datePast = time.toString().substring(0, 8);
        String timePast = time.toString().substring(9, 13);
        long tempDate = Long.parseLong(dateNow) - Long.parseLong(datePast);
        long tempTime = Long.parseLong(timeNow) - Long.parseLong(timePast);
        return tempDate == 0L ? timePast.substring(0, 2) + ":" + timePast.substring(2, 4) : (tempDate == 1L ? "昨天" : (tempDate >= 2L ? datePast.substring(4, 6) + "/" + datePast.substring(6, 8) : null));
    }

    public static String timeTransfer(String i)
    {
        Time time = new Time();
        time.setToNow();
        String dateNow = time.toString().substring(0, 8);
        String timeNow = time.toString().substring(9, 13);
        time.set(Long.parseLong(i));
        String dateSet = time.toString().substring(0, 8);
        String timeSet = time.toString().substring(9, 13);
        long tempDate = Long.parseLong(dateSet) - Long.parseLong(dateNow);
        long tempTime = Long.parseLong(timeSet) - Long.parseLong(timeNow);
        return tempDate >= 0L && (tempDate != 0L || tempTime >= 0L) ? (tempDate == 0L && tempTime > 0L ? "今天" + timeSet.substring(0, 2) + ":" + timeSet.substring(2, 4) : (tempDate == 1L ? "明天" + timeSet.substring(0, 2) + ":" + timeSet.substring(2, 4) : (tempDate == 2L ? "后天" + timeSet.substring(0, 2) + ":" + timeSet.substring(2, 4) : (tempDate > 2L ? dateSet.substring(4, 6) + "/" + dateSet.substring(6, 8) : null)))) : "已过期";
    }
}