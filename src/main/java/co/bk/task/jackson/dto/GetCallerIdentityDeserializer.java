package co.bk.task.jackson.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class GetCallerIdentityDeserializer extends StdDeserializer<BindGetCallerIdentityResponseDeserializerEnabled> {

    public GetCallerIdentityDeserializer() {
      this(null);
    }

    public GetCallerIdentityDeserializer(Class<?> vc) {
      super(vc);
    }

    @Override
    public BindGetCallerIdentityResponseDeserializerEnabled deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

      // TODO https://www.baeldung.com/jackson-nested-values

      JsonNode callerIdentityNode = jp.getCodec().readTree(jp);

      BindGetCallerIdentityResponseDeserializerEnabled callerIdentity = new BindGetCallerIdentityResponseDeserializerEnabled();
      callerIdentity.setTest_key(callerIdentityNode.get("test_key").textValue());

      return callerIdentity;

    }
  }