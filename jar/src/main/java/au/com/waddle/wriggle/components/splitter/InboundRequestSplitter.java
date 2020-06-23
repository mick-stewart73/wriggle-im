package au.com.waddle.wriggle.components.splitter;

import au.com.waddle.wriggle.model.WriggleInboundRequest;
import au.com.waddle.wriggle.model.WriggleInternalRequest;
import org.ikasan.spec.component.splitting.Splitter;
import org.ikasan.spec.component.splitting.SplitterException;

import java.util.ArrayList;
import java.util.List;

public class InboundRequestSplitter implements Splitter<WriggleInboundRequest, WriggleInternalRequest> {

    @Override
    public List<WriggleInternalRequest> split(WriggleInboundRequest event) throws SplitterException {
        ArrayList<WriggleInternalRequest> wriggleInternalRequests = new ArrayList<>();

        for(String requestType: event.getRequestTypes()){
            WriggleInternalRequest wriggleInternalRequest = new WriggleInternalRequest();

            wriggleInternalRequest.setAccount(event.getAccount());
            wriggleInternalRequest.setPlatform(event.getPlatform());
            wriggleInternalRequest.setId(event.getId());
            wriggleInternalRequest.setRequestType(requestType);

            wriggleInternalRequests.add(wriggleInternalRequest);
        }

        return wriggleInternalRequests;
    }
}
