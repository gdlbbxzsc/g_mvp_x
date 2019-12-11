package c.g.a.x.lib_support.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class IDCardHelper {

    private final int[] WEIGHT = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private final int[] CHECKDIGIT = new int[]{1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2}; // 校验码

    private final Map<String, String> cityCodes = new HashMap<>();


    private String idCard;

    public static IDCardHelper getInstance(String idCard) {
        return InnerInstance.INSTANCE.init(idCard);
    }

    private static class InnerInstance {
        private static final IDCardHelper INSTANCE = new IDCardHelper();
    }

    private IDCardHelper() {
        cityCodes.put("11", "北京");
        cityCodes.put("12", "天津");
        cityCodes.put("13", "河北");
        cityCodes.put("14", "山西");
        cityCodes.put("15", "内蒙古");
        cityCodes.put("21", "辽宁");
        cityCodes.put("22", "吉林");
        cityCodes.put("23", "黑龙江");
        cityCodes.put("31", "上海");
        cityCodes.put("32", "江苏");
        cityCodes.put("33", "浙江");
        cityCodes.put("34", "安徽");
        cityCodes.put("35", "福建");
        cityCodes.put("36", "江西");
        cityCodes.put("37", "山东");
        cityCodes.put("41", "河南");
        cityCodes.put("42", "湖北");
        cityCodes.put("43", "湖南");
        cityCodes.put("44", "广东");
        cityCodes.put("45", "广西");
        cityCodes.put("46", "海南");
        cityCodes.put("50", "重庆");
        cityCodes.put("51", "四川");
        cityCodes.put("52", "贵州");
        cityCodes.put("53", "云南");
        cityCodes.put("54", "西藏");
        cityCodes.put("61", "陕西");
        cityCodes.put("62", "甘肃");
        cityCodes.put("63", "青海");
        cityCodes.put("64", "宁夏");
        cityCodes.put("65", "新疆");
        cityCodes.put("71", "台湾");
        cityCodes.put("81", "香港");
        cityCodes.put("82", "澳门");
        cityCodes.put("91", "国外");
    }

    private IDCardHelper init(String idCard) {
        this.idCard = idCard.toUpperCase();
        return this;
    }

    public boolean isIDCard() {

        String PARRENT_IDCARD = "^[0-9]{17}[0-9|X]$";
        if (!idCard.matches(PARRENT_IDCARD)) return false;

        int sum = 0;

        for (int i = 0; i < WEIGHT.length; i++) {
            sum += (WEIGHT[i] * Integer.parseInt(idCard.substring(i, i + 1)));
        }

        int num;
        try {
            num = Integer.parseInt(idCard.substring(idCard.length() - 1));
        } catch (Exception e) {
            num = CHECKDIGIT[2];
        }

        return num == CHECKDIGIT[sum % CHECKDIGIT.length];
    }

    /**
     * @return -1 未知 0 女 1 男
     */
    public int getSex() {
        try {
            return Integer.parseInt(idCard.substring(16, 17)) % 2;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Date getBirthday() {
        try {
            return   DateHelper.getDateMnger(idCard.substring(6, 14), DateHelper.Pattern.PATTERN_D3_2).getDate();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getAge() {
        Date date = getBirthday();
        return getAge(date);
    }

    public int getAge(Date birthday) {
        Calendar calendar = Calendar.getInstance();
        int now = calendar.get(Calendar.YEAR);
        calendar.setTime(birthday);
        int birth = calendar.get(Calendar.YEAR);
        return now - birth;
    }


    public String getCity() {
        return cityCodes.get(idCard.substring(0, 2));
    }

}

