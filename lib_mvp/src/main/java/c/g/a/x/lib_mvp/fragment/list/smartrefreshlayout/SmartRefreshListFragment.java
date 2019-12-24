package c.g.a.x.lib_mvp.fragment.list.smartrefreshlayout;

import androidx.databinding.ViewDataBinding;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import c.g.a.x.lib_mvp.fragment.list.ListFragment;
import c.g.a.x.lib_mvp.fragment.list.ListPresenter;
import c.g.a.x.lib_support.views.adapter.v2.abslistview.DataAdapter;

/**
 * Created by Administrator on 2018/1/19.
 */

public abstract class SmartRefreshListFragment<D extends ViewDataBinding, T extends ListPresenter, A extends DataAdapter> extends ListFragment<D, T, A> {

    public SmartRefreshLayout smartRefreshLayout;

    @Override
    public void onCreateViewFirst() {
        smartRefreshLayout = setSmartRefreshLayout();
        super.onCreateViewFirst();
    }

    @Override
    public void setRefreshLayout() {
        if (smartRefreshLayout == null) return;

        enableReLoadDatas(false);
        enableloadMoreDatas(false);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(context));
        smartRefreshLayout.setHeaderHeight(60);

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> presenter.getDatasFstPage());
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> presenter.getDatasNxtPage());
    }

    @Override
    public void enableReLoadDatas(boolean canReload) {
        smartRefreshLayout.setEnableRefresh(canReload);//是否启用下拉刷新功能
    }

    @Override
    public void enableloadMoreDatas(boolean canLoadMore) {
        smartRefreshLayout.setEnableLoadMore(canLoadMore);//是否启用上拉加载功能
    }

    @Override
    public void onRefreshedDatas() {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
    }

    public abstract SmartRefreshLayout setSmartRefreshLayout();
}
