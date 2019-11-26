package c.g.a.x.lib_support.views.inputfilter;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

import c.g.a.x.lib_support.android.utils.Logger;

/**
 * int pre_point_num_length,小数点前可输入数字长度
 * int suf_point_num_length,小数点后可输入数字长度
 * boolean zero 是否可输入0
 * boolean negative, 是否可输入加减号
 **/
public class MoneyInputFilter implements InputFilter {
    //小数点前后的位数
//    private int pre_point_num_length;
//    private int suf_point_num_length;

    private Pattern pattern;

    public MoneyInputFilter(int pre_point_num_length) {
        this(pre_point_num_length, 0, true, false);
    }

    public MoneyInputFilter(int pre_point_num_length, int suf_point_num_length) {
        this(pre_point_num_length, suf_point_num_length, true, false);
    }

    public MoneyInputFilter(int pre_point_num_length, int suf_point_num_length, boolean zero) {
        this(pre_point_num_length, suf_point_num_length, zero, false);
    }

    public MoneyInputFilter(int pre_point_num_length, int suf_point_num_length, boolean zero, boolean negative) {
        if (pre_point_num_length <= 0) pre_point_num_length = 1;
        if (suf_point_num_length < 0) pre_point_num_length = 0;

//        this.pre_point_num_length = pre_point_num_length;
//        this.suf_point_num_length = suf_point_num_length;

        StringBuilder sb = new StringBuilder();

        sb.append("^");
//        加减号
        if (negative) sb.append("(+|-)?");
//        自然数
        sb.append(String.format("(%s[1-9][0-9]{0,%d})", zero ? "0|" : "", (pre_point_num_length - 1)));
//        小数
        if (suf_point_num_length > 0)
            sb.append(String.format("(\\.\\d{0,%d})?", suf_point_num_length));

        sb.append("$");

        Logger.e(sb.toString());
        pattern = Pattern.compile(sb.toString());
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        String result = new StringBuilder(dest).replace(dstart, dend, source.toString()).toString();
        if (!pattern.matcher(result).matches()) return "";

        return null;
    }
}
