package c.g.a.x.lib_support.views.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("unused")
public class FlowLayout extends RelativeLayout {

    LayoutInflater inflater;
    // 存储所有子View
    private List<List<View>> mAllChildViews = new ArrayList<List<View>>();
    // 每一行的高度
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    View choose_view;
    public Map<View, Boolean> choose_map;

    ViewChooseMode mode = ViewChooseMode.None;


    public FlowLayoutItemCreater layoutItemCreater;

    LayoutParams layoutParams;

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

        layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = layoutParams.rightMargin = layoutParams.topMargin = layoutParams.bottomMargin = 10;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 父控件传进来的宽度和高度以及对应的测量模式
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

//        sizeHeight=0;

        // 自己测量的宽度
        int max_width = 0;
        int max_height = 0;

        // 记录每一行的宽度和高度
        int line_width = 0;
        int line_height = 0;

        mAllChildViews.clear();
        mLineHeight.clear();

        List<View> lineViews = new ArrayList<View>();
        // 获取子view的个数
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == GONE) continue;

            // 测量子View的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            // 换行时候
            if (line_width + childWidth > sizeWidth) {

                max_height += line_height;
                // 记录LineHeight
                mLineHeight.add(line_height);
                // 记录当前行的Views
                mAllChildViews.add(lineViews);
                lineViews = new ArrayList();

                line_width = 0;
                line_height = 0;
            }

            line_width += childWidth;  // 叠加行宽

            max_width = Math.max(line_width, max_width); // 得到最大行高

            line_height = Math.max(line_height, childHeight); // 得到最大行高

            lineViews.add(child);
        }

        if (lineViews.size() > 0) {

            max_height += line_height;

            mLineHeight.add(line_height);
            // 记录当前行的Views
            mAllChildViews.add(lineViews);

        }

//        // 父控件传进来的宽度和高度以及对应的测量模式
//        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
//        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        setMeasuredDimension(sizeWidth > max_width ? sizeWidth : max_width, sizeHeight > max_height ? sizeHeight : max_height);
//        setMeasuredDimension(sizeWidth, sizeHeight > max_height ? sizeHeight : max_height);
//        setMeasuredDimension(sizeWidth, modeHeight == MeasureSpec.EXACTLY ? max_height : sizeHeight);

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

                // 判断是否显示
                if (child.getVisibility() == View.GONE) continue;

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

    public void setMargins(int... ints) {
        if (ints == null || ints.length <= 0) return;

        if (ints.length == 1) {
            layoutParams.leftMargin = layoutParams.rightMargin = layoutParams.topMargin = layoutParams.bottomMargin = ints[0];
            return;
        }

        for (int i = 0; i < ints.length; i++) {
            switch (i) {
                case 0:
                    layoutParams.leftMargin = ints[i];
                    break;
                case 1:
                    layoutParams.rightMargin = ints[i];
                    break;
                case 2:
                    layoutParams.topMargin = ints[i];
                    break;
                case 3:
                    layoutParams.bottomMargin = ints[i];
                    break;
            }
        }
    }

    public void setChooseMode(ViewChooseMode mode) {
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
    }

    private void addViewByLayoutParams(View view) {
        this.addView(view, layoutParams);
    }


    public void setDatas(List<? extends Object> list) {
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

            layoutItemCreater.onFlowLayoutItemClick(mode, view, false);

            layoutItemCreater.initItem(i, view, vo);

            view.setOnTouchListener(onTouchListener);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    OnTouchListener onTouchListener = new OnTouchListener() {

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


    public void putChoice(View v) {
        switch (mode) {
            case None:
                layoutItemCreater.onFlowLayoutItemClick(mode, v, true);
                break;
            case Single: {
                if (choose_view == v) return;

                if (choose_view != null)
                    layoutItemCreater.onFlowLayoutItemClick(mode, choose_view, false);

                choose_view = v;
                layoutItemCreater.onFlowLayoutItemClick(mode, choose_view, true);
            }
            break;
            case Multiple: {
                Boolean b = choose_map.get(v);
                if (b == null) {
                    layoutItemCreater.onFlowLayoutItemClick(mode, v, true);
                    choose_map.put(v, true);
                } else {
                    layoutItemCreater.onFlowLayoutItemClick(mode, v, false);
                    choose_map.remove(v);
                }
            }
            break;
        }
    }

    public interface FlowLayoutItemCreater {

        int getLayoutId();

        void initItem(int i, View view, Object vo);

        void onFlowLayoutItemClick(ViewChooseMode mode, View view, boolean click);
    }

    public enum ViewChooseMode {
        None, Single, Multiple
    }
}
