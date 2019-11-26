package c.g.a.x.lib_support.views.adapter.v1.abslistview.choicehelper;


import c.g.a.x.lib_support.views.adapter.ChoiceHelper;
import c.g.a.x.lib_support.views.adapter.v1.abslistview.DataAdapter;

/**
 * Created by Administrator on 2018/7/10.
 */

public class SingleChoiceHelper<T extends DataAdapter> extends ChoiceHelper<T, Object> {


    public Object choice_data;

    public SingleChoiceHelper(T adapter) {
        super(adapter);
    }


    @Override
    public Object putChoice(int pos) {
        if (pos < 0 || pos >= adapter.getCount()) return null;
        return choice_data = adapter.getItem(pos);
    }

    @Override
    public int putChoice(Object data) {
        int pos = adapter.getPosition(data);
        if (pos != -1) choice_data = data;
        return pos;
    }


    @Override
    public Object removeChoice(int pos) {
        if (pos < 0 || pos >= adapter.getCount()) return null;
        choice_data = null;
        return adapter.getItem(pos);
    }

    @Override
    public int removeChoice(Object data) {
        int pos = adapter.getPosition(data);
        if (pos != -1) choice_data = null;
        return pos;
    }

    @Override
    public void choiceAll() {
    }

    @Override
    public void clearChoices() {
        choice_data = null;
    }

    @Override
    public boolean isChoiced(int pos) {
        if (pos < 0 || pos >= adapter.getCount()) return false;
        return isChoiced(adapter.getItem(pos));
    }

    @Override
    public boolean isChoiced(Object data) {
        return this.choice_data == data;
    }

    @Override
    public Object getResult() {
        return choice_data;
    }

    @Override
    public void notifyDataSetChanged(int position) {
        adapter.notifyDataSetChanged();
    }
}
