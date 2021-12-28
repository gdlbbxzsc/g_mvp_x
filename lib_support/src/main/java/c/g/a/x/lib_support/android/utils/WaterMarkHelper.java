package c.g.a.x.lib_support.android.utils;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// Watermark.getInstance().show(this, "测试水印");
//为当前界面增加水印
public class WaterMarkHelper {

    private static volatile WaterMarkHelper sInstance;


    public static WaterMarkHelper getInstance() {
        if (sInstance == null) {
            synchronized (WaterMarkHelper.class) {
                if (sInstance == null) {
                    sInstance = new WaterMarkHelper();
                }
            }
        }
        return sInstance;
    }

    private WaterMarkHelper() {
    }

    public void show(Activity activity, String text) {
        WatermarkDrawable drawable = new WatermarkDrawable();
        drawable.mText = text;

        ViewGroup rootView = activity.findViewById(android.R.id.content);
        FrameLayout layout = new FrameLayout(activity);
        layout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setBackground(drawable);
        rootView.addView(layout);
    }

    private class WatermarkDrawable extends Drawable {
        private Paint mPaint;

        private String mText;


        private WatermarkDrawable() {
            mPaint = new Paint();
            mPaint.setColor(0xAEAEAEAE);
            mPaint.setTextSize(50);
            mPaint.setAntiAlias(true);
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            int width = getBounds().right;
            int height = getBounds().bottom;

            float textWidth = mPaint.measureText(mText) + 20;
            float wh = (float) Math.sqrt(textWidth * textWidth / 2);


            canvas.drawColor(0x00000000);//背景色

            // 右上角
//            canvas.translate(width - wh - 20, 50);
//            canvas.rotate(45);//角度
//            canvas.drawText(mText, 0, 0, mPaint);


            canvas.translate(width - width / 2, -width / 2);
            canvas.rotate(-35);//角度

            int diagonal = (int) Math.sqrt(width * width + height * height); // 对角线的长度


            int startx = width / 4;
            int stepx = (int) (startx + textWidth);
            int stepy = height / 10;

            int index = 0;
            float fromX;
            for (int positionY = stepy; positionY <= diagonal; positionY += stepy) {
                fromX = -diagonal + (index++ % 2) * startx;
                for (float positionX = fromX; positionX <= diagonal; positionX += stepx) {
                    canvas.drawText(mText, positionX, positionY, mPaint);
                }
            }

        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

    }
}
