package c.g.a.x.lib_support.android.utils.viewclickable;

import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/12.
 */

public final class ViewClickableHelper<T extends View> {

    private Map<String, T> viewMap = new HashMap<>(2);

    private Map<String, Integer> countMap = new HashMap<>(2);

    public ViewClickableHelper() {
    }

    public String putView(T view, Object... objs) {
        String key = getKey(view, objs);
        return putView(key, view);
    }

    public void setClickableFalse(T view, Object... objs) {
        String key = getKey(view, objs);
        setClickableFalse(key);
    }

    public void setClickableTrue(T view, Object... objs) {
        String key = getKey(view, objs);
        setClickableTrue(key);
    }

    public boolean isClickable(T view, Object... objs) {
        String key = getKey(view, objs);
        return isClickable(key, view);
    }
    ///////////////////////////

    public String putView(String key, T view) {

        T temp = viewMap.get(key);

        if (temp == null) viewMap.put(key, view);

        return key;
    }

    public synchronized void setClickableFalse(String key) {

        Integer count = countMap.get(key);
        if (count == null) count = 0;

        count += 1;
        countMap.put(key, count);

        T view = getView(key);
        if (view == null) return;

        view.setClickable(false);
    }

    public synchronized void setClickableTrue(String key) {

        Integer count = countMap.get(key);
        if (count == null) return;

        count -= 1;

        if (count > 0) {
            countMap.put(key, count);
            return;
        }
        countMap.remove(key);

        T view = getView(key);
        if (view == null) return;

        view.setClickable(true);
    }

    public synchronized boolean isClickable(String key, T resView) {

        Integer count = countMap.get(key);

        if (count != null) {
            boolean b = count <= 0;
            resView.setClickable(b);
            return b;
        }

        return resView.isClickable();
    }

    public T getView(String key) {
        return viewMap.get(key);
    }

    private String getKey(T view, Object... objs) {
        StringBuilder sb = new StringBuilder();
        sb.append(view.getId());
        sb.append(view.hashCode());

        for (Object obj : objs) {
            sb.append(obj);
        }

        return sb.toString();
    }

    public synchronized void clear() {
        viewMap.clear();
        countMap.clear();
    }
}
