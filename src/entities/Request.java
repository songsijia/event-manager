package entities;
import java.io.Serializable;

/**
 * The Request is a class that stores relevant information for a user request.
 * It stores information of the username of the user who sends the request, the content of the request, the identifier
 * of the request, and the status of the request.
 */

public class Request implements Serializable {

  private String username;
  private String content;
  private int identifier;
  private String status;

  public Request(String username, String content, int identifier) {
    this.username = username;
    this.content = content;
    this.identifier = identifier;
    this.status = "pending";
  }

  public String getUsername() {
    return this.username;
  }

  public String getContent() {
    return this.content;
  }

  public String getStatus() {
    return this.status;
  }

  public int getIdentifier() {
    return this.identifier;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return identifier + ": " + content;
  }
}
