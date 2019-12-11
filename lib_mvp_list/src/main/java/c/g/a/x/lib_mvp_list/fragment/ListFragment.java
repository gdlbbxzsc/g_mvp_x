package c.g.a.x.lib_mvp_list.fragment;

import androidx.databinding.ViewDataBinding;

import java.util.List;

import c.g.a.x.lib_mvp.fragment.MvpFragment;
import c.g.a.x.lib_support.views.adapter.v2.abslistview.DataAdapter;

/**
 * Created by Administrator on 2018/1/19.
 */

public abstract class ListFragment<D extends ViewDataBinding, T extends ListPresenter, A extends DataAdapter> extends MvpFragment<D, T> implements IListFragmentView {

    public A adapter;

    @Override
    public void onCreateViewFirst() {
        adapter = setAdapter();
        setRefreshLayout();
        super.onCreateViewFirst();
    }

    public abstract A setAdapter();

    public abstract void setRefreshLayout();

    @Override
    public void clearDatas() {
        adapter.clearDatas();
    }

    @Override
    public <L> void setDatas(List<L> list) {
        if (list == null || list.size() <= 0) return;
        adapter.addDatas(list);
        adapter.notifyDataSetChanged();
    }
}
