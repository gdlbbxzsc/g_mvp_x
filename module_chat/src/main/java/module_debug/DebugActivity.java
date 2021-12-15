package module_debug;


import androidx.viewbinding.ViewBinding;

import c.g.a.x.lib_mvp.activity.MvpActivity;


public class DebugActivity extends MvpActivity<ViewBinding, Presenter> implements Contract.View {


    @Override
    protected int layoutResID() {
        return -1;
    }

    @Override
    protected void getDataFromPreActivity() throws Exception {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter<>(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
//        startActivity(new Intent(context, GuideActivity.class));
        finish();
    }

}
