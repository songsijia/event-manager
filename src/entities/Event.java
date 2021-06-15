package entities;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;

/**
 * The Event class represents a single unique event in the conference system. The Event class
 * stores all information about itself and contains getters to get this information. It allows
 * the attendees' usernames signed up for this event to be modified and can be easily printed as
 * a String/text representation for easy read for the user.
 *
 * @author Mahan Mohammadi, Valerie Wirianto
 */
public class Event implements Serializable {
  private String name, room;
  private ArrayList<String> speakerUsernames;
  private String identifier;
  private LocalDateTime startTime;
  private ArrayList<String> attendeeUsernames;
  private int durationInMinutes;
  private int maxCapacity;
  private boolean isVIP;

  /**
   * Constructs an Event object from the given information from the user.
   *
   * @param identifier a unique value that distinguishes the Event from other Event
   * @param name the name of this event
   * @param startTime the starting date and time of this event
   * @param room the room that this event will take place in
   * @param speakerUserNames the list of username of the speakers of this event
   * @param durationInMinutes the duration in minutes of how long this event will take place
   * @param maxCapacity is an integer that represents the maximum capacity of this Event
   */
  public Event(String identifier,String name, LocalDateTime startTime, String room,
               ArrayList<String> speakerUserNames, int durationInMinutes, int maxCapacity,
               boolean isVIP){
    this.name = name;
    this.identifier = identifier;
    this.startTime = startTime;
    this.durationInMinutes = durationInMinutes;
    this.room = room;
    attendeeUsernames = new ArrayList<String>();
    this.speakerUsernames = speakerUserNames;
    this.maxCapacity = maxCapacity;
    this.isVIP = isVIP;
  }

  /**
   * Returns the unique identifier of this event
   *
   * @return identifier
   */
  public String  getIdentifier(){
    return identifier;
  }

  /**
   * Returns the name of this event
   * @return name of event
   */
  public String getName(){
    return name;
  }

  /**
   * Returns the date and start time of this event
   * @return date and start time of event
   */
  public LocalDateTime getDate(){
    return startTime;
  }

  /**
   * Return the duration in minutes of this event
   * @return duration of event in minutes
   */
  public int getDurationInMinutes(){
    return durationInMinutes;
  }

  /**
   * Return room of the event
   * @return the room of the event
   */
  public String getRoom(){
    return room;
  }

  /**
   * Return the integer, which represents the maximum capacity of this event.
   * @return the integer, which represents the maximum capacity of this event.
   */
  public int getMaxCapacity() {
    return maxCapacity;
  }


  /**
   * Return a list of all usernames of attendees that are signed up for this event
   * @return an array list of attendees' usernames
   */
  public ArrayList<String> getAttendeeUsernames(){
    attendeeUsernames.sort(String::compareTo);
    return attendeeUsernames;
  }

  /**
   * Add a user's username to this event
   * @param username the username of the user who is signing up for this event
   */
  public void addAttendee(String username){
    attendeeUsernames.add(username);
  }

  /**
   * Remove a user from the sign up list of this event
   * @param username the username of the user to be removed from this event
   */
  public void removeAttendee(String username){
    attendeeUsernames.remove(username);
  }

  /**
   * Return the list of username of the speakers of this event
   * @return the list of username of the speakers
   */
  public ArrayList<String> getSpeakerUsernames(){
    return speakerUsernames;
  }

  /**
   * Return whether this event still has space for users to sign up
   * @return a boolean for whether this event has space left
   */
  public boolean hasCapacity(){
    return attendeeUsernames.size() < maxCapacity;
  }
  /**
   * Set or change the maximum capacity of this event to a new maximum capacity.
   * @param newmaxCapacity this is the integer that will represent the new maximum capacity of this event.
   */
  public void setMaxCapacity(int newmaxCapacity) {
    this.maxCapacity = newmaxCapacity;
  }

  /**
   * Return whether this event has a particular user signed up for it
   * @param attendeeUserName the username of the user that will be checked if they are signed up
   * @return a boolean for whether this user is signed up at this event
   */
  public boolean hasAttendee(String attendeeUserName){
    return attendeeUsernames.contains(attendeeUserName);
  }

  public boolean getIsVIP(){
    return this.isVIP;
  }


  /**
   * Return a String/text representation of this event for easy read for users
   * @return a String representation of this event
   */

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append( "{");

    Field[] fields = this.getClass().getDeclaredFields();

    for ( Field field : fields  ) {
      result.append("  ");
      try {
        result.append( field.getName() );
        result.append(": ");
        result.append( field.get(this) );
        result.append(";");
      } catch ( IllegalAccessException exception ) {
        System.out.println(exception);
      }
    }
    result.append("}");

    return result.toString();
  };

  public HashMap<String,String> toDict() {
    HashMap<String,String> result = new HashMap<String,String>();
    Field[] fields = this.getClass().getDeclaredFields();

    for ( Field field : fields  ) {
      try {
        result.put( field.getName(),field.get(this).toString() );
      } catch ( IllegalAccessException exception ) {
        System.out.println(exception);
      }
    }
    return result;
  };
}