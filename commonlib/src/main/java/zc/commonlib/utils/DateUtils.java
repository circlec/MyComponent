package zc.commonlib.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
    @SuppressLint("SimpleDateFormat")
    public static String getYYMMDD(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getYYMM(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(time);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getYMDHMS(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getHMS(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(time);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getYMDChina(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getYMDChinaHM(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return sdf.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getYMDSlashHM(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return sdf.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getMDHM(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("M月d日 HH:mm");
        return sdf.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getMD(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("M月d日");
        return sdf.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getMDFromStringDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("M月d日");
        long time = getStringToDate(date);
        return sdf.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getWeekFromStringDate(String date) {
        long time = getStringToDate(date);
        return getWeek(time);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getYYMMDDHHMM(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return sdf.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getHHMM(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getMMSS(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        return sdf.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getMMSS_SS(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SS");
        return sdf.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getYY(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(time);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getMM(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDD(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getM_D(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        return sdf.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getMSplitD(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
        return sdf.format(new Date(time));
    }

    public static Integer[] getYY_MM_DD(long time) {
        int year = Integer.parseInt(getYY(time));
        int month = Integer.parseInt(getMM(time));
        int day = Integer.parseInt(getDD(time));
        Integer[] strings = {year, month, day};
        return strings;
    }

    public static String getGeneralTime(long time) {
        long nowTime = System.currentTimeMillis();
        long betweenTime = nowTime - time;

        if (betweenTime >= 0 && betweenTime < 60 * 1000) {
            return "刚刚";
        } else if (betweenTime >= 60 * 1000 && betweenTime < 3600 * 1000) {
            int minute = (int) (betweenTime / (60 * 1000));
            return minute + "分钟前";
        } else if (betweenTime >= 60 * 1000 && betweenTime < 24 * 3600 * 1000) {
            int hour = (int) (betweenTime / (3600 * 1000));
            return hour + "小时前";
        } else if (getYYMMDD(nowTime).equals(getYYMMDD(time))) {
            return getHHMM(time);
        } else {
            return getYYMMDD(time);
        }
    }

    public static long getStringToLongTime(String time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateFromTimeStr(String time) {
        SimpleDateFormat oldSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat newSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = oldSdf.parse(time);
            return newSdf.format(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


    @SuppressLint("SimpleDateFormat")
    public static String getTimeFromDateStr(String time) {
        SimpleDateFormat oldSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat newSdf = new SimpleDateFormat("HH:mm:ss");
        Date date;
        try {
            date = oldSdf.parse(time);
            return newSdf.format(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String getYMDHMFromDateStr(String time) {
        SimpleDateFormat oldSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat newSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date;
        try {
            date = oldSdf.parse(time);
            return newSdf.format(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getStringFromTimeStr(String time, String oldPattern, String newPattern) {
        SimpleDateFormat oldSdf = new SimpleDateFormat(oldPattern);
        SimpleDateFormat newSdf = new SimpleDateFormat(newPattern);
        Date date;
        try {
            date = oldSdf.parse(time);
            return newSdf.format(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /*将字符串转为时间戳*/
    public static long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /*将字符串转为时间戳*/
    public static long getStringToTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getWeek(long time) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date(time));
        int week = cd.get(Calendar.DAY_OF_WEEK); //获取星期
        String weekString;
        switch (week) {
            case Calendar.SUNDAY:
                weekString = "周日";
                break;
            case Calendar.MONDAY:
                weekString = "周一";
                break;
            case Calendar.TUESDAY:
                weekString = "周二";
                break;
            case Calendar.WEDNESDAY:
                weekString = "周三";
                break;
            case Calendar.THURSDAY:
                weekString = "周四";
                break;
            case Calendar.FRIDAY:
                weekString = "周五";
                break;
            default:
                weekString = "周六";
                break;
        }
        return weekString;
    }

    /**
     * 将毫秒转时分秒
     *
     * @param time
     * @return
     */
    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
//        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static String getNextDay(int space, String nowDate, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(getStringToDate(nowDate)));
        calendar.add(Calendar.DATE, space);
        String next_day = sdf.format(calendar.getTime());
        return next_day;
    }

    public static Date getNextDay(int space) {
        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date(DateUtils.getStringToDate("2019-08-30")));
        calendar.add(Calendar.DATE, space);
        Date next_day = calendar.getTime();
        return next_day;
    }

    public static String getNextDayStr(int space) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, space);
        Date next_day = calendar.getTime();
        String next_day_Str = getYYMMDD(next_day.getTime());
        return next_day_Str;
    }

    public static List<Date> getNextDays(boolean contd, int space) {
        List<Date> dates = new ArrayList<>();
        if (contd)
            dates.add(new Date());
        for (int i = 1; i <= space; i++) {
            dates.add(getNextDay(i));
        }
        return dates;
    }

    public static long getSpaceMonths(int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, count);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTimeInMillis();
    }

    public static String getMonthBeginDate(String date) {
        Calendar cale = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cale.setTime(formatter.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        String firstDayOfMonth = formatter.format(cale.getTime());
        return firstDayOfMonth;
    }

    public static String getMonthEndDate(String date) {
        Calendar cale = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cale.setTime(formatter.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        String lastDayOfMonth = formatter.format(cale.getTime());
        return lastDayOfMonth;
    }

    public static boolean compareTimeToNow(Date date) {
        Date date1 = new Date();
        //先转成日期字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date1Str = sdf.format(new Date());
        //再利用字符串转成日期,确保日期里面都没有带小时和分钟
        try {
            date1 = sdf.parse(date1Str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.before(date1);
    }

    public static boolean compareTimeStrToNow(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String dateNowStr = sdf.format(new Date());
        Date dateNow;
        Date date1;
        try {
            date1 = sdf.parse(timeStr);
            dateNow = sdf.parse(dateNowStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return date1.before(dateNow);
    }

    public static boolean compareTwoTime(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1;
        Date date2;
        try {
            date1 = sdf.parse(startDate);
            date2 = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return date1.before(date2);
    }

    public static String formatData(Date time, String tformateYmd) {
        return getYYMMDD(time.getTime());
    }

    public static long getDataTime(String time) {
        return getStringToDate(time);
    }

    @SuppressLint("DefaultLocale")
    public static String getHHMMSS(long time) {
        String timeStr;
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        if (hours == 0 && minutes == 0) {
            timeStr = String.format("%02d秒", seconds);
        } else if (hours == 0) {
            timeStr = String.format("%02d分钟%02d秒", minutes, seconds);
        } else {
            timeStr = String.format("%02d小时%02d分钟%02d秒", hours, minutes, seconds);
        }
        return timeStr;
    }

    @SuppressLint("DefaultLocale")
    public static String getHHMMSSStr(long time) {
        String timeStr;
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        if (hours == 0 && minutes == 0) {
            timeStr = String.format("00:00:%02d", seconds);
        } else if (hours == 0) {
            timeStr = String.format("00:%02d:%02d", minutes, seconds);
        } else {
            timeStr = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
        return timeStr;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getPhyOrderListTime(long orderTime) {
        String timeStr = "";
        long currentTime = System.currentTimeMillis();
        if (getYYMMDD(orderTime).equals(getYYMMDD(currentTime))) {
            timeStr = "今日 " + getHHMM(orderTime);
        } else if (getNextDay(-1, getYYMMDD(currentTime), "yyyy-MM-dd").equals(getYYMMDD(orderTime))) {
            timeStr = "昨天 " + getHHMM(orderTime);
        } else if (getYY(orderTime).equals(getYY(currentTime))) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
            timeStr = sdf.format(new Date(orderTime));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            timeStr = sdf.format(new Date(orderTime));
        }
        return timeStr;
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean checkNowMonth(long compareTime) {
        long currentTime = System.currentTimeMillis();
        return getYYMM(compareTime).equals(getYYMM(currentTime));
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean checkNowYear(long compareTime) {
        long currentTime = System.currentTimeMillis();
        return getYY(compareTime).equals(getYY(currentTime));
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean checkNowDay(long compareTime) {
        long currentTime = System.currentTimeMillis();
        return getYYMMDD(compareTime).equals(getYYMMDD(currentTime));
    }


    public static String[] getTimeIntervalArray(Calendar nextDate, Calendar previousDate) {
        int year = nextDate.get(Calendar.YEAR) - previousDate.get(Calendar.YEAR);
        int month = nextDate.get(Calendar.MONTH) - previousDate.get(Calendar.MONTH);
        int day = nextDate.get(Calendar.DAY_OF_MONTH) - previousDate.get(Calendar.DAY_OF_MONTH);
        int hour = nextDate.get(Calendar.HOUR_OF_DAY) - previousDate.get(Calendar.HOUR_OF_DAY);// 24小时制
        int min = nextDate.get(Calendar.MINUTE) - previousDate.get(Calendar.MINUTE);
        int second = nextDate.get(Calendar.SECOND) - previousDate.get(Calendar.SECOND);

        boolean hasBorrowDay = false;// "时"是否向"天"借过一位

        if (second < 0) {
            second += 60;
            min--;
        }

        if (min < 0) {
            min += 60;
            hour--;
        }

        if (hour < 0) {
            hour += 24;
            day--;

            hasBorrowDay = true;
        }

        if (day < 0) {
            // 计算截止日期的上一个月有多少天，补上去
            Calendar tempDate = (Calendar) nextDate.clone();
            tempDate.add(Calendar.MONTH, -1);// 获取截止日期的上一个月
            day += tempDate.getActualMaximum(Calendar.DAY_OF_MONTH);

            // nextDate是月底最后一天，且day=这个月的天数，即是刚好一整个月，比如20160131~20160229，day=29，实则为1个月
            if (!hasBorrowDay
                    && nextDate.get(Calendar.DAY_OF_MONTH) == nextDate.getActualMaximum(Calendar.DAY_OF_MONTH)// 日期为月底最后一天
                    && day >= nextDate.getActualMaximum(Calendar.DAY_OF_MONTH)) {// day刚好是nextDate一个月的天数，或大于nextDate月的天数（比如2月可能只有28天）
                day = 0;// 因为这样判断是相当于刚好是整月了，那么不用向 month 借位，只需将 day 置 0
            } else {// 向month借一位
                month--;
            }
        }

        if (month < 0) {
            month += 12;
            year--;
        }

        return new String[]{toStringWithZero(year), toStringWithZero(month),
                toStringWithZero(day), toStringWithZero(hour),
                toStringWithZero(min), toStringWithZero(second)};
    }

    private static String toStringWithZero(int number) {
        return number > 9 ? String.valueOf(number) : "0" + number;
    }


    /**
     * 获取一年后时间
     *
     * @param currentTimeMillis
     * @return
     */
    public static String getYearLater(long currentTimeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);
        calendar.add(Calendar.YEAR, 1);
        return getYMDHMS(calendar.getTimeInMillis());
    }
}
