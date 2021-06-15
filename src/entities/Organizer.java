package entities;
import java.io.Serializable;

/**
 * The Organizer class extends the User class. The Organizer class stores information of an
 * organizer of an event. It contains the name of Organizer, password of the organizer and the string
 * representation of the type of organizer.
 */
public class Organizer extends User implements Serializable {

  private String usertype;

  public Organizer(String name, String password) {
    super(name, password);
    usertype = "Organizer";
  }

  @Override
  public String getUserType() {
    return usertype;
  }
}
