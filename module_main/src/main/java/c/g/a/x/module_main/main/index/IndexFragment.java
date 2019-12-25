package c.g.a.x.module_main.main.index;

import c.g.a.x.lib_mvp.fragment.MvpFragment;
import c.g.a.x.module_main.R;
import c.g.a.x.module_main.databinding.FragmentIndexBinding;

/**
 * Created by v on 2019/9/19.
 */
public class IndexFragment extends MvpFragment<FragmentIndexBinding, Presenter> implements Contract.View {

    @Override
    protected Presenter createPresenter() {
        return new Presenter<>(this);
    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_index;
    }

    @Override
    public void initView(FragmentIndexBinding viewDataBinding) {

    }

    @Override
    protected void initData() {

    }

}