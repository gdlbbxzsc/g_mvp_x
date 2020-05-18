package c.g.a.x.lib_support.views.adapter.v2.abslistview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public abstract class ViewHolder<T, V extends ViewBinding> {

    protected DataAdapter adapter;

    public int position;

    public T item;

    protected V binder;



    protected int getLayout() {
        return 0;
    }

    protected void getView() {
        try {
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            // 获取第一个类型参数的真实类型
            Class<V> clazz = (Class<V>) pt.getActualTypeArguments()[1];
            Method method = clazz.getMethod("inflate", LayoutInflater.class);
            binder = (V) method.invoke(null, adapter.inflater);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void showView();

}