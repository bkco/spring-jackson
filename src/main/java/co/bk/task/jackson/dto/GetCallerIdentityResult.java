package co.bk.task.jackson.dto;

public class GetCallerIdentityResult {

  private String Account;

  private String Arn;

  private String UserId;

  public String getAccount() {
    return Account;
  }

  public void setAccount(String account) {
    Account = account;
  }

  public String getArn() {
    return Arn;
  }

  public void setArn(String arn) {
    Arn = arn;
  }

  public String getUserId() {
    return UserId;
  }

  public void setUserId(String userId) {
    UserId = userId;
  }
}
