package module_debug;



import androidx.databinding.ViewDataBinding;

import c.g.a.x.lib_mvp.activity.MvpActivity;


public class DebugActivity extends MvpActivity<ViewDataBinding,Presenter> implements Contract.View {


    @Override
    protected int layoutResID() {
        return -1;
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
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
