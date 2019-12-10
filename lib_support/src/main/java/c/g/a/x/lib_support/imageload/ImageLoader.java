package c.g.a.x.lib_support.imageload;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import c.g.a.x.lib_support.R;

public final class ImageLoader {
    public static void loadPhoto(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url).asBitmap()//
                .centerCrop()//
                .error(R.drawable.default_head_portrait)//
                .placeholder(R.drawable.default_head_portrait)//
                .transform(new GlideCircleTransform(context))//
                .into(imageView);
    }


}
