package c.g.a.x.lib_support.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/7/20.
 */

public final class MoneyHelper {

    private BigDecimal money;
    private MoneyUnit unit;

    public static MoneyHelper getInstance4Yuan(Object object) {
        return new MoneyHelper().initByYuan(object);
    }

    public static MoneyHelper getInstance4Jiao(Object object) {
        return new MoneyHelper().initByJiao(object);
    }

    public static MoneyHelper getInstance4Fen(Object object) {
        return new MoneyHelper().initByFen(object);
    }

    private MoneyHelper() {
    }

    private MoneyHelper initByYuan(Object object) {
        return init(object, MoneyUnit.Unit_Yuan);
    }

    private MoneyHelper initByJiao(Object object) {
        return init(object, MoneyUnit.Unit_Jiao);
    }

    private MoneyHelper initByFen(Object object) {
        return init(object, MoneyUnit.Unit_Fen);
    }

    private MoneyHelper init(Object object, MoneyUnit moneyUnit) {
        money = createBigDecimal(object);
        this.unit = moneyUnit;
        return this;
    }

    private BigDecimal createBigDecimal(Object object) {

        if (object == null) return new BigDecimal(0);

        if (object instanceof BigDecimal) return (BigDecimal) object;

        if (object instanceof Integer) return new BigDecimal((Integer) object);
        if (object instanceof Long) return new BigDecimal((Long) object);
        if (object instanceof Float) return new BigDecimal((Float) object);
        if (object instanceof Double) return new BigDecimal((Double) object);
        if (object instanceof BigInteger) return new BigDecimal((BigInteger) object);
        if (object instanceof String) {
            String str = (String) object;

            if (str.startsWith("."))
                str = str.substring(1);
            else if (str.endsWith("."))
                str = str.substring(0, str.length() - 1);

            if (TextUtils.isEmpty(str))
                str = "0";

            return new BigDecimal(str);
        }

        return new BigDecimal(0);
    }

    //加法
    public MoneyHelper add(Object object) {
        money = money.add(createBigDecimal(object));
        return this;
    }

    //减法
    public MoneyHelper subtract(Object object) {
        money = money.subtract(createBigDecimal(object));
        return this;
    }

    //乘法
    public MoneyHelper multiply(Object object) {
        money = money.multiply(createBigDecimal(object));
        return this;
    }

    //除法
    public MoneyHelper divide(Object object) {
        money = money.divide(createBigDecimal(object));
        return this;
    }

    public MoneyHelper change2Yuan() {
        return changeUnit(MoneyUnit.Unit_Yuan);
    }

    public MoneyHelper change2Jiao() {
        return changeUnit(MoneyUnit.Unit_Jiao);
    }

    public MoneyHelper change2Fen() {
        return changeUnit(MoneyUnit.Unit_Fen);
    }


    private MoneyHelper changeUnit(MoneyUnit wardUnit) {

        if (unit == wardUnit) return this;
        //换算出要转换到对应单位单位需要*10 、*100、 *0.1、 *0.01；
        double dv = Math.pow(10, unit.dv - wardUnit.dv);
        multiply(dv);

        unit = wardUnit;

        return this;
    }

    public MoneyHelper setMax(int max) {
        unit.max = max;
        return this;
    }

    public MoneyHelper setQuantile(boolean b) {
        unit.quantile = b;
        return this;
    }

    public MoneyHelper setPreTrim(boolean b) {
        unit.preTrim = b;
        return this;
    }

    public MoneyHelper setSufTrim(boolean b) {
        unit.sufTrim = b;
        return this;
    }

    public Integer getInt() {
        return money.intValue();
    }

    public Long getLong() {
        return money.longValue();
    }

    public Float getFloat() {
        return money.floatValue();
    }

    public Double getDouble() {
        return money.doubleValue();
    }


    //format 0.00 ex:2.10 to 2.10
    public String getStringNormal() {
        unit.max = 1;
        unit.quantile = false;

        unit.preTrim = true;
        unit.sufTrim = false;
        return format(unit.getPattern());
    }

    //format 0.##  ex:2.10 to 2.1
    public String getStringNormalTrimZero() {
        unit.max = 1;
        unit.quantile = false;

        unit.preTrim = true;
        unit.sufTrim = true;
        return format(unit.getPattern());
    }


    //format "#,###.00  ex:123456.10 to 123,456.10
    public String getStringQuantile() {

        unit.max = String.valueOf(money.longValue()).length();
        unit.quantile = true;

        unit.preTrim = true;
        unit.sufTrim = false;
        return format(unit.getPattern());
    }


    public String format(String pattern) {
        return new DecimalFormat(pattern).format(money);
    }

    private enum MoneyUnit {
        Unit_Yuan(2), Unit_Jiao(1), Unit_Fen(0);

        //对应单位转换到分进位数
        // 例如：元到分：2进位；角到分：1进位
        private final int dv;
        //对应单位钱的通常显示格式。

        private int max = 1;
        private boolean quantile = false;
        private boolean preTrim = true;
        private boolean sufTrim = false;

//        private String pattern1;//0.00
//        private String pattern2;//0.##
//        private String pattern3;//###,###.00
//        private String pattern4;//###,###.##

        MoneyUnit(int dv) {
            this.dv = dv;
        }

        public final String getPattern() {
            StringBuilder sb = new StringBuilder();

            String replace = preTrim ? "#" : "0";
            for (int i = 0; i < max; i++) {
                if (quantile && i % 3 == 0) sb.append(",");
                sb.append(replace);
            }

            if (dv > 0) {
                sb.append(".");

                replace = sufTrim ? "#" : "0";
                for (int i = 0; i < dv; i++) {
                    sb.append(replace);
                }
            }
            return sb.substring(1);
        }
    }

    public static void main(String[] args) {
        MoneyHelper helper = MoneyHelper.getInstance4Fen("1234567890");

        helper.change2Yuan();
        System.out.println(helper.getDouble());
        System.out.println(helper.getLong().toString());
        System.out.println(helper.getStringNormal());
        System.out.println(helper.getStringNormalTrimZero());
        System.out.println(helper.getStringQuantile());

    }
}
