package c.g.a.x.lib_support.views.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import c.g.a.x.lib_support.R;


@SuppressWarnings("unused")
public final class FlowLayout extends ViewGroup {

    private int maxLines;

    private List<List<View>> mAllChildViews = new ArrayList<>();   // 存储所有子View
    private List<Integer> mLineHeight = new ArrayList<>(); // 每一行的高度
    private MarginLayoutParams layoutParams;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressWarnings("ResourceType")
    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        layoutParams = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = layoutParams.rightMargin = layoutParams.topMargin = layoutParams.bottomMargin = 10;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 父控件传进来的宽度和高度以及对应的测量模式
//        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        boolean heightType = modeHeight == MeasureSpec.EXACTLY;

        // 自己测量的宽度
        int max_height = 0;

        // 记录每一行的宽度和高度
        int line_width = 0;
        int line_height = 0;

        mAllChildViews.clear();
        mLineHeight.clear();

        List<View> lineViews = new ArrayList<>();
        // 获取子view的个数
        for (int i = 0, childCount = getChildCount() - 1; i <= childCount; i++) {
            View child = getChildAt(i);
            // 测量子View的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (maxLines > 0 && mAllChildViews.size() >= maxLines) {
                child.setVisibility(GONE);
                continue;
            }

            if (heightType && max_height > sizeHeight) {
                child.setVisibility(GONE);
                continue;
            }

            if (i >= childCount && lineViews.size() > 0) {
                child.setVisibility(GONE);
                continue;
            }

            if (heightType && max_height + line_height > sizeHeight) {
                child.setVisibility(GONE);
                for (View temp : lineViews) {
                    temp.setVisibility(GONE);
                }
                lineViews.clear();
                continue;
            }

            line_width += childWidth;  // 叠加行宽
            if (line_width <= sizeWidth) {

                line_height = Math.max(line_height, childHeight); // 得到最大行高
                lineViews.add(child);

                if (i >= childCount) {
                    line_width = Integer.MAX_VALUE;
                    i -= 1;//回退一位,重新计算
                }
                continue;
            }
            // 换行时候
            max_height += line_height;
            // 记录LineHeight
            mLineHeight.add(line_height);
            // 记录当前行的Views
            mAllChildViews.add(lineViews);
            lineViews = new ArrayList<>();
            line_width = 0;
            line_height = 0;
            i -= 1;//回退一位,重新计算

        }
        setMeasuredDimension(sizeWidth, heightType ? sizeHeight : max_height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 设置子View的位置
        int left = 0;
        int top = 0;

        for (int i = 0; i < mAllChildViews.size(); i++) {
            // 当前行的views和高度
            int lineHeight = mLineHeight.get(i);

            List<View> lineViews = mAllChildViews.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    child.layout(0, 0, 0, 0);
                    continue;
                }            // 判断是否显示

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int cLeft = left + lp.leftMargin;
                int cTop = top + lp.topMargin;
                int cRight = cLeft + child.getMeasuredWidth();
                int cBottom = cTop + child.getMeasuredHeight();
                // 进行子View进行布局
                child.layout(cLeft, cTop, cRight, cBottom);

                left = cRight + lp.rightMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }


    public final FlowLayout setMaxLines(int maxLines) {
        this.maxLines = maxLines;
        return this;
    }

    public final FlowLayout setMargins(int... ints) {
        if (ints == null || ints.length <= 0) return this;

        if (ints.length >= 4) {
            layoutParams.leftMargin = ints[0];
            layoutParams.rightMargin = ints[1];
            layoutParams.topMargin = ints[2];
            layoutParams.bottomMargin = ints[3];
        } else if (ints.length >= 2) {
            layoutParams.leftMargin = layoutParams.rightMargin = ints[0];
            layoutParams.topMargin = layoutParams.bottomMargin = ints[1];
        } else {
            layoutParams.leftMargin = layoutParams.rightMargin = layoutParams.topMargin = layoutParams.bottomMargin = ints[0];
        }
        return this;
    }

    public static abstract class DataHelper<V extends View, O, T> {

        FlowLayout flowLayout;
        private LayoutInflater inflater;

        private int layoutId = R.layout.layout_default_flowlayout_item;

        private List<O> dataList = new ArrayList<>();

        OnItemInitListener<V, O> onItemInitListener;
        OnItemStateChangeListener onItemStateChangeListener;
        OnItemClickListener<V, O> onItemClickListener;

        public DataHelper(FlowLayout flowLayout) {
            this.flowLayout = flowLayout;
        }

        public DataHelper setLayoutId(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        public final void setOnItemClickListener(OnItemClickListener<V, O> onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public void setOnItemStateChangeListener(OnItemStateChangeListener onItemStateChangeListener) {
            this.onItemStateChangeListener = onItemStateChangeListener;
        }

        public final void setOnItemInitListener(OnItemInitListener<V, O> onItemInitListener) {
            this.onItemInitListener = onItemInitListener;
        }

        public final void setDatas(List<O> temps) {
            dataList.clear();
            flowLayout.removeAllViews();

            if (temps == null) return;

            dataList.addAll(temps);
            if (inflater == null) inflater = LayoutInflater.from(flowLayout.getContext());
            for (int i = 0; i < dataList.size(); i++) {
                flowLayout.addView(createView(inflater, i), flowLayout.layoutParams);
            }
        }

        private V createView(LayoutInflater inflater, int i) {

            V view = (V) inflater.inflate(layoutId, null);

            O obj = dataList.get(i);
            view.setId(i);
            view.setTag(obj);
            view.setClickable(true);
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);

            if (onItemInitListener != null) {
                onItemInitListener.onItemInit(i, view, obj);
            } else if (view instanceof TextView) {
                ((TextView) view).setText(obj.toString());
            }

            view.setOnTouchListener(onTouchListener);
            return view;
        }

        @SuppressLint("ClickableViewAccessibility")
        private final OnTouchListener onTouchListener = new OnTouchListener() {

            private long lastTime;
            private int lastX, lastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        lastTime = System.currentTimeMillis();
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                    }
                    break;
                    case MotionEvent.ACTION_UP: {
                        if (System.currentTimeMillis() - lastTime > 500) break;
                        if (Math.abs(lastX - event.getRawX()) > 50) break;
                        if (Math.abs(lastY - event.getRawY()) > 50) break;

                        putChoice((V) v);
                    }
                    break;
                }
                return true;
            }
        };

        public abstract void clearChoice();

        public abstract void choiceAll();

        protected abstract void putChoice(V view);

        protected abstract void onItemStateChange(V view, boolean click);

        public abstract T getResult();

    }

    public static class ClickDataHelper<V extends View, O> extends DataHelper<V, O, O> {


        public ClickDataHelper(FlowLayout flowLayout) {
            super(flowLayout);
        }

        @Override
        public void clearChoice() {
        }

        @Override
        public void choiceAll() {
        }

        @Override
        protected void putChoice(V view) {
            onItemStateChange(view, true);
        }

        @Override
        protected void onItemStateChange(V v, boolean click) {
            if (click && onItemClickListener != null) {
                onItemClickListener.onItemClick(v.getId(), v, (O) v.getTag());
            }
        }

        @Override
        public O getResult() {
            return null;
        }
    }

    public static class SingleChoiceDataHelper<V extends View, O> extends DataHelper<V, O, O> {

        private V choose_view;

        public SingleChoiceDataHelper(FlowLayout flowLayout) {
            super(flowLayout);
        }

        @Override
        public void clearChoice() {
            onItemStateChange(choose_view, false);
            choose_view = null;
        }

        @Override
        public void choiceAll() {
        }

        @Override
        protected void putChoice(V view) {
            if (choose_view == view) return;
            if (choose_view != null) onItemStateChange(choose_view, false);
            choose_view = view;
            onItemStateChange(choose_view, true);
        }

        @Override
        protected void onItemStateChange(V v, boolean click) {
            if (onItemStateChangeListener != null) {
                onItemStateChangeListener.onItemStateChange(v.getId(), v, (O) v.getTag(), click);
            } else if (v instanceof CheckBox) {
                CheckBox cb = (CheckBox) v;
                cb.setChecked(click);
            }

            if (click && onItemClickListener != null) {
                onItemClickListener.onItemClick(v.getId(), v, (O) v.getTag());
            }
        }

        @Override
        public O getResult() {
            if (choose_view == null) return null;
            return (O) choose_view.getTag();
        }

    }

    public static class MultipleChoiceDataHelper<V extends View, O> extends DataHelper<V, O, List<O>> {

        private Map<V, Boolean> choose_map = new HashMap<>();

        public MultipleChoiceDataHelper(FlowLayout flowLayout) {
            super(flowLayout);
        }


        @Override
        public void clearChoice() {
            for (V item : choose_map.keySet()) {
                onItemStateChange(item, false);
            }
            choose_map.clear();
        }

        @Override
        public void choiceAll() {
            for (int i = 0; i < flowLayout.getChildCount(); i++) {
                V item = (V) flowLayout.getChildAt(i);
                if (item.getVisibility() == GONE) continue;
                onItemStateChange(item, true);
                choose_map.put(item, true);
            }
        }

        @Override
        protected void putChoice(V v) {
            Boolean b = choose_map.get(v);
            if (b == null) {
                choose_map.put(v, true);
                onItemStateChange(v, true);
            } else {
                choose_map.remove(v);
                onItemStateChange(v, false);
            }
        }

        @Override
        protected void onItemStateChange(V v, boolean click) {
            if (onItemStateChangeListener != null) {
                onItemStateChangeListener.onItemStateChange(v.getId(), v, (O) v.getTag(), click);
            } else if (v instanceof CheckBox) {
                CheckBox cb = (CheckBox) v;
                cb.setChecked(click);
            }
            if (click && onItemClickListener != null) {
                onItemClickListener.onItemClick(v.getId(), v, (O) v.getTag());
            }
        }
 
        @Override
        public List<O> getResult() {
            List<O> list = new ArrayList<>();
            for (View view : choose_map.keySet()) {
                list.add((O) view.getTag());
            }
            return list;
        }
    }

    public interface OnItemInitListener<V extends View, O> {
        void onItemInit(int i, V view, O vo);
    }

    public interface OnItemClickListener<V extends View, O> {
        void onItemClick(int i, V view, O vo);
    }

    public interface OnItemStateChangeListener<V extends View, O> {
        void onItemStateChange(int i, V view, O vo, boolean click);
    }
}
