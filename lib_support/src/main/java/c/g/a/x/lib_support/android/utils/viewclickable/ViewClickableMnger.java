package c.g.a.x.lib_support.android.utils.viewclickable;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/12.
 */

public final class ViewClickableMnger<T extends View> {

    private Map<Object, ViewClickableHelper> map = new HashMap<>(2);

    public static ViewClickableMnger getInstance() {
        return InnerInstance.INSTANCE;
    }

    private static class InnerInstance {
        private static ViewClickableMnger INSTANCE = new ViewClickableMnger();
    }

    private ViewClickableMnger() {
    }

    public synchronized ViewClickableHelper load(Activity activity) {
        ViewClickableHelper helper = map.get(activity);

        if (helper == null) {
            helper = new ViewClickableHelper();
            map.put(activity, helper);
        }

        return helper;
    }

    public synchronized ViewClickableHelper load(Fragment fragment) {
        ViewClickableHelper helper = map.get(fragment);

        if (helper == null) {
            helper = new ViewClickableHelper();
            map.put(fragment, helper);
        }

        return helper;
    }

    public synchronized void clear(Activity activity) {

        ViewClickableHelper helper = map.get(activity);

        if (helper != null) helper.clear();

        map.remove(activity);
    }

    public synchronized void clear(Fragment fragment) {

        ViewClickableHelper helper = map.get(fragment);

        if (helper != null) helper.clear();

        map.remove(fragment);
    }
}
