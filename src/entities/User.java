package entities;
import java.io.Serializable;
import java.util.*;

/**
 * The User is an abstract class that stores information relevant to a user. Its subclasses include Attendee, Organizer,
 * and Speaker.
 * It stores information of the user's username(name), password, and contact list.
 */
public abstract class User implements Serializable {

  private String name;
  private String password;
  private ArrayList<String> contact;
  private boolean isVIP;
  private ArrayList<String> convos = new ArrayList<>();
  private Set<String> readHistory = new HashSet<>();
  private ArrayList<String> archivedConvos = new ArrayList<>();

  public User(String name, String password) {
    this.name = name;
    this.password = password;
    this.contact = new ArrayList<String>();
    this.isVIP = false;
  }

  public String getName() {
    return this.name;
  }

  public String getPassword() {
    return this.password;
  }

  public ArrayList<String> getContact() {
    return this.contact;
  }

  public ArrayList<String> getConvos() { return this.convos; }

  public Set<String> getReadHistory() {
    return this.readHistory;
  }

  public ArrayList<String> getArchivedConvos() { return this.archivedConvos; }

  public boolean addContact(String contact) {
    if (!this.contact.contains(contact)) {
      this.contact.add(contact);
      return true;
    }
    return false;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void changeVIPStatus(boolean isVIP) {
    this.isVIP = isVIP;
  }

  public boolean getVIPStatus() {
    return this.isVIP;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public abstract String getUserType();
}
