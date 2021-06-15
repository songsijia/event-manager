package presenters;
public class MainPresenter {

  // Menus appear only after you login.
  public void chooseDifferentMenus() {
    System.out.println("Below are the different menus you can choose from. Please enter 1-4: ");
    System.out.println("You can return to the previous menu by clicking Enter.");
    System.out.println("1. View options for event management.");
    System.out.println("2. View options for account settings/user-specific tasks.");
    System.out.println("3. View messages/announcements options.");
    System.out.println("4. Quit the program.");
  }
}
