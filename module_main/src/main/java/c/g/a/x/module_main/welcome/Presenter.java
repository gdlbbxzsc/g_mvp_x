package c.g.a.x.module_main.welcome;


import android.Manifest;

import c.g.a.x.lib_mvp.base.BasePresenter;
import pub.devrel.easypermissions.EasyPermissions;

public class Presenter<T extends Contract.View> extends BasePresenter<T> implements Contract.Presenter {

    public Presenter(T baseView) {
        super(baseView);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
    }

    public static final int REQUEST_CODE_4_DOSOMETHING = 1111;

    @Override
    public void checkPermission2doSomeThing() {
        String[] permission4doSomeThing = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE//
                , Manifest.permission.READ_EXTERNAL_STORAGE//
                , Manifest.permission.CAMERA//
                , Manifest.permission.READ_PHONE_STATE
        };

        boolean b = EasyPermissions.hasPermissions(getBaseView().getContext(), permission4doSomeThing);
        if (b) {
            getBaseView().doSomeThing();//这里我们要自己调用达到后置函数自动调用的目的
            return;
        }
        getBaseView().requestByEasyPermissions(REQUEST_CODE_4_DOSOMETHING, permission4doSomeThing);

    }
}
