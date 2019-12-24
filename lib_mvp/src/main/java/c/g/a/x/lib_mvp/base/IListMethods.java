package c.g.a.x.lib_mvp.base;


import java.util.List;

public interface IListMethods {
    //是否可以下拉刷新
    void enableReLoadDatas(boolean canReload);

    //是否可以上拉加载
    void enableloadMoreDatas(boolean canLoadMore);

    //加载完毕操作
    void onRefreshedDatas();

    //清楚数据
    void clearDatas();

    //展示数据
    <T> void setDatas(List<T> list);

}
