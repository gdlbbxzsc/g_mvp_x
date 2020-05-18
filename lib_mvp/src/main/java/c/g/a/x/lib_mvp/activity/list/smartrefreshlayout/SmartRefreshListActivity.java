package c.g.a.x.lib_mvp.activity.list.smartrefreshlayout;

import androidx.viewbinding.ViewBinding;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import c.g.a.x.lib_mvp.activity.list.ListActivity;
import c.g.a.x.lib_mvp.activity.list.ListPresenter;
import c.g.a.x.lib_support.views.adapter.v2.abslistview.DataAdapter;

/**
 * Created by Administrator on 2018/1/19.
 */

public abstract class SmartRefreshListActivity<D extends ViewBinding, T extends ListPresenter, A extends DataAdapter> extends ListActivity<D, T, A> {

    public SmartRefreshLayout smartRefreshLayout;

    @Override
    public void afterInitView() {
        smartRefreshLayout = setSmartRefreshLayout();
        super.afterInitView();
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
