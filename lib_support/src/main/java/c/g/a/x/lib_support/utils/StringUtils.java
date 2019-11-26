package c.g.a.x.lib_support.utils;

import android.text.TextUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.regex.Pattern;

public final class StringUtils {

    public static boolean isEmpty(String str) {
        return null == str || str.length() <= 0;
    }

    public static boolean isEqual(Object foo, Object bar) {
//        return foo == bar || (null != foo && foo.equals(bar));
        return foo == bar || (null != foo && null != bar && foo.equals(bar));
//        return null == foo || null == bar ? foo == bar : foo.equals(bar);
    }

    public static String nullReturn(String str) {
        return str == null || "null".equals(str.toLowerCase()) ? "" : str;
    }

    /////////////
    //是否为手机号
    public static boolean isMobile(String phoneNumber) {
        return Pattern.compile("^1[0-9]\\d{9}$").matcher(phoneNumber).matches();
    }

    //是否为验证码
    public static boolean isNumCode(String code) {
        return Pattern.compile("[0-9]{6}").matcher(code).matches();
    }

    //
    public static boolean isMD5(String str) {
        return Pattern.compile("[0-9a-zA-Z]*").matcher(str).matches();
    }

    //
    public static boolean isCommonWord(String str) {
        return Pattern.compile("[a-zA-Z0-9_\u4e00-\u9fa5]*").matcher(str).matches();
    }

    //
    public static boolean isNumber(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    //
    public static boolean isLetter(String str) {
        return Pattern.compile("[a-zA-Z]*").matcher(str).matches();
    }

    /////////////
    public static boolean hasHanZi(String str) {
        return str.getBytes().length != str.length();
    }

    //
    public static boolean hasEmoji(String content) {    //    验证 是否有表情
        return Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]").matcher(content).find();
    }

    /////////////
    //    字符串拼接,线程安全
    public static <T extends Object> String buffer(T... array) {
        return buffer("", array);
    }

    public static <T extends Object> String buffer(String join, T... array) {
        if (array == null || array.length <= 0) return "";

        if (join == null) join = "";
        StringBuffer s = new StringBuffer();
        for (T str : array) {
            s.append(join);
            s.append(str);
        }
        return s.substring(join.length());
    }

    public static <T extends Object> String builder(T... array) {
        return builder("", array);
    }

    //    字符串拼接,线程不安全,效率高
    public static <T extends Object> String builder(String join, T... array) {
        if (array == null || array.length <= 0) return "";

        if (join == null) join = "";
        StringBuilder s = new StringBuilder();
        for (T str : array) {
            s.append(join);
            s.append(str);
        }
        return s.substring(join.length());
    }

    public static <T extends Object> String buffer(List<T> array) {
        return buffer("", array);
    }

    public static <T extends Object> String buffer(String join, List<T> array) {
        if (array == null || array.isEmpty()) return "";

        if (join == null) join = "";
        StringBuffer s = new StringBuffer();
        for (T str : array) {
            s.append(join);
            s.append(str);
        }
        return s.substring(join.length());
    }

    public static <T extends Object> String builder(List<T> array) {
        return builder("", array);
    }

    //    字符串拼接,线程不安全,效率高
    public static <T extends Object> String builder(String join, List<T> array) {
        if (array == null || array.isEmpty()) return "";

        if (join == null) join = "";
        StringBuilder s = new StringBuilder();
        for (T str : array) {
            s.append(join);
            s.append(str);
        }
        return s.substring(join.length());
    }


    /////////////
    //    转半角的函数(DBC case)
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++)
            c[i] = c[i] == 12288 ? (char) 32
                    : c[i] > 65280 && c[i] < 65375 ? (char) (c[i] - 65248)
                    : c[i];
        return new String(c);
    }

    //    转全角的函数(SBC case)
    public static String toSBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++)
            c[i] = c[i] == 32 ? (char) 12288
                    : c[i] < 127 ? (char) (c[i] + 65248) : c[i];
        return new String(c);
    }

    /////////////

    public static String upperFirstLatter(String letter) {
        char[] chars = letter.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char) (chars[0] - 32);
        }
        return new String(chars);
    }

    /**
     * 将原数据前补零，补后的总长度为指定的长度，以字符串的形式返回
     * 例如 formatInt2String（5,3）为005
     */
    public static String formatLong2String(long sourceDate, int formatLength) {
        // formatLength 字符总长度为 formatLength
        return String.format("%0" + formatLength + "d", sourceDate);
    }


    // 手机号码前三后四脱敏
    public static String encryptMobile(String mobile) {
        if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) return mobile;
//        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
//        return mobile.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
        return encrypt(mobile, 3, 4, "*");
    }

    //身份证前三后四脱敏
    public static String encryptIDCard(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) return id;
//        return id.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
        return encrypt(id, 3, 4, "*");
    }


    public static String encrypt(String str, int pre, int suf) {
        return encrypt(str, pre, suf, "*");
    }

    /**
     * pre 前边不被替换长度
     * suf 后边不被替换长度
     * rep 替换字符串
     * 例如 encrypt("0123456789", 3, 3, "*") 返回 012****789
     * 例如 encrypt("0123456789", 3, 3, "*-") 返回 012*-*-*-*-789
     **/
    public static String encrypt(String str, int pre, int suf, String rep) {
        if (StringUtils.isEmpty(str) || (str.length() <= pre + suf)) return str;
        String regex = String.format("(?<=\\w{%d})\\w(?=\\w{%d})", pre, suf);
        if (TextUtils.isEmpty(rep)) rep = "*";
        return str.replaceAll(regex, rep);
    }

    /**
     * pre 前边不被替换长度
     * suf 后边不被替换长度
     * rep 替换字符串
     * repTimes rep重复次数
     * 例如 encrypt("0123456789", 3, 3,"*", 2) 返回 012**789
     * 例如 encrypt("0123456789", 3, 3,"*-", 2) 返回 012*-*-789
     **/
    public static String encrypt(String src, int pre, int suf, String rep, int repTimes) {
        if (StringUtils.isEmpty(src) || (src.length() <= pre + suf)) return src;

        if (repTimes > 1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < repTimes; i++) {
                sb.append(rep);
            }
            rep = sb.toString();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(src);
        sb.replace(pre, src.length() - suf, rep);

        return sb.toString();
    }

    /////////////
    public static String exception2String(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        } catch (Exception e2) {
            return "";
        }
    }
}
