import java.sql.*;
import java.util.Scanner;

public class mainClass {
    public static final Scanner scan = new Scanner(System.in);
    public static int selection = 0;
    public static int menuSelection = 0;
    public static String url = "jdbc:mysql://localhost:3306/airsoftapp?useSSL=false";
    public static String username = "root"; //  username
    public static String password = "z_1755a1B!2c,/3Jk"; // password

    public static void main(String[] args) throws SQLException {

        do {
            loginSystem.getSelection();
            if (selection == 1){
                loginSystem.userCreation(url, username, password);
            }
            else if (selection == 2){
                loginSystem.getUsersFromDb(url, username, password);
                loginSystem.userListComparison = loginSystem.userList;
                loginSystem.loginCheck();
                if (loginSystem.loginSuccessful && loginSystem.userIsAdmin > 0){
                    System.out.println("Welcome, " + loginSystem.currentUser);
                    System.out.println("This is an Admin menu");
                }
                else if (loginSystem.loginSuccessful && loginSystem.userIsAdmin == 0){
                    do {
                        System.out.println("Welcome, " + loginSystem.currentUser);
                        System.out.println("This is a user menu. Available functions are:");
                        System.out.println("1. Check your stats");
                        System.out.println("2. Calculate KDA");
                        //System.out.println("3. Play accuracy game.");
                        System.out.println("3. exit.");
                        menuSelection = Integer.parseInt(scan.next());
                        if (menuSelection==1){
                            System.out.println("Kill count: " + loginSystem.currentKills);
                            System.out.println("Death count: " + loginSystem.currentDeaths);
                            System.out.println("Assist count: " + loginSystem.currentAssists);
                        }
                        else if (menuSelection==2){
                            System.out.println("KDA: " + (double)((loginSystem.currentAssists)/2+loginSystem.currentKills)/
                                    loginSystem.currentDeaths);
                        }
                    } while (menuSelection!=3);
                }
                else if (!loginSystem.loginSuccessful){
                    System.out.println("Login was unsuccessful.");
                }
            }

        } while (selection!=3);
    }
}