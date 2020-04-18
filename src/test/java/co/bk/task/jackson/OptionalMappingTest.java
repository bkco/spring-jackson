package co.bk.task.jackson;

import co.bk.task.jackson.dto.OptionalPojo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.Optional;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_FLOAT_AS_INT;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

/**
 * Googles objectmapper format Optional's incorrectly (treats them as objects outputting "value" fields!!).
 * Jackson formats Optionals correctly.
 */
public class OptionalMappingTest {

  Gson problemMapper = new GsonBuilder()
    .setPrettyPrinting()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    .create();


  ObjectMapper mapper = new Jackson2ObjectMapperBuilder().json()
    .modulesToInstall(
      new Jdk8Module(),
      new ParameterNamesModule())
    .featuresToDisable(
      WRITE_DATES_AS_TIMESTAMPS,
      FAIL_ON_EMPTY_BEANS,
      DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
      ACCEPT_FLOAT_AS_INT)
    .featuresToEnable(
      MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,
      DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
      DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
      DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
    .build();

  @Test
  public void testProblemObjectMapper() {

    String result = null;
    try {
      OptionalPojo pojo = new OptionalPojo(Optional.of("testOptional"), "http://localhost:8080");

      result = problemMapper.toJson(pojo);

      System.out.println(result);

      JSONAssert.assertEquals("{\"client_info\": {\"value\": \"testOptional\"},\"server_address\": \"http://localhost:8080\"}", result, JSONCompareMode.LENIENT);
    } catch (Exception e) {
      System.out.println("exception: " + e);
    }

  }


  @Test
  public void testSolution() {

    String result = null;
    try {
      OptionalPojo pojo = new OptionalPojo(Optional.of("testOptional"), "http://localhost:8080");

      result = mapper.writeValueAsString(pojo);

      System.out.println(result);

      JSONAssert.assertEquals("{\"clientInfo\":\"testOptional\",\"serverAddress\":\"http://localhost:8080\"}", result, JSONCompareMode.LENIENT);
    } catch (Exception e) {
      System.out.println("exception: " + e);
    }
  }


}
