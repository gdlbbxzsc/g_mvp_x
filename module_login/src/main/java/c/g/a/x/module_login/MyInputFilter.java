package c.g.a.x.module_login;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

public final class MyInputFilter implements InputFilter {

    private final Pattern pattern;

    public MyInputFilter(String regex) {
        pattern = Pattern.compile(regex);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        String result = new StringBuilder(dest).replace(dstart, dend, source.toString()).toString();
        if (!pattern.matcher(result).matches()) return "";

        return null;
    }
}
