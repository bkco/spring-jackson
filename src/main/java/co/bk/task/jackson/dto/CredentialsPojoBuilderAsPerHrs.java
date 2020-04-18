package co.bk.task.jackson.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

/**
 * ```{
 * "aws_access_key_id": "accessKey",
 * "aws_secret_access_key": "secretKey",
 * "aws_session_token": "token"
 * }```
 */
@Builder(toBuilder = true)
@Value
@JsonDeserialize(builder = CredentialsPojoBuilderAsPerHrs.CredentialsPojoBuilderAsPerHrsBuilder.class)
public class CredentialsPojoBuilderAsPerHrs {

  private String aws_access_key_id;

  private String aws_secret_access_key;

  private String aws_session_token;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class CredentialsPojoBuilderAsPerHrsBuilder {
  }
}
