package co.bk.task.jackson.jsonwithlist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_FLOAT_AS_INT;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

// Example demoing how to bind following JSON to Java objects using JacksonMapper without a custom deserializer.
/*
 * {
        "result_code": 0,
        "state": {
            "state_name": "Quotation created",
            "actions": [
                {
                    "action_name": "submit_quotation",
                    "meta": {
                        "permission": "supplier1:can submit quotations in own tenant"
                    }
                },
                {
                    "action_name": "delete_quotation",
                    "meta": {
                        "permission": "supplier1:can delete own quotations in own tenant"
                    }
                }
            ]
        }
    }
 */
public class JsonWithListTest {

    //@Bean
    public ObjectMapper defaultObjectMapper() {

        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().json()
                .modulesToInstall(
                        new JavaTimeModule(),
                        new Jdk8Module(),
                        new ParameterNamesModule()
                        //new ProblemModule()
                        )
                .featuresToDisable(
                        WRITE_DATES_AS_TIMESTAMPS,
                        FAIL_ON_EMPTY_BEANS,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        ACCEPT_FLOAT_AS_INT)
                .featuresToEnable(
                        DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
                        DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
                        DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS,
                        JsonParser.Feature.IGNORE_UNDEFINED)
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();

        return objectMapper;
    }

    @Test
    public void testMappingJsonToObject() {

        try {

            String json = "{\"result_code\":0,\"state\":{\"state_name\":\"Quotation created\",\"actions\":[{\"action_name\":\"submit_quotation\",\"meta\":{\"permission\":\"supplier1:can submit quotations in own tenant\"}},{\"action_name\":\"delete_quotation\",\"meta\":{\"permission\":\"supplier1:can delete own quotations in own tenant\"}}]}}";
            JsonWithList jsonAsObject = defaultObjectMapper().readValue(json, JsonWithList.class);

            System.out.println(jsonAsObject.toString());

            Assertions.assertThat(jsonAsObject.getState().getState_name()).isEqualToIgnoringCase("Quotation created");

        } catch (Exception e) {
            System.out.println("exception: " + e);
        }
    }


}
