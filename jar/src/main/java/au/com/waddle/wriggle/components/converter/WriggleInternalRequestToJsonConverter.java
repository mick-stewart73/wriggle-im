package au.com.waddle.wriggle.components.converter;

import au.com.waddle.wriggle.model.WriggleInternalRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ikasan.spec.component.transformation.Converter;
import org.ikasan.spec.component.transformation.TransformationException;

public class WriggleInternalRequestToJsonConverter implements Converter<WriggleInternalRequest, String> {
    private ObjectMapper objectMapper;

    public WriggleInternalRequestToJsonConverter(){
        objectMapper = new ObjectMapper();
    }

    @Override
    public String convert(WriggleInternalRequest payload) throws TransformationException {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);
        }
        catch (JsonProcessingException e) {
            throw new TransformationException(String.format("Could not transform WriggleInternalRequest[%s]!", payload), e);
        }
    }
}
