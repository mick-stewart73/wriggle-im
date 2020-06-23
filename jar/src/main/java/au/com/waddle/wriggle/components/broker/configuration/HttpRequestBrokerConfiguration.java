package au.com.waddle.wriggle.components.broker.configuration;

import org.ikasan.spec.configuration.Masked;

public class HttpRequestBrokerConfiguration {
    private String username;
    @Masked
    private String password;

    private String baseUrl;

    private String requestUrlContext;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getRequestUrlContext() {
        return requestUrlContext;
    }

    public void setRequestUrlContext(String requestUrlContext) {
        this.requestUrlContext = requestUrlContext;
    }
}
