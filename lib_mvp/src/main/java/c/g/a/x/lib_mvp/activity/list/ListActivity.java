package c.g.a.x.lib_mvp.activity.list;

import androidx.databinding.ViewDataBinding;

import java.util.List;

import c.g.a.x.lib_mvp.activity.MvpActivity;
import c.g.a.x.lib_support.views.adapter.v2.abslistview.DataAdapter;

/**
 * Created by Administrator on 2018/1/19.
 */

public abstract class ListActivity<D extends ViewDataBinding, T extends ListPresenter, A extends DataAdapter> extends MvpActivity<D, T> implements IListActivityView {

    private A adapter;

    @Override
    public void afterInitView() {
        adapter = setAdapter();
        setRefreshLayout();
        super.afterInitView();
    }

    public abstract void setRefreshLayout();

    public abstract A setAdapter();

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
