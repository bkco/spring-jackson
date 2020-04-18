package co.bk.task.jackson.example.nestedmap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_FLOAT_AS_INT;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

/**
 * Approach uses
 *   com.google.code.gson.Gson
 * To parse JSON string into objects.... rather than use deserializer registered with Jackson.
 *
 * Approach suitable when you want to load a file into some data object in an application.
 *
 * Learning points:
 * 1. javascript associative array is NOT json formatted data!! It can be converted to JSON data by using js stringify....
 *              {
 *         "psg" {
 *             "activityStatus": "ASSESSMENT_PENDING",
 *             "processingTaskOrder": [
 *                 {
 *                             "order": 0,
 *                             "processingTask": "ConsolidatorPsgNotificationPublisher"
 *                 },
 *                 {
 *                             "order": 1,
 *                             "processingTask": "StateChangedEventPublisher"
 *                 },
 *             ]
 *         }
 *         "easyrfx" {
 *
 *         }
 *     }
 * 2. Proper JSON for this use case:
 *             {
 *                 "channels": [
 *                     {
 *                         "name": "psg",
 *                         "activityStatuses": [
 *                             {
 *                             "name": "ASSESSMENT_PENDING",
 *                             "processingTasks": [
 *                                 {
 *                                     "order": 0,
 *                                     "name": "ConsolidatorPsgNotificationPublisher"
 *                                 },
 *                                 {
 *                                     "order": 1,
 *                                     "name": "StateChangedEventPublisher"
 *                                 }
 *                             ]
 *                             }
 *                         ]
 *                     }
 *                 ]
 *             }
 *     Prettified:
 *     {"channels":[{"channelName":"psg","states":[{"name":"ASSESSMENT_PENDING","tasks":[{"order":0,"processingTaskName":"ConsolidatorPsgNotificationPublisher"},{"order":1,"processingTaskName":"StateChangedEventPublisher"}]}]}]}
 *
 */
public class NestedMapGsonParserTest {

    //@Bean
    public ObjectMapper defaultObjectMapper() {

        /*
         * Custom object mapper to override the one provided by wescale web-spring-boot-starter jar. Prevents stacktrace leaking out in reponse messages and intended to hide usage of Camunda.
         * Restricts data returned by ZalandoProblem https://github.com/zalando/problem
         */
        SimpleModule simpleModule = new SimpleModule();
        //simpleModule.addSerializer(BigDecimal.class, new BigDecimalSerializer());
        //simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleModule.addDeserializer(PojoWithNestedMap.class, new NestedMapDeserializer());

        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().json()
                .modulesToInstall(
                        new JavaTimeModule(),
                        new Jdk8Module(),
                        new ParameterNamesModule(),
                        //new ProblemModule(),
                        simpleModule)
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


////    ObjectMapper mapper = new Jackson2ObjectMapperBuilder().json()
////            .modulesToInstall(
////                    new Jdk8Module(),
////                    new ParameterNamesModule())
////            .featuresToDisable(
////                    WRITE_DATES_AS_TIMESTAMPS,
////                    FAIL_ON_EMPTY_BEANS,
////                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
////                    ACCEPT_FLOAT_AS_INT)
////            .featuresToEnable(
////                    MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,
////                    DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
////                    DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
////                    DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
////            .build();
//
//   ObjectMapper mapper = new Jackson2ObjectMapperBuilder().json()
//           .featuresToEnable(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
//
//
//    //objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false)


    // https://www.baeldung.com/gson-string-to-jsonobject
    @Test
    public void testSolution() {

        String result = null;
        try {
//            Map<String,String> channelToActivityStatus = new HashMap();
//            channelToActivityStatus.put("psg", "ASSESSMENT_PENDING");
//            channelToActivityStatus.put("psg", "QUOTATION_REJECTED");
//            //OptionalPojo pojo = new OptionalPojo(Optional.of("testOptional"), "http://localhost:8080");
//            //PojoWithList pojo = new PojoWithList("florence", null);
//            PojoWithNestedMap pojo = new PojoWithNestedMap();
//            pojo.setTest_key("testing");
//            pojo.setChannelToActivityStatus(channelToActivityStatus);



            //result = defaultObjectMapper().writeValueAsString(pojo); // {"test_key":"testing","channelToActivityStatus":{"psg":"QUOTATION_REJECTED"}}

            //String json = "{\"test_key\":\"testing\",\"channelToActivityStatus\":{\"psg\":\"ASSESSMENT_PENDING\",\"psg\":\"QUOTATION_REJECTED\"}}";

            String json = "{\"channels\":[{\"name\":\"psg\",\"activityStatuses\":[{\"name\":\"ASSESSMENT_PENDING\",\"processingTasks\":[{\"order\":0,\"name\":\"ConsolidatorPsgNotificationPublisher\"},{\"order\":1,\"name\":\"StateChangedEventPublisher\"}]}]}]}";

            JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);

            JsonArray channels = convertedObject.get("channels").getAsJsonArray();

            JsonObject channelAndStatuses = channels.get(0).getAsJsonObject();

            JsonArray activityStatuses = channelAndStatuses.get("activityStatuses").getAsJsonArray();

            for (JsonElement item: activityStatuses) {
                String name = item.getAsJsonObject().get("name").getAsString();
                System.out.println("name is" + name);
                JsonArray processingTasks = item.getAsJsonObject().get("processingTasks").getAsJsonArray();


            }



            Assert.assertTrue(convertedObject.isJsonObject());
            Assert.assertTrue(convertedObject.get("test_key").getAsString().equals("testing"));
            Assert.assertTrue(convertedObject.get("channelToActivityStatus").getAsJsonArray().equals("testing"));

//            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
//
//            Assert.assertTrue(jsonObject.isJsonObject());
//            Assert.assertTrue(jsonObject.get("name").getAsString().equals("Baeldung"));
//            Assert.assertTrue(jsonObject.get("java").getAsBoolean() == true);

//            Object jsonMap = defaultObjectMapper().readValue(json, PojoWithNestedMap.class);
//
//            System.out.println(jsonMap.toString());
//
//            JSONAssert.assertEquals("{\"name\":\"florence\",\"contacts\":\"[]]\"}", result, JSONCompareMode.LENIENT);
        } catch (Exception e) {
            System.out.println("exception: " + e);
        }
    }


}
