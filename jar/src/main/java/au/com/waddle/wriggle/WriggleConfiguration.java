package au.com.waddle.wriggle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WriggleConfiguration {
    // Destinations
    @Value("${queue.inbound.wriggle.request.public}")
    private String inboundWriggleRequestPublic;
    @Value("queue.inbound.wriggle.internal.request.private")
    private String inboundWriggleInternalRequestPrivate;
    @Value("${queue.wriggle.request.company.private}")
    private String wriggleRequestCompanyPrivate;
    @Value("${queue.wriggle.request.bank.account.private}")
    private String wriggleRequestAccountPrivate;
    @Value("${queue.wriggle.request.customer.private}")
    private String wriggleRequestCustomerPrivate;
    @Value("${queue.wriggle.request.invoice.private}")
    private String wriggleRequestInvoicePrivate;
    @Value("${queue.wriggle.request.ledger.private}")
    private String wriggleRequestLedgerPrivate;
    @Value("${queue.wriggle.response.company.public}")
    private String wriggleResponseCompanyPublic;
    @Value("${queue.wriggle.response.bank.account.public}")
    private String wriggleResponseAccountPublic;
    @Value("${queue.wriggle.response.customer.public}")
    private String wriggleResponseCustomerPublic;
    @Value("${queue.wriggle.response.invoice.public}")
    private String wriggleResponseInvoicePublic;
    @Value("${queue.wriggle.response.ledger.public}")
    private String wriggleResponseLedgerPublic;

    // HTTP Details
    @Value("${http.request.username}")
    private String httpRequestUsername;
    @Value("${http.request.password}")
    private String httpRequestPassword;
    @Value("${http.request.base.url}")
    private String httpRequestBaseUrl;
    @Value("${http.customer.request.url}")
    private String httpCustomerRequestUrl;
    @Value("${http.ledger.request.url}")
    private String httpLedgerRequestUrl;
    @Value("${http.invoice.request.url}")
    private String httpInvoiceRequestUrl;
    @Value("${http.company.request.url}")
    private String httpCompanyRequestUrl;
    @Value("${http.account.request.url}")
    private String httpAccountRequestUrl;

    public String getInboundWriggleRequestPublic() {
        return inboundWriggleRequestPublic;
    }

    public String getWriggleRequestCompanyPrivate() {
        return wriggleRequestCompanyPrivate;
    }

    public String getWriggleRequestAccountPrivate() {
        return wriggleRequestAccountPrivate;
    }

    public String getWriggleRequestCustomerPrivate() {
        return wriggleRequestCustomerPrivate;
    }

    public String getWriggleRequestInvoicePrivate() {
        return wriggleRequestInvoicePrivate;
    }

    public String getWriggleRequestLedgerPrivate() {
        return wriggleRequestLedgerPrivate;
    }

    public String getWriggleResponseCompanyPublic() {
        return wriggleResponseCompanyPublic;
    }

    public String getWriggleResponseAccountPublic() {
        return wriggleResponseAccountPublic;
    }

    public String getWriggleResponseCustomerPublic() {
        return wriggleResponseCustomerPublic;
    }

    public String getWriggleResponseInvoicePublic() {
        return wriggleResponseInvoicePublic;
    }

    public String getWriggleResponseLedgerPublic() {
        return wriggleResponseLedgerPublic;
    }

    public String getInboundWriggleInternalRequestPrivate() {
        return inboundWriggleInternalRequestPrivate;
    }

    public String getHttpRequestBaseUrl() {
        return httpRequestBaseUrl;
    }

    public String getHttpRequestUsername() {
        return httpRequestUsername;
    }

    public String getHttpRequestPassword() {
        return httpRequestPassword;
    }

    public String getHttpCustomerRequestUrl() {
        return httpCustomerRequestUrl;
    }

    public String getHttpLedgerRequestUrl() {
        return httpLedgerRequestUrl;
    }

    public String getHttpInvoiceRequestUrl() {
        return httpInvoiceRequestUrl;
    }

    public String getHttpCompanyRequestUrl() {
        return httpCompanyRequestUrl;
    }

    public String getHttpAccountRequestUrl() {
        return httpAccountRequestUrl;
    }
}
