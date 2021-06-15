package usecases;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import entities.*;

/**
 * Represents the system responsible for managing conversations in the program.
 * @author Sijia Song, Kara Han
 */
public class ConversationManager implements Serializable {

  private ArrayList<Conversation> conversations;

  /**
   * Creates a ConversationManager with a list of Conversation that is empty
   */
  public ConversationManager() {
    this.conversations = new ArrayList<>();
  }

  public Conversation startConversation(String user1, String user2) {
    Conversation newConversation = new Conversation(user1, user2);
    conversations.add(newConversation);
    return newConversation;
  }

  public Conversation getConvo(String user1, String user2) {
    for (Conversation conversation : conversations) {
      if ((conversation.getUser1().equalsIgnoreCase(user1) && conversation.getUser2().equalsIgnoreCase(user2)) |
        (conversation.getUser1().equalsIgnoreCase(user2) && conversation.getUser2().equalsIgnoreCase(user1))) {
        return conversation;
      }
    }
    return startConversation(user1, user2);
  }

  /**
   * Returns all the messages in a conversation between two given users
   * @param user1 the username of user1 in the conversation
   * @param user2 the username of user2 in the conversation
   * @return messages between user1 and user2
   */
  public ArrayList<HashMap<String, String>> getMessages(String user1, String user2) {
    Conversation convo = getConvo(user1, user2);
    return convo.getMessages();
  }

  private String getReceiver(String sender, String receiver, Conversation conversation) {
    //  Sets our user-inputted receiver to the correct case.
    if (conversation.getUser1().equalsIgnoreCase(receiver)) {
      receiver = conversation.getUser1();
    } else {
      receiver = conversation.getUser2();
    }
    return receiver;
  }

  /**
   * Sends a message from the sender to the receiver
   * @param sender the sender username of the message
   * @param receiver the receiver username of the message
   * @param message the content of the message
   */
  public void sendMessage(String sender, String receiver, String message) {
    Conversation conversation = getConvo(sender, receiver);

    receiver = getReceiver(sender, receiver, conversation);

    HashMap<String, String> messageDict = new HashMap<>();
    messageDict.put("sender", sender);
    messageDict.put("receiver", receiver);
    messageDict.put("content", message);

    SimpleDateFormat formatter = new SimpleDateFormat(
      "yyyy-MM-dd 'at' HH:mm:ss z"
    );
    Date date = new Date(System.currentTimeMillis());
    messageDict.put("time", formatter.format(date));

    conversation.addMessage(messageDict);
  }

  /**
   * Sends a message from the sender to multiple receivers
   * @param sender the sender username of the message
   * @param listReceivers the usernames of the receivers of the message
   * @param message the content of the message
   */
  public void sendMassMessage(String sender, ArrayList<String> listReceivers, String message) {
    for (String receiver : listReceivers) {
      this.sendMessage(sender, receiver, message);
    }
  }
}
