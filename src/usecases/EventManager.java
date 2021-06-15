package usecases;
// import com.sun.org.apache.xpath.internal.operations.Bool;
import entities.Event;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventManager implements Serializable {

  private ArrayList<Event> listOfEvent;
  private ArrayList<String> rooms;
  private HashMap<String, ArrayList> roomResources;


  public EventManager(){
    listOfEvent = new ArrayList<Event>();
    rooms = new ArrayList<String>();
    roomResources = new HashMap<String, ArrayList>();
  }


  /**
   * Check if the input room exists.
   * @param room this is the input room.
   * @return true if the input room exists. Otherwise, will return false.
   */
  public boolean roomExists(String room){
    if(this.rooms.isEmpty()){
      return false;
    }
    else {
      return this.rooms.contains(room);
    }
  }

  /**
   * Make sure that the identifier of an event is unique and that this identifier has length equal to 4.
   * @param identifier this is a string representing the identifier of an event
   * @return true if the identifier is unique and has length equal to 4, otherwise return false.
   */
  //Helper method
  private boolean uniqueIdentifier(String identifier){
    for(Event event: listOfEvent){
      if(event.getIdentifier().equals(identifier) && identifier.length() != 4){
        return false;
      }
    }
    return true;
  }

  /**
   * Check if the input event booked a room at a different time than other existing events.
   * @param newEvent the input event
   * @return true if the input event booked a room at a different time than other existing events,
   * otherwise returns false.
   */
  //Helper method.
  private boolean roomAvailability(Event newEvent){
    for (Event event: listOfEvent) {
      if(newEvent.getRoom().equals(event.getRoom()) && newEvent.getDate().getDayOfYear() == event.getDate().getDayOfYear()){
        if(!noteventOverlap(event, newEvent)){
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Checking if two Events do not overlap
   * @param oldEvent this is an already existing Event
   * @param newEvent this is a new Event that is going to be added to the list of events
   * @return true if two Events do not overlap, otherwise return false.
   */
  //Helper method
  private boolean noteventOverlap(Event oldEvent, Event newEvent){
    LocalDateTime endTimeOldEvent = oldEvent.getDate().plusMinutes(oldEvent.getDurationInMinutes());
    LocalDateTime endTimeNewEvent = newEvent.getDate().plusMinutes(newEvent.getDurationInMinutes());
    LocalDateTime startTimeOldEvent = oldEvent.getDate();
    LocalDateTime startTimeNewEvent = newEvent.getDate();

    if((startTimeNewEvent.isAfter(startTimeOldEvent) && startTimeNewEvent.isBefore(endTimeOldEvent)) ||
            (endTimeNewEvent.isBefore(endTimeOldEvent) && endTimeNewEvent.isAfter(startTimeOldEvent))){
      return false;
    }
    else if(startTimeNewEvent.isBefore(startTimeOldEvent) && endTimeNewEvent.isAfter(endTimeOldEvent)){
      return false;
    }
    else if(startTimeNewEvent.equals(startTimeOldEvent) || endTimeNewEvent.equals(endTimeOldEvent)){
      return false;
    }
    return true;
  }

  /**
   * Make sure a speaker is not double booked at the same time
   * @param newEvent which we use to to access its list of speaker usernames.
   * @return true if the speaker is not double booked at the same time.
   */
  //helper method.
  private boolean speakerAvailability(Event newEvent){
    for (Event event: listOfEvent){
      for (String speakerusername: newEvent.getSpeakerUsernames()){
        if(event.getSpeakerUsernames().contains(speakerusername) && !noteventOverlap(event, newEvent)){
          return false;
        }
      }

    }
    return true;
  }

  /**
   * Add a new event if it has room availability and speaker availability.
   * @param name name of the new event
   * @param starttime starttime of the new event
   * @param room room of the new event
   * @param speakerusernames  the list of username of the speakers of the new event
   * @param durationInMinutes duration(in minutes) of the new event
   */
  public boolean addEvent(String identifier,String name, LocalDateTime starttime, String room, ArrayList<String> speakerusernames, int durationInMinutes,  int maxCapacity, boolean isVIP){
    Event newEvent = new Event(identifier,name, starttime, room, speakerusernames, durationInMinutes, maxCapacity, isVIP);
    if(uniqueIdentifier(identifier) && roomAvailability(newEvent) && speakerAvailability(newEvent) && roomExists(room)){
      listOfEvent.add(newEvent);
      return true;
    }
    return false;
  }

  /**
   * Get the event strings that a particular user(Attendee/Organizer) has signed-up for.
   * @param attendeeUsername of the user(Attendee/Organizer), which is unique and is used to get
   * the events that the user(Attendee/Organizer) has signed-up for.
   * @return The list of event strings that the particular user(Attendee/Organizer) has signed-up for.
   */
  public ArrayList<String> getEventStringsParticularAttendee(String attendeeUsername){
    ArrayList<String> eventStrings = new ArrayList<String>();
    ArrayList<Event> events = getEventsofParticularAttendee(attendeeUsername);
    for (Event event: events){
      eventStrings.add(event.toString());
    }
    return eventStrings;
  }

  /**
   * Get the events that a particular user(Attendee/Organizer) has signed-up for.
   * @param attendeeusername of the user(Attendee/Organizer), which is unique and is used to get
   * the events that the user(Attendee/Organizer) has signed-up for.
   * @return The list of events that the particular user(Attendee/Organizer) has signed-up for.
   */
  public ArrayList<Event> getEventsofParticularAttendee(String attendeeusername){
    ArrayList<Event> eventsofParticularAttendee = new ArrayList<Event>();
    for (Event event: listOfEvent){
      if (event.hasAttendee(attendeeusername)){
        eventsofParticularAttendee.add(event);
      }
    }
    return eventsofParticularAttendee;
  }

  /**
   * Add a user (Attendee/Organizer) to an event by adding the username of the user (Attendee/Organizer)
   * to the list of usernames of attendees of a particular event.
   * @param username this is a unique string that represents the username of the user (Attendee/Organizer) who is
   * going to be added to the event.
   * @param identifier this is a unique string that represents the identifier of the event to which the
   * user (Attendee/Organizer) is added.
   * @return true if the user (Attendee/Organizer) was successfully added to the particular event, otherwise returns
   * false.
   */
  public boolean addAttendee(String username, String identifier, boolean isVIP) {
    Event event = getEvent(identifier);
    boolean t = true;

    if(event == null){
      return false;
    }

    ArrayList<Event> eventsofParticularAttendee = getEventsofParticularAttendee(username);

    for (Event event1: eventsofParticularAttendee){
      if(!noteventOverlap(event1, event)){
        t = false;
      }
    }
    if (t && event.hasCapacity() && !event.getAttendeeUsernames().contains(username)){
      if(!isVIP && event.getIsVIP()){
        return false;
      }
      event.addAttendee(username);
      return true;
    }
    return false;
  }

  /**
   * Remove a user (Attendee/Organizer) from an event by removing the username of the user (Attendee/Organizer)
   * from the list of usernames of attendees of a particular event.
   * @param username this is a unique string that represents the username of the user (Attendee/Organizer) who is
   * going to be removed from the event.
   * @param identifier this is a unique string that represents the identifier of the event from which the
   * user (Attendee/Organizer) is removed.
   * @return true if the user (Attendee/Organizer) was successfully removed from the particular event, otherwise returns
   * false.
   */
  public boolean removeAttendee(String username, String identifier){
    Event eventRemove = getEvent(identifier);
    if (eventRemove == null){
      return false;
    }
    if(eventRemove.getAttendeeUsernames().contains(username)){
      eventRemove.removeAttendee(username);
      return true;
    }
    return false;
  }

  /**
   *  Remove an event from the list of all events, and return true if the event
   *  was successfully removed from the list of all events. Otherwise, return false.
   * @param event this is the event to be removed from the list of all events.
   * @return Return true if the event was successfully removed from the
   * list of all events. Otherwise, return false.
   */
  public boolean removeAnEvent(String event){
    Event event1 = getEvent(event);
    if (listOfEvent.isEmpty()){
      return false;
    }
    else if (listOfEvent.contains(event1)){
      listOfEvent.remove(event1);
      return true;}
    else{
      return false;}
  }

  /**
   * Get the list of usernames of users (Attendee/Organizer) in a particular event.
   * @param identifier this is a unique string that represents the identifier of the event for which this method is
   * going to output the list of usernames of users (Attendee/Organizer) in it.
   * @return the list of usernames of users (Attendee/Organizer) in a particular event.
   */
  public ArrayList<String> getUsernamesEvent(String identifier){
    Event eventCheck = getEvent(identifier);
    return eventCheck.getAttendeeUsernames();
  }

  /**
   * Get the list of events where the a particular speaker is speaking at them.
   * @param speakerUserName is a string that is unique to identify a particular speaker, and it is used to get
   * the list of events where the speaker is speaking at them.
   * @return  the list of events where the a particular speaker is speaking at them
   */
  public ArrayList<String> getEventsOfSpeaker(String speakerUserName){
    ArrayList<String> event_of_speaker = new ArrayList<String>();
    for(Event event : listOfEvent){
      if(event.getSpeakerUsernames().contains(speakerUserName)){
        event_of_speaker.add(event.getIdentifier());
      }

    }
    return event_of_speaker;
  }

  /**
   * Get the list of all events in the system.
   * @return the list of all events in the system.
   */
  public ArrayList<String> getListofALLEvent(){
    ArrayList<String> allEventString = new ArrayList<>();
    for(Event event: listOfEvent){
      allEventString.add("\n"+event.toString());
    }
    return allEventString;
  }

  public ArrayList<HashMap<String,String>> getDictofAllEvent() {
    ArrayList<HashMap<String,String>> allEventDict = new ArrayList<>();
    for (Event event : listOfEvent) {
      allEventDict.add(event.toDict());
    }
    return allEventDict;
  }

  /**
   * Get the list of available events for which a particular user (Attendee/Organizer) can be added(signed-up).
   * @param username is a string that is a unique identifier for the particular user (Attendee/Organizer), and
   * it is needed to get the list of available events for which the particular user (Attendee/Organizer) can be added
   * to(signed-up).
   * @return the list of available events for which the particular user (Attendee/Organizer) can be added(signed-up).
   */
  public ArrayList<String> getAvailableEvents(String username, boolean isVIP) {
    ArrayList<Event> eventsofParticularAttendee = getEventsofParticularAttendee(
            username
    );
    ArrayList<String> availableEventsString = new ArrayList<String>();
    if (eventsofParticularAttendee.isEmpty() && isVIP) {
      return getListofALLEvent();
    }
    else if(eventsofParticularAttendee.isEmpty() && !isVIP){
      for (Event event1: listOfEvent){
            if(!event1.getIsVIP()){
              availableEventsString.add(event1.toString());
        }
        }
      }

    for (Event event : eventsofParticularAttendee) {
      for (Event event1 : listOfEvent) {
        if (
                noteventOverlap(event, event1) &&
                        event1.hasCapacity() &&
                        !event1.getAttendeeUsernames().contains(username)
        ) {
          if(!event.getIsVIP() | (event.getIsVIP() && isVIP)){
            availableEventsString.add(event1.toString());
          }
        }
      }
    }
    return availableEventsString;
  }

  /**
   * Return true if the maximum capacity of a particular event is changed or set to a new maximum capacity, otherwise
   * return false.
   * @param eventIdentifier is the string representing the particular event identifier and is used to
   * find that particular event that will have a new maximum capacity.
   * @param newMaxCapacity this is the integer that will represent the new maximum capacity of this event.
   * @return true if the maximum capacity of a particular event is changed or set to a new maximum capacity, otherwise
   * return false.
   */
  public boolean changeMaxCapacityOfEvent(String eventIdentifier, int newMaxCapacity){
    Event event = getEvent(eventIdentifier);
    if (listOfEvent.isEmpty()){
      return false;
    }
    else if (listOfEvent.contains(event)){
      if (event.getMaxCapacity() < newMaxCapacity){
        event.setMaxCapacity(newMaxCapacity);
        return true; }
      else {return false;
      }
    }
    else{
      return false;}

  }

  /**
   * Add a room to the list of all rooms for events if and only if the list of all rooms is empty or
   * (the list of all rooms does not contain the input room and the length of the input room is equal to 3).
   * @param room this is the string that represents the name of the input room. The name of
   * the input room must have digits only.
   * @return true if the input room was added successfully to the list of all rooms for events, otherwise return
   * false.
   */
  public boolean addRoom(String room){
    if(!this.rooms.contains(room) && room.length()== 3){
      try {
        Integer.parseInt(room);
        this.rooms.add(room);
        return true;
      } catch (NumberFormatException e) {
      }
    }
    return false;
  }

  public void addRoomResource(String room, ArrayList<String> resources) {
    roomResources.put(room,resources);
  }

  public ArrayList<String> findRoomResource(ArrayList<String> resources) {
   ArrayList<String> matching = new ArrayList<String>();
    for (Map.Entry<String,ArrayList> entry: roomResources.entrySet()
         ) {
      String room = entry.getKey();
      ArrayList<String> roomHas = entry.getValue();
      Boolean m = true;
      for (String r: resources
           ) {
        if (!roomHas.contains(r)) {
          m = false;
        }
      }
      if (m) {
        matching.add(room);
      }
    }
    return matching;
  };

  /**
   * Find and get a particular event.
   * @param identifierevent is a string that represents the unique identifier of a potential event and is needed to
   * match an identifier of a particular event, so that the particular event can be found.
   * @return the event if found, otherwise return null.
   */
  //Helper method.
  private Event getEvent(String identifierevent){
    Event returnEvent = null;
    for (Event event : listOfEvent) {
      if (event.getIdentifier().equals(identifierevent)) {
        returnEvent = event;
      }
    }
    return returnEvent;
  }


}
