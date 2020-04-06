//package co.bk.task.jackson;
//
//import co.bk.task.jackson.dto.PojoWithList;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import org.junit.Test;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.skyscreamer.jsonassert.JSONCompareMode;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//
//public class AvoidNullListsTest {
//
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
//
//
//    @Test
//    public void testSolution() {
//
//        String result = null;
//        try {
//            //OptionalPojo pojo = new OptionalPojo(Optional.of("testOptional"), "http://localhost:8080");
//            PojoWithList pojo = new PojoWithList("florence", null);
//
//
//
//            result = mapper.writeValueAsString(pojo);
//
//            System.out.println(result);
//
//            JSONAssert.assertEquals("{\"name\":\"florence\",\"contacts\":\"[]]\"}", result, JSONCompareMode.LENIENT);
//        } catch (Exception e) {
//            System.out.println("exception: " + e);
//        }
//    }
//
//
//}
