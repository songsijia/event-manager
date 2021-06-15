package entities;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Speaker class extends the User class. The Speaker class stores information of the
 * single Speaker who is going to give talks. It contains a list of Events that the Speaker
 * will present.
 *
 */

public class Speaker extends User implements Serializable {

  private String usertype;

  /**
   * Constructs a Speaker class with following attributes.
   *
   * @param name the Speaker's name
   * @param password the password of this Speaker
   */

  public Speaker(String name, String password) {
    super(name, password);
    this.usertype = "Speaker";
  }

  @Override
  public String getUserType() {
    return usertype;
  }
}
