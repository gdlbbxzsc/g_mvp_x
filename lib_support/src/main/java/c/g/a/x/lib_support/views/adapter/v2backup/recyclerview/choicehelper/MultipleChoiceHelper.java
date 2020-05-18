//package c.g.a.x.lib_support.views.adapter.v2backup.recyclerview.choicehelper;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import c.g.a.x.lib_support.views.adapter.ChoiceHelper;
//import c.g.a.x.lib_support.views.adapter.v2.recyclerview.DataAdapter;
//
///**
// * Created by Administrator on 2018/7/10.
// */
//
//public class MultipleChoiceHelper<T extends DataAdapter> extends ChoiceHelper<T,List<Object>> {
//
//    public final List<Object> choice_data = new ArrayList<>();
//
//    public MultipleChoiceHelper(T adapter) {
//        super(adapter);
//    }
//
//    @Override
//    public Object putChoice(int pos) {
//        if (pos < 0 || pos >= adapter.getItemCount()) return null;
//        Object obj = adapter.getItem(pos);
//        putChoice(obj);
//        return obj;
//    }
//
//    @Override
//    public int putChoice(Object data) {
//        if (data == null) return -1;
//
//        if (isChoiced(data)) {
//            choice_data.remove(data);
//        } else {
//            choice_data.add(data);
//        }
//        return adapter.getPosition(data);
//    }
//
//
//    @Override
//    public Object removeChoice(int pos) {
//        if (pos < 0 || pos >= adapter.getItemCount()) return null;
//        Object obj = adapter.getItem(pos);
//        removeChoice(obj);
//        return obj;
//    }
//
//    @Override
//    public int removeChoice(Object data) {
//        if (data == null) return -1;
//        choice_data.remove(data);
//        return adapter.getPosition(data);
//    }
//
//    @Override
//    public void choiceAll() {
//        choice_data.clear();
//        choice_data.addAll(adapter.viewDataList);
//    }
//
//    @Override
//    public void clearChoices() {
//        choice_data.clear();
//    }
//
//
//    @Override
//    public boolean isChoiced(int pos) {
//        if (pos < 0 || pos >= adapter.getItemCount()) return false;
//        return isChoiced(adapter.getItem(pos));
//    }
//
//    @Override
//    public boolean isChoiced(Object data) {
//        return choice_data.contains(data);
//    }
//
//    @Override
//    public List<Object> getResult() {
//        return choice_data;
//    }
//
//    @Override
//    public void notifyDataSetChanged(int position) {
//        if (position == -1) adapter.notifyDataSetChanged();
//        else adapter.notifyItemChanged(position, -1);
//    }
//}
