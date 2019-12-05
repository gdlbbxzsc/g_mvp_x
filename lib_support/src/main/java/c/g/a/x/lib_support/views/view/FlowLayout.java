package c.g.a.x.lib_support.views.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("unused")
public final class FlowLayout extends ViewGroup {

    private LayoutInflater inflater;
    // 存储所有子View
    private List<List<View>> mAllChildViews = new ArrayList<List<View>>();
    // 每一行的高度
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    private View choose_view;
    private Map<View, Boolean> choose_map;

    private ViewChooseMode mode = ViewChooseMode.None;

    private FlowLayoutItemCreater layoutItemCreater;

    private MarginLayoutParams layoutParams;

    private int maxLines;

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
            if (heightType && max_height > sizeHeight) {
                for (View temp : lineViews) {
                    temp.setVisibility(GONE);
                }
            } else {
                // 记录LineHeight
                mLineHeight.add(line_height);
                // 记录当前行的Views
                mAllChildViews.add(lineViews);
                lineViews = new ArrayList<>();
                line_width = 0;
                line_height = 0;
                i -= 1;//回退一位,重新计算
            }
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

    public final FlowLayout setChooseMode(ViewChooseMode mode) {
        this.mode = mode;
        switch (mode) {
            case None:
                break;
            case Single:
                break;
            case Multiple:
                choose_map = new HashMap<>();
                break;
        }
        return this;
    }

    public final FlowLayout setLayoutItemCreater(FlowLayoutItemCreater layoutItemCreater) {
        this.layoutItemCreater = layoutItemCreater;
        return this;
    }

    public final void setDatas(List<? extends Object> list) {
        removeAllViews();
        if (list == null || list.size() <= 0) return;

        for (int i = 0, count = list.size(); i < count; i++) {

            Object vo = list.get(i);

            View view = inflater.inflate(layoutItemCreater.getLayoutId(), null);
            addViewByLayoutParams(view);

            view.setId(i);
            view.setTag(vo);
            view.setClickable(true);
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);

            layoutItemCreater.initItem(i, view, vo);

            view.setOnTouchListener(onTouchListener);
        }
    }

    private void addViewByLayoutParams(View view) {
        this.addView(view, layoutParams);
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
                case MotionEvent.ACTION_MOVE: {
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
                layoutItemCreater.onFlowLayoutItemClick(v.getId(), v, v.getTag(), true);
                break;
            case Single: {
                if (choose_view == v) return;

                if (choose_view != null)
                    layoutItemCreater.onFlowLayoutItemClick(v.getId(), v, v.getTag(), false);

                choose_view = v;
                layoutItemCreater.onFlowLayoutItemClick(v.getId(), v, v.getTag(), true);
            }
            break;
            case Multiple: {
                Boolean b = choose_map.get(v);
                if (b == null) {
                    layoutItemCreater.onFlowLayoutItemClick(v.getId(), v, v.getTag(), true);
                    choose_map.put(v, true);
                } else {
                    layoutItemCreater.onFlowLayoutItemClick(v.getId(), v, v.getTag(), false);
                    choose_map.remove(v);
                }
            }
            break;
        }
    }

    public interface FlowLayoutItemCreater<V extends View, O extends Object> {

        int getLayoutId();

        void initItem(int i, V view, O vo);

        void onFlowLayoutItemClick(int i, V view, O vo, boolean click);
    }

    public enum ViewChooseMode {
        None, Single, Multiple
    }
}
