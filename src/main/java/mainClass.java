import java.sql.*;
import java.util.Scanner;

public class mainClass {
    public static final Scanner scan = new Scanner(System.in);
    public static int selection = 0;
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
                if (loginSystem.loginSuccessful){
                    System.out.println("Welcome, " + loginSystem.currentUser);
                }
            }

        } while (selection!=3);
    }
}