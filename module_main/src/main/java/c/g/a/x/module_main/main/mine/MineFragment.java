package c.g.a.x.module_main.main.mine;

import c.g.a.x.lib_mvp.fragment.MvpFragment;
import c.g.a.x.module_main.R;
import c.g.a.x.module_main.databinding.FragmentMineBinding;

/**
 * Created by v on 2019/9/19.
 */
public class MineFragment extends MvpFragment<FragmentMineBinding, Presenter> implements Contract.View {

    @Override
    protected Presenter createPresenter() {
        return new Presenter<>(this);
    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(FragmentMineBinding viewDataBinding) {

    }

    @Override
    protected void initData() {

    }

}