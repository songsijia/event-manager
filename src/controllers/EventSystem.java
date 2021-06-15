package controllers;
import usecases.EventManager;

import java.time.LocalDateTime;
import java.util.*;
import presenters.*;
import usecases.*;

/**
 * This class prints out the menu options related to event management in the system. It will print different menu
 * options depending on the user type. It controls program flow by calling the appropriate methods in the UserManager
 * class.
 *
 * @author Mahan Mohammadi, Valerie Wirianto
 */
public class EventSystem {

  EventManager em;
  UserManager um;
  EventPresenter presenter;
  ScheduleBuilder scheduler;
  private String[] availableResources = {"projector","tables","chairs",};

  public EventSystem(
    EventManager em,
    UserManager um,
    EventPresenter presenter,
    ScheduleBuilder scheduler
  ) {
    this.em = em;
    this.um = um;
    this.presenter = presenter;
    this.scheduler= scheduler;
  }

  /**
   * This method prompts the Attendee with different menu options for event related actions
   */
  public void runAttendee() {
    Scanner eventInput = new Scanner(System.in);
    String input = "";

    while (true) {
      presenter.displayMenu("eventSystemAttendee");
      input = eventInput.nextLine();
      if (input.equals("1")) {
        getListOfAllEvent();
      } else if (input.equals("2")) {
        getListAvailableEvent();
      } else if (input.equals("3")) {
        getListOfMySignedUpEvents();
      } else if (input.equals("4")) {
        signupForEvent();
      } else if (input.equals("5")) {
        cancelSpotFromEvent();
      } else if (input.equals("6")) {
        scheduler.buildEventList();
      } else if (input.equals("7")) {
        break;
      }
    }
  }

  /**
   * This method prompts the Organizer with different menu options for event related actions
   */
  public void
  runOrganizer() {
    Scanner eventInput = new Scanner(System.in);
    String input = "";

    while (true) {
      presenter.displayMenu("eventSystemOrganizer");
      input = eventInput.nextLine();
      if (input.equals("1")) {
        getListOfAllEvent();
      } else if (input.equals("2")) {
        getListAvailableEvent();
      } else if (input.equals("3")) {
        getListOfMySignedUpEvents();
      } else if (input.equals("4")) {
        signupForEvent();
      } else if (input.equals("5")) {
        cancelSpotFromEvent();
      } else if (input.equals("6")) {
        addRoom();
      } else if (input.equals("7")) {
        addRoomResource();
      } else if (input.equals("8")) {
        findRoomResource();
      } else if (input.equals("9")) {
        scheduleAnEvent();
      } else if (input.equals("10")) {
        removeEvent();
      } else if (input.equals("11")) {
        changeMaxCapacityOfEvent();
      } else if (input.equals("12")) {
        scheduler.buildEventList();
      } else if(input.equals("13")){
        break;
      }
    }
  }

  /**
   * This method prompts the Speaker with different menu options for event related actions
   */
  public void runSpeaker() {
    Scanner eventInput = new Scanner(System.in);
    String input = "";

    while (true) {
      presenter.displayMenu("eventSystemSpeaker");
      input = eventInput.nextLine();
      if (input.equals("1")) {
        getListOfSpeakerEvents();
      } else if (input.equals("2")) {
        break;
      }
    }
  }

  /**
   * Get and show the list of all Event to the user. Print out the list of all events to the user.
   */
  public void getListOfAllEvent() {
    presenter.displayResponse("listOfEvents");
    System.out.println(em.getListofALLEvent());
    returnToEventPage();
  }


  /**
   * Get and show the list of available events to the user.
   * Print out the list of all events that the user can
   * sign up.
   */
  public void getListAvailableEvent() {
    presenter.displayResponse("eventsWithAccessibility");
/*    boolean isVIP = false;
    if(em.getEventsofParticularAttendee(um.getUserNameOfCurrentUser()).size() >= 2 | um.getVIPStatus(um.getUserNameOfCurrentUser())){
      isVIP = true;
    }*/
    System.out.println(em.getAvailableEvents(um.getUserNameOfCurrentUser(), um.getVIPStatus(um.getUserNameOfCurrentUser())));

    returnToEventPage();
  }

  /**
   * This method prints out the list of events the current user is signed-up for
   */
  public void getListOfMySignedUpEvents() {
    presenter.displayResponse("listOfSignedUpEvents");

    System.out.println(
      em.getEventStringsParticularAttendee(um.getUserNameOfCurrentUser())
    );

    returnToEventPage();
  }

  /**
   * Allow the user to sign-up for an event by choosing the identifier of the event they want to sign-up
   * for.
   */
  public void signupForEvent() {
    Scanner eventIdentifierInput = new Scanner(System.in);

    String inputIdentifier;
    presenter.displayResponse("signupIdentifier");
    inputIdentifier = eventIdentifierInput.nextLine();

    if (returntoEventPageIfyouWant(inputIdentifier)) {
      return;
    }



    if (em.addAttendee(um.getUserNameOfCurrentUser(), inputIdentifier, um.getVIPStatus(um.getUserNameOfCurrentUser()))) {
      um.potentialVIP(um.getUserNameOfCurrentUser(), em.getEventsofParticularAttendee(um.getUserNameOfCurrentUser()).size());
      presenter.displayResponse("signupSuccess");
    } else {
      presenter.displayResponse("signupFailure");
    }

    returnToEventPage();
  }

  /**
   * Allow the current user to cancel their spot or dropdown from an
   * event by choosing the identifier of the event
   * they want to be removed from.
   */
  public void cancelSpotFromEvent() {
    Scanner eventIdentifierInput = new Scanner(System.in);

    String inputIdentifier = "";

    presenter.displayResponse("removeByIdentifier");
    inputIdentifier = eventIdentifierInput.nextLine();

    if (returntoEventPageIfyouWant(inputIdentifier)) {
      return;
    }

    if (em.removeAttendee(um.getUserNameOfCurrentUser(), inputIdentifier)) {
      presenter.displayResponse("cancelSpotSuccess");
    } else {
      presenter.displayResponse("cancelSpotFailure");
    }

    returnToEventPage();
  }

  /**
   * This method allows the Organizer to add rooms into the system
   */
  public void addRoom() {
    Scanner eventInput = new Scanner(System.in);

    String inputRoom;

    presenter.displayResponse("inputNewRoom");
    inputRoom = eventInput.nextLine();
    if (returntoEventPageIfyouWant(inputRoom)) {
      return;
    }

    if (em.addRoom(inputRoom)) {
      presenter.displayResponse("roomSuccess");
    } else {
      presenter.displayResponse("roomFailure");
    }

    returnToEventPage();
  }

  /**
   * This method allows the Organizer to create a new event. The method will also sign up the Organizer
   * to the event they just created.
   */
  public void scheduleAnEvent() {
    String id, name, room, speakerUsername, vipStr;
    int month, day, year, hour, min, duration, maxCapacity;
    boolean vip = false;
    LocalDateTime startTime;
    ArrayList<String> speakerUsernames = new ArrayList<String>();

    Scanner eventInput = new Scanner(System.in);

    while (true) {
      presenter.displayResponse("inputIdentifier");
      id = eventInput.nextLine();
      presenter.displayResponse("inputEventName");
      name = eventInput.nextLine();

      presenter.displayResponse("inputVIP");
      vipStr = eventInput.nextLine();
      if(vipStr.equalsIgnoreCase("yes")){
        vip = true;
      }

      while (true) {
        try {
          presenter.displayResponse("inputMaxCapacity");
          maxCapacity = Integer.parseInt(eventInput.nextLine());
          if(maxCapacity >= 1){
            break;
          }
        } catch (NumberFormatException e) {
          presenter.displayResponse("validInteger");
        }
      }

      while (true) {
        presenter.displayResponse("inputNewRoom");
        room = eventInput.nextLine();
        if (returntoEventPageIfyouWant(room)) {
          return;
        }
        if (em.roomExists(room)) {
          break;
        }
      }

      while (true) {
        presenter.displayResponse("inputSpeakerUserName");
        speakerUsername = eventInput.nextLine();
        if (speakerUsername.equalsIgnoreCase("none")) {
          break;
        }
        speakerUsernames.add(speakerUsername);
      }
      while (true) {
        try {
          presenter.displayResponse("inputDuration");
          duration = Integer.parseInt(eventInput.nextLine());
          break;
        } catch (NumberFormatException e) {
          presenter.displayResponse("validInteger");
        }
      }

      while (true) {
        try {
          presenter.displayResponse("inputDate");
          String date = eventInput.nextLine();
          presenter.displayResponse("inputTime");
          String time = eventInput.nextLine();
          month = Integer.parseInt(date.substring(0, 2));
          day = Integer.parseInt(date.substring(3, 5));
          year = Integer.parseInt(date.substring(6, 10));
          hour = Integer.parseInt(time.substring(0, 2));
          min = Integer.parseInt(time.substring(3, 5));
          break;
        } catch (NumberFormatException e) {
          presenter.displayResponse("invalidDateTime");
        }
      }
      startTime = LocalDateTime.of(year, month, day, hour, min);

      if (em.addEvent(id, name, startTime, room, speakerUsernames, duration, maxCapacity, vip)) {
        presenter.displayResponse("scheduleEvent");
        break;
      }
      presenter.displayResponse("failToScheduleEvent");
    }
  }
  public void changeMaxCapacityOfEvent(){
    Scanner eventIdentifierInput = new Scanner(System.in);
    String eventID;
    int num = 0;
    presenter.displayResponse("maxCapacityEvent");
    eventID = eventIdentifierInput.nextLine();
    if (returntoEventPageIfyouWant(eventID)) {
      return;
    }
    while (true) {
      try {
        presenter.displayResponse("maxCapacityNum");
        num = Integer.parseInt(eventIdentifierInput.nextLine());
        if(num >= 1){
          break;
        }
      } catch (NumberFormatException e) {
        presenter.displayResponse("validInteger");
      }
    }
    if(em.changeMaxCapacityOfEvent(eventID,num)){
      presenter.displayResponse("maxCapacitySuccess");
    }
    else{
      presenter.displayResponse("maxCapacityFail");
    }
    returnToEventPage();

  }
  public void removeEvent(){
    Scanner eventInput = new Scanner(System.in);
    String eventID;
    boolean result;
    presenter.displayResponse("cancelEvent");
    eventID = eventInput.nextLine();
    if (returntoEventPageIfyouWant(eventID)) {
      return;
    }
    result = em.removeAnEvent(eventID);
    if(result){
      presenter.displayResponse("cancelEventSuccess");
    }
    else{
      presenter.displayResponse("cancelEventFailure");
    }
    returnToEventPage();
  }

  /**
   * This method prints a list of events where some speaker is speaking at them.
   */
  public void getListOfSpeakerEvents() {
    presenter.displayResponse("eventsOfSpeaker");

    System.out.println(em.getEventsOfSpeaker(um.getUserNameOfCurrentUser()));

    returnToEventPage();
  }

  /**
   * This method prompts the current user to press the 'Enter' key to return to the Main Menu of the Events Page
   */
  private void returnToEventPage() {
    presenter.displayResponse("returnToEventManager");
    try {
      System.in.read();
    } catch (Exception ex) {}
  }

  /**
   * This method is used when the user wants to go back again to the main menu page for event related
   * tasks to choose another option that he may have forgotten to choose.
   */
  public boolean returntoEventPageIfyouWant(String input) {
    if (input.equals("")){
       return true;
    }
    return false;
  }

  public void addRoomResource() {
      Scanner eventInput = new Scanner(System.in);

      String inputRoom;

      presenter.displayResponse("inputNewRoom");
      inputRoom = eventInput.nextLine();
    if (returntoEventPageIfyouWant(inputRoom)) {
      return;
    }
    if (!em.roomExists(inputRoom)) {
      presenter.displayResponse("roomNotExist");
      return;
    }
    ArrayList<String> resources = buildResourceList();

    em.addRoomResource(inputRoom,resources);

    presenter.displayResponse("resourceAdded");
      returnToEventPage();
  }

  public void findRoomResource() {
    ArrayList<String> resources = buildResourceList();
    System.out.println(em.findRoomResource(resources));
  }

  public ArrayList<String> buildResourceList() {
    Scanner eventInput = new Scanner(System.in);
    ArrayList<String> resources = new ArrayList<String>();
    while(true) {
      presenter.displayResponse("amenitiesOptions");
      int i = 0;
      for (String s: availableResources
      ) {
        System.out.println(i++ +". "+ s);
      }
      String r = eventInput.nextLine();
      if (r.equalsIgnoreCase("")) {
        break;
      } else {
        try {
          int index= Integer.parseInt(r);
          resources.add(availableResources[index]);
          System.out.println(resources);
        } catch(NumberFormatException e) {
          presenter.displayResponse("integerWarning");
        } catch(NullPointerException e) {
          presenter.displayResponse("integerWarning");
        }

      }
    }
    return resources;
  }

}
