package c.g.a.x.lib_support.views.adapter.v1.recyclerview;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import c.g.a.x.lib_support.views.splistener.custom.OnSPClickListener;


public abstract class ViewHolder<T extends Object> {

    protected DataAdapter adapter;

    public int position;

    public T item;

    protected RecyclerViewViewHolder recyclerViewViewHolder;

    protected abstract int getLayout();

    protected abstract void getView(View itemView);

    protected abstract void showView();

    protected void changeView(List<Object> payloads) {
    }


    protected class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder viewHolder;

        public RecyclerViewViewHolder(ViewHolder viewHolder, View itemView) {
            super(itemView);
            this.viewHolder = viewHolder;
        }
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