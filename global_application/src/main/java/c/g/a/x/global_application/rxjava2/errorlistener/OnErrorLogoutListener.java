package c.g.a.x.global_application.rxjava2.errorlistener;


import c.g.a.x.global_application.GlobalApplication;
import c.g.a.x.global_application.UserInfo;
import c.g.a.x.lib_http.rxjava2.filterobserver.BaseFilter;

/**
 * Created by Administrator on 2018/1/9.
 * 监听登录互斥错误码的listener
 */

public class OnErrorLogoutListener implements BaseFilter.OnErrorListener {

    @Override
    public void onDo(BaseFilter.Error error) {
        UserInfo userInfo = GlobalApplication.getInstances().userInfo;

//        因为下面的   userInfo.clear(); 会清除掉用户信息。而会清除掉用户信息且会走入这里的逻辑，一般是登录互斥和token失效
//        所以，此处的处理是 希望第二次进入这里的逻辑不要在跳转到 LoginActivity了 因为 第一个进入这里且清楚用户信息的已经跳转了
//        if (TextUtils.isEmpty(userInfo.userId) || TextUtils.isEmpty(userInfo.token)) {
//            return;
//        }

        userInfo.clear();

//        //TODO 这里我应该做一个跳转到登录页面或者是登录弹窗。
        GlobalApplication.getInstances().checkLogin4GoLogin(error.context);
    }
}