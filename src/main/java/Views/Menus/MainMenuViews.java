package Views.Menus;

import menus.MainMenu;
import java.util.Scanner;

public class MainMenuViews {

    public static final Scanner scan = new Scanner(System.in);

    public static void getSelection() {
        System.out.println("Welcome to the airsoft data management tool.");
        System.out.println("1. Register a new user");
        System.out.println("2. Login with an already existing user");
        //System.out.println("3. Show top 5 players");
        System.out.println("3. Exit");
        try {
            MainMenu.selection = Integer.parseInt(scan.next());
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
    }
}
