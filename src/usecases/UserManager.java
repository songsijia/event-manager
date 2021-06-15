package usecases;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import entities.*;

public class UserManager implements Serializable {

  private ArrayList<User> users;
  private UserFactory ufactory;
  private User currUser;

  /**
   * Instantiate a UserManager object.
   */
  public UserManager() {
    this.users = new ArrayList<>();
    this.ufactory = new UserFactory();
    this.currUser = null;
  }

  /**
   * Returns a specific User object according to the given username.
   *
   * @param username the username of the user
   * @return a User object with the given username
   */
  public User getSpecificUser(String username) {
    for (User u : users) {
      if (u.getName().equalsIgnoreCase(username)) {
        return u;
      }
    }
    return null;
  }

  /**
   * Returns a String representing the user type of the given user
   *
   * @param username the username of the given user
   * @return the user type of this user
   */
  public String getSpecificUserType(String username) {
    return getSpecificUser(username).getUserType();
  }

  /**
   * Returns the String representation of current user's name.
   *
   * @return the current user's name
   */
  public String getUserNameOfCurrentUser() {
    return currUser.getName();
  }

  /**
   * Returns a User object representing the current user of the system.
   *
   * @return currUser
   */
  public User getCurrUser() {
    return this.currUser;
  }

  /**
   * Returns whether the user is successfully signed-up for the system
   *
   * @param usertype the type of user to be signed-up as
   * @param username username for the user
   * @param password password for the user
   * @return true if a user is successfully signed up for the system, and false otherwise.
   */
  public boolean signUpUser(String usertype, String username, String password) {
    User newUser = ufactory.getUser(usertype, username, password);

    if (newUser != null) {
      users.add(newUser);
      return true;
    }

    return false;
  }

  /**
   * Returns whether the user is successfully logged-in for the system
   *
   * @param username the username of the user
   * @param password the password of the user
   * @return true if the user is successfully logged-in to the system, and false otherwise.
   */
  public boolean loginUser(String username, String password) {
    for (User u : users) {
      if (
        u.getName().equalsIgnoreCase(username) &&
        u.getPassword().equals(password)
      ) {
        this.currUser = getSpecificUser(username);
        return true;
      }
    }
    return false;
  }

  /**
   * Returns whether the user is successfully logged-out for the system
   *
   * @return true if the user is successfully logged-out, and false otherwise.
   */
  public boolean logOut() {
    if (currUser != null) {
      this.currUser = null;
      return true;
    } else {
      return false;
    }
  }

  /**
   * Returns whether the user already exists in the system
   *
   * @param username the username of the user
   */
  public boolean existingUser(String username) {
    for (User u : users) {
      if (u.getName().equalsIgnoreCase(username)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns arraylist of all Attendees' usernames in the whole system.
   *
   * @return list of usernames of the Attendees in the system
   */
  public ArrayList<String> getAllAttendeeUserNames() {
    ArrayList<String> result = new ArrayList<>();
    for (User user : users) {
      if (user.getUserType().equalsIgnoreCase("Attendee")) {
        result.add(user.getName());
      }
    }
    return result;
  }

  /**
   * Returns arraylist of all Speakers' usernames in the whole system.
   *
   * @return list of usernames of the Speakers in the system
   */
  public ArrayList<String> getAllSpeakerUserNames() {
    ArrayList<String> result = new ArrayList<>();
    for (User user : users) {
      if (user.getUserType().equalsIgnoreCase("Speaker")) {
        result.add(user.getName());
      }
    }
    return result;
  }

  /**
   * Return true if the contact is successfully added to the current user's contact list.
   *
   * @return true after successfully adding a user to the current user's contact list.
   */
  public boolean addContact(String contact) {
    return currUser.addContact(contact);
  }

  /**
   * Change the VIP status for a user.
   * change the status to true to indicate the user is a VIP user, and false otherwise.
   */
  public void changeVIPStatus(String username, boolean status) {
    User user = getSpecificUser(username);
    user.changeVIPStatus(status);
  }

  public void potentialVIP(String username, int numberOfEvents){
    if(numberOfEvents>=5){
      changeVIPStatus(username, true);
    }
  }

  /**
   * Return the VIP status of a user.
   *
   * @return true if the user is VIP, and false otherwise.
   */
  public boolean getVIPStatus(String username) {
    User user = getSpecificUser(username);
    return user.getVIPStatus();
  }

  /**
   * Change the corresponding message of the user to status "read"
   */
  public void changeToRead(String user1, String user2) { getSpecificUser(user1).getReadHistory().add(user2); }

  /**
   * Change the corresponding message of the user to status "unread"
   */
  public void changeToUnread(String user1, String user2) { getSpecificUser(user1).getReadHistory().remove(user2); }

  public void Archive(String user1, String user2) { getSpecificUser(user1).getArchivedConvos().add(user2); }

  public void undoArchive(String user1, String user2) { getSpecificUser(user1).getArchivedConvos().remove(user2); }

  public boolean existingConvo(String user1, String user2) {
    User u = getSpecificUser(user1);
    if ( u.getConvos().contains(user2)) {
      return true;
    }
    return false;
  }

  public void addConvo(String user1, String user2) { getSpecificUser(user1).getConvos().add(user2); }

  public void removeConvo(String user1, String user2) { getSpecificUser(user1).getConvos().remove(user2); }
}
