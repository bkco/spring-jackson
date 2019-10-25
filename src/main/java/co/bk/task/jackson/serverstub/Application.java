package co.bk.task.jackson.serverstub;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class Application {

  @ResponseStatus(code = HttpStatus.OK)
  @RequestMapping(method = RequestMethod.GET, value = "/ping")
  String ping() {
    return "pong";
  }


  @ResponseStatus(code = HttpStatus.OK)
  @RequestMapping(method = RequestMethod.GET, value = {"/simple_data"}, produces = "application/json")
  public String getSimpleData(@RequestHeader Map<String, String> requestHeaders, HttpServletResponse response) {


    return String.format("{\"aws_access_key_id\" : \"%s\",\"aws_secret_access_key\" : \"%s\",\"aws_session_token\" : \"%s\"}",
      "sessionCredentials.getAccessKeyId", "sessionCredentials.getSecretAccessKey", "sessionCredentials.getSessionToken");
  }

  @ResponseStatus(code = HttpStatus.OK)
  @RequestMapping(method = RequestMethod.GET, value = {"/complex_data"}, produces = "application/json")
  public String getComplexData(@RequestHeader Map<String, String> requestHeaders, HttpServletResponse response) {

    return "{\"test_key\": \"helloWorld\", \"GetCallerIdentityResponse\":{\"GetCallerIdentityResult\":{\"Account\":\"128193004453\",\"Arn\":\"arn:aws:sts::128193004453:assumed-role/test/xyz\",\"UserId\":\"AROAIDRHXISJPDVWNDY2Y:xyz\"},\"ResponseMetadata\":{\"RequestId\":\"ed0e898e-c8d6-11e9-a549-a57c1765306b\"}}}";
  }

  @ResponseStatus(code = HttpStatus.OK)
  @RequestMapping(method = RequestMethod.GET, value = {"/complex_data_xml"}, produces = "text/xml")
  public String getComplexDataAsXml(@RequestHeader Map<String, String> requestHeaders, HttpServletResponse response) {

    //return "{\"test_key\": \"helloWorld\", \"GetCallerIdentityResponse\":{\"GetCallerIdentityResult\":{\"Account\":\"128193004453\",\"Arn\":\"arn:aws:sts::128193004453:assumed-role/test/xyz\",\"UserId\":\"AROAIDRHXISJPDVWNDY2Y:xyz\"},\"ResponseMetadata\":{\"RequestId\":\"ed0e898e-c8d6-11e9-a549-a57c1765306b\"}}}";

    return "<GetCallerIdentityResponse xmlns=\"https://sts.amazonaws.com/doc/2011-06-15/\">\n" +
      "  <GetCallerIdentityResult>\n" +
      "    <Arn>arn:aws:sts::036396015065:assumed-role/Shibboleth-PowerUser/brkelly</Arn>\n" +
      "    <UserId>AROAQQ6K7DXMT2EWVKITN:brkelly</UserId>\n" +
      "    <Account>036396015065</Account>\n" +
      "  </GetCallerIdentityResult>\n" +
      "  <ResponseMetadata>\n" +
      "    <RequestId>629aeb7e-d86f-11e9-918a-c7adbd7fdb70</RequestId>\n" +
      "  </ResponseMetadata>\n" +
      "</GetCallerIdentityResponse>";
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }


}