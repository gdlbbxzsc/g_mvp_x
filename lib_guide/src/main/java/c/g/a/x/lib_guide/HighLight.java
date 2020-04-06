package c.g.a.x.lib_guide;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import c.g.a.x.lib_support.android.utils.ViewUtils;

public final class HighLight {


    private View mHole;
    private View.OnClickListener onClickListener;

    private RectF rectF;
    private int padding;

    private Shape shape;
    private int round = 5;

    private List<HighLightGuide> highLightGuideList = new ArrayList<>();

    public boolean fetchLocationEveryTime;

    public HighLight(View mHole) {
        this.mHole = mHole;
    }

    public RectF getRectF(View target) {
        if (rectF == null || fetchLocationEveryTime) rectF = fetchLocation(target);
        return rectF;
    }

    public Shape getShape() {
        return shape;
    }

    public float getRadius() {
        return Math.min(rectF.width() / 2, rectF.height() / 2);
    }

    public int getRound() {
        return round;
    }

    public List<HighLightGuide> getHighLightGuideList() {
        return highLightGuideList;
    }


    public HighLight setPadding(int padding) {
        this.padding = padding;
        return this;
    }

    public HighLight setShapeCircle() {
        this.shape = Shape.CIRCLE;
        return this;
    }

    public HighLight setShapeOval() {
        this.shape = Shape.OVAL;
        return this;
    }

    public HighLight setShapeRectangle() {
        this.shape = Shape.RECTANGLE;
        return this;
    }

    public HighLight setShapeRectangleRound() {
        this.shape = Shape.RECTANGLE_ROUND;
        return this;
    }

    public HighLight setShapeRectangleRound(int round) {
        this.shape = Shape.RECTANGLE_ROUND;
        this.round = round;
        return this;
    }


    public HighLight addRelativeGuide(HighLightGuide highLightGuide) {
        highLightGuide.setHighLight(this);
        highLightGuideList.add(highLightGuide);
        return this;
    }

    public HighLight addRelativeGuide(int layoutId) {
        HighLightGuide highLightGuide = new HighLightGuide(layoutId);
        return addRelativeGuide(highLightGuide);
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public HighLight setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    private RectF fetchLocation(View target) {
        RectF location = new RectF();
        Rect locationInView = ViewUtils.getLocationInView(target, mHole);
        location.left = locationInView.left - padding;
        location.top = locationInView.top - padding;
        location.right = locationInView.right + padding;
        location.bottom = locationInView.bottom + padding;
        return location;
    }

    public enum Shape {
        CIRCLE,//圆形
        OVAL,//椭圆
        RECTANGLE, //矩形
        RECTANGLE_ROUND //圆角矩形
    }
}
