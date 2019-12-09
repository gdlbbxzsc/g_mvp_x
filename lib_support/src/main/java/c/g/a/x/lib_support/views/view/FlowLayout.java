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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import c.g.a.x.lib_support.R;
import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.lib_support.views.toast.SysToast;


@SuppressWarnings("unused")
public final class FlowLayout extends ViewGroup {

    private LayoutInflater inflater;
    // 存储所有子View
    private List<List<View>> mAllChildViews = new ArrayList<>();
    // 每一行的高度
    private List<Integer> mLineHeight = new ArrayList<>();

    private View choose_view;
    private Map<View, Boolean> choose_map;

    private ChooseMode mode = ChooseMode.None;

    private OnItemInitListener onItemInitListener;
    private OnItemClickListener onItemClickListener;

    private MarginLayoutParams layoutParams;

    private int maxLines;

    private int layoutId;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressWarnings("ResourceType")
    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        inflater = LayoutInflater.from(context);

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

    public FlowLayout setLayoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
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

    public final FlowLayout setChooseMode(ChooseMode mode) {
        this.mode = mode;
        if (mode == ChooseMode.Multiple) choose_map = new HashMap<>();
        return this;
    }

    public final FlowLayout setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    public final FlowLayout setOnItemInitListener(OnItemInitListener onItemInitListener) {
        this.onItemInitListener = onItemInitListener;
        return this;
    }

    public final void setDatas(List<? extends Object> list) {
        removeAllViews();
        if (list == null || list.size() <= 0) return;

        if (layoutId == 0) layoutId = R.layout.layout_default_flowlayout_item;
        for (int i = 0, count = list.size(); i < count; i++) {

            Object vo = list.get(i);

            View view = inflater.inflate(layoutId, null);
            this.addView(view, layoutParams);

            view.setId(i);
            view.setTag(vo);
            view.setClickable(true);
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);

            if (onItemInitListener != null) {
                onItemInitListener.onItemInit(i, view, vo);
            } else if (view instanceof TextView) {
                ((TextView) view).setText(vo.toString());
            }

            view.setOnTouchListener(onTouchListener);
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private final OnTouchListener onTouchListener = new OnTouchListener() {

        long lastTime;
        int lastX, lastY;

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

                    putChoice(v);
                }
                break;
            }
            return true;
        }
    };

    private void putChoice(View v) {
        switch (mode) {
            case None:
                onItemClick(v, true);
                break;
            case Single: {
                if (choose_view == v) return;

                if (choose_view != null) onItemClick(choose_view, false);

                choose_view = v;
                onItemClick(choose_view, true);
            }
            break;
            case Multiple: {
                Boolean b = choose_map.get(v);
                if (b == null) {
                    choose_map.put(v, true);
                    onItemClick(v, true);
                } else {
                    choose_map.remove(v);
                    onItemClick(v, false);
                }
            }
            break;
        }
    }

    public final void clearChoice() {
        switch (mode) {
            case Single: {
                onItemClick(choose_view, false);
                choose_view = null;
            }
            break;
            case Multiple: {
                Iterator<View> iterator = choose_map.keySet().iterator();
                while (iterator.hasNext()) {
                    View item = iterator.next();
                    onItemClick(item, false);
                }
                choose_map.clear();
            }
            break;
        }
    }

    public final void choiceAll() {
        if (mode == ChooseMode.Multiple) {
            for (int i = 0; i < getChildCount(); i++) {
                View item = getChildAt(i);
                if (item.getVisibility() == GONE) continue;
                onItemClick(item, true);
                choose_map.put(item, true);
            }
        }
    }

    public final List<Object> getResult() {
        List<Object> list = new ArrayList<>();
        switch (mode) {
            case Single: {
                if (choose_view != null) list.add(choose_view.getTag());
            }
            break;
            case Multiple: {
                for (View view : choose_map.keySet()) {
                    list.add(view.getTag());
                }
            }
            break;
        }
        return list;
    }

    public final Object getResultSingle() {
        if (mode != ChooseMode.Single) {
            Logger.e("this mode can not use getResultSingle");
            SysToast.showToastShort(getContext(), "this mode can not use getResultSingle");
            return null;
        }
        if (choose_view == null) return null;

        return choose_view.getTag();
    }

    public final List<Object> getResultMultiple() {
        if (mode != ChooseMode.Multiple) {
            Logger.e("this mode can not use getResultMultiple");
            SysToast.showToastShort(getContext(), "this mode can not use getResultMultiple");
            return null;
        }

        List<Object> list = new ArrayList<>(choose_map.size());
        for (View view : choose_map.keySet()) {
            list.add(view.getTag());
        }
        return list;
    }

    private void onItemClick(View v, boolean b) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v.getId(), v, v.getTag(), b);
        } else if (v instanceof CheckBox) {
            CheckBox cb = (CheckBox) v;
            switch (mode) {
                case Single:
                case Multiple: {
                    cb.setChecked(b);
                }
                break;
            }
        }
    }


    public static final class DataHelper {
    }

    public interface OnItemInitListener<V extends View, O extends Object> {
        void onItemInit(int i, V view, O vo);
    }

    public interface OnItemClickListener<V extends View, O extends Object> {
        void onItemClick(int i, V view, O vo, boolean click);
    }

    public enum ChooseMode {
        None, Single, Multiple
    }
}
