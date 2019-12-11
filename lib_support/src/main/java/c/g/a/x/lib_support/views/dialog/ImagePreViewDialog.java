package c.g.a.x.lib_support.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import c.g.a.x.lib_support.R;


public class ImagePreViewDialog extends Dialog {

    private final Context context;

    private final ImageView imageView;

    public static void show(Context context, String url) {
        new ImagePreViewDialog(context).show(url);
    }

    private ImagePreViewDialog(Context context) {
        super(context);
        this.context = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= 19)
            window.setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        window.setBackgroundDrawableResource(android.R.color.transparent);
        //全屏
        window.getDecorView().setPadding(0, 0, 0, 0);
        LayoutParams lp = window.getAttributes();
        lp.width = lp.height = LayoutParams.MATCH_PARENT;

        lp.alpha = 1f;
        lp.gravity = Gravity.CENTER;

        window.setAttributes(lp);


        View view = LayoutInflater.from(context).inflate(R.layout.dialog_imagepreview, null);
        imageView = view.findViewById(R.id.imageView);

        view.setOnClickListener(v -> cancel());

        setContentView(view);
    }

    public void show(String url) {
        super.show();
        Glide.with(context).load(url).into(imageView);
    }
}
