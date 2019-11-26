package c.g.a.x.lib_mvp_list.fragment;


import java.util.List;

import c.g.a.x.lib_mvp.base.BasePresenter;
import c.g.a.x.lib_mvp_list.IListPresenter;

public abstract class ListPresenter<T extends IListFragmentView> extends BasePresenter<T> implements IListPresenter {


    public int page_num;

    public ListPresenter(T baseView) {
        super(baseView);
        page_num = firstPageNum();
    }

    @Override
    public void getDatasFstPage() {
        page_num = firstPageNum();
        if (!beforeLoad(page_num)) return;
        getDatasByPage(page_num);
    }

    @Override
    public void getDatasNxtPage() {
        if (!beforeLoad(page_num)) return;
        getDatasByPage(page_num);
    }

    public boolean beforeLoad(int page) {
        if (page != page_num) {
//            预防性操作，99.99%不会发生
            return false;
        }

        if (page == -1) {
            //=-1：代表没有更多数据了。所以 只能刷新不能加载
            getBaseView().enableReLoadDatas(true);
            getBaseView().enableloadMoreDatas(false);
            return false;
        }
        //=1or=0：代表第一次加载，所以 只能刷新不能加载
        if (page == firstPageNum()) {
            getBaseView().enableReLoadDatas(true);
            getBaseView().enableloadMoreDatas(false);
        }
        return true;
    }

    public void afterLoadFail() {
        getBaseView().onRefreshedDatas();
    }

    public void afterLoadSucc(int page, List<? extends Object> list) {

        getBaseView().onRefreshedDatas();

        if (page != page_num) {
//            预防性操作，抛出页数不对的数据
            return;
        }

        if (page == firstPageNum()) {
            //=1or=0：代表第一次加载，所以清楚列表全部数据
            getBaseView().clearDatas();
        }


        if (list == null || list.isEmpty() || list.size() < getPageCount()) {
            page_num = -1;
            getBaseView().enableReLoadDatas(true);
            getBaseView().enableloadMoreDatas(false);
        } else {
            page_num += 1;
            getBaseView().enableReLoadDatas(true);
            getBaseView().enableloadMoreDatas(true);
        }
        ////向listview中加载数据
        getBaseView().setDatas(list);
    }

    public int firstPageNum() {
        return 0;
    }

    public int getPageCount() {
        return 20;
    }
}
