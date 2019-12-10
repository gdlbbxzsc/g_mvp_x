package c.g.a.x.lib_support.imageload.glide;

/**
 * Created by Administrator on 2018/5/15.
 */

import android.content.Context;
import android.os.Looper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;

import c.g.a.x.lib_support.utils.FileUtils;


public final class CacheUtil {

    // 获取Glide磁盘缓存大小
    public static String getCacheSize(Context context) {
        return getCacheSize(Glide.getPhotoCacheDir(context).getAbsolutePath());
    }

    public static String getExternalCacheSize(Context context) {
        return getCacheSize(getImageExternalCatchDir(context));
    }

    public static String getInternalCacheSize(Context context) {
        return getCacheSize(getImageInternalCatchDir(context));
    }

    public static String getCacheSize(String path) {
        return FileUtils.convertFileSize(FileUtils.getSize(path));
    }


    // 清除Glide磁盘缓存，自己获取缓存文件夹并删除方法
    public static boolean cleanExternalCatchDisk(Context context) {
        return cleanCatchDisk(getImageExternalCatchDir(context));
    }

    public static boolean cleanInternalCatchDisk(Context context) {
        return cleanCatchDisk(getImageInternalCatchDir(context));
    }

    public static boolean cleanCatchDisk(String path) {
        return FileUtils.deleteAll(path);
    }

    // 清除图片磁盘缓存，调用Glide自带方法
    public static void clearCacheDisk(Context context) {
        //只能在子线程运行
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Glide.get(context).clearDiskCache();
            return;
        }
        new Thread(() -> Glide.get(context).clearDiskCache()).start();
    }

    // 清除Glide内存缓存
    public static boolean clearCacheMemory(Context context) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return false;
        }//只能在主线程执行
        Glide.get(context).clearMemory();
        return true;
    }

    public static String getImageExternalCatchDir(Context context) {
//        ExternalCacheDiskCacheFactory代表/sdcard/Android/data/<application package>/cache
        String ImageExternalCatchDir = context.getExternalCacheDir() + ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        return ImageExternalCatchDir;

    }

    public static String getImageInternalCatchDir(Context context) {
//        InternalCacheDiskCacheFactory代表/data/data/<application package>/cache
        String ImageInternalCatchDir = context.getCacheDir() + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        return ImageInternalCatchDir;
    }

}
