package c.g.a.x.lib_support.views.adapter;

/**
 * Created by Administrator on 2018/7/10.
 */

public abstract class ChoiceHelper<T extends Object, R> {

    protected T adapter;

    public ChoiceHelper(T adapter) {
        this.adapter = adapter;
    }

    public abstract Object putChoice(int pos);

    public abstract int putChoice(Object data);

    public abstract Object removeChoice(int pos);

    public abstract int removeChoice(Object data);

    public abstract void choiceAll();

    public abstract void clearChoices();

    public abstract boolean isChoiced(int pos);

    public abstract boolean isChoiced(Object data);

    public abstract R getResult();

    ////////
    public Object putChoiceNotify(int pos) {
        Object obj = putChoice(pos);
        if (obj == null) return null;
        notifyDataSetChanged(pos);
        return obj;
    }

    public int putChoiceNotify(Object data) {
        int pos = putChoice(data);
        if (pos != -1) notifyDataSetChanged(pos);
        return pos;
    }

    public Object removeChoiceNotify(int pos) {
        Object obj = removeChoice(pos);
        if (obj == null) return null;
        notifyDataSetChanged(pos);
        return obj;
    }

    public int removeChoiceNotify(Object data) {
        int pos = removeChoice(data);
        if (pos != -1) notifyDataSetChanged(pos);
        return pos;
    }

    public void choiceAllNotify() {
        choiceAll();
        notifyDataSetChanged(-1);
    }

    public void clearChoicesNotify() {
        clearChoices();
        notifyDataSetChanged(-1);
    }

    public abstract void notifyDataSetChanged(int position);
}
