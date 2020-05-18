//package c.g.a.x.lib_support.views.adapter.v1.recyclerview;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import c.g.a.x.lib_support.views.adapter.ChoiceHelper;
//import c.g.a.x.lib_support.views.adapter.v1.recyclerview.choicehelper.MultipleChoiceHelper;
//import c.g.a.x.lib_support.views.adapter.v1.recyclerview.choicehelper.SingleChoiceHelper;
//
//public class DataAdapter extends RecyclerView.Adapter<ViewHolder.RecyclerViewViewHolder> {
//
//    public final Context context;
//    public final LayoutInflater inflater;
//    public final Resources resources;
//
//    public final RecyclerView recyclerView;// adapter 持有者控件
//
//    // key position value view type
//    protected final List<Class> viewTypeCount;
//    // viewDataList.get(i)'s viewHolder is viewTypeList.get(i)
//    protected List<Integer> viewTypeList;
//
//    // vo
//    public final List<Object> viewDataList = new ArrayList<>(10);
//    //
//
//    public Bundle bundle = new Bundle();
//
//    public OnItemClickListener onItemClickListener;
//    public OnItemViewClickListener onItemViewClickListener;//列表item中控件点击事件
//    public ChoiceHelper choiceHelper;
//
//    private DataAdapter(Context context, RecyclerView view, Class viewHolder, int view_type_count, Class choiceHelperClz) {
//        this.context = context;
//
//        this.inflater = LayoutInflater.from(context);
//        this.resources = (context).getResources();
//
//        this.recyclerView = view;
//
//        viewTypeCount = new ArrayList<>(view_type_count);
//        if (viewHolder != null) {
//            viewTypeCount.add(viewHolder);
//        }
//        if (view_type_count > 1) {
//            viewTypeList = new ArrayList<>(10);
//        }
//
//        if (choiceHelperClz == SingleChoiceHelper.class) {
//            choiceHelper = new SingleChoiceHelper<>(this);
//        } else if (choiceHelperClz == MultipleChoiceHelper.class) {
//            choiceHelper = new MultipleChoiceHelper<>(this);
//        }
//
//        view.setAdapter(this);
//    }
//
//    public DataAdapter(Context context, RecyclerView view, Class viewHolder) {
//        this(context, view, viewHolder, 1, null);
//    }
//
//    public DataAdapter(Context context, RecyclerView view, int view_type_count) {
//        this(context, view, null, view_type_count, null);
//    }
//
//    public DataAdapter(Context context, RecyclerView view, Class viewHolder, Class choiceHelperClz) {
//        this(context, view, viewHolder, 1, choiceHelperClz);
//    }
//
//    public DataAdapter(Context context, RecyclerView view, int view_type_count, Class choiceHelperClz) {
//        this(context, view, null, view_type_count, choiceHelperClz);
//    }
//
//    @Override
//    public int getItemCount() {
//        return viewDataList.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//
//        if (viewTypeList == null || viewTypeList.size() <= 0) {
//            return 0;
//        }
//        return viewTypeList.get(position);
//    }
//
//    public Object getItem(int position) {
//        return viewDataList.get(position);
//    }
//
////    public Class getItemViewTypeClass(int position) {
////        return viewTypeCount.get(getItemViewType(position));
////    }
//
//    @Override
//    public ViewHolder.RecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        ViewHolder holder = null;
//        try {
//            holder = (ViewHolder) viewTypeCount.get(viewType).newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        View view = inflater.inflate(holder.getLayout(), parent, false);
//        holder.recyclerViewViewHolder = holder.new RecyclerViewViewHolder(holder, view);
//
//        holder.adapter = this;
//        view.setOnClickListener(holder.listener);
//
//        holder.getView(holder.recyclerViewViewHolder.itemView);
//
//        onGetView(holder);
//
//        return holder.recyclerViewViewHolder;
//
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder.RecyclerViewViewHolder holder, int position) {
//
////        holder.viewHolder.position = position;
//        holder.viewHolder.item = viewDataList.get(position);
//        holder.viewHolder.showView();
//        onShowView(holder.viewHolder, null);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder.RecyclerViewViewHolder holder, int position, List<Object> payloads) {
////        super.onBindViewHolder(holder, position, payloads);
//        if (payloads == null || payloads.size() <= 0) {
//            onBindViewHolder(holder, position);
//            return;
//        }
//
////        holder.viewHolder.position = position;
//        holder.viewHolder.item = viewDataList.get(position);
//
//        holder.viewHolder.changeView(payloads);
//        onShowView(holder.viewHolder, payloads);
//    }
//
//    public void onGetView(ViewHolder holder) {
//    }
//
//    public void onShowView(ViewHolder holder, List<Object> payloads) {
//    }
//
//    // ////single adapter use.*if separator adapter use will get error;
//
//    public <T> void setDatas(List<T> datas) {
//
//        clearDatas();
//
//        addDatas(datas);
//    }
//
//    //
//    public <T> void addDatas(List<T> datas) {
//        if (datas == null || datas.size() <= 0) return;
//        viewDataList.addAll(datas);
//        notifyDataSetChanged();
//    }
//
//
//    public <T> void addData(T data) {
//        viewDataList.add(data);
//    }
//
//    public <T> void addData(int pos, T data) {
//        if (pos >= viewDataList.size()) {
//            pos = viewDataList.size();
//        }
//        viewDataList.add(pos, data);
//    }
//
//    public <T> void addData(T data, Class viewHolder) {
//        addData(null, data, viewHolder);
//    }
//
//    public <T> void addData(Integer pos, T data, Class viewHolder) {
//
//        int type = viewTypeCount.indexOf(viewHolder);
//        if (type == -1) {
//            viewTypeCount.add(viewHolder);
//            type = viewTypeCount.indexOf(viewHolder);
//        }
//        if (pos == null) {
//            viewDataList.add(data);
//            viewTypeList.add(type);
//        } else {
//            if (pos >= viewDataList.size()) {
//                pos = viewDataList.size();
//            }
//            viewDataList.add(pos, data);
//            viewTypeList.add(pos, type);
//        }
//    }
//
//    //
//    public <T> void removeData(T vo) {
//        int pos = viewDataList.indexOf(vo);
//        if (pos < 0) {
//            return;
//        }
//        removeData(pos);
//    }
//
//    public void removeData(int pos) {
//        if (choiceHelper != null) {
//            choiceHelper.removeChoice(pos);
//        }
//        viewDataList.remove(pos);
//        if (viewTypeList != null) {
//            viewTypeList.remove(pos);
//        }
//    }
//
//    //
//    public void clearDatas() {
//        if (choiceHelper != null) {
//            choiceHelper.clearChoices();
//        }
//        viewDataList.clear();
//        if (viewTypeList != null) {
//            viewTypeList.clear();
//        }
//
//        notifyDataSetChanged();
//    }
//
//    public int getPosition(Object obj) {
//        return viewDataList.indexOf(obj);
//    }
//
//    public boolean contains(Object obj) {
//        return viewDataList.contains(obj);
//    }
//
//
//    public interface OnItemClickListener<T extends ViewHolder> {
//        void onItemClick(RecyclerView parent, T viewHolder);
//    }
//
//    public interface OnItemViewClickListener<T extends ViewHolder> {
//        void onItemViewClick(int rid, T viewHolder, Object... objs);
//    }
//}