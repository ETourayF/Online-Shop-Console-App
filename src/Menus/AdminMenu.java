package Menus;

import java.util.Scanner;

public class AdminMenu extends Menu {
    @Override
    public void listOptions() {
        while (true) {
            //list options
            System.out.println();
            System.out.println("Admin");
            System.out.println("=========================================================");
            System.out.println("[1]Stock Item");
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
                    case 1 -> new StockMenu().listOptions();
                    default -> showMessage("invalid input");
                }
            } else {
                //if input isn't a number then check if user is trying to quit
                if (String.valueOf(inp).equals("Q") || String.valueOf(inp).equals("q")) {
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
