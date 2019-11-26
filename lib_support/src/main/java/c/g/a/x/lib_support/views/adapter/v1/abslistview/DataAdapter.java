package c.g.a.x.lib_support.views.adapter.v1.abslistview;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import c.g.a.x.lib_support.views.adapter.ChoiceHelper;
import c.g.a.x.lib_support.views.adapter.v1.abslistview.choicehelper.MultipleChoiceHelper;
import c.g.a.x.lib_support.views.adapter.v1.abslistview.choicehelper.SingleChoiceHelper;

public class DataAdapter extends BaseAdapter {

    public Context context;
    public LayoutInflater inflater;
    public Resources resources;

    public AbsListView view;// adapter 持有者控件

    //
    protected int view_type_count = 1;
    // key position value view type
    protected List<Class> viewTypeCount;
    // viewDataList.get(i)'s viewholder is viewTypeList.get(i)
    protected List<Integer> viewTypeList;

    // vo
    public List<Object> viewDataList = new ArrayList<Object>(10);
    //


    public Bundle bundle = new Bundle();

    public OnItemViewClickListener onItemViewClickListener;//列表item中控件点击事件
    public ChoiceHelper choiceHelper;

    private DataAdapter(Context context, AbsListView view, Class viewholder, int view_type_count, Class choiceHelperClz) {
        this.context = context;

        this.inflater = LayoutInflater.from(context);
        this.resources = (context).getResources();

        this.view = view;

        this.view_type_count = view_type_count;

        viewTypeCount = new ArrayList<>(view_type_count);
        if (viewholder != null) {
            viewTypeCount.add(viewholder);
        }
        if (view_type_count > 1) {
            viewTypeList = new ArrayList<>(10);
        }

        if (choiceHelperClz == SingleChoiceHelper.class) {
            choiceHelper = new SingleChoiceHelper(this);
        } else if (choiceHelperClz == MultipleChoiceHelper.class) {
            choiceHelper = new MultipleChoiceHelper(this);
        }


        view.setAdapter(this);
    }

    public DataAdapter(Context context, AbsListView view, Class viewholder) {
        this(context, view, viewholder, 1, null);
    }

    public DataAdapter(Context context, AbsListView view, int view_type_count) {
        this(context, view, null, view_type_count, null);
    }

    public DataAdapter(Context context, AbsListView view, Class viewholder, Class choiceHelperClz) {
        this(context, view, viewholder, 1, choiceHelperClz);
    }

    public DataAdapter(Context context, AbsListView view, int view_type_count, Class choiceHelperClz) {
        this(context, view, null, view_type_count, choiceHelperClz);
    }

    @Override
    public int getViewTypeCount() {
        return view_type_count;
        // return viewTypeList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (view_type_count == 1) {
            return 0;
        }
        return viewTypeList.get(position);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return super.areAllItemsEnabled();
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public int getCount() {
        return viewDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return viewDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Class getItemViewTypeClass(int position) {
        return viewTypeCount.get(getItemViewType(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            try {
                holder = (ViewHolder) getItemViewTypeClass(position).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.adapter = this;

            convertView = inflater.inflate(holder.getLayout(), null);

            holder.getView(convertView);

            onGetView(holder);

            convertView.setTag(holder);// important必须有

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.position = position;
        holder.item = getItem(position);

        holder.showView();
        onShowView(holder);
        return convertView;
    }

    public void onGetView(ViewHolder holder) {
    }

    public void onShowView(ViewHolder holder) {
    }

    // ////single adapter use.*if separator adapter use will get error;
    public <T extends Object> void setDatas(List<T> datas) {

        clearDatas();

        addDatas(datas);
    }

    //
    public <T extends Object> void addDatas(List<T> datas) {
        if (datas == null || datas.size() <= 0) return;
        viewDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public <T extends Object> void addData(T data) {
        viewDataList.add(data);
    }

    public <T extends Object> void addData(int pos, T data) {
        if (pos >= viewDataList.size()) {
            pos = viewDataList.size();
        }
        viewDataList.add(pos, data);
    }

    public <T extends Object> void addData(T data, Class viewholder) {

        int type = viewTypeCount.indexOf(viewholder);
        if (type == -1) {
            viewTypeCount.add(viewholder);
            type = viewTypeCount.indexOf(viewholder);
        }
        viewDataList.add(data);
        viewTypeList.add(type);
    }

    public <T extends Object> void addData(int pos, T data, Class viewholder) {

        int type = viewTypeCount.indexOf(viewholder);
        if (type == -1) {
            viewTypeCount.add(viewholder);
            type = viewTypeCount.indexOf(viewholder);
        }
        if (pos >= viewDataList.size()) {
            pos = viewDataList.size();
        }
        viewDataList.add(pos, data);
        viewTypeList.add(pos, type);
    }

    //


    public <T extends Object> void removeData(T vo) {
        int pos = viewDataList.indexOf(vo);
        if (pos < 0) {
            return;
        }
        removeData(pos);
    }

    public void removeData(int pos) {
        if (choiceHelper != null) {
            choiceHelper.removeChoice(pos);
        }
        viewDataList.remove(pos);
        if (viewTypeList != null) {
            viewTypeList.remove(pos);
        }
    }

    //
    public void clearDatas() {
        if (choiceHelper != null) {
            choiceHelper.clearChoices();
        }
        viewDataList.clear();
        if (viewTypeList != null) {
            viewTypeList.clear();
        }

        notifyDataSetChanged();
    }

    public int getPosition(Object obj) {
        return viewDataList.indexOf(obj);
    }

    public boolean contains(Object obj) {
        return viewDataList.contains(obj);
    }


    public interface OnItemViewClickListener<T extends ViewHolder> {
        void onItemViewClick(int rid, T holder, Object... objs);
    }

}