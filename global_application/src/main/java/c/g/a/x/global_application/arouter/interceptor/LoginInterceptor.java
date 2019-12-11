package c.g.a.x.global_application.arouter.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;

import c.g.a.x.global_application.GlobalApplication;
import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.lib_support.android.utils.Logger;

@Interceptor(priority = 1)
public class LoginInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {

        String path = postcard.getPath();
        int extra = postcard.getExtra();

        Logger.e("======>" + this.getClass().getSimpleName() + " extra: " + extra + " path: " + path);

        if (extra != Constant.EXTRA_NEED_LOGIN) {
            callback.onContinue(postcard);
            return;
        }

        boolean isLogin = GlobalApplication.getInstances().checkLogin();

        if (isLogin) {
            callback.onContinue(postcard);
            return;
        }

        callback.onInterrupt(null);

        ARouter.getInstance().build(Constant.LOGIN_ACTIVITY).navigation();
    }

    @Override
    public void init(Context context) {
        Logger.e(this.getClass().getSimpleName() + " init " + context);
    }

}