package eu.erasmuswithoutpaper.common.boundary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.johnzon.mapper.JohnzonAny;
import org.apache.johnzon.mapper.JohnzonIgnore;

public class ClientRequest implements Serializable {

    private String url;
    private String heiId;
    private HttpMethodEnum method;
    private ParamsClass params;

    private boolean httpsec = true;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethodEnum getMethod() {
        return method;
    }

    public void setMethod(HttpMethodEnum method) {
        this.method = method;
    }

    public String getHeiId() {
        return heiId;
    }

    public void setHeiId(String heiId) {
        this.heiId = heiId;
    }

    public ParamsClass getParams() {
        return params;
    }

    public void setParams(ParamsClass params) {
        this.params = params;
    }

    public boolean isHttpsec() {
        return httpsec;
    }

    public void setHttpsec(boolean httpsec) {
        this.httpsec = httpsec;
    }
}
