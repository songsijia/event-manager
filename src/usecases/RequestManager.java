package usecases;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import entities.*;

public class RequestManager implements Serializable {

  List<Request> pendingRequests;
  List<Request> addressedRequests;
  private int totalRequest;

  public RequestManager() {
    pendingRequests = new ArrayList<>();
    addressedRequests = new ArrayList<>();
    totalRequest = 0;
  }

  /**
   * Returns a boolean of whether a new request is being successfully added to the system;
   *
   * @param username the username of the user who sends the request
   * @param content the content of the request
   * @return true if the request is successfully added, and false otherwise
   */
  public boolean addRequest(String username, String content) {
    int identifier = totalRequest + 1;
    Request newRequest = new Request(username, content, identifier);
    pendingRequests.add(newRequest);
    totalRequest += 1;
    return true;
  }

  /**
   * Return a list of String which contains String representation of all the pending requests in the system.
   *
   * @return the String representation of all the pending requests.
   */
  public ArrayList<String> viewAllPendingRequest() {
    ArrayList<String> allRequest = new ArrayList<>();

    for (Request r : pendingRequests) {
      allRequest.add(r.toString());
    }
    return allRequest;
  }

  /**
   * Return a list of String which contains String representation of all the addressed requests in the system.
   *
   * @return the String representation of all the addressed requests.
   */
  public ArrayList<String> viewAllAddressedRequest() {
    ArrayList<String> allRequest = new ArrayList<>();

    for (Request r : addressedRequests) {
      allRequest.add(r.toString());
    }
    return allRequest;
  }

  /**
   * Return true if the request is successfully addressed. The method would change the status of the Request to
   * "addressed", and remove this Request from the list of pendingRequests, and then add this request to the list of
   * addressedRequest.
   * @param identifier the identifier for the Request that needs to be addressed
   * @return a boolean representing whether the Request is addressed successfully
   */
  public boolean addressRequest(int identifier) {
    for (Request r : pendingRequests) {
      if (r.getIdentifier() == identifier) {
        r.setStatus("addressed"); // need to make sure the input status is valid
        addressedRequests.add(r);
        removeRequest(identifier, pendingRequests);
        return true;
      }
    }
    return false;
  }

  /**
   * Return true if the addressed request is now pending. The method would change the status of the Request to
   * "pending", and add this Request to the list of addressedRequests.
   * @param identifier the identifier for the Request
   * @return a boolean representing whether the Request is successfully being changed to a pending Request
   */
  public boolean pendRequest(int identifier) {
    for (Request r : addressedRequests) {
      if (r.getIdentifier() == identifier) {
        r.setStatus("pending");
        pendingRequests.add(r);
        removeRequest(identifier, addressedRequests);
        return true;
      }
    }
    return false;
  }

  private boolean removeRequest(int identifier, List<Request> requests) {
    for (Request r : requests) {
      if (r.getIdentifier() == identifier) {
        requests.remove(r);
        return true;
      }
    }
    return false;
  }
}
