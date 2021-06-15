package controllers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import usecases.*;
import presenters.*;
import entities.*;


/**
 * Controls the entire messaging system in the program.
 * @author Sijia Song, Kara Han
 */
public class MessagingSystem {

  ConversationManager cm;
  UserManager um;
  MessagePresenter presenter;
  EventManager em;

  public MessagingSystem(ConversationManager cm, UserManager um, MessagePresenter presenter, EventManager em) {
    this.cm = cm;
    this.um = um;
    this.presenter = presenter;
    this.em = em;
  }

  /**
   * Prompts the Attendee with different menu options for messaging related actions.
   */
  public void runAttendee() {
    Scanner messageInput = new Scanner(System.in);
    String input;

    while (true) {
      presenter.displayMenu("messagingSystemAttendee");
      input = messageInput.nextLine();
      if (input.equals("1")) {
        viewAllConversations();
      } else if (input.equals("2")) {
        sendMessage();
      } else if (input.equals("3")) {
        viewFavoriteContacts();
      } else if (input.equals("4")) {
        addFavoriteContacts();
      } else if (input.equals("5")) {
        break;
      }
    }
  }

  /**
   * Prompts the Organizer with different menu options for messaging related actions.
   */
  public void runOrganizer() {
    Scanner messageInput = new Scanner(System.in);
    String input;

    while (true) {
      presenter.displayMenu("messagingSystemOrganizer");
      input = messageInput.nextLine();
      if (input.equals("1")) {
        viewAllConversations();
      } else if (input.equals("2")) {
        sendMessage();
      } else if (input.equals("3")) {
        eventAnnouncement();
      } else if (input.equals("4")) {
        organizerAnnouncement(true, false);
      } else if (input.equals("5")) {
        organizerAnnouncement(false, true);
      } else if (input.equals("6")) {
        organizerAnnouncement(true, true);
      } else if (input.equals("7")) {
        viewFavoriteContacts();
      } else if (input.equals("8")) {
        addFavoriteContacts();
      } else if (input.equals("9")) {
        break;
      }
    }
  }

  /**
   * Prompts the Speaker with different menu options for messaging related actions.
   */
  public void runSpeaker() {
    Scanner messageInput = new Scanner(System.in);
    String input;

    while (true) {
      presenter.displayMenu("messagingSystemSpeaker");
      input = messageInput.nextLine();
      if (input.equals("1")) {
        viewAllConversations();
      }
      else if (input.equals("2")) {
        sendMessage();
      }
      else if (input.equals("3")) {
        eventAnnouncement();
      }
      else if (input.equals("4")) {
        viewFavoriteContacts();
      }
      else if (input.equals("5")) {
        addFavoriteContacts();
      }
      else if (input.equals("6")) {
        break;
      }
    }
  }

  private ArrayList<String> getReadStatus(ArrayList<String> convos) {
    ArrayList<String> readStatus = new ArrayList<>();
    for (String c : convos) {
      if (um.getCurrUser().getReadHistory().contains(c)) {
        readStatus.add("Read");
      }
      else {
        readStatus.add("Unread");
      }
    }
    return readStatus;
  }

  public void viewAllConversations() {
    ArrayList<String> convos = um.getCurrUser().getConvos();
    ArrayList<String> readStatus = getReadStatus(convos);
    presenter.formatConvoList(convos, readStatus);
    Scanner messageInput = new Scanner(System.in);

    while(true) {
      presenter.displaySubMenu();
      String input = messageInput.nextLine();
      if (input.equals("1")) {
        viewConversation();
      }
      else if (input.equals("2")) {
        markAsUnread();
      }
      else if (input.equals("3")) {
        markAsRead();
      }
      else if (input.equals("4")) {
        deleteConvo();
      }
      else if (input.equals("5")) {
        addToArchive();
      }
      else if (input.equals("6")) {
        undoArchive();
      }
      else if (input.equals("7")) {
        viewArchive();
      }
      else if (input.equals("8")) {
        break;
      }
    }
  }

  private void showMessage(String user1, String user2) {
    ArrayList<HashMap<String, String>> messages = cm.getMessages(user1, user2);
    presenter.formatConvo(messages);
  }

  /**
   * View the conversation between the current user and another user.
   * The conversation will be automatically marked as "Read" for the current user once it's viewed by the user.
   */
  public void viewConversation() {
    boolean userExists = false;

    while (!userExists) {
      Scanner messageInput = new Scanner(System.in);
      presenter.displayResponses("inputReceiverUserName");
      presenter.displayResponses("returnToPreviousPage");
      String inputUserName = messageInput.nextLine();

      if (checkReturnToPreviousPage(inputUserName)) {
        return;
      }
      else if (!um.existingUser(inputUserName)) {
        presenter.displayResponses("userNotExist");
      } else {
        userExists = true;
        showMessage(um.getUserNameOfCurrentUser(), inputUserName);
        um.changeToRead(um.getUserNameOfCurrentUser(), inputUserName);
        }
      }
    returnToPreviousPage();
  }


  /**
   * Sends a message from the current user to another user.
   * The conversation will be automatically marked as "Unread" for the other user once the message is sent from the
   * current user.
   */
  public void sendMessage() {
    boolean userExists = false;

    while(!userExists) {
      Scanner messageInput = new Scanner(System.in);
      presenter.displayResponses("inputReceiverUserName");
      presenter.displayResponses("returnToPreviousPage");
      String inputUserName = messageInput.nextLine();

      if (checkReturnToPreviousPage(inputUserName)) {
        return;
      }
      else if (!um.existingUser(inputUserName)) {
        presenter.displayResponses("userNotExist");
    } else {
        userExists = true;
        presenter.displayResponses("inputMessage");
        String inputMessage = messageInput.nextLine();
        cm.sendMessage(um.getUserNameOfCurrentUser(), inputUserName, inputMessage);
        presenter.displayResponses("messageSent");
        um.changeToUnread(inputUserName, um.getUserNameOfCurrentUser());
        if (!um.existingConvo(um.getUserNameOfCurrentUser(), inputUserName)) {
          um.addConvo(um.getUserNameOfCurrentUser(), inputUserName);
        }
      }
    }
    returnToPreviousPage();
  }

  /**
   * Sends a message from the current user to all Attendees of a given event.
   * The current user has to be either an Organizer or a Speaker.
   */
  public void eventAnnouncement() {
    Scanner messageInput = new Scanner(System.in);
    String inputEventID;
    String inputMessage;

    presenter.displayResponses("inputIdentifier");
    inputEventID = messageInput.nextLine();

    presenter.displayResponses("inputMessage");
    inputMessage = messageInput.nextLine();

    ArrayList<String> eventAttendees = em.getUsernamesEvent(inputEventID);
    cm.sendMassMessage(um.getUserNameOfCurrentUser(), eventAttendees, inputMessage);
    presenter.displayResponses("messageSent");
    for (String attendee : eventAttendees) {
      um.changeToUnread(attendee, um.getUserNameOfCurrentUser());
    }
  }

  /**
   * Sends a message from the current user to all Attendees registered in the conference, or all Speakers registered
   * in the conference, or both.
   * The current user has to be an Organizer.
   * @param isAllAttendees decides if the message will be sent to all Attendees
   * @param isAllSpeakers decides if the message will be sent to all Speakers
   */
  public void organizerAnnouncement(
    boolean isAllAttendees,
    boolean isAllSpeakers
  ) {
    Scanner messageInput = new Scanner(System.in);
    String inputMessage;

    presenter.displayResponses("inputMessage");
    inputMessage = messageInput.nextLine();

    ArrayList<String> receivers = new ArrayList<>();
    if (isAllAttendees) {
      for (String username : um.getAllAttendeeUserNames()) {
        if (!receivers.contains(username)) {
          receivers.add(username);
        }
      }
    }
    if (isAllSpeakers) {
      for (String username : um.getAllSpeakerUserNames()) {
        if (!receivers.contains(username)) {
          receivers.add(username);
        }
      }
    }
    cm.sendMassMessage(um.getUserNameOfCurrentUser(), receivers, inputMessage);
    presenter.displayResponses("messageSent");
    for (String receiver : receivers) {
      um.changeToUnread(receiver, um.getUserNameOfCurrentUser());
    }
  }

  public void deleteConvo() {
    boolean userExists = false;
    while (!userExists) {
      Scanner messageInput = new Scanner(System.in);
      presenter.displayResponses("inputUserNameDelete");
      presenter.displayResponses("returnToPreviousPage");
      String inputUserName = messageInput.nextLine();

      if (checkReturnToPreviousPage(inputUserName)) {
        return;
      }
      else if (!um.existingUser(inputUserName)) {
        presenter.displayResponses("userNotExist");
      }
      else {
        userExists = true;
        if (um.existingConvo(um.getUserNameOfCurrentUser(), inputUserName)) {
          um.removeConvo(um.getUserNameOfCurrentUser(), inputUserName);
          presenter.displayResponses("convoDeleted");
        }
        else {
          presenter.displayResponses("convoNotDeleted");
        }
      }
    }
    returnToPreviousPage();
  }

  /**
   * Obtains the current user's favorite contacts and prints it to the screen.
   */
  public void viewFavoriteContacts() {
    User current = um.getCurrUser();
    ArrayList<String> contacts = current.getContact();
    presenter.formatUserList(contacts);
  }

  /**
   * Add a given user to a current user's favorite contacts.
   */
  public boolean addFavoriteContacts() {
    Scanner messageInput = new Scanner(System.in);
    String inputUserToAdd;

    presenter.displayResponses("inputUserToAdd");
    inputUserToAdd = messageInput.nextLine();
    if (!um.existingUser(inputUserToAdd)) {
      presenter.displayResponses("contactNotAdded");
      return false;
    }
    if (um.addContact(inputUserToAdd)) {
      presenter.displayResponses("contactAdded");
      return true;
    } else {
      presenter.displayResponses("contactNotAdded");
      return false;
    }
  }

  public void markAsUnread() {
    boolean userExists = false;

    while (!userExists) {
      Scanner messageInput = new Scanner(System.in);
      presenter.displayResponses("inputUserNameUnread");
      presenter.displayResponses("returnToPreviousPage");
      String inputUserName = messageInput.nextLine();

      if (checkReturnToPreviousPage(inputUserName)) {
        return;
      }

      else if (!um.existingUser(inputUserName)) {
        presenter.displayResponses("userNotExist");
      } else {
        userExists = true;
        if (um.existingConvo(um.getUserNameOfCurrentUser(), inputUserName)) {
          um.changeToUnread(um.getUserNameOfCurrentUser(), inputUserName);
          presenter.displayResponses("changedToUnread");
        }
        else {
          presenter.displayResponses("convoNotExist");
        }
      }
    }
  returnToPreviousPage();
  }

  public void markAsRead() {
    boolean userExists = false;

    while (!userExists) {
      Scanner messageInput = new Scanner(System.in);
      presenter.displayResponses("inputUserNameRead");
      presenter.displayResponses("returnToPreviousPage");
      String inputUserName = messageInput.nextLine();

      if (checkReturnToPreviousPage(inputUserName)) {
        return;
      }

      else if (!um.existingUser(inputUserName)) {
        presenter.displayResponses("userNotExist");
      } else {
        userExists = true;
        if (um.existingConvo(um.getUserNameOfCurrentUser(), inputUserName)) {
          um.changeToRead(um.getUserNameOfCurrentUser(), inputUserName);
          presenter.displayResponses("changedToRead");
        }
        else {
          presenter.displayResponses("convoNotExist");
        }
      }
    }
    returnToPreviousPage();
  }

  public void addToArchive() {
    boolean userExists = false;

    while (!userExists) {
      Scanner messageInput = new Scanner(System.in);
      presenter.displayResponses("inputUserNameArchive");
      presenter.displayResponses("returnToPreviousPage");
      String inputUserName = messageInput.nextLine();

      if (checkReturnToPreviousPage(inputUserName)) {
        return;
      }

      else if (!um.existingUser(inputUserName)) {
        presenter.displayResponses("userNotExist");
      } else {
        userExists = true;
        if (um.existingConvo(um.getUserNameOfCurrentUser(), inputUserName)) {
          um.Archive(um.getUserNameOfCurrentUser(), inputUserName);
          presenter.displayResponses("archiveAdded");
        }
        else {
          presenter.displayResponses("convoNotExist");
        }
      }
    }
    returnToPreviousPage();
  }

  public void undoArchive() {
    boolean userExists = false;
    while (!userExists) {
      Scanner messageInput = new Scanner(System.in);
      presenter.displayResponses("inputUserNameUndoArchive");
      presenter.displayResponses("returnToPreviousPage");
      String inputUserName = messageInput.nextLine();

      if (checkReturnToPreviousPage(inputUserName)) {
        return;
      }

      else if (!um.existingUser(inputUserName)) {
        presenter.displayResponses("userNotExist");
      } else {
        userExists = true;
        if (um.existingConvo(um.getUserNameOfCurrentUser(), inputUserName)) {
          um.undoArchive(um.getUserNameOfCurrentUser(), inputUserName);
          presenter.displayResponses("archiveRemoved");
        }
        else {
          presenter.displayResponses("convoNotExist");
        }
      }
    }
    returnToPreviousPage();
  }

  public void viewArchive() {
    ArrayList<String> archivedConvos = um.getCurrUser().getArchivedConvos();
    ArrayList<String> readStatus = getReadStatus(archivedConvos);
    presenter.formatConvoList(archivedConvos, readStatus);
    presenter.displayResponses("viewConvo");
    viewConversation();
  }

  private void returnToPreviousPage() {
    presenter.displayResponses("returnToPreviousPage2");
    try {
      System.in.read();
    } catch (Exception ex) {}
  }

  public boolean checkReturnToPreviousPage(String input) {
    if (input.equals("")){
      return true;
    }
    return false;
  }
}
