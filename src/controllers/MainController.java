package controllers;
import java.util.Scanner;
import presenters.*;
import gateways.*;
import usecases.*;

/**
 * The MainController class is going to control the flow of the whole conference system.
 * It stores all the Use case classes, and is responsible for the interactions
 * between each Controller classes as well.
 * */

public class MainController {

  private final MainPresenter mainPresenter = new MainPresenter();
  private final LoginPresenter loginPresenter = new LoginPresenter();
  private final EventPresenter eventPresenter = new EventPresenter();
  private final MessagePresenter messagePresenter = new MessagePresenter();

  private final FileGateway gateway = new FileGateway(
    loginPresenter,
    eventPresenter,
    messagePresenter
  );
  private final UserManager userm = (UserManager) gateway.readFromFile(
    "UserManager",
    "UserManager.ser"
  );
  private final EventManager eventm = (EventManager) gateway.readFromFile(
    "EventManager",
    "EventManager.ser"
  );
  private final ConversationManager conversationm = (ConversationManager) gateway.readFromFile(
    "ConversationManager",
    "ConversationManager.ser"
  );
  private final RequestManager requestm = (RequestManager) gateway.readFromFile(
    "RequestManager",
    "RequestManager.ser"
  );

  private final ScheduleBuilder printer = new ScheduleBuilder(eventm,gateway);

  private final LoginSystem logins = new LoginSystem(
    userm,
    loginPresenter,
    requestm
  );
  private final EventSystem events = new EventSystem(
    eventm,
    userm,
    eventPresenter,
          printer
  );
  private final MessagingSystem messagings = new MessagingSystem(
    conversationm,
    userm,
    messagePresenter,
    eventm
  );

  /**
   * The main run method. Start from the main menu options.
   * Call each sub-systems run methods after choosing from the main menu options.
   * Read in /Save all users login information or any changes they make to gateway.
   */

  public void run() {
    while (true) {
      String usertype = logins.run();
      logInHelper(usertype);
      Scanner userInput = new Scanner(System.in);
      while (true) {
        mainPresenter.chooseDifferentMenus();
        String input = userInput.nextLine();
        if (input.equalsIgnoreCase("1")) {
          showEventMenu(usertype);
        } else if (input.equalsIgnoreCase("2")) {
          String logOut;
          if (usertype.equalsIgnoreCase("Attendee")) {
            logOut = logins.runAttendee();
          } else if (usertype.equalsIgnoreCase("Speaker")) {
            logOut = logins.runSpeaker();
          } else {
            logOut = logins.runOrganizer();
          }
          if(logOutHelper(logOut)){
            break;
          }
        } else if (input.equalsIgnoreCase("3")) {
          showMessageMenus(usertype);
        } else if (input.equalsIgnoreCase("4")){
          logins.logOut();
          gateway.serSaveHelper(eventm, userm, conversationm, requestm);
          System.exit(0);
        }
      }
    }
  }

  /**
   * Helper method let users register into the system and save their information.
   * @param usertype the usertype of this registered user.
   */
  private void logInHelper(String usertype) {
    if (usertype.equalsIgnoreCase("Null")) {
      gateway.serSaveHelper(eventm, userm, conversationm, requestm);
      System.exit(0);
    }
  }

  /**
   * Helper method that let users log out the system
   * @param logOut if user inputs the logOut option
   * @return True if the user has logged out successfully, False if he/she does not
   */
  private boolean logOutHelper(String logOut) {
    if (logOut.equalsIgnoreCase("logOut")) {
      gateway.serSaveHelper(eventm, userm, conversationm, requestm);
      return true;
    }
    return false;
  }

  /**
   * Show different message options to different users.
   * @param usertype the current user type
   */
  private void showMessageMenus(String usertype) {
    if (usertype.equalsIgnoreCase("Attendee")) {
      messagings.runAttendee();
    } else if (usertype.equalsIgnoreCase("Speaker")) {
      messagings.runSpeaker();
    } else if (usertype.equalsIgnoreCase("Organizer")) {
      messagings.runOrganizer();
    }
  }

  /**
   * Show event menus to different types of users.
   * @param usertype the current usertype
   **/
  private void showEventMenu(String usertype) {
    if (usertype.equalsIgnoreCase("Attendee")) {
      events.runAttendee();
    }
    else if (usertype.equalsIgnoreCase("Organizer")) {
      events.runOrganizer();
    }
    else if (usertype.equalsIgnoreCase("Speaker")) {
      events.runSpeaker();
    }
  }
}
