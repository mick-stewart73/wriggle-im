package au.com.waddle.wriggle.broker;

import au.com.waddle.wriggle.components.broker.HttpRequestBroker;
import au.com.waddle.wriggle.components.broker.configuration.HttpRequestBrokerConfiguration;
import au.com.waddle.wriggle.model.WriggleInternalRequest;
import org.junit.Test;

public class HttpRequestBrokerTest {

    @Test
    public void test(){
        HttpRequestBrokerConfiguration configuration = new HttpRequestBrokerConfiguration();
        configuration.setUsername("tyl");
        configuration.setPassword("38980ae2-bc70-4746-a667-ad8c3a48b71b");
        configuration.setBaseUrl("http://localhost:8080");
        configuration.setRequestUrlContext("/api/account/%s/accounting/%s/%s/invoice?from=2015-01-01&page=1");

        HttpRequestBroker requestBroker = new HttpRequestBroker();
        requestBroker.setConfiguration(configuration);

        WriggleInternalRequest wriggleInternalRequest = new WriggleInternalRequest();
        wriggleInternalRequest.setPlatform("xero");
        wriggleInternalRequest.setAccount("xero");
        wriggleInternalRequest.setId("029be834-7b5d-4478-b993-7753f7f922d5");

        System.out.println(requestBroker.invoke(wriggleInternalRequest));
    }
}
