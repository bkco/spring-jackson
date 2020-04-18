package co.bk.task.jackson.dto;

/**
 * ```{
 * "aws_access_key_id": "AccessKey",
 * "aws_secret_access_key": "SecretKey",
 * "aws_session_token": "token"
 * }```
 */
public class CredentialsPojo {

  private String aws_access_key_id;

  private String aws_secret_access_key;

  private String aws_session_token;

  public String getAws_access_key_id() {
    return aws_access_key_id;
  }

  public void setAws_access_key_id(String aws_access_key_id) {
    this.aws_access_key_id = aws_access_key_id;
  }

  public String getAws_secret_access_key() {
    return aws_secret_access_key;
  }

  public void setAws_secret_access_key(String aws_secret_access_key) {
    this.aws_secret_access_key = aws_secret_access_key;
  }

  public String getAws_session_token() {
    return aws_session_token;
  }

  public void setAws_session_token(String aws_session_token) {
    this.aws_session_token = aws_session_token;
  }
}
