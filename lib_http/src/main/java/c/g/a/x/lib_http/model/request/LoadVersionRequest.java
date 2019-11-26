package c.g.a.x.lib_http.model.request;


import c.g.a.x.lib_http.base.BaseRequest;
import c.g.a.x.lib_http.model.response.LoadVersionResponse;

public class LoadVersionRequest<T extends LoadVersionResponse> extends BaseRequest<T> {

    @Override
    public String getRequestPath() {
        return "sdf";
    }
}
