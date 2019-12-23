package c.g.a.x.lib_support.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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

    public static LastTimeMnger getLastTimeMnger() {
        return new LastTimeMnger();
    }

    public static LastTimeMnger getLastTimeMnger(long msec) {
        return new LastTimeMnger(msec);
    }

    public static LastTimeMnger getLastTimeMnger(long frt, long sec) {
        return new LastTimeMnger(frt - sec);
    }

    public static LastTimeMnger getLastTimeMngerAbs(long frt, long sec) {
        return new LastTimeMnger(Math.abs(frt - sec));
    }

    public static DateMnger getDateMnger(String dateStr, String pattern) throws Exception {
        return new DateMnger(dateStr, pattern);
    }

    public static DateMnger getDateMnger(Calendar calendar) {
        return new DateMnger(calendar);
    }

    public static DateMnger getDateMnger(Date date) {
        return new DateMnger(date.getTime());
    }

    public static DateMnger getDateMnger(Timestamp timestamp) {
        return new DateMnger(timestamp.getTime());
    }

    public static DateMnger getDateMnger(java.sql.Date sqlDate) {
        return new DateMnger(sqlDate.getTime());
    }

    public static DateMnger getDateMnger(long longDate) {
        return new DateMnger(longDate);
    }

    public static DateMnger getDateMnger(int year, int month, int day, int hour, int minute, int second) {
        return new DateMnger(year, month - 1, day, hour, minute, second);
    }

    public static DateMnger getDateMnger() {
        return new DateMnger();
    }

    public static final class DateMnger {
        private Calendar calendar;

        private DateMnger(String dateStr, String pattern) throws Exception {
            getCalendar().setTime(Objects.requireNonNull(new SimpleDateFormat(pattern, Locale.getDefault()).parse(dateStr)));
        }

        private DateMnger(Calendar calendar) {
            this.calendar = calendar;
        }

        private DateMnger(long longDate) {
            getCalendar().setTimeInMillis(longDate);
        }

        private DateMnger(int year, int month, int day, int hour, int minute, int second) {
            getCalendar().set(year, month - 1, day, hour, minute, second);
        }

        private DateMnger() {
            getCalendar();
        }

        public final Calendar getCalendar() {
            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            return calendar;
        }

        public final Date getDate() {
            return getCalendar().getTime();
        }

        public final Date getTimestamp() {
            return new Timestamp(getCalendar().getTimeInMillis());
        }

        public final java.sql.Date getSqlDate() {
            return new java.sql.Date(getCalendar().getTimeInMillis());
        }

        public final String getString(String pattern) {
            return new SimpleDateFormat(pattern, Locale.getDefault()).format(getCalendar().getTime());
        }

        public final long getLong() {
            return getCalendar().getTimeInMillis();
        }


        public final DateMnger calculate(int year, int month, int day, int hour, int minute, int second) {
            getCalendar();
            calendar.add(Calendar.YEAR, year);
            calendar.add(Calendar.MONTH, month);
            calendar.add(Calendar.DATE, day);
            calendar.add(Calendar.HOUR_OF_DAY, hour);
            calendar.add(Calendar.MINUTE, minute);
            calendar.add(Calendar.SECOND, second);
            return this;
        }

        public final DateMnger calculateDay(int day) {
            getCalendar().add(Calendar.DATE, day);
            return this;
        }

        public final DateMnger calculateMinute(int minute) {
            getCalendar().add(Calendar.MINUTE, minute);
            return this;
        }

        public final DateMnger calculateHour(int hour) {
            getCalendar().add(Calendar.HOUR_OF_DAY, hour);
            return this;
        }

        public final DateMnger setDateTime(int year, int month, int day, int hour,
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

        public final DateMnger setDate(int year, int month, int day) {
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

        public final DateMnger setTime(int hour, int minute, int second) {
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

        public final int getDay() {
            return getCalendar().get(Calendar.DATE);
        }

        public final int getHour() {
            return getCalendar().get(Calendar.HOUR_OF_DAY);
        }

        public final int getMinute() {
            return getCalendar().get(Calendar.MINUTE);
        }

        public final int getSecond() {
            return getCalendar().get(Calendar.SECOND);
        }

        public final int getMonth() {
            return getCalendar().get(Calendar.MONTH) + 1;
        }

        public final int getYear() {
            return getCalendar().get(Calendar.YEAR);
        }

//    public String getYear2() {
//        String str = String.valueOf(getCalendar().get(Calendar.YEAR));
//        return str.substring(str.length() - 2);
//    }

        public final LastTimeMnger dValue() {
            return dValue(System.currentTimeMillis());
        }

        public final LastTimeMnger dValue(DateMnger time) {
            return dValue(time.getLong());
        }

        public final LastTimeMnger dValueAbs(long time) {
            return dValue(Math.abs(this.getLong() - time));
        }

        public final LastTimeMnger dValue(long time) {
            return new LastTimeMnger(this.getLong() - time);
        }
    }


    public static final class LastTimeMnger {
        //是否保留时间中多余的前缀0,全为0至少保留一个,例如：
        // true：    05-08-22    00-08-22
        // false：   5-8-22      0-8-22
        private boolean retainZeroValue = true;
        //是否保留一个时间单位组 例如：
        // true：0000-00-00_05-08-22
        // false：05-08-22
        private boolean retainZeroTerm = true;

        private long all_milsec;

        private long all_sec;
        private long all_min;
        private long all_hour;
        private long all_day;
        private long all_month;
        private long all_year;

        private long last_sec = -1;
        private long last_min = -1;
        private long last_hour = -1;
        private long last_day = -1;
        private long last_month = -1;
        private long last_year = -1;

        private String time_ago_tag;

        private LastTimeMnger() {
        }

        private LastTimeMnger(long msec) {
            initLastTime(msec);
        }

        public final void initLastTime(long msec) {
            Logger.e("LastTimeMnger d-value====>", msec);
            if (all_milsec <= 0) return;
            this.all_milsec = msec;

            all_sec = all_milsec / 1000;
            last_sec = all_sec;
            //<1min
            if (all_sec < 60) {
                time_ago_tag = "刚刚";
                return;
            }

            last_min = all_min = all_sec / 60;
            last_sec = all_sec % 60;
            //<1hour
            if (all_min < 60) {
                time_ago_tag = all_min + "分钟前";
                return;
            }

            last_hour = all_hour = all_min / 60;
            last_min = all_min % 60;
            //<1 day
            if (all_hour < 24) {
                time_ago_tag = all_hour + "小时前";
                return;
            }

            last_day = all_day = all_hour / 24;
            last_hour = all_hour % 24;
            //<1 month
            if (all_day < 30) {
                time_ago_tag = all_day + "天前";
                return;
            }

            last_month = all_month = all_day / 30;
            last_day = all_day % 30;
            //<1 year
            if (all_month < 12) {
                time_ago_tag = all_month + "月前";
                return;
            }

            last_year = all_year = all_month / 12;
            last_month = all_month % 12;

            time_ago_tag = all_year + "年前";
        }

        public final LastTimeMnger retainZeroValue() {
            retainZeroValue = true;
            return this;
        }

        public final LastTimeMnger unRetainZeroValue() {
            retainZeroValue = false;
            return this;
        }

        public final LastTimeMnger retainZeroTerm() {
            retainZeroTerm = true;
            return this;
        }

        public final LastTimeMnger unRetainZeroTerm() {
            retainZeroTerm = false;
            return this;
        }

        public final String getTimeTag() {
            return time_ago_tag;
        }

        ////////////
        public final long getAllMilSec() {
            return all_milsec;
        }

        public final long getAllSec() {
            return all_sec;
        }

        public final long getAllMin() {
            return all_min;
        }

        public final long getAllHour() {
            return all_hour;
        }

        public final long getAllDay() {
            return all_day;
        }

        public final long getAllMonth() {
            return all_month;
        }

        public final long getAllYear() {
            return all_year;
        }

        ////////////
        public final long getLastSec() {
            return last_sec == -1 ? 0 : last_sec;
        }

        public final long getLastMin() {
            return last_min == -1 ? 0 : last_min;
        }

        public final long getLastHour() {
            return last_hour == -1 ? 0 : last_hour;
        }

        public final long getLastDay() {
            return last_day == -1 ? 0 : last_day;
        }

        public final long getLastMonth() {
            return last_month == -1 ? 0 : last_month;
        }

        public final long getLastYear() {
            return last_year == -1 ? 0 : last_year;
        }

        public final String getLastSecStr() {
            return retainZeroValue ? StringUtils.formatLong2String(getLastSec(), 2) : String.valueOf(getLastSec());
        }

        public final String getLastMinStr() {
            return retainZeroValue ? StringUtils.formatLong2String(getLastMin(), 2) : String.valueOf(getLastMin());
        }

        public final String getLastHourStr() {
            return retainZeroValue ? StringUtils.formatLong2String(getLastHour(), 2) : String.valueOf(getLastHour());
        }

        public final String getLastDayStr() {
            return retainZeroValue ? StringUtils.formatLong2String(getLastDay(), 2) : String.valueOf(getLastDay());
        }

        public final String getLastMonthStr() {
            return retainZeroValue ? StringUtils.formatLong2String(getLastMonth(), 2) : String.valueOf(getLastMonth());
        }

        public final String getLastYearStr2() {
            return retainZeroValue ? StringUtils.formatLong2String(getLastYear(), 2) : String.valueOf(getLastYear());
        }

        public final String getLastYearStr4() {
            return retainZeroValue ? StringUtils.formatLong2String(getLastYear(), 4) : String.valueOf(getLastYear());
        }

        //            "yyyy-MM-dd_HH-mm-ss"
        public final String getString(String pattern) {

            char[] array = pattern.toCharArray();
            List<String> list = new ArrayList<>(array.length);

            Character last = null;
            int times = 0;

            Character ch;
            boolean delete = false;
            for (int i = 0, c = pattern.length(); i <= c; i++) {

                ch = i < c ? pattern.charAt(i) : null;

                if (last != null && last == ch) {
                    times++;
                    continue;
                }

                if (last == null) {
                    last = ch;
                    times = 1;
                    continue;
                }

                switch (last) {
                    case 'y':
                        delete = addTime(list, last_year, times);
                        break;
                    case 'M':
                        delete = addTime(list, last_month, times);
                        break;
                    case 'd':
                        delete = addTime(list, last_day, times);
                        break;
                    ////
                    case 'H':
                        delete = addTime(list, last_hour, times);
                        break;
                    case 'm':
                        delete = addTime(list, last_min, times);
                        break;
                    case 's':
                        delete = addTime(list, last_sec, times);
                        break;
                    ////
                    default:
                        if (!delete) list.add(String.valueOf(last));
                        delete = false;
                        break;
                }
                last = ch;
                times = 1;
            }
            String res = StringUtils.builder(list);
            Logger.e("LastTimeMnger getString====>", pattern, " ", res);
            return res;
        }

        private boolean addTime(List<String> list, long lastTime, int times) {
            if (!retainZeroTerm && lastTime == -1) return true;
            if (lastTime == -1) lastTime = 0;
            list.add(retainZeroValue ? StringUtils.formatLong2String(lastTime, times) : String.valueOf(lastTime));
            return false;
        }
    }


    public static void main(String[] args) throws Exception {
//
//        System.out.println(DateHelper.getDateMnger("1234-12-21_09-08-07", Pattern.PATTERN_D3_T3_1).getString(Pattern.PATTERN_D2_T2_1));
//        System.out.println(DateHelper.getDateMnger().getString(Pattern.PATTERN_D2_T2_1));
//        System.out.println(DateHelper.getDateMnger().getDay());
//        System.out.println(DateHelper.getDateMnger().getMonth());
//        System.out.println("=====");
//        System.out.println(DateHelper.getDateMnger().calculate(0, 1, 0, 0, 0, 0).getString(Pattern.PATTERN_D3_T3_1));
//        System.out.println(DateHelper.getDateMnger().calculate(0, 1, 0, 0, 0, 0).getDay());
//        System.out.println(DateHelper.getDateMnger().calculate(0, 1, 0, 0, 0, 0).getMonth());
//        DateMnger dateMnger = DateHelper.getDateMnger();
//        long a = dateMnger.getLong();
//        long b = dateMnger.calculateDay(-1).getLong();
//        System.out.println(a - b);

        //        5时0分
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).getString(DateHelper.Pattern.PATTERN_D3_T3_1), " = ", DateHelper.Pattern.PATTERN_D3_T3_1);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).getString(DateHelper.Pattern.PATTERN_D2_T3_2), " = ", DateHelper.Pattern.PATTERN_D2_T3_2);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).getString(DateHelper.Pattern.PATTERN_D2_T2_2), " = ", DateHelper.Pattern.PATTERN_D2_T2_2);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).getString(DateHelper.Pattern.PATTERN_T2_2), " = ", DateHelper.Pattern.PATTERN_T2_2);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).getString(DateHelper.Pattern.PATTERN_T2_1), " = ", DateHelper.Pattern.PATTERN_T2_1);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).getString("HHmmss"), " = ", "HHmmss");
//        Logger.e("sssss=====>", "========================");
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroTerm().getString(DateHelper.Pattern.PATTERN_D3_T3_1), " = ", DateHelper.Pattern.PATTERN_D3_T3_1);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroTerm().getString(DateHelper.Pattern.PATTERN_D2_T3_2), " = ", DateHelper.Pattern.PATTERN_D2_T3_2);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroTerm().getString(DateHelper.Pattern.PATTERN_D2_T2_2), " = ", DateHelper.Pattern.PATTERN_D2_T2_2);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroTerm().getString(DateHelper.Pattern.PATTERN_T2_2), " = ", DateHelper.Pattern.PATTERN_T2_2);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroTerm().getString(DateHelper.Pattern.PATTERN_T2_1), " = ", DateHelper.Pattern.PATTERN_T2_1);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroTerm().getString("HHmmss"), " = ", "HHmmss");
//        Logger.e("sssss=====>", "========================");
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroValue().getString(DateHelper.Pattern.PATTERN_D3_T3_1), " = ", DateHelper.Pattern.PATTERN_D3_T3_1);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroValue().getString(DateHelper.Pattern.PATTERN_D2_T3_2), " = ", DateHelper.Pattern.PATTERN_D2_T3_2);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroValue().getString(DateHelper.Pattern.PATTERN_D2_T2_2), " = ", DateHelper.Pattern.PATTERN_D2_T2_2);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroValue().getString(DateHelper.Pattern.PATTERN_T2_2), " = ", DateHelper.Pattern.PATTERN_T2_2);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroValue().getString(DateHelper.Pattern.PATTERN_T2_1), " = ", DateHelper.Pattern.PATTERN_T2_1);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroValue().getString("HHmmss"), " = ", "HHmmss");
//        Logger.e("sssss=====>", "========================");
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroTerm().unRetainZeroValue().getString(DateHelper.Pattern.PATTERN_D3_T3_1), " = ", DateHelper.Pattern.PATTERN_D3_T3_1);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroTerm().unRetainZeroValue().getString(DateHelper.Pattern.PATTERN_D2_T3_2), " = ", DateHelper.Pattern.PATTERN_D2_T3_2);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroTerm().unRetainZeroValue().getString(DateHelper.Pattern.PATTERN_D2_T2_2), " = ", DateHelper.Pattern.PATTERN_D2_T2_2);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroTerm().unRetainZeroValue().getString(DateHelper.Pattern.PATTERN_T2_2), " = ", DateHelper.Pattern.PATTERN_T2_2);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroTerm().unRetainZeroValue().getString(DateHelper.Pattern.PATTERN_T2_1), " = ", DateHelper.Pattern.PATTERN_T2_1);
//        Logger.e("sssss=====>", DateHelper.getLastTimeMnger(18034922).unRetainZeroTerm().unRetainZeroValue().getString("HHmmss"), " = ", "HHmmss");

    }

}
