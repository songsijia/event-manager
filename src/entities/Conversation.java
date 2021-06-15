package entities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Conversation class represents a conversation between two users.
 * A conversation consists of a list of messages, and each message is stored as a HashMap with keys sender, receiver,
 * content and time.
 * @author Sijia Song, Kara Han
 */
public class Conversation implements Serializable {

  private String user1;
  private String user2;
  private ArrayList<HashMap<String, String>> messages;

  // ArrayList<Dict> {sender: 'Kara', receiver: 'Sijia', content: 'hi', time: '2020-11-12 15:16'}

  /**
   * Constructs an instance of Conversation between user1 and user2
   *
   * @param user1 the username of user1 in the Conversation
   * @param user2 the username of user2 in the Conversation
   */
  public Conversation(String user1, String user2) {
    this.user1 = user1;
    this.user2 = user2;
    this.messages = new ArrayList<>();
  }

  /**
   * Returns the username of user1
   *
   * @return username of user1
   */
  public String getUser1() {
    return this.user1;
  }

  /**
   * Returns the username of user2
   *
   * @return username of user2
   */
  public String getUser2() {
    return this.user2;
  }

  /**
   * Adds the given message to this Conversation
   *
   * @param messageDict message to add to the Conversation
   */
  public void addMessage(HashMap<String, String> messageDict) {
    messages.add(messageDict);
  }

  /**
   * Returns all the messages stored in this Conversation
   *
   * @return messages stored in the Conversation
   */
  public ArrayList<HashMap<String, String>> getMessages() {
    return this.messages;
  }
}
