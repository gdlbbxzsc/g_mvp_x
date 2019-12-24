//package c.g.a.x.lib_sp.base;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import java.lang.ref.SoftReference;
//
///**
// * Created by Administrator on 2018/7/9.
// */
//
//public abstract class BaseSpHelper {
//
//    public final Context context;
//    private final String sp_name;
//    private SoftReference<SharedPreferences> reference;
//
//    public BaseSpHelper(Context context, String sp_name) {
//        this.context = context;
//        this.sp_name = sp_name;
//    }
//
//    public SharedPreferences getSp() {
//        if (reference == null) {
//            return makeSp();
//        }
//        SharedPreferences sp = reference.get();
//        if (sp == null) {
//            return makeSp();
//        }
//        return sp;
//    }
//
//    //    context.getSharedPreferences(PREFS_FILE, Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ? Context.MODE_PRIVATE : Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
//    private SharedPreferences makeSp() {
//        SharedPreferences sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
//        reference = new SoftReference<>(sp);
//        return sp;
//    }
//}
