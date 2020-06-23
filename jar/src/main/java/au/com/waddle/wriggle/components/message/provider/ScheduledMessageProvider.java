package au.com.waddle.wriggle.components.message.provider;

import org.ikasan.component.endpoint.quartz.consumer.MessageProvider;
import org.quartz.JobExecutionContext;

public class ScheduledMessageProvider implements MessageProvider {
    @Override
    public Object invoke(JobExecutionContext context) {
        return "{\n" +
                "  \"account\" : \"xero\",\n" +
                "  \"platform\" : \"xero\",\n" +
                "  \"id\" : \"029be834-7b5d-4478-b993-7753f7f922d5\",\n" +
                "  \"requestTypes\" : [ \"ledger\", \"invoice\", \"company\", \"bank\", \"customer\"]\n" +
                "}";
    }
}
