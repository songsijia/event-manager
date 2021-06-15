package gateways;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import presenters.*;
import usecases.*;

/**
 * This is a gateway class that can read and write the use case classes into and from .ser files.
 * Its functions are called depending on what we wish to read and write.
 */
public class FileGateway {

  LoginPresenter lp;
  EventPresenter ep;
  MessagePresenter mp;

  public FileGateway(
    LoginPresenter lp,
    EventPresenter ep,
    MessagePresenter mp
  ) {
    this.lp = lp;
    this.ep = ep;
    this.mp = mp;
  }

  public void serSaveHelper(
    EventManager em,
    UserManager um,
    ConversationManager cm,
    RequestManager rm
  ) {
    saveToFile(um, "UserManager.ser");
    saveToFile(em, "EventManager.ser");
    saveToFile(cm, "ConversationManager.ser");
    saveToFile(rm, "RequestManager.ser");
  }

  /**
   * This method reads in the information from a .ser file. It will return the object it has read or a new one
   * if the file is nonexistent or an error occurred while reading from the file.
   * @param type What class we are trying to get. Valid options are UserManager, EventManager, ConversationManager,
   *             and RequestManager.
   * @param filename The name of the file we are trying to read from.
   * @return obj the newly read user manager
   */
  public Object readFromFile(String type, String filename) {
    try {
      InputStream file = new FileInputStream(filename);
      InputStream buffer = new BufferedInputStream(file);
      ObjectInput input = new ObjectInputStream(buffer);
      Object obj = input.readObject();
      input.close();
      return obj;
    } catch (ClassNotFoundException | IOException e) {
      lp.displayResponse("errorReadingObj", type);
      return createClass(type);
    }
  }

  private Object createClass(String type) {
    Object ret;
    switch (type) {
      case "UserManager":
        ret = new UserManager();
        break;
      case "EventManager":
        ret = new EventManager();
        break;
      case "ConversationManager":
        ret = new ConversationManager();
        break;
      case "RequestManager":
        ret = new RequestManager();
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + type);
    }
    return ret;
  }

  /**
   * This method saves the object as a file outside of the program.
   * @param obj The object we want to save.
   * @param filename The filename we want to save it as.
   */

  public void saveToFile(Serializable obj, String filename) {
    try {
      OutputStream file = new FileOutputStream(filename);
      OutputStream buffer = new BufferedOutputStream(file);
      ObjectOutput output = new ObjectOutputStream(buffer);
      output.writeObject(obj);
      output.close();
    } catch (IOException ex) {
      lp.displayResponse("errorWritingObj" + filename);
    }
  }

  private String readFile(String path) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(path));
    return reader.lines().collect(Collectors.joining(System.lineSeparator()));
  }


  private String readHTMLTemplate(String filename) {
    String filePath = filename;

    String content = null;
    try {
      content = readFile(filePath);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return content;
  };


  public void saveEventHTML(String content) {
    try {
      URL resource = FileGateway.class.getResource("template.html");
      String str = Paths.get(resource.toURI()).toString();
      String html = readHTMLTemplate(str);
      html = html.replace("$TABLEDATA$", content);
      PrintWriter out = new PrintWriter(new FileWriter("schedule.html", false));
      out.write(html);
      out.close();}
    catch (IOException | URISyntaxException ioe){}

  }
}
