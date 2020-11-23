package main;

import menus.MainMenu;

import java.sql.SQLException;
import java.util.Scanner;

public class MainClass {
    public static final Scanner scan = new Scanner(System.in);
    public static int menuSelection = 0;

    public static void main(String[] args) throws SQLException {
        MainMenu.getMainMenu();
    }
}