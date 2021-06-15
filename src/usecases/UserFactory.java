package usecases;
import java.io.Serializable;
import entities.*;

/**
 * The UserFactory is a factory method that creates corresponding User object according to user input
 */
public class UserFactory implements Serializable {

  /**
   * Returns a corresponding User object to different user type.
   * @param userType the type of User to be created
   * @param username username for the user
   * @param password password for the user
   * @return a User object if the input userType is valid, and null otherwise
   */
  public User getUser(String userType, String username, String password) {
    if (userType.equalsIgnoreCase("Attendee")) {
      return new Attendee(username, password);
    } else if (userType.equalsIgnoreCase("Organizer")) {
      return new Organizer(username, password);
    } else if (userType.equalsIgnoreCase("Speaker")) {
      return new Speaker(username, password);
    } else if(userType.equalsIgnoreCase("VIP")){
      User user = new Attendee(username, password);
      user.changeVIPStatus(true);
      return user;
    } else {
      return null;
    }
  }
}
