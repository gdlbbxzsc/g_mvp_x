package c.g.a.x.module_main.main.index;

import c.g.a.x.lib_mvp.fragment.MvpFragment;
import c.g.a.x.lib_support.views.adapter.v2.abslistview.DataAdapter;
import c.g.a.x.lib_support.views.toast.SysToast;
import c.g.a.x.module_main.R;
import c.g.a.x.module_main.databinding.FragmentIndexBinding;

/**
 * Created by v on 2019/9/19.
 */
public class IndexFragment extends MvpFragment<FragmentIndexBinding, Presenter> implements Contract.View {

    DataAdapter adapter;

    @Override
    protected Presenter createPresenter() {
        return new Presenter<>(this);
    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_index;
    }

    @Override
    public void initView() {
        binder.tv1.setOnClickListener(v -> SysToast.showToastShort(context, "ss"));

        adapter = new DataAdapter(context, binder.lv1, IndexViewHolder.class);
        adapter.addData("aa");
        adapter.addData("aa");
        adapter.addData("aa");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {

    }

}