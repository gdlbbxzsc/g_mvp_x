package c.g.a.x.lib_support.views.adapter.v2.recyclerview;


import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import c.g.a.x.lib_support.views.splistener.custom.OnSPClickListener;


public abstract class ViewHolder<T, V extends ViewBinding> {

    protected DataAdapter adapter;

//    public int position;

    public T item;

    protected V binder;

    protected RecyclerViewViewHolder recyclerViewViewHolder;


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

    protected void changeView(List<Object> payloads) {
    }


    protected class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        public final ViewHolder viewHolder;

        public RecyclerViewViewHolder(ViewHolder viewHolder, View itemView) {
            super(itemView);
            this.viewHolder = viewHolder;
        }
    }

    protected int position() {
        return adapter.getPosition(item);
    }

    final OnSPClickListener listener = new OnSPClickListener() {

        @Override
        public void onClickSucc(View v) {
            if (adapter == null) return;

            if (adapter.onItemClickListener == null) return;
            adapter.onItemClickListener.onItemClick(adapter.recyclerView, ViewHolder.this);
        }
    };

}