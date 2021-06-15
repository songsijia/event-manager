package presenters;
import java.util.ArrayList;
import java.util.HashMap;

public class MessagePresenter {

  private HashMap<String, ArrayList<String>> menus = new HashMap<String, ArrayList<String>>();
  private HashMap<String, String> responses = new HashMap<String, String>();

  public MessagePresenter() {
    // Construct our menus
    ArrayList<String> messagingSystemAttendee = new ArrayList<String>();
    messagingSystemAttendee.add("View your conversations.");
    messagingSystemAttendee.add("Send a message to another user.");
    messagingSystemAttendee.add("View my favorite contacts.");
    messagingSystemAttendee.add("Add a user to my favorite contacts.");
    messagingSystemAttendee.add("Return to Main Menu");

    ArrayList<String> messagingSystemOrganizer = new ArrayList<String>();
    messagingSystemOrganizer.add("View your conversations.");
    messagingSystemOrganizer.add("Send a message to another user.");
    messagingSystemOrganizer.add(
      "Send a message to all attendees attending an event."
    );
    messagingSystemOrganizer.add(
      "Send a message to all attendees registered for the conference."
    );
    messagingSystemOrganizer.add(
      "Send a message to all speakers registered for the conference."
    );
    messagingSystemOrganizer.add(
      "Send a message to all attendees AND speakers registered for the conference."
    );
    messagingSystemOrganizer.add("View my favorite contacts.");
    messagingSystemOrganizer.add("Add a user to my favorite contacts.");
    messagingSystemOrganizer.add("Return to Main Menu");

    ArrayList<String> messagingSystemSpeaker = new ArrayList<String>();
    messagingSystemSpeaker.add("View your conversations.");
    messagingSystemSpeaker.add("Send a message to another user.");
    messagingSystemSpeaker.add(
      "Send a message to all attendees attending my event(s)."
    );
    messagingSystemSpeaker.add("View my favorite contacts.");
    messagingSystemSpeaker.add("Add a user to my favorite contacts.");
    messagingSystemSpeaker.add("Return to Main Menu");

    this.menus.put("messagingSystemAttendee", messagingSystemAttendee);
    this.menus.put("messagingSystemOrganizer", messagingSystemOrganizer);
    this.menus.put("messagingSystemSpeaker", messagingSystemSpeaker);

    //construct responses;
    this.responses.put("inputReceiverUserName", "Enter the username:");
    this.responses.put("inputMessage", "Enter the message:");
    this.responses.put(
        "inputIdentifier",
        "Please input the identifier of the event:"
      );
    this.responses.put(
        "inputUserToAdd",
        "Please input the username you would like to add to your favorite contacts:"
      );
    this.responses.put(
        "contactAdded",
        "The user is successfully added to your favorite contacts."
      );
    this.responses.put(
        "contactNotAdded",
        "Add favorites failed. User might already be in your favorite contacts, or user might " +
        "not exist."
      );
    this.responses.put(
        "returnToPreviousMenu",
        "You can return to the previous menu by clicking Enter."
      );
    this.responses.put(
        "errorReadingConversation",
        "No file found or error reading from file! Creating new ConversationManager."
      );
    this.responses.put(
        "errorWritingConversation",
        "Error writing to file for Conversation related objects!."
      );
    this.responses.put("inputUserNameUnread", "Please input the username you would like to mark the conversation as Unread with:");
    this.responses.put("inputUserNameRead", "Please input the username you would like to mark the conversation as Read with:");
    this.responses.put("inputUserNameDelete", "Please enter the username you would like to delete the conversation with:");
    this.responses.put("convoDeleted", "The conversation has been successfully deleted from your conversations.");
    this.responses.put("convoNotDeleted", "Delete conversation failed. You might not have a conversation with the user, "
    + "or user might not exist.");
    this.responses.put("inputUserNameArchive", "Please enter the username you would like to archive the conversation with:");
    this.responses.put("inputUserNameUndoArchive", "Please enter the username you would like to undo the conversation archive with:");
    this.responses.put("userNotExist", "Sorry, this user does not exist in our system. Please enter another username.");
    this.responses.put("viewConvo", "Please enter the username below if you would like to view a conversation.");
    this.responses.put("returnToPreviousPage", "or press the Enter key to return to the previous page.");
    this.responses.put("returnToPreviousPage2", "Press the Enter key to return to the previous page.");
    this.responses.put("messageSent", "Message sent!");
    this.responses.put("changedToUnread", "Successfully changed to Unread!");
    this.responses.put("changedToRead", "Successfully changed to Read!");
    this.responses.put("convoNotExist", "You do not have a conversation with this user.");
    this.responses.put("archiveAdded", "Conversation successfully archived!");
    this.responses.put("archiveRemoved", "Undo archive succeeded!");
  }

  public void formatConvo(ArrayList<HashMap<String, String>> messages) {
    for (HashMap<String, String> m : messages) {
      System.out.println("Message #: " + messages.indexOf(m));
      System.out.println("From: " + m.get("sender"));
      System.out.println("To: " + m.get("receiver"));
      System.out.println("Date: " + m.get("time"));
      System.out.println("Message: " + m.get("content") + "\n");
    }
  }

  public void formatUserList(ArrayList<String> list) {
    int i = 0;
    for (String u : list) {
      i++;
      System.out.println("[" + i + "] " + (u));
    }
    System.out.println(i+" contacts.");
  }

  public void displayMenu(String menu) {
    ArrayList<String> options = this.menus.get(menu);
    System.out.println("\nWelcome to the Message Management Page!");
    for (int i = 0; i < options.size(); i++) {
      int j = i + 1;
      System.out.println(j + ". " + options.get(i));
    }
    System.out.print("Please input a valid number option you wish to select:");
  }

  public void displayResponses(String response) {
    String res = this.responses.get(response);
    System.out.println(res);
  }

  public void formatConvoList(ArrayList<String> convos, ArrayList<String> readStatus) {
    System.out.println("***My conversations***");
    int i = 0;
    for (String c : convos) {
      System.out.println("- " + c + " " + "(" + readStatus.get(i) + ")");
      i++;
    }
  }

  public void displaySubMenu() {
    System.out.println("\nPlease choose from one of the options:");
    System.out.println("1. View your conversation with another user.");
    System.out.println("2. Mark a conversation as Unread.");
    System.out.println("3. Mark a conversation as Read.");
    System.out.println("4. Delete your conversation with another user.");
    System.out.println("5. Archive a conversation.");
    System.out.println("6. Undo conversation archive.");
    System.out.println("7. View your archived conversations.");
    System.out.println("8. Return to previous page.");
    System.out.print("Please input a valid number option you wish to select:");
  }
}
