package Views.Menus.SpecialFeatures;

import main.MainClass;

public class EventMenuView {

    public static void getCreateEventMenu() {
        //eventListCacheClear();
        System.out.println("Please choose one of the available options:");
        System.out.println("1. Create a new event.");
        System.out.println("2. Check created events.");
        System.out.println("3. Exit.");
        try {
            MainClass.menuSelection = Integer.parseInt(MainClass.scan.next());
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
    }
}
