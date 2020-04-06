package c.g.a.x.lib_guide;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class PaintLayout extends FrameLayout {

    private final int DEFAULT_BACKGROUND_COLOR = 0xb2000000;
    private final int touchSlop;

    private Paint mPaint;

    private GuideController controller;

    private GuidePage guidePage;

    public PaintLayout(Context context, GuideController controller) {
        super(context);
        this.controller = controller;

        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        //设置画笔遮罩滤镜,可以传入BlurMaskFilter或EmbossMaskFilter，前者为模糊遮罩滤镜而后者为浮雕遮罩滤镜
        //这个方法已经被标注为过时的方法了，如果你的应用启用了硬件加速，你是看不到任何阴影效果的
        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER));
        //关闭当前view的硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        //ViewGroup默认设定为true，会使onDraw方法不执行，如果复写了onDraw(Canvas)方法，需要清除此标记
        setWillNotDraw(false);

    }

    void setGuidePage(GuidePage page) {
        this.guidePage = page;
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guidePage.isTouchCancel()) controller._onTouchCancelListener.onClick(v);
            }
        });
        addHighLightGuides();
//        invalidate();
    }

    private float downX;
    private float downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float upX = event.getX();
                float upY = event.getY();
                if (Math.abs(upX - downX) > touchSlop) break;
                if (Math.abs(upY - downY) > touchSlop) break;

                if (guidePage == null) break;

                for (HighLight highLight : guidePage.getHighLights()) {
                    OnClickListener listener = highLight.getOnClickListener();
                    if (listener == null) continue;
                    RectF rectF = highLight.getRectF((ViewGroup) getParent());
                    if (rectF.contains(upX, upY)) {
                        listener.onClick(this);
                        return true;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(DEFAULT_BACKGROUND_COLOR);
        drawHighlights(canvas);
    }

    private void drawHighlights(Canvas canvas) {
        if (guidePage == null) return;
        for (HighLight highLight : guidePage.getHighLights()) {
            RectF rectF = highLight.getRectF((ViewGroup) getParent());
            switch (highLight.getShape()) {
                case CIRCLE:
                    canvas.drawCircle(rectF.centerX(), rectF.centerY(), highLight.getRadius(), mPaint);
                    break;
                case OVAL:
                    canvas.drawOval(rectF, mPaint);
                    break;
                case RECTANGLE_ROUND:
                    canvas.drawRoundRect(rectF, highLight.getRound(), highLight.getRound(), mPaint);
                    break;
                case RECTANGLE:
                default:
                    canvas.drawRect(rectF, mPaint);
                    break;
            }
        }
    }


    private void addHighLightGuides() {
        removeAllViews();
        if (guidePage == null) return;
        for (HighLight highLight : guidePage.getHighLights()) {
            for (HighLightGuide highLightGuide : highLight.getHighLightGuideList()) {
                View view = highLightGuide.getGuideLayout((ViewGroup) getParent());
                OnClickListener listener = highLightGuide.getOnClickListener();
                if (listener != null) view.setOnClickListener(listener);

                addView(view);
            }
        }
    }

    public void dismiss() {
        if (getParent() == null) return;
        ((ViewGroup) getParent()).removeView(this);

        View.OnClickListener listener = controller.getOnDismissListener();
        if (listener != null) listener.onClick(this);
    }

}
