package co.bk.task.jackson.example.nestedmap;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class NestedMapDeserializer extends StdDeserializer<PojoWithNestedMap> {

    public NestedMapDeserializer() {
      this(null);
    }

    public NestedMapDeserializer(Class<?> vc) {
      super(vc);
    }

    @Override
    public PojoWithNestedMap deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

      // TODO https://www.baeldung.com/jackson-nested-values

        JsonNode jsonNode = jp.getCodec().readTree(jp);

        PojoWithNestedMap pojoWithNestedMaps = new PojoWithNestedMap();
        pojoWithNestedMaps.setTest_key(jsonNode.get("test_key").textValue());

        JsonNode mapChannelToActivityStatus = jsonNode.get("channelToActivityStatus");
        System.out.println("mapChannelToActivityStatus" + mapChannelToActivityStatus.textValue());

      return pojoWithNestedMaps;

    }
  }