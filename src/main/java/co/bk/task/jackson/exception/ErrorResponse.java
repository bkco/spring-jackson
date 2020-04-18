package co.bk.task.jackson.exception;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Builder(toBuilder = true)
@Value
@JsonDeserialize(builder = ErrorResponse.ErrorResponseBuilder.class)
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = -925569961625320534L;

    int http_status;
    String application_code;
    String message;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class ErrorResponseBuilder {

    }
}
