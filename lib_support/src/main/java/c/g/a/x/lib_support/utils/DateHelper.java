package c.g.a.x.lib_support.utils;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import c.g.a.x.lib_support.android.utils.Logger;

public final class DateHelper {

    public static final class TimeValue {

        public static final long SEC_MILLIS = 1000;

        //        public static final long MIN_MILLIS = SEC_MILLIS * 60;
        public static final long MIN_MILLIS = 60000;

        //        public static final long HOUR_MILLIS = MIN_MILLIS * 60;
        public static final long HOUR_MILLIS = 3600000;

        //        public static final long DAY_MILLIS = HOUR_MILLIS * 24;
        public static final long DAY_MILLIS = 86400000;

    }

    /**
     * Letter		Date or Time Component									Presentation			Examples
     * G			Era designator											Text					AD
     * y			Year													Year					1996; 96
     * Y			Week year												Year					2009; 09
     * M			Month in year (context sensitive)						Month					July; Jul; 07
     * L			Month in year (standalone form)							Month					July; Jul; 07
     * w			Week in year											Number					27
     * W			Week in month											Number					2
     * D			Day in year												Number					189
     * d			Day in month											Number					10
     * F			Day of week in month									Number					2
     * E			Day name in week										Text					Tuesday; Tue
     * u			Day number of week (1 = Monday, ..., 7 = Sunday)		Number					1
     * a			Am/pm marker											Text					PM
     * H			Hour in day (0-23)										Number					0
     * k			Hour in day (1-24)										Number					24
     * K			Hour in am/pm (0-11)									Number					0
     * h			Hour in am/pm (1-12)									Number					12
     * m			Minute in hour											Number					30
     * s			Second in minute										Number					55
     * S			Millisecond												Number					978
     * z			Time zone												General time zone		Pacific Standard Time; PST; GMT-08:00
     * Z			Time zone												RFC 822 time zone		-0800
     * X			Time zone												ISO 8601 time zone		-08; -0800; -08:00
     * <p>
     * 使用注意
     * year y：建议使用yy或yyyy；超过四位（ps：yyyyy）会补0（ps：02018）；otherwise：按四位匹配
     * Month M：建议使用MM；M匹配一位；otherwise：会展示系统默认
     * Day d：建议使用 dd或d（同dd）；超过2位（ps：dddd）会补0（ps：0013）
     * Hour H：同上
     * Minute m：同上
     * Second s：同上
     * week e：建议使用E（Sun）或EEEE（Sunday）；
     **/
    public static final class Pattern {
        ///////// date time
        //date3 time3
        public static final String PATTERN_D3_T3_1 = "yyyy-MM-dd_HH-mm-ss";
        public static final String PATTERN_D3_T3_2 = "yyyy-MM-dd HH:mm:ss";
        public static final String PATTERN_D3_T3_3 = "yyyy|MM|dd HH:mm:ss";
        public static final String PATTERN_D3_T3_4 = "yyyy年MM月dd日 HH:mm:ss";
        public static final String PATTERN_D3_T3_5 = "yyyy/MM/dd HH:mm:ss";

        //date3 time2
        public static final String PATTERN_D3_T2_1 = "yy/MM/dd HH:mm";
        public static final String PATTERN_D3_T2_2 = "yyyy-MM-dd HH:mm";
        public static final String PATTERN_D3_T2_3 = "yyyyMMddHHmm";

        //date2 time3
        public static final String PATTERN_D2_T3_1 = "MMddHHmmss";
        public static final String PATTERN_D2_T3_2 = "MM-dd HH:mm:ss";

        //date2 time2
        public static final String PATTERN_D2_T2_1 = "MM-dd HH:mm";
        public static final String PATTERN_D2_T2_2 = "MM/dd HH:mm";

        ///////// date
        //date3
        public static final String PATTERN_D3_1 = "yyyy.MM.dd";
        public static final String PATTERN_D3_2 = "yyyyMMdd";
        public static final String PATTERN_D3_3 = "yyyy-MM-dd";
        public static final String PATTERN_D3_4 = "yy年MM月dd日";

        //date2
        public static final String PATTERN_D2_1 = "yyyy-MM";
        public static final String PATTERN_D2_2 = "yyyy年MM月";
        public static final String PATTERN_D2_3 = "yyyyMM";
        public static final String PATTERN_D2_4 = "MM-dd";

        //date1
        public static final String PATTERN_D1_1 = "yyyy年";
        public static final String PATTERN_D1_2 = "yyyy";
        public static final String PATTERN_D1_3 = "yy";
        public static final String PATTERN_D1_4 = "MM";
        public static final String PATTERN_D1_5 = "dd";

        /////////
        //time3
        public static final String PATTERN_T3_1 = "HH:mm:ss";

        //time2
        public static final String PATTERN_T2_1 = "HH:mm";
        public static final String PATTERN_T2_2 = "mm分ss秒";

        ///////// otherwise
        //week
        public static final String PATTERN_D3_WEEK_1 = "yyyy.MM.dd EEEE";


        public static final String PATTERN_DAY_HOUR_MIN = "d天H时m分";


    }

    //

    private Calendar calendar;

    public DateHelper(String dateStr, String pattern) throws Exception {
        getCalendar().setTime(new SimpleDateFormat(pattern).parse(dateStr));
    }

    public DateHelper(Calendar calendar) {
        this.calendar = calendar;
    }

    public DateHelper(Date date) {
        getCalendar().setTime(date);
    }

    public DateHelper(Timestamp timestamp) {
        getCalendar().setTimeInMillis(timestamp.getTime());
    }

    public DateHelper(java.sql.Date sqlDate) {
        getCalendar().setTime(sqlDate);
    }

    public DateHelper(long longDate) {
        getCalendar().setTimeInMillis(longDate);
    }

    public DateHelper(int year, int month, int day, int hour, int minute, int second) {
        getCalendar().set(year, month - 1, day, hour, minute, second);
    }

    public DateHelper() {
        getCalendar();
    }

    public Calendar getCalendar() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        return calendar;
    }

    public Date getDate() {
        return getCalendar().getTime();
    }

    public Date getTimestamp() {
        return new Timestamp(getCalendar().getTimeInMillis());
    }

    public java.sql.Date getSqlDate() {
        return new java.sql.Date(getCalendar().getTimeInMillis());
    }

    public String getString(String pattern) {
        return new SimpleDateFormat(pattern).format(getCalendar().getTime());
    }

    public long getLong() {
        return getCalendar().getTimeInMillis();
    }


    public DateHelper calculate(int year, int month, int day, int hour, int minute, int second) {
        getCalendar();
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DATE, day);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, second);
        return this;
    }

    public DateHelper calculateDay(int day) {
        getCalendar().add(Calendar.DATE, day);
        return this;
    }

    public DateHelper calculateMinute(int minute) {
        getCalendar().add(Calendar.MINUTE, minute);
        return this;
    }

    public DateHelper calculateHour(int hour) {
        getCalendar().add(Calendar.HOUR_OF_DAY, hour);
        return this;
    }

    public DateHelper setDateTime(int year, int month, int day, int hour,
                                  int minute, int second) {

        getCalendar();
        if (year >= 0) {
            calendar.set(Calendar.YEAR, year);
        }
        if (month >= 0) {
            calendar.set(Calendar.MONTH, month - 1);
        }
        if (day >= 0) {
            calendar.set(Calendar.DATE, day);
        }
        if (hour >= 0) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute >= 0) {
            calendar.set(Calendar.MINUTE, minute);
        }
        if (second >= 0) {
            calendar.set(Calendar.SECOND, second);
        }
        return this;
    }

    public DateHelper setDate(int year, int month, int day) {

        getCalendar();
        if (year >= 0) {
            calendar.set(Calendar.YEAR, year);
        }
        if (month >= 0) {
            calendar.set(Calendar.MONTH, month - 1);
        }
        if (day >= 0) {
            calendar.set(Calendar.DATE, day);
        }

        return this;
    }

    public DateHelper setTime(int hour, int minute, int second) {

        getCalendar();
        if (hour >= 0) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute >= 0) {
            calendar.set(Calendar.MINUTE, minute);
        }
        if (second >= 0) {
            calendar.set(Calendar.SECOND, second);
        }

        return this;
    }

    public int getDay() {
        return getCalendar().get(Calendar.DATE);
    }

    public int getHour() {
        return getCalendar().get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return getCalendar().get(Calendar.MINUTE);
    }

    public int getSecond() {
        return getCalendar().get(Calendar.SECOND);
    }

    public int getMonth() {
        return getCalendar().get(Calendar.MONTH) + 1;
    }

    public int getYear() {
        return getCalendar().get(Calendar.YEAR);
    }

//    public String getYear2() {
//        String str = String.valueOf(getCalendar().get(Calendar.YEAR));
//        return str.substring(str.length() - 2);
//    }

    public LastTime getDvalue4Now() {
        return getDvalue(System.currentTimeMillis());
    }

    public LastTime getDvalue(DateHelper time) {
        return getDvalue(time.getLong());
    }

    public LastTime getDvalue(long time) {
        return new LastTime(this.getLong() - time);
    }

    public LastTime getDvalueAbs(long time) {
        return new LastTime(Math.abs(this.getLong() - time));
    }


//    public static String getAgoTime(long msec) {
//
//        long all_secs = msec / 1000;
//        //<1min
//        if (all_secs < 60) return "刚刚";
//
//        long all_mins = all_secs / 60;
//        //<1hour
//        if (all_mins < 60) return all_mins + "分钟前";
//
//        long all_hours = all_mins / 60;
//        //<1 day
//        if (all_hours < 24) return all_hours + "小时前";
//
//        long all_days = all_hours / 24;
//        //<1 month
//        if (all_days < 30) return all_days + "天前";
//
//        long all_months = all_days / 30;
//        //<1 year
//        if (all_months < 12) return all_months + "月前";
//
//        long all_years = all_months / 12;
//        return all_years + "年前";
//    }


    public final class LastTime {

        public long msec;

        public long all_secs;
        public long all_mins;
        public long all_hours;
        public long all_days;
        public long all_months;
        public long all_years;

        public long last_secs = -1;
        public long last_mins = -1;
        public long last_hours = -1;
        public long last_days = -1;
        public long last_months = -1;
        public long last_years = -1;

        public String time_ago_tag;

        public LastTime(long msec) {
            this.msec = msec;
            Logger.e("d-value====>", msec);
            getLastTime();
        }

        private void getLastTime() {

            if (msec <= 0) return;

            all_secs = msec / 1000;
            last_secs = all_secs;
            //<1min
            if (all_secs < 60) {
                time_ago_tag = "刚刚";
                return;
            }

            last_mins = all_mins = all_secs / 60;
            last_secs = all_secs % 60;
            //<1hour
            if (all_mins < 60) {
                time_ago_tag = all_mins + "分钟前";
                return;
            }

            last_hours = all_hours = all_mins / 60;
            last_mins = all_mins % 60;
            //<1 day
            if (all_hours < 24) {
                time_ago_tag = all_hours + "小时前";
                return;
            }

            last_days = all_days = all_hours / 24;
            last_hours = all_hours % 24;
            //<1 month
            if (all_days < 30) {
                time_ago_tag = all_days + "天前";
                return;
            }

            last_months = all_months = all_days / 30;
            last_days = all_days % 30;
            //<1 year
            if (all_months < 12) {
                time_ago_tag = all_months + "月前";
                return;
            }

            last_years = all_years = all_months / 12;
            last_months = all_months % 12;

            time_ago_tag = all_years + "年前";
        }

        public String getString(String pattern) {
            if (msec <= 0) return "";

//            "yyyy-MM-dd_HH-mm-ss"

//            if (last_years >= 0 && pattern.contains("y")) {
//                pattern.replaceAll("y", " y");
//                pattern.replaceAll("y ", "");
//                pattern.replaceAll(" y", "y");
//                pattern = pattern.replaceAll("y", String.valueOf(last_years));
//            }
//            if (last_months >= 0 && pattern.contains("M")) {
//                pattern.replaceAll("M", " M");
//                pattern.replaceAll("M ", "");
//                pattern.replaceAll(" M", "M");
//                pattern = pattern.replaceAll("M", String.valueOf(last_months));
//            }
//            if (last_days >= 0 && pattern.contains("d")) {
//                pattern.replaceAll("d", " d");
//                pattern.replaceAll("d ", "");
//                pattern.replaceAll(" d", "d");
//                pattern = pattern.replaceAll("d", String.valueOf(last_days));
//            }
//
//            if (last_hours >= 0 && pattern.contains("H")) {
//                pattern.replaceAll("H", " H");
//                pattern.replaceAll("H ", "");
//                pattern.replaceAll(" H", "H");
//                pattern = pattern.replaceAll("H", String.valueOf(last_hours));
//            }
//            if (last_mins >= 0 && pattern.contains("m")) {
//                pattern.replaceAll("m", " m");
//                pattern.replaceAll("m ", "");
//                pattern.replaceAll(" m", "m");
//                pattern = pattern.replaceAll("m", String.valueOf(last_mins));
//            }
//            if (last_secs >= 0 && pattern.contains("s")) {
//                pattern.replaceAll("s", " s");
//                pattern.replaceAll("s ", "");
//                pattern.replaceAll(" s", "s");
//                pattern = pattern.replaceAll("s", String.valueOf(last_secs));
//            }

            if (last_years >= 0 && pattern.contains("y")) {
                pattern = pattern.replaceAll("(y)+", String.valueOf(last_years));
            }
            if (last_months >= 0 && pattern.contains("M")) {
                pattern = pattern.replaceAll("(M)+", String.valueOf(last_months));
            }
            if (last_days >= 0 && pattern.contains("d")) {
                pattern = pattern.replaceAll("(d)+", String.valueOf(last_days));
            }

            if (last_hours >= 0 && pattern.contains("H")) {
                pattern = pattern.replaceAll("(H)+", String.valueOf(last_hours));
            }
            if (last_mins >= 0 && pattern.contains("m")) {
                pattern = pattern.replaceAll("(m)+", String.valueOf(last_mins));
            }
            if (last_secs >= 0 && pattern.contains("s")) {
                pattern = pattern.replaceAll("(s)+", String.valueOf(last_secs));
            }

            return pattern;
        }
    }

    public static void main(String[] args) {
        // System.out.println(new DateHelper("11:11:11", PATTERN_7)
        // .getString(PATTERN_1));
        // System.out.println(new DateHelper().getString(PATTERN_1));
        // System.out.println(new DateHelper().getDay());
        // System.out.println(new DateHelper().getMonth());
        // System.out.println("=====");
        // System.out.println(new DateHelper().calculate(0, 1, 0, 0, 0, 0)
        // .getString(PATTERN_1));
        // System.out
        // .println(new DateHelper().calculate(0, 1, 0, 0, 0, 0).getDay());
        // System.out.println(new DateHelper().calculate(0, 1, 0, 0, 0, 0)
        // .getMonth());
        // DateHelper dateHelper = new DateHelper();
        // long a = dateHelper.getLong();
        // long b = dateHelper.calculateDay(-1).getLong();
        // System.out.println(a - b);
        DateHelper dateHelper = new DateHelper(1000 * 90);

        System.out.println(dateHelper.getString(Pattern.PATTERN_D3_T3_2));
    }

}
