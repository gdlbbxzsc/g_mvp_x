package c.g.a.x.lib_support.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.lib_support.utils.StringUtils;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public abstract class BaseFragment<D extends ViewBinding> extends Fragment implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    protected BaseFragment fragment;
    protected Context context;

    protected D binder;


    private boolean isVisibleToUser;
    private boolean isOnResume;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.e(this + " setUserVisibleHint " + isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;

        if (isVisibleToUserOnResume()) {
            onVisibleResume();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (this.context == null) {
            this.context = context;
            this.fragment = this;
        }
        Logger.e(this + " onAttach new");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (this.context == null) {
            this.context = getActivity();
            this.fragment = this;
        }
        Logger.e(this + " onAttach old");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.e(this + " onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binder == null) {
//            mContentView = inflater.inflate(layoutResID(), container, false);
//            binder = DataBindingUtil.inflate(inflater, layoutResID(), container, false);

            if (hasContentView()) {
                try {
                    ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
                    // 获取第一个类型参数的真实类型
                    Class<D> clazz = (Class<D>) pt.getActualTypeArguments()[0];
                    Method method = clazz.getMethod("inflate", LayoutInflater.class);
                    binder = (D) method.invoke(null, inflater);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            initView();
            onCreateViewFirst();
            initData();
            Logger.e(this + " onCreateView new");
        }
        onCreateViewOtherTimes();
        Logger.e(this + " onCreateView old");
        return binder.getRoot();
    }

    public void onCreateViewFirst() {
    }

    public void onCreateViewOtherTimes() {
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initData();
        Logger.e(this + " onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.e(this + " onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.e(this + " onResume");
        isOnResume = true;
        if (isVisibleToUserOnResume()) {
            onVisibleResume();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        Logger.e(this + " onPause");
        isOnResume = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.e(this + " onStop");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.e(this + " onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.e(this + " onDestroy");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.e(this + " onDetach");
    }

    protected void onVisibleResume() {
    }

    public Context getContext() {
        return context;
    }

    public BaseFragment getFragment() {
        return fragment;
    }

    private boolean isVisibleToUserOnResume() {
        return isOnResume && isVisibleToUser;
    }

    public boolean isVisibleToUser() {
        return isVisibleToUser;
    }

    public boolean isOnResume() {
        return isOnResume;
    }

    protected abstract int layoutResID();

    protected boolean hasContentView() {
        return true;
    }

    public abstract void initView();

    protected abstract void initData();

    //////
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
        EasyPermissions.requestPermissions(fragment,
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
