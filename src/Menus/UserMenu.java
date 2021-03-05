package Menus;

import Objects.User;

import java.util.Scanner;

public class UserMenu extends Menu {

    @Override
    public void listOptions() {
        User.authenticateUser();
        while (true) {
            //list options
            System.out.println("\n=========================================================");
            System.out.printf("Hi, %s\n", User.getCurrentUser().getFullName().toUpperCase());
            System.out.println("=========================================================");
            System.out.println("[1]Catalogue");
            System.out.println("[2]Order History");
            System.out.println("[Q]Back");
            System.out.println();

            //allow for user input
            Scanner myScanner = new Scanner(System.in);
            System.out.print("Option: ");
            String inp = myScanner.nextLine();
            System.out.println();

            //check if input is int or string
            if (isInteger(inp)) {
                //if integer then parse to get the numerical value of the input
                int option = Integer.parseInt(inp);
                //actions based on option
                switch (option) {
                    //catalogue is classed as a menu...
                    //...because options are listed and input is allowed to...
                    //...pick from options
                    case 1 -> new Catalogue().listOptions();
                    case 2 -> User.displayOrders();
                    default -> showMessage("invalid input");
                }
            } else {
                //if input isn't a number then check if user is trying to quit
                if (String.valueOf(inp).equals("Q") || String.valueOf(inp).equals("q")) {
                    //reset current user static variables
                    User.setCurrentUser(null);
                    User.setCurrentUserLogIndex(0);
                    User.setCurrentUserJSONObject(null);
                    break;
                } else {
                    //input isn't an option
                    showMessage("invalid input");
                }
            }
        }
    }

    private void showMessage(String message) {
        System.out.println(message);
    }

}
