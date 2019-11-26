package c.g.a.x.module_main.main;


import android.Manifest;


import c.g.a.x.lib_mvp.base.BasePresenter;
import pub.devrel.easypermissions.EasyPermissions;

public class Presenter<T extends Contract.View> extends BasePresenter<T> implements Contract.Presenter {


    public Presenter(T baseView) {
        super(baseView);
    }

    @Override
    public void subscribe() {
//        checkPermission2doSomeThing();
    }

    @Override
    public void unsubscribe() {
    }


    //即使没有用到后置函数，也要写一个这个常量，因为获取权限需要一个标识符。他可不仅仅是为了后置函数而存在的
    public static final int REQUEST_CODE_4_DOSOMETHING = 1111;

    //这个 就是 我比较喜欢的，仅仅是 检查权限，检查完了 要做什么你自己决定。
    //true 拥有权限，otherwise 没有权限，并正在请求中
    @Override
    public void checkPermission2doSomeThing() {
        String[] permission4doSomeThing = new String[]{
                Manifest.permission.INTERNET
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
//                , Manifest.permission.SYSTEM_ALERT_WINDOW
        };

//        这里检查没有获取到的权限列表
//        permission4DoSomeThing = PermissionUtils.checkNotAllowPermissions(getBaseView().getContext(), permission4DoSomeThing);
//        //已经拥有权限，直接做什么事情，没有，则获取
//        if (permission4DoSomeThing == null || permission4DoSomeThing.length <= 0) {
//            getBaseView().gotoScan();//这里我们要自己调用达到后置函数自动调用的目的
//        return;
//        }

        boolean b = EasyPermissions.hasPermissions(getBaseView().getContext(), permission4doSomeThing);
        if (b) {
            getBaseView().doSomeThing();//这里我们要自己调用达到后置函数自动调用的目的
            return;
        }

        getBaseView().requestByEasyPermissions(REQUEST_CODE_4_DOSOMETHING, permission4doSomeThing);

    }

}
