package au.com.waddle.wriggle.components.converter;

import au.com.waddle.wriggle.model.WriggleInboundRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ikasan.spec.component.transformation.Converter;
import org.ikasan.spec.component.transformation.TransformationException;

public class InboundJsonToWriggleInboundRequestConverter implements Converter<String, WriggleInboundRequest> {
    private ObjectMapper objectMapper;

    public InboundJsonToWriggleInboundRequestConverter(){
        objectMapper = new ObjectMapper();
    }

    @Override
    public WriggleInboundRequest convert(String payload) throws TransformationException {
        try {
            return objectMapper.readValue(payload, WriggleInboundRequest.class);
        }
        catch (JsonProcessingException e) {
            throw new TransformationException(String.format("Could not transform JSON[%s]!", payload), e);
        }
    }
}
