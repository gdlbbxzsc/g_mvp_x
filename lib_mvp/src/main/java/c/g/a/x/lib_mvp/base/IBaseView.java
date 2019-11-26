package c.g.a.x.lib_mvp.base;

import android.content.Context;


public interface IBaseView {

    Context getContext();

    //    这个是谷歌第三方权限框架才能用的方法，如果不是EasyPermissions，请注释掉
    void requestByEasyPermissions(int requestCode, String[] perms);
}
