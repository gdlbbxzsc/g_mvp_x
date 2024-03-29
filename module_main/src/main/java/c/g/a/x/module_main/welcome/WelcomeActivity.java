package c.g.a.x.module_main.welcome;

import android.os.Handler;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.lib_mvp.activity.MvpActivity;
import c.g.a.x.lib_sp.AccountSpHelper;
import c.g.a.x.lib_support.utils.SignCheckUtils;
import c.g.a.x.lib_support.views.dialog.MyDialog;
import c.g.a.x.lib_support.views.toast.SysToast;
import c.g.a.x.module_main.R;
import c.g.a.x.module_main.databinding.ActivityWelcomeBinding;
import pub.devrel.easypermissions.AfterPermissionGranted;

@Route(path = Constant.WELCOME_ACTIVITY)
public class WelcomeActivity extends MvpActivity<ActivityWelcomeBinding, Presenter> implements Contract.View {

    @Override
    protected int layoutResID() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void getDataFromPreActivity() throws Exception {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter<>(this);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        //检测签名 防止被重签
        boolean b = SignCheckUtils.check(context, "2D:52:C3:2B:88:44:A4:1D:92:2B:43:C9:03:ED:A3:A4:B5:55:73:20");
        if (!b) doForceFinish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.checkPermission2doSomeThing();
    }

    @AfterPermissionGranted(Presenter.REQUEST_CODE_4_DOSOMETHING)
    @Override
    public void doSomeThing() {
        new Handler().postDelayed(() -> {
            ARouter.getInstance().build(new AccountSpHelper().isFirstUse() ? Constant.GUIDE_ACTIVITY : Constant.MAIN_ACTIVITY).navigation();
            finish();
        }, 2000);
    }

    @Override
    public void onPermissionsDeniedNormal(int requestCode, List<String> perms) {
        super.onPermissionsDeniedNormal(requestCode, perms);
        MyDialog.confirm(context, "请给与app相应权限，否则将无法正常使用,点击确认获取权限", (dialog, which) -> {
            switch (which) {
                case No:
                    finish();
                    break;
                case Yes:
                    presenter.checkPermission2doSomeThing();
                    break;
            }
            return true;
        });
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        super.onRationaleDenied(requestCode);
        SysToast.showToastShort(context, "请给与app相应权限，否则将无法正常使用");
        presenter.checkPermission2doSomeThing();
    }

}
