package c.g.a.x.lib_mvp.base;


import c.g.a.x.lib_mvp.base.IBasePresenter;

public interface IListPresenter extends IBasePresenter {

    //获取第一页
    void getDatasFstPage();

    //获取下一页
    void getDatasNxtPage();

    //按页数获取
    void getDatasByPage(int page);

}
