package au.com.waddle.wriggle.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.ArrayList;

public class WriggleRequestTest {

    @Test
    public void test() throws JsonProcessingException {
        WriggleInboundRequest wriggleRequest = new WriggleInboundRequest();
        wriggleRequest.setAccount("xero");
        wriggleRequest.setPlatform("xero");
        wriggleRequest.setId("029be834-7b5d-4478-b993-7753f7f922d5");

        ArrayList<String> requestTypes = new ArrayList<>();
        requestTypes.add("ledger");
        requestTypes.add("invoice");

        wriggleRequest.setRequestTypes(requestTypes);

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(wriggleRequest));
    }
}
