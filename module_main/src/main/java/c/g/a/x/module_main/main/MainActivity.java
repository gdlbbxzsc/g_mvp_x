package c.g.a.x.module_main.main;

import com.alibaba.android.arouter.facade.annotation.Route;

import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.lib_mvp.activity.MvpActivity;
import c.g.a.x.lib_support.android.utils.NotificationHelper;
import c.g.a.x.module_main.R;
import c.g.a.x.module_main.databinding.ActivityMainBinding;
import pub.devrel.easypermissions.AfterPermissionGranted;


@Route(path = Constant.MAIN_ACTIVITY)
public class MainActivity extends MvpActivity<ActivityMainBinding, Presenter> implements Contract.View {


    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @Override
    protected void initView() {
        NotificationHelper.checkEnabledDialog(context);

    }


    @Override
    protected void initData() {
    }


    @AfterPermissionGranted(Presenter.REQUEST_CODE_4_DOSOMETHING)
    @Override
    public void doSomeThing() {
    }

}
