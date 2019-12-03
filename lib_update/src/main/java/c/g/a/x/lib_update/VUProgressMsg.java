package c.g.a.x.lib_update;


import c.g.a.x.lib_rxbus.base.BaseMsg;

/**
 * Created by Administrator on 2018/4/11.
 */

public class VUProgressMsg extends BaseMsg {

    public int state;//0 正在下载  1下载成功 otherwise 失败

    public int progress;

}
