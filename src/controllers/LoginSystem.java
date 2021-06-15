package controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import usecases.*;
import presenters.*;

public class LoginSystem {

  private UserManager userManager;
  private LoginPresenter p;
  private RequestManager rm;

  public LoginSystem(
          UserManager userManager,
          LoginPresenter p,
          RequestManager rm
  ) {
    this.userManager = userManager;
    this.p = p;
    this.rm = rm;
  }

  /**
   * This method displays menu options for the user and let them choose to sign-up for/log-in to/quit the program;
   *
   * @return String of usertype if the user is successfully logged-in to the program
   */
  public String run() {
    Scanner userInput = new Scanner(System.in);

    while (true) {
      p.displayMenu("signUpSystem");
      String input = userInput.nextLine();
      if (input.equals("1")) {
        signUp();
      } else if (input.equals("2")) {
        String usertype = logIn();
        if (!usertype.equalsIgnoreCase("Null")) {
          return usertype;
        }
      } else if (input.equals("3")) {
        return "Null";
      } else {
        p.displayResponse("invalidOption");
      }
    }
  }

  /**
   * This method displays available options to the Attendee after signed-in to the program and allows the user
   * to select from the options;
   * Available options include: change the password; log-out of the program; quit the program.
   */
  public String runAttendee() {
    Scanner asInput = new Scanner(System.in);
    while (true) {
      p.displayMenu("loginSystemAttendee");
      String choice = asInput.nextLine();
      if (choice.equals("1")) {
        changePassword();
      } else if (choice.equals("2")) {
        logOut();
        return "logOut";
      } else if (choice.equals("3")) {
        sendRequest();
      } else if (choice.equals("4")) {
        break;
      } else {
        p.displayResponse("invalidOption");
      }
    }
    return "Null";
  }

  /**
   * This method displays available options to the Speaker after signed-in to the program and allows the user
   * to select from the options;
   * Available options include: change the password; log-out of the program; quit the program.
   */
  public String runSpeaker() {
    Scanner asInput = new Scanner(System.in);
    while (true) {
      p.displayMenu("loginSystemSpeaker");
      String choice = asInput.nextLine();
      if (choice.equals("1")) {
        changePassword();
      } else if (choice.equals("2")) {
        logOut();
        return "logOut";
      } else if (choice.equals("3")) {
        break;
      } else {
        p.displayResponse("invalidOption");
      }
    }
    return "Null";
  }

  /**
   * This method displays available options to the Organizer after an organizer is signed-in to the program and allows
   * the user to select from the options;
   * Available options include: create an account for a speaker; log-out of the program; view the pending list of request;
   * view the addressed list of the request; quit the program.
   */
  public String runOrganizer() {
    Scanner organizerInput = new Scanner(System.in);
    while (true) {
      p.displayMenu("loginSystemOrganizer");
      String choice = organizerInput.nextLine();
      if (choice.equals("1")) {
        signUp();
      } else if (choice.equals("2")) {
        changePassword();
      } else if (choice.equals("3")) {
        logOut();
        return "logOut";
      } else if (choice.equals("4")) {
        viewPendingRequest();
      } else if (choice.equals("5")) {
        viewAddressedRequest();
      } else if (choice.equals("6")) {
        break;
      } else {
        p.displayResponse("invalidOption");
      }
    }
    return "Null";
  }

  /**
   * Signs up a new user as an Attendee or Organizer to the system if the user inputs valid user type and is
   * not an existing user in the program.
   */
  public void signUp() {
    Scanner signUpInput = new Scanner(System.in);
    String userVersion;
    while (true) {
      if (userManager.getCurrUser() == null) {
        p.displayResponse("chooseTypeOfUser");
        userVersion = "normal";
      } else {
        p.displayResponse("chooseTypeOfUsers");
        userVersion = "organizer";
      }
      String typeUser = signUpInput.nextLine();
      if (returnLastLevel(typeUser)) {
        break;
      } else if (checkTypeValidity(typeUser, userVersion)) {
        signUpOtherUser(typeUser);
        break;
      } else {
        p.displayResponse("invalidUserType");
      }
    }
  }

  /**
   * This method runs after an organizer chooses to create an account another user(attendee/organizer/speaker).
   * It checks whether the user just presses Enter key without typing in any character as username or password.
   */
  public void signUpOtherUser(String typeUser) {
    Scanner signUpInput = new Scanner(System.in);
    while (true) {
      p.displayResponse("inputUserName");
      String username = signUpInput.nextLine();
      if (returnLastLevel(username)) {
        break;
      } else if (userManager.existingUser(username)) {
        p.displayResponse("nameExist");
        break;
      } else if (username.isEmpty()) {
        p.displayResponse("emptyUsername");
      } else {
        while (true) {
          p.displayResponse("inputPassword");
          String password = signUpInput.nextLine();
          if (returnLastLevel(password)) {
            break;
          } else if (password.isEmpty()) {
            p.displayResponse("emptyPassword");
          } else if (userManager.signUpUser(typeUser, username, password)) {
            p.displayResponse("createAccount");
            break;
          } else {
            p.displayResponse("failToCreateAccount");
            break;
          }
        }
        break;
      }
    }
  }

  /**
   * Returns a String representation of the usr type of the user if the user successfully logged-in to the program,
   * and returns "Null" if log-in process is not successful.
   * This method would check whether the user already exists in the system, and checks
   * whether the username and password match for this user.
   *
   * @return a String representing the type of the user(Attendee/Organizer/Speaker) if the user is successfully
   * logged-in to the program.
   */
  public String logIn() {
    Scanner logInInput = new Scanner(System.in);

    p.displayResponse("inputUserName");
    String username = logInInput.nextLine();

    if (!userManager.existingUser(username)) {
      p.displayResponse("nameNotExist");
      return "Null";
    }
    String usertype = userManager.getSpecificUserType(username);

    p.displayResponse("inputPassword");
    String password = logInInput.nextLine();

    if (userManager.loginUser(username, password)) {
      p.displayResponse("login");
        return usertype;
      } else {
        p.displayResponse("failToLogin");
        return "Null";
      }
    }

    /**
     * Changes password for the user if user correctly types out the old password then enters the new one.
     */
    public void changePassword () {
      Scanner input = new Scanner(System.in);
      while (true) {
        p.displayResponse("typeOldPassword");
        String oldPass = input.nextLine();
        if (returnLastLevel(oldPass)) {
          break;
        } else if (userManager.getCurrUser().getPassword().equals(oldPass)) {
          p.displayResponse("typeNewPassword");
          String newPass = input.nextLine();
          if (returnLastLevel(newPass)) {
            break;
          } else {
            userManager.getCurrUser().setPassword(newPass);
            p.displayResponse("successfullyChangedPassword");
            break;
          }
        } else {
          p.displayResponse("incorrectOldPassword");
        }
      }
    }

    /**
     * Logs a user out of the program.
     */
    public void logOut () {
      if (userManager.logOut()) {
        p.displayResponse("logOut");
      } else {
        p.displayResponse("failedToLogOut");
      }
    }

    /**
     * Allow the Organizer to view a list of Pending Requests and change the status of certain
     * request if needed.
     */
    public void viewPendingRequest () {
      p.displayResponse("listOfPending");
      System.out.println(rm.viewAllPendingRequest());
      p.displayMenu("changePendingRequestStatus");
      while (true) {
        Scanner input = new Scanner(System.in);
        String num = input.nextLine();
        if (num.equals("1")) {
          addressRequest();
        } else {
          break;
        }
      }
    }

    /**
     * Allow the Organizer to view a list of Addressed Requests.
     */
    public void viewAddressedRequest () {
      p.displayResponse("listOfAddressed");
      System.out.println(rm.viewAllAddressedRequest());
      p.displayMenu("changeAddressedRequestStatus");
      while (true) {
        Scanner input = new Scanner(System.in);
        String num = input.nextLine();
        if (num.equals("1")) {
          pendRequest();
        } else {
          break;
        }
      }
    }

    /**
     * Allow Attendee to send a pending request.
     * Note: If user input is empty string, then the request will NOT be sent and the program will
     * return to the previous menu.
     */
    public void sendRequest () {
      String username = userManager.getUserNameOfCurrentUser();
      Scanner input = new Scanner(System.in);
      p.displayResponse("inputRequest");
      String content = input.nextLine();
      rm.addRequest(username, content);
      p.displayResponse("requestSent");
    }

    /**
     * Allow the Organizer to address a pending request.
     */
    public void addressRequest () {
      Scanner input = new Scanner(System.in);
      p.displayResponse("chooseRequest");
      int identifier = input.nextInt();
      if (rm.addressRequest(identifier)) {
        p.displayResponse("requestAddressed");
      } else {
        p.displayResponse("requestFailedToChangeStatus");
      }
    }

    /**
     * Allow the Organizer to change an addressed request back to pending.
     */
    public void pendRequest () {
      Scanner input = new Scanner(System.in);
      p.displayResponse("chooseRequest");
      int identifier = input.nextInt();
      if (rm.pendRequest(identifier)) {
        p.displayResponse("requestPending");
      } else {
        p.displayResponse("requestFailedToChangeStatus");
      }
    }

    private boolean checkTypeValidity (String userType, String version){
      List<String> acceptedType = new ArrayList<>();
      if (version.equalsIgnoreCase("normal")) {
        acceptedType.add("attendee");
        acceptedType.add("organizer");
      } else {
        acceptedType.add("attendee");
        acceptedType.add("organizer");
        acceptedType.add("speaker");
        acceptedType.add("vip");
      }

      for (String uType : acceptedType) {
        if (userType.equalsIgnoreCase(uType)) {
          return true;
        }
      }
      return false;
    }

    private boolean returnLastLevel (String input){
      return input.isEmpty();
    }
  }
