package c.g.a.x.lib_sp;

import android.content.Context;


import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import c.g.a.x.lib_sp.base.BaseSpHelper;
import c.g.a.x.lib_sp.helpers.SpDefaultHelper;

public final class SpMnger<T extends BaseSpHelper> {

    private Context context;

    private final Map<Class<T>, SoftReference<T>> sp_name_map = new HashMap<>(1);

    /////////////
    public static SpDefaultHelper getDefaultHelper() {
        return (SpDefaultHelper) getInstance().open();
    }

    public static SpMnger getInstance() {
        return InnerInstance.INSTANCE;
    }

    private static class InnerInstance {
        private static final SpMnger INSTANCE = new SpMnger();
    }

    private SpMnger() {
    }

    /////////////
    public void init(Context context) {
        this.context = context;
    }

    public T open() {
        return open((Class<T>) SpDefaultHelper.class);
    }

    public T open(Class<T> clz) {
        try {
            if (clz == null) {
                clz = (Class<T>) SpDefaultHelper.class;
            }

            T t;
            SoftReference<T> reference = sp_name_map.get(clz);
            if (reference == null) {
                reference = makeSp(clz);
            }
            t = reference.get();
            if (t == null) {
                reference = makeSp(clz);
                t = reference.get();
            }
            return t;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SoftReference<T> makeSp(Class<T> clz) throws Exception {
        Constructor c = clz.getConstructor(Context.class, String.class);//获取有参构造

        T t = (T) c.newInstance(context, makeSpName(clz));    //通过有参构造创建对象

        SoftReference<T> reference = new SoftReference(t);

        sp_name_map.put(clz, reference);

        return reference;
    }

    private String makeSpName(Class<T> keyName) {
        return context.getPackageName() + "_" + keyName.getSimpleName() + "_sp";
    }

}
