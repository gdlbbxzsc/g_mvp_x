package c.g.a.x.lib_weixin;

import java.io.Serializable;

/**
 * Created by v on 2019/11/22.
 */
public class MyPayInfo implements Serializable {

    public String appId;
    public String partnerId;
    public String prepayId;
    public String nonceStr;
    public String timeStamp;
    public String packageValue;
    public String sign;
    public String extData;
}
