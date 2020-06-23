package au.com.waddle.wriggle.components.router;

import au.com.waddle.wriggle.model.WriggleInternalRequest;
import org.ikasan.spec.component.routing.RouterException;
import org.ikasan.spec.component.routing.SingleRecipientRouter;

public class RequestTypeRouter implements SingleRecipientRouter<WriggleInternalRequest> {

    @Override
    public String route(WriggleInternalRequest messageToRoute) throws RouterException {
        return messageToRoute.getRequestType();
    }
}
