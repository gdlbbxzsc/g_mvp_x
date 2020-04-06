package c.g.a.x.lib_guide;

import android.content.SharedPreferences;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import c.g.a.x.lib_support.utils.StringUtils;

public class GuidePage {

    private String tag;

    private int maxTimes = 1;
    private int resetNum = 0;

    private boolean touchCancel = true;
    private View.OnClickListener onTouchCancelListener;

    private List<HighLight> highLights = new ArrayList<>();

    public GuidePage(@NonNull String tag) {
        this.tag = tag;
    }

    List<HighLight> getHighLights() {
        return highLights;
    }

    public GuidePage addHighLight(HighLight highLight) {
        highLights.add(highLight);
        return this;
    }

    public boolean isTouchCancel() {
        return touchCancel;
    }

    public GuidePage setTouchCancel(boolean touchCancel) {
        this.touchCancel = touchCancel;
        return this;
    }

    public View.OnClickListener getOnTouchCancelListener() {
        return onTouchCancelListener;
    }

    public GuidePage setOnTouchCancelListener(View.OnClickListener onTouchCancelListener) {
        this.onTouchCancelListener = onTouchCancelListener;
        return this;
    }

    public GuidePage setResetNum(int resetNum) {
        this.resetNum = resetNum;
        return this;
    }

    public GuidePage setMaxTimes(int maxTimes) {
        this.maxTimes = maxTimes;
        return this;
    }

    boolean canShow(SharedPreferences sp, String preTag) {
        int num = sp.getInt(preTag + tag, 0);
        return num < maxTimes;
    }

    void reset(SharedPreferences sp, String preTag, String ver) {
        String temp = sp.getString(preTag + tag + "_flag", "");
        if (StringUtils.isEqual(temp, ver)) return;

        SharedPreferences.Editor edit = sp.edit();
        if (resetNum < 0) resetNum = 0;
        edit.putString(preTag + tag + "flag", ver).
                putInt(preTag + tag, resetNum).apply();
    }

    void addTimes(SharedPreferences sp, String preTag) {
        int num = sp.getInt(preTag + tag, 0);
        sp.edit().putInt(preTag + tag, num + 1).apply();
    }

    void reduceTimes(SharedPreferences sp, String preTag) {
        int num = sp.getInt(preTag + tag, 0);
        if (num == 0) return;
        sp.edit().putInt(preTag + tag, num - 1).apply();
    }


}
