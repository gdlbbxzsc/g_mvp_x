package c.g.a.x.lib_support.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.alibaba.android.arouter.launcher.ARouter;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import c.g.a.x.lib_support.android.utils.AndroidUtils;
import c.g.a.x.lib_support.android.utils.viewclickable.ViewClickableMnger;
import c.g.a.x.lib_support.utils.StringUtils;
import c.g.a.x.lib_support.views.dialog.WaitDialogMnger;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    protected BaseActivity activity;
    protected Context context;

    protected T binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;
        context = this;

        initSysSetting();

//        int layout_id = layoutResID();
//        if (layout_id > 0) {
////            setContentView(layout_id);
//            binder = DataBindingUtil.setContentView(this, layout_id);
//        }
        if (hasContentView()) {
            try {
                ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
                // 获取第一个类型参数的真实类型
                Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
                Method method = clazz.getMethod("inflate", LayoutInflater.class);
                binder = (T) method.invoke(null, getLayoutInflater());
                setContentView(binder.getRoot());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        initView();

        afterInitView();

        initData();
    }


    @Override
    protected void onPause() {
        AndroidUtils.hideSoftInput(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        WaitDialogMnger.getInstance().clear(context);
        ViewClickableMnger.getInstance().clear(this);
        super.onDestroy();
    }

    protected void initSysSetting() {
        // 设置不能横屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        ARouter.getInstance().inject(this);
    }

    protected void afterInitView() {
    }


    public Context getContext() {
        return context;
    }

    public BaseActivity getActivity() {
        return activity;
    }

    protected int layoutResID() {
        return 0;
    }

    protected boolean hasContentView() {
        return true;
    }

    protected abstract void initView();

    protected abstract void initData();

    /////////////permissions 相关
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public final void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            toastShort("已拒绝:\n" + str + "\n权限,并不再询问");
            new AppSettingsDialog.Builder(this).build().show();
//            new AppSettingsDialog
//                    .Builder(this)
//                    .setRationale("此功能需要" + str + "权限，否则无法正常使用，是否打开设置")
//                    .setPositiveButton("好")
//                    .setNegativeButton("不行", (dialog, which) -> {
//                        onPermissionsDeniedNegativeButtonClick(requestCode);
//                    })
//
//                    .build()
//                    .show();
            return;
        }

        //        String str = PermissionUtils.checkNotAllowPermissions2String(getContext(), strs);
//        String str = StringUtils.builder("\n", perms);
//        toastShort("已拒绝:\n" + str + "\n权限");
        onPermissionsDeniedNormal(requestCode, perms);
    }

    public void onPermissionsDeniedNormal(int requestCode, List<String> perms) {
    }

    @Override
    public void onRationaleAccepted(int requestCode) {
        // Rationale accepted to request some permissions
        // ...
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        // Rationale denied to request some permissions
        // ...
    }


    //    这个是谷歌第三方权限框架才能用的方法，如果不是EasyPermissions，请注释掉
    public final void requestByEasyPermissions(int requestCode, String[] perms) {
        String permsStr = StringUtils.builder("\n", perms);
        EasyPermissions.requestPermissions(activity,
                "需要获取:\n" + permsStr + "权限",
                requestCode,
                perms);

//        EasyPermissions.requestPermissions(
//                new PermissionRequest.Builder(this, permsStr, perms)
//                        .setRationale(R.string.camera_and_location_rationale)
//                        .setPositiveButtonText(R.string.rationale_ask_ok)
//                        .setNegativeButtonText(R.string.rationale_ask_cancel)
//                        .setTheme(R.style.my_fancy_style)
//                        .build());
    }


}
