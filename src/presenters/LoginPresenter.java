package presenters;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginPresenter {

  private HashMap<String, ArrayList<String>> menus = new HashMap<String, ArrayList<String>>();
  private HashMap<String, String> responses = new HashMap<String, String>();

  public LoginPresenter() {
    // Construct our menus
    ArrayList<String> signUpSystem = new ArrayList<String>();
    signUpSystem.add("Sign up (Attendee/Organizer)");
    signUpSystem.add("Log in");
    signUpSystem.add("Quit the program");

    ArrayList<String> loginSystemAttendee = new ArrayList<String>();
    loginSystemAttendee.add("Change password");
    loginSystemAttendee.add("Log out");
    loginSystemAttendee.add("Send request");
    loginSystemAttendee.add("Return to main menu");

    ArrayList<String> loginSystemSpeaker = new ArrayList<String>();
    loginSystemSpeaker.add("Change password");
    loginSystemSpeaker.add("Log out");
    loginSystemSpeaker.add("Return to main menu");

    ArrayList<String> loginSystemOrganizer = new ArrayList<String>();
    loginSystemOrganizer.add(
      "Create account for an user."
    );
    loginSystemOrganizer.add("Change password");
    loginSystemOrganizer.add("Log out");
    loginSystemOrganizer.add("View the list of all pending requests");
    loginSystemOrganizer.add("View the list of all addressed requests");
    loginSystemOrganizer.add("Return to main");

    // menu for request
    ArrayList<String> changePendingRequestStatus = new ArrayList<>();
    changePendingRequestStatus.add("Address a pending request");
    changePendingRequestStatus.add("Return to main");

    ArrayList<String> changeAddressedRequestStatus = new ArrayList<>();
    changeAddressedRequestStatus.add("Pend an addressed request");
    changeAddressedRequestStatus.add("Return to main");

    this.menus.put("signUpSystem", signUpSystem);
    this.menus.put("loginSystemOrganizer", loginSystemOrganizer);
    this.menus.put("loginSystemAttendee", loginSystemAttendee);
    this.menus.put("loginSystemSpeaker", loginSystemSpeaker);
    this.menus.put("changePendingRequestStatus", changePendingRequestStatus);
    this.menus.put(
        "changeAddressedRequestStatus",
        changeAddressedRequestStatus
      );

    // Construct our responses
    this.responses.put("logOut", "You are successfully logged out.");
    this.responses.put("login", "You are successfully logged in!");
    this.responses.put(
        "failToLogin",
        "There is something wrong with your username/password."
      );
    this.responses.put("failedToLogOut", "Failed to log out.");
    this.responses.put("selectOptions", "Please select a valid option.");
    this.responses.put("invalidOption", "Input option is invalid!");
    this.responses.put(
        "emptyUsername",
        "Empty username! Please try another one."
      );
    this.responses.put(
        "emptyPassword",
        "Empty password! Please try another one."
      );
    this.responses.put(
        "chooseTypeOfUser",
        "Which type of user are you signing up for(Attendee/Organizer)?"
      );
    this.responses.put(
        "chooseTypeOfUsers",
        "Which type of user are you signing up for(Attendee/Organizer/Speaker/VIP)?"
      );
    this.responses.put("inputUserName", "Please enter the username: ");
    this.responses.put("nameNotExist", "The username does not exist.");
    this.responses.put("nameExist", "Your username already exists.");
    this.responses.put("inputPassword", "Please enter the password: ");
    this.responses.put(
        "createAccount",
        "Your account is successfully created."
      );
    this.responses.put("failToCreateAccount", "Failed to create account.");
    this.responses.put("invalidUserType", "Invalid user type!");
    this.responses.put("typeOldPassword", "Please type in your old password: ");
    this.responses.put(
        "incorrectOldPassword",
        "Incorrect password. Please try again!"
      );
    this.responses.put("typeNewPassword", "Please enter the new password");
    this.responses.put(
        "successfullyChangedPassword",
        "You successfully changed your password!"
      );
    this.responses.put(
        "returnToPreviousMenu",
        "You can return to the previous menu by clicking Enter."
      );
    this.responses.put(
        "errorReadingUser",
        "No file found or error reading from file! Creating new LoginManager."
      );
    this.responses.put(
        "errorWritingUser",
        "Error writing to file for User related objects!."
      );
    // for request
    this.responses.put(
        "listOfPending",
        "Here is the list of all Pending Requests:"
      );
    this.responses.put(
        "listOfAddressed",
        "Here is the list of all Addressed Requests:"
      );
    this.responses.put("inputRequest", "Please type in your request:");
    this.responses.put("requestSent", "Your request is successfully sent.");
    this.responses.put(
        "chooseRequest",
        "Please type in the identifier of the request that you would like to change:"
      );
    this.responses.put(
        "requestAddressed",
        "The status of this request is successfully changed to Addressed."
      );
    this.responses.put(
        "requestPending",
        "The status of this request is successfully changed to Pending."
      );
    this.responses.put(
        "requestFailedToChangeStatus",
        "There is something wrong with the identifier, please try again."
      );
    this.responses.put(
        "errorReadingObj",
        "No file found or error reading from file! Creating new "
      );
    this.responses.put("errorWritingObj", "Error writing to file ");
  }

  public void displayMenu(String menu) {
    ArrayList<String> options = this.menus.get(menu);
    System.out.println("\nWelcome to the User Management Page!");
    System.out.println(
      "You can return to the previous menu by clicking Enter."
    );
    for (int i = 0; i < options.size(); i++) {
      int j = i + 1;
      System.out.println(j + ". " + options.get(i));
    }
    System.out.print("Please input a valid number option you wish to select:");
  }

  public void displayResponse(String response) {
    String res = this.responses.get(response);
    System.out.println(res);
  }

  public void displayResponse(String response, String add) {
    String res = this.responses.get(response);
    System.out.println(res + add);
  }
}
