package co.bk.task.jackson;


public class GetCallerIdentityResponse {

  private GetCallerIdentityResult GetCallerIdentityResult;

  private ResponseMetadata ResponseMetadata;

  public co.bk.task.jackson.GetCallerIdentityResult getGetCallerIdentityResult() {
    return GetCallerIdentityResult;
  }

  public void setGetCallerIdentityResult(co.bk.task.jackson.GetCallerIdentityResult getCallerIdentityResult) {
    GetCallerIdentityResult = getCallerIdentityResult;
  }

  public co.bk.task.jackson.ResponseMetadata getResponseMetadata() {
    return ResponseMetadata;
  }

  public void setResponseMetadata(co.bk.task.jackson.ResponseMetadata responseMetadata) {
    ResponseMetadata = responseMetadata;
  }
}
