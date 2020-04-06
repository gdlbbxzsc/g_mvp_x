package c.g.a.x.lib_guide;

import android.graphics.RectF;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.HashSet;
import java.util.Set;

public final class HighLightGuide {

    private int layoutId;

    private Set<LayoutType> layoutTypes = new HashSet<>(4, 1);
    private int marginLeft, marginRight, marginTop, marginBottom;

    private HighLight highLight;

    private View view;
    private View.OnClickListener onClickListener;

    public HighLightGuide(int layoutId) {
        this.layoutId = layoutId;
    }

    public HighLightGuide setLayoutType(LayoutType layoutType) {
        layoutTypes.add(layoutType);
        return this;
    }

    public HighLightGuide setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
        return this;
    }

    public HighLightGuide setMarginRight(int marginRight) {
        this.marginRight = marginRight;
        return this;
    }

    public HighLightGuide setMarginTop(int marginTop) {
        this.marginTop = marginTop;
        return this;
    }

    public HighLightGuide setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
        return this;
    }

    void setHighLight(HighLight highLight) {
        this.highLight = highLight;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public HighLightGuide setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public final View getGuideLayout(ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        scaleLayoutParams(layoutParams, viewGroup);
        view.setLayoutParams(layoutParams);
        return view;
    }

    private void scaleLayoutParams(FrameLayout.LayoutParams layoutParams, ViewGroup viewGroup) {
        RectF rectF = highLight.getRectF(viewGroup);
        int gravity = 0;
        for (LayoutType layoutType : layoutTypes) {
            switch (layoutType) {
                case Bottom2Top: {
                    gravity |= Gravity.BOTTOM;
                    layoutParams.bottomMargin += (int) (viewGroup.getHeight() - rectF.top + marginBottom);
                }
                break;
                case Top2Bottom: {
                    gravity |= Gravity.TOP;
                    layoutParams.topMargin += (int) (rectF.bottom + marginTop);
                }
                break;
                case Right2Left: {
                    gravity |= Gravity.RIGHT;
                    layoutParams.rightMargin += (int) (viewGroup.getWidth() - rectF.left + marginRight);
                }
                break;
                case Left2Right: {
                    gravity |= Gravity.LEFT;
                    layoutParams.leftMargin += (int) (rectF.right + marginLeft);
                }
                break;
                case Top2Top: {
                    gravity |= Gravity.TOP;
                    layoutParams.topMargin += (int) (rectF.top + marginTop);
                }
                break;
                case Left2Left: {
                    gravity |= Gravity.LEFT;
                    layoutParams.leftMargin += (int) (rectF.left + marginLeft);
                }
                break;
                case Right2Right: {
                    gravity |= Gravity.RIGHT;
                    layoutParams.rightMargin += (int) (viewGroup.getWidth() - rectF.right + marginRight);
                }
                break;
                case Bottom2Bottom: {
                    gravity |= Gravity.BOTTOM;
                    layoutParams.bottomMargin += (int) (viewGroup.getHeight() - rectF.bottom + marginBottom);
                }
                break;
            }
        }
        layoutParams.gravity = gravity | Gravity.CENTER;
    }

    public enum LayoutType {
        //        TopToParent, LeftToParent, RightToParent, BottomToParent,
        Bottom2Top, Right2Left, Left2Right, Top2Bottom, Top2Top, Left2Left, Right2Right, Bottom2Bottom
    }
}
