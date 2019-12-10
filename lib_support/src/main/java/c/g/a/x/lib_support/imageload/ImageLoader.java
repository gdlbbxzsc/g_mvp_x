package c.g.a.x.lib_support.imageload;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

public final class ImageLoader {

    public static void loadHead(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
    }

    public static void load(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url).into(imageView);
    }

}
