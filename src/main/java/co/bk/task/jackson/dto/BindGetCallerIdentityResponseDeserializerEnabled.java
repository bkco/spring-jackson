package co.bk.task.jackson.dto;

/**
 * {
 *     "test_key": "helloWorld",
 *     "GetCallerIdentityResponse": {
 *         "GetCallerIdentityResult": {
 *             "Account": "188193004453",
 *             "Arn": "arn:aws:sts::188193004453:assumed-role/Shibboleth-PowerUser/brkelly",
 *             "UserId": "AROAIDRHXISJPDVWNDY2Y:brkelly"
 *         },
 *         "ResponseMetadata": {
 *             "RequestId": "ed0e898e-c8d6-11e9-a549-a57c1765306b"
 *         }
 *     }
 * }
 *
 */
public class BindGetCallerIdentityResponseDeserializerEnabled {

  private GetCallerIdentityResponse GetCallerIdentityResponse;

  public String getTest_key() {
    return test_key;
  }

  public void setTest_key(String test_key) {
    this.test_key = test_key;
  }

  private String test_key;

  public co.bk.task.jackson.dto.GetCallerIdentityResponse getGetCallerIdentityResponse() {
    return GetCallerIdentityResponse;
  }

  public void setGetCallerIdentityResponse(co.bk.task.jackson.dto.GetCallerIdentityResponse getCallerIdentityResponse) {
    GetCallerIdentityResponse = getCallerIdentityResponse;
  }
}
