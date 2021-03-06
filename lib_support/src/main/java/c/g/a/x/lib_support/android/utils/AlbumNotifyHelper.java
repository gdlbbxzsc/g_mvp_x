package c.g.a.x.lib_support.android.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 下载视频图片后徐调用此方法通知系统更新相册数据库。好让相册软件能够查看你下载的文件。
 **/
public final class AlbumNotifyHelper {

    private final Context context;

    private String path;
    private File file;
    private Uri uri;

    public static AlbumNotifyHelper getInstance(Context context) {
        return new AlbumNotifyHelper(context);
    }

    private AlbumNotifyHelper(Context context) {
        this.context = context;
        initMimeType();
    }


    public final AlbumNotifyHelper insertPhoto(String path) {
        load(path);

        if (fileNotExists()) return this;
        ContentValues values = createContentValues();

        values.put(MediaStore.Images.ImageColumns.ORIENTATION, 0);
        values.put(MediaStore.Images.ImageColumns.ORIENTATION, 0);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        return this;
    }

    public final AlbumNotifyHelper insertVideo(String path) {
        load(path);

        if (fileNotExists()) return this;
        ContentValues values = createContentValues();

        context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);

        return this;
    }

    public final AlbumNotifyHelper notifyByBroadcast() {
        if (fileNotExists()) return this;
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);
        context.sendBroadcast(intent);
        return this;
    }

    private ContentValues createContentValues() {
        ContentValues values = new ContentValues();

        String name = file.getName();
        long timeMillis = getTime();

        values.put(MediaStore.MediaColumns.TITLE, name);
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, name);

        values.put(MediaStore.MediaColumns.DATE_MODIFIED, timeMillis);
        values.put(MediaStore.MediaColumns.DATE_ADDED, timeMillis);
        values.put(MediaStore.Video.VideoColumns.DATE_TAKEN, timeMillis);

        values.put(MediaStore.MediaColumns.DATA, path);
        values.put(MediaStore.MediaColumns.SIZE, file.length());

        values.put(MediaStore.MediaColumns.MIME_TYPE, getMimeType(getSuffix()));

        return values;
    }

    private void load(String path) {
        this.path = path;
        file = new File(path);
        uri = Uri.fromFile(file);
    }

    private String getMimeType(String sfx) {
        return mimeTypeMap.get(sfx);
    }

    private void initMimeType() {
        initMimeTypePhoto();
        initMimeTypeVidio();
    }

    private final Map<String, String> mimeTypeMap = new HashMap<>();

    private void initMimeTypePhoto() {
        mimeTypeMap.put("jpg", "image/jpeg");
        mimeTypeMap.put("jpeg", "image/jpeg");
        mimeTypeMap.put("png", "image/png");
        mimeTypeMap.put("gif", "image/gif");
    }


    private void initMimeTypeVidio() {
        mimeTypeMap.put("mp4", "video/mp4");
        mimeTypeMap.put("mpeg4", "video/mp4");
        mimeTypeMap.put("3gp", "video/3gp");
    }


    private long getTime() {
        return System.currentTimeMillis();
    }

    private boolean fileNotExists() {
        return !file.exists();
    }

    private String getSuffix() {
        return path.substring(path.lastIndexOf(".") + 1).toLowerCase().trim();
    }

    // 是不是系统相册
    private boolean isSystemAlbum() {
        String str = path.toLowerCase();
        return str.contains("/dcim/") || str.contains("/camera/");
    }

    public static void main(String[] args) {
        AlbumNotifyHelper helper = new AlbumNotifyHelper(null);
        helper.insertPhoto("").notifyByBroadcast();
    }
}