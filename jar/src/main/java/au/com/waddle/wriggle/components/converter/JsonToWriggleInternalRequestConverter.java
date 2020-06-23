package au.com.waddle.wriggle.components.converter;

import au.com.waddle.wriggle.model.WriggleInternalRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ikasan.spec.component.transformation.Converter;
import org.ikasan.spec.component.transformation.TransformationException;

public class JsonToWriggleInternalRequestConverter implements Converter<String, WriggleInternalRequest> {
    private ObjectMapper objectMapper;

    public JsonToWriggleInternalRequestConverter(){
        objectMapper = new ObjectMapper();
    }

    @Override
    public WriggleInternalRequest convert(String payload) throws TransformationException {
        try {
            return objectMapper.readValue(payload, WriggleInternalRequest.class);
        }
        catch (JsonProcessingException e) {
            throw new TransformationException(String.format("Could not transform WriggleInternalRequest[%s]!", payload), e);
        }
    }
}
