package com.epam.ta.reportportal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * SendEmailSendEmail
 */
@Validated



public class SendEmailSendEmail   {
  @JsonProperty("recipients")
  @Valid
  private List<String> recipients = null;

  @JsonProperty("subject")
  private String subject = null;

  @JsonProperty("body")
  private String body = null;

  public SendEmailSendEmail recipients(List<String> recipients) {
    this.recipients = recipients;
    return this;
  }

  public SendEmailSendEmail addRecipientsItem(String recipientsItem) {
    if (this.recipients == null) {
      this.recipients = new ArrayList<>();
    }
    this.recipients.add(recipientsItem);
    return this;
  }

  /**
   * Get recipients
   * @return recipients
   **/
  @Schema(description = "")
      @NotNull

    public List<String> getRecipients() {
    return recipients;
  }

  public void setRecipients(List<String> recipients) {
    this.recipients = recipients;
  }

  public SendEmailSendEmail subject(String subject) {
    this.subject = subject;
    return this;
  }

  /**
   * Get subject
   * @return subject
   **/
  @Schema(description = "")
      @NotNull

    public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public SendEmailSendEmail body(String body) {
    this.body = body;
    return this;
  }

  /**
   * Get body
   * @return body
   **/
  @Schema(description = "")
      @NotNull

    public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SendEmailSendEmail sendEmailSendEmail = (SendEmailSendEmail) o;
    return Objects.equals(this.recipients, sendEmailSendEmail.recipients) &&
        Objects.equals(this.subject, sendEmailSendEmail.subject) &&
        Objects.equals(this.body, sendEmailSendEmail.body);
  }

  @Override
  public int hashCode() {
    return Objects.hash(recipients, subject, body);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SendEmailSendEmail {\n");
    
    sb.append("    recipients: ").append(toIndentedString(recipients)).append("\n");
    sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
    sb.append("    body: ").append(toIndentedString(body)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
