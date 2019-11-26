package c.g.a.x.lib_http.base;


import java.io.Serializable;

public abstract class BaseRequest<T extends BaseResponse> implements Serializable {

    public abstract String getRequestPath();

}
