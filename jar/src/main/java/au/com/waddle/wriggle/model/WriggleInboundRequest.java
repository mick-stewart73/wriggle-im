package au.com.waddle.wriggle.model;

import java.io.Serializable;
import java.util.List;

public class WriggleInboundRequest implements Serializable {
    private String account;
    private String platform;
    private String id;
    private List<String> requestTypes;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getRequestTypes() {
        return requestTypes;
    }

    public void setRequestTypes(List<String> requestTypes) {
        this.requestTypes = requestTypes;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WriggleRequest{");
        sb.append("account='").append(account).append('\'');
        sb.append(", platform='").append(platform).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", requestTypes=").append(requestTypes);
        sb.append('}');
        return sb.toString();
    }
}
