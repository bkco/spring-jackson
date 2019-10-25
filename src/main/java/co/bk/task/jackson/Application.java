package co.bk.task.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.http.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_FLOAT_AS_INT;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

/**
 * https://stackoverflow.com/questions/19389723/can-not-deserialize-instance-of-java-lang-string-out-of-start-object-token
 *
 */
public class Application {

  public static void main(String[] args) throws JsonParseException,
    JsonMappingException, IOException {

    //ObjectMapper mapper = new ObjectMapper();
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


    String jsonNested = "{\"id\": 2,\"socket\": \"0c317829-69bf-43d6-b598-7c0c550635bb\",\"type\": \"getDashboard\",\"data\": {\"workstationUuid\": \"ddec1caa-a97f-4922-833f-632da07ffc11\"},\"reply\": true}";
    MyPojo details = mapper.readValue(jsonNested, MyPojo.class);
    System.out.println("Value for getFirstName is: " + details.getId());
    System.out.println("Value for getLastName  is: " + details.getSocket());
    System.out.println("Value for getChildren is: " +
      details.getData().getWorkstationUuid());
    System.out.println("Value for getChildren is: " + details.getReply());


    String json = "{\"aws_access_key_id\": \"my-access-key\",\"aws_secret_access_key\": \"my-secret-key\",\"aws_session_token\": \"my-session-token\"}";
    CredentialsPojo creds = mapper.readValue(json, CredentialsPojo.class);
    System.out.println("aws_access_key_id: " + creds.getAws_access_key_id());
    System.out.println("aws_secret_access_key: " + creds.getAws_secret_access_key());
    System.out.println("aws_session_token: " + creds.getAws_session_token());

    String jsonViaBuilder = "{\"aws_access_key_id\": \"my-access-key\",\"aws_secret_access_key\": \"my-secret-key\",\"aws_session_token\": \"my-session-token\"}";
    CredentialsPojoBuilderAsPerHrs credsViaBuilder = mapper.readValue(jsonViaBuilder, CredentialsPojoBuilderAsPerHrs.class);
    System.out.println("awsaccessKeyId: " + credsViaBuilder.getAws_access_key_id());
    System.out.println("awssecretKey: " + credsViaBuilder.getAws_secret_access_key());
    System.out.println("sessionToken: " + credsViaBuilder.getAws_session_token());

    String jsonGetCallerIdentity = "{\"GetCallerIdentityResponse\":{\"GetCallerIdentityResult\":{\"Account\":\"128193004453\",\"Arn\":\"arn:aws:sts::128193004453:assumed-role/test/xyz\",\"UserId\":\"AROAIDRHXISJPDVWNDY2Y:xyz\"},\"ResponseMetadata\":{\"RequestId\":\"ed0e898e-c8d6-11e9-a549-a57c1765306b\"}}}";
    BindGetCallerIdentityResponse credsNestedObjects = mapper.readValue(jsonGetCallerIdentity, BindGetCallerIdentityResponse.class);
    System.out.println("account: " + credsNestedObjects.getGetCallerIdentityResponse().getGetCallerIdentityResult().getAccount());

    bindSimpleData_willWork();

    bindComplexData_willFail();

    bindComplexData_willWork();
  }

  private static void bindSimpleData_willWork() {

    RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

    ResponseEntity<CredentialsPojo> responseAsJson = restTemplate.exchange("http://localhost:8080/simple_data", HttpMethod.GET, entity, CredentialsPojo.class);

    System.out.println("print" + responseAsJson.toString());
  }

  private static void bindComplexData_willFail() {

    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

    // FAILS because a restTemplate does not know how to deserialize it. It can only bind one level of simple strings unless a custom deserializer is registered.
    ResponseEntity<BindGetCallerIdentityResponse> responseAsJson = restTemplate.exchange("http://localhost:8080/complex_data", HttpMethod.GET, entity, BindGetCallerIdentityResponse.class);

    System.out.println("print" + responseAsJson.toString());
  }

  private static void bindComplexData_willWork() {

    // SEE https://www.baeldung.com/jackson-nested-values

    //RestTemplate restTemplate = new RestTemplate();
    RestTemplate restTemplate = registerDeserializerWithRestTemplate();

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

    ResponseEntity<BindGetCallerIdentityResponseDeserializerEnabled> responseAsJson = restTemplate.exchange("http://localhost:8080/complex_data", HttpMethod.GET, entity, BindGetCallerIdentityResponseDeserializerEnabled.class);

    System.out.println("print" + responseAsJson.toString());
  }

  private static void bindComplexDataAsXml_willWork() {

    // SEE https://www.baeldung.com/jackson-nested-values

    //RestTemplate restTemplate = new RestTemplate();
    RestTemplate restTemplate = registerDeserializerWithRestTemplate();

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setAccept(Arrays.asList(MediaType.TEXT_XML));
    HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

    ResponseEntity<BindGetCallerIdentityResponseDeserializerEnabled> responseAsJson = restTemplate.exchange("http://localhost:8080/complex_data", HttpMethod.GET, entity, BindGetCallerIdentityResponseDeserializerEnabled.class);

    System.out.println("print" + responseAsJson.toString());
  }

  private static RestTemplate registerDeserializerWithRestTemplate() {

    // Manual registration of objectmapper with RestTemplate when not using SpringIOC e.g https://stackoverflow.com/questions/9381665/how-can-we-configure-the-internal-jackson-mapper-when-using-resttemplate

    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(BindGetCallerIdentityResponseDeserializerEnabled.class, new GetCallerIdentityDeserializer());
    objectMapper.registerModule(module);

    RestTemplate restTemplate = new RestTemplate();

    MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
    messageConverter.setPrettyPrint(false);
    messageConverter.setObjectMapper(objectMapper);
    restTemplate.getMessageConverters().removeIf(m -> m.getClass().getName().equals(MappingJackson2HttpMessageConverter.class.getName()));
    restTemplate.getMessageConverters().add(messageConverter);
    return restTemplate;
  }



//  public static ObjectMapper getDefaultObjectMapperForXML() {
//    JacksonXmlModule xmlModule = new JacksonXmlModule();
//    xmlModule.setDefaultUseWrapper(false);
//    return new XmlMapper(xmlModule);
//  }



}
