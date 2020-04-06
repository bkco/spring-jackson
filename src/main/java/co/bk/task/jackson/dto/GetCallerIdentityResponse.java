package co.bk.task.jackson.dto;


public class GetCallerIdentityResponse {

  private GetCallerIdentityResult GetCallerIdentityResult;

  private ResponseMetadata ResponseMetadata;

  public co.bk.task.jackson.dto.GetCallerIdentityResult getGetCallerIdentityResult() {
    return GetCallerIdentityResult;
  }

  public void setGetCallerIdentityResult(co.bk.task.jackson.dto.GetCallerIdentityResult getCallerIdentityResult) {
    GetCallerIdentityResult = getCallerIdentityResult;
  }

  public co.bk.task.jackson.dto.ResponseMetadata getResponseMetadata() {
    return ResponseMetadata;
  }

  public void setResponseMetadata(co.bk.task.jackson.dto.ResponseMetadata responseMetadata) {
    ResponseMetadata = responseMetadata;
  }
}
