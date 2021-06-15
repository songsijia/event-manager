package presenters;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class prints all the String output the user will see on the screen.
 */
public class EventPresenter {

  private HashMap<String, ArrayList<String>> menus = new HashMap<String, ArrayList<String>>();
  private HashMap<String, String> responses = new HashMap<String, String>();

  public EventPresenter() {
    // Construct our menus
    ArrayList<String> eventSystemAttendee = new ArrayList<String>();
    eventSystemAttendee.add("View the list of all events");
    eventSystemAttendee.add("View the list of all available events for me");
    eventSystemAttendee.add("View the list of  events I am signed up for");
    eventSystemAttendee.add("Sign up for an event");
    eventSystemAttendee.add("Cancel my spot from an event");
    eventSystemAttendee.add("Print the schedule of events");
    eventSystemAttendee.add("Return to Main Menu");

    ArrayList<String> eventSystemOrganizer = new ArrayList<String>();
    eventSystemOrganizer.add("View the list of all events");
    eventSystemOrganizer.add("View the list of all available events for me");
    eventSystemOrganizer.add("View the list of  events I am signed up for");
    eventSystemOrganizer.add("Sign up for an event");
    eventSystemOrganizer.add("Cancel my spot from an event");
    eventSystemOrganizer.add("Add a room to the system");
    eventSystemOrganizer.add("Add a resource to the room");
    eventSystemOrganizer.add("Find a room with resources");
    eventSystemOrganizer.add("Schedule a new event");
    eventSystemOrganizer.add("Cancel an event");
    eventSystemOrganizer.add("Change the maximum capacity of an event");
    eventSystemOrganizer.add("Print the schedule of events");
    eventSystemOrganizer.add("Return to Main Menu");

    ArrayList<String> eventSystemSpeaker = new ArrayList<String>();
    eventSystemSpeaker.add("1. View events I am speaking at");
    eventSystemSpeaker.add("2. Return to Main Menu");

    this.menus.put("eventSystemAttendee", eventSystemAttendee);
    this.menus.put("eventSystemOrganizer", eventSystemOrganizer);
    this.menus.put("eventSystemSpeaker", eventSystemSpeaker);

    // Construct our responses
    this.responses.put(
            "listOfEvents",
            "Here is the list of all scheduled events:"
    );
    this.responses.put(
        "eventsWithAccessibility",
        "Here is the list of all the events you are available to sign-up for:"
      );
    this.responses.put(
        "listOfSignedUpEvents",
        "Here is a list of all events you are signed up for:"
      );

    this.responses.put(
        "inputNewRoom",
        "Please enter the room you would like to enter into the system as an integer 3 digits long. If you would like to return \n" +
                "to the Event Menu, press the enter key:"
      );
    this.responses.put(
        "inputIdentifier",
        "Enter the new, unique identifier for the event with 4 characters:"
      );
    this.responses.put("inputEventName", "Enter the event name:");
    this.responses.put("inputRoom", "Enter the room of the event:");
    this.responses.put(
        "inputSpeakerUserName",
        "Enter a speaker username. If there are no more speakers, enter 'none':"
      );
    this.responses.put(
        "inputDuration",
        "Enter the duration in minutes of the event:"
      );
    this.responses.put("inputDate", "Enter the date(MM/DD/YYYY):");
    this.responses.put("inputTime", "Enter the time(Hour:Min):");

    this.responses.put("validInteger", "Please input a valid integer.");
    this.responses.put(
        "invalidDateTime",
        "Please input an appropriate date and time."
      );

    this.responses.put(
            "inputVIP",
            "Is this a VIP event? Enter yes or no:"
    );
    this.responses.put(
            "inputMaxCapacity",
            "Enter the maximum number of people that can attend this event. An event must have a maximum capacity of at least 1 person:"
    );

    this.responses.put(
        "scheduleEvent",
        "Yay! You have successfully scheduled an event."
      );
    this.responses.put(
        "failToScheduleEvent",
        "Sorry! We cannot add an event for you. Check if you double booked a speaker, double booked a room, or if your identifier was not unique. Please try again."
      );

    this.responses.put(
        "signUpByIdentifier",
        "Enter the identifier of the event you want to sign up for:"
      );
    this.responses.put(
        "removeByIdentifier",
        "Enter the identifier of the event you want to be removed from. If you would like to return \n" +
                "to the Event Menu, press the enter key:"
      );

    this.responses.put(
        "eventsOfSpeaker",
        "Here is a list of all events you are speaking at:"
      );
    this.responses.put(
        "returnToEventManager",
        "\nPress the Enter key to return to the Event Management page..."
      );

    this.responses.put("roomSuccess", "The room was successfully created!");
    this.responses.put(
        "roomFailure",
        "Room not created. The room is already in the system or your room is not 3 characters long."
      );

    this.responses.put(
        "organizerSignup",
        "Since you are the organizer of this event, you will be automatically signed-up for this event you organized"
      );
    this.responses.put(
        "signupFailure",
        "Failed to sign up for this event. This event may be full, you may already be in it, you may not be a VIP and this is a VIP event, " +
                "or you did not input a valid event identifier."
      );
    this.responses.put(
            "maxCapacityEvent",
            "Please enter the identifier of the event you wish to change the capacity of. If you would like to return to the Event Menu," +
                    "press enter."
    );
    this.responses.put(
            "maxCapacityNum",
            "Please enter the number of the maximum capacity you wish to change this event to. Make sure it is at least 1 and greater than the " +
                    "maximum capacity of the event currently."
    );
    this.responses.put(
            "maxCapacitySuccess",
            "The maximum capacity of this event was successfully changed!"
    );
    this.responses.put(
            "maxCapacityFail",
            "The maximum capacity of this event was unsuccessfully changed because you did not input a valid event identifier."
    );
    this.responses.put(
        "signupSuccess",
        "You have successfully signed up for this event!"
      );
    this.responses.put(
        "signupIdentifier",
        "Enter the identifier of the event you wish to sign up at. If you would like to return \n" +
                "to the Event Menu, press the enter key:"
      );
    this.responses.put(
        "cancelSpotFailure",
        "Failed to cancel your spot from this specified event. Either no such event exist in our system, or you are not in this event already."
      );
    this.responses.put(
        "cancelSpotSuccess",
        "You were successfully drop down from this event, and you canceled your spot from this event."
      );
    this.responses.put("cancelEvent", "Enter the unique ID of the event you wish to cancel. If you would like to return to the" +
            "Event Menu, press the enter key:");
    this.responses.put("cancelEventSuccess", "You have successfully cancelled the event.");
    this.responses.put("cancelEventFailure", "The event failed to be cancelled. You may have inputted an event ID not in the system.");
    this.responses.put(
        "returnToEventPageIfYouWant",
        "If you want to go back to main menu for event related tasks to check something again or to choose another option, please type Yes; otherwise type No:"
      );
    this.responses.put(
        "returnToPreviousMenu",
        "You can return to the previous menu by clicking Enter."
      );
    this.responses.put(
        "errorReadingEvent",
        "No file found or error reading from file! Creating new EventManager."
      );
    this.responses.put(
        "errorWritingEvent",
        "Error writing to file for Event related objects!."
      );
    this.responses.put( "roomNotExist", "That room doesn't exist!");
    this.responses.put("resourceAdded", "Resources added!");
    this.responses.put("amenitiesOptions", "Which amenities does the room have? Type a number to add, or enter to finish;");
    this.responses.put("integerWarning","Must be an integer");
  }

  public void displayMenu(String menu) {
    ArrayList<String> options = this.menus.get(menu);
    System.out.println("\nWelcome to the Event Management Page!");
    for (int i = 0; i < options.size(); i++) {
      int j = i + 1;
      System.out.println(j + ". " + options.get(i));
    }
    System.out.print("Please input a valid number option you wish to select:");
  }

  public void displayResponse(String response) {
    String res = this.responses.get(response);
    System.out.println(res);
  }
}
