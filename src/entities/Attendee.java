package entities;
import java.io.Serializable;
import java.util.*;

/**
 * The Attendee class extends the User class. The Attendee class stores information of an
 * Attendee in an event. It contains the name of Attendee, password of the Attendee and the string
 * representation of the type of the Attendee.
 */
public class Attendee extends User implements Serializable {

  private String usertype;

  public Attendee(String name, String password) {
    super(name, password);
    usertype = "Attendee";
  }

  @Override
  public String getUserType() {
    return usertype;
  }
}
