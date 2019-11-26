package c.g.a.x.lib_support.views.adapter.v2.abslistview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;


import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import c.g.a.x.lib_support.views.adapter.ChoiceHelper;
import c.g.a.x.lib_support.views.adapter.v2.abslistview.choicehelper.MultipleChoiceHelper;
import c.g.a.x.lib_support.views.adapter.v2.abslistview.choicehelper.SingleChoiceHelper;

public class DataAdapter extends BaseAdapter {

    public Context context;
    public LayoutInflater inflater;

    public AbsListView view;// adapter 持有者控件

    //
    protected Class viewholder;
    protected HashMap<Integer, Class> typeViewMap;    //num from 0    value viewholder.class
    protected HashMap<String, Integer> dataTypeMap;  //key:vo.class    value num from 0

    // vo
    public List<Object> viewDataList = new ArrayList<Object>(10);
    //

    public Bundle bundle = new Bundle();

    public OnItemViewClickListener onItemViewClickListener;//列表item中控件点击事件
    public ChoiceHelper choiceHelper;

    public DataAdapter(Context context, AbsListView view, Class... viewHolderClz) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        this.view = view;


        if (viewHolderClz.length == 1) {
            this.viewholder = viewHolderClz[0];
        } else {
            typeViewMap = new HashMap<>(viewHolderClz.length, 1f);
            dataTypeMap = new HashMap<>(viewHolderClz.length, 1f);

            for (Class clz : viewHolderClz) {
                putDataViewType(clz);
            }
        }

        view.setAdapter(this);
    }

    public DataAdapter singleChoice() {
        choiceHelper = new SingleChoiceHelper(this);
        return this;
    }

    public DataAdapter multipleChoice() {
        choiceHelper = new MultipleChoiceHelper(this);
        return this;
    }


    @Override
    public int getViewTypeCount() {
        return viewholder == null ? typeViewMap.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        return viewholder == null ? dataTypeMap.get(getDataType(position)) : 0;
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

    public int getPosition(Object obj) {
        return viewDataList.indexOf(obj);
    }

    public boolean contains(Object obj) {
        return viewDataList.contains(obj);
    }

    private void putDataViewType(Class clz) {
        int type = typeViewMap.size();
        typeViewMap.put(type, clz);

        dataTypeMap.put(getDataType(clz), type);
    }

    protected String getDataType(Class clz) {
        return ((ParameterizedType) clz.getGenericSuperclass()).getActualTypeArguments()[0].toString();
    }

    protected String getDataType(int position) {
        return getItem(position).getClass().toString();
    }

    public Class getItemViewTypeClass(int position) {
        return viewholder == null ? typeViewMap.get(getItemViewType(position)) : viewholder;
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
    }

    //
    public void clearDatas() {
        if (choiceHelper != null) {
            choiceHelper.clearChoices();
        }
        viewDataList.clear();
        notifyDataSetChanged();
    }


    public interface OnItemViewClickListener<T extends ViewHolder> {
        void onItemViewClick(int rid, T holder, Object... objs);
    }

}