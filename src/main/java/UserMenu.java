public class UserMenu {
    public static void getUserMenu() {
        do {
            System.out.println("Welcome, " + loginSystem.currentUser);
            System.out.println("This is a user menu. Available functions are:");
            System.out.println("1. Check your stats");
            System.out.println("2. Calculate KDA");
            //System.out.println("3. Play accuracy game.");
            System.out.println("3. exit.");
            mainClass.menuSelection = Integer.parseInt(mainClass.scan.next());
            if (mainClass.menuSelection == 1) {
                System.out.println("Kill count: " + loginSystem.currentKills);
                System.out.println("Death count: " + loginSystem.currentDeaths);
                System.out.println("Assist count: " + loginSystem.currentAssists);
            } else if (mainClass.menuSelection == 2) {
                System.out.println("KDA: " + (double) ((loginSystem.currentAssists) / 2 + loginSystem.currentKills) /
                        loginSystem.currentDeaths);
            }
        } while (mainClass.menuSelection != 3);
    }
}
