package c.g.a.x.lib_support.views.adapter.v2.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import c.g.a.x.lib_support.views.adapter.ChoiceHelper;
import c.g.a.x.lib_support.views.adapter.v2.recyclerview.choicehelper.MultipleChoiceHelper;
import c.g.a.x.lib_support.views.adapter.v2.recyclerview.choicehelper.SingleChoiceHelper;

public class DataAdapter extends RecyclerView.Adapter<ViewHolder.RecyclerViewViewHolder> {

    public final Context context;
    public final LayoutInflater inflater;

    public final RecyclerView recyclerView;// adapter 持有者控件

    //
    protected Class viewHolder;
    protected SparseArray<Class> typeViewMap;    //num from 0    value viewHolder.class
    protected HashMap<String, Integer> dataTypeMap;  //key:vo.class    value num from 0

    // vo
    public final List<Object> viewDataList = new ArrayList<>(10);
    //

    public Bundle bundle = new Bundle();

    public OnItemClickListener onItemClickListener;
    public OnItemViewClickListener onItemViewClickListener;//列表item中控件点击事件
    public ChoiceHelper choiceHelper;

    public DataAdapter(Context context, RecyclerView view, Class... viewHolderClz) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);


        this.recyclerView = view;

        if (viewHolderClz.length == 1) {
            this.viewHolder = viewHolderClz[0];
        } else {
            typeViewMap = new SparseArray<>(viewHolderClz.length);
            dataTypeMap = new HashMap<>(viewHolderClz.length, 1f);

            for (Class clz : viewHolderClz) {
                putDataViewType(clz);
            }
        }

        view.setAdapter(this);
    }

    public DataAdapter singleChoice() {
        choiceHelper = new SingleChoiceHelper<>(this);
        return this;
    }

    public DataAdapter multipleChoice() {
        choiceHelper = new MultipleChoiceHelper<>(this);
        return this;
    }

    @Override
    public int getItemCount() {
        return viewDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewHolder == null ? dataTypeMap.get(getDataType(position)) : 0;
    }

    public Object getItem(int position) {
        return viewDataList.get(position);
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


    public Class getItemViewTypeClass(int viewType) {
        return viewHolder == null ? typeViewMap.get(viewType) : viewHolder;
    }

    @Override
    public ViewHolder.RecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        try {
            holder = (ViewHolder) getItemViewTypeClass(viewType).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        View view = inflater.inflate(holder.getLayout(), parent, false);
        holder.recyclerViewViewHolder = holder.new RecyclerViewViewHolder(holder, view);

        holder.adapter = this;
        view.setOnClickListener(holder.listener);

        holder.getView(holder.recyclerViewViewHolder.itemView);

        onGetView(holder);

        return holder.recyclerViewViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder.RecyclerViewViewHolder holder, int position) {

//        holder.viewHolder.position = position;
        holder.viewHolder.item = viewDataList.get(position);
        holder.viewHolder.showView();
        onShowView(holder.viewHolder, null);
    }

    @Override
    public void onBindViewHolder(ViewHolder.RecyclerViewViewHolder holder, int position, List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);
        if (payloads == null || payloads.size() <= 0) {
            onBindViewHolder(holder, position);
            return;
        }

//        holder.viewHolder.position = position;
        holder.viewHolder.item = viewDataList.get(position);

        holder.viewHolder.changeView(payloads);
        onShowView(holder.viewHolder, payloads);
    }

    public void onGetView(ViewHolder holder) {
    }

    public void onShowView(ViewHolder holder, List<Object> payloads) {
    }

    public <T> void setDatas(List<T> datas) {
        clearDatas();
        addDatas(datas);
    }

    //
    public <T> void addDatas(List<T> datas) {
        if (datas == null || datas.size() <= 0) return;
        viewDataList.addAll(datas);
        notifyDataSetChanged();
    }


    public <T> void addData(T data) {
        viewDataList.add(data);
    }

    public <T> void addData(int pos, T data) {
        if (pos >= viewDataList.size()) {
            pos = viewDataList.size();
        }
        viewDataList.add(pos, data);
    }


    //
    public <T> void removeData(T vo) {
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


    public interface OnItemClickListener<T extends ViewHolder> {
        void onItemClick(RecyclerView parent, T viewHolder);
    }

    public interface OnItemViewClickListener<T extends ViewHolder> {
        void onItemViewClick(int rid, T viewHolder, Object... objs);
    }
}