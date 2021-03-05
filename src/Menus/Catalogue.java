package Menus;

import Objects.Item;
import Objects.Order;
import Objects.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Catalogue extends Menu {
    @Override
    public void listOptions() {
        String inp;
        //temporary list to keep original static list safe until changes are saved
        ArrayList<Item> tmpList = new ArrayList<>(Item.getItemArrayList());
        while (true) {
            //go through array list and display all items as options
            System.out.println("Choose items to add to your basket?");
            System.out.println("==========================================================================================================================");
            //format to display as table
            System.out.printf("%-5s%-35s%-45s%-15s%s\n", "No.", "Name", "Description", "Price", "No. in Stock");
            for (int count = 0; count < tmpList.size(); count++) {
                Item displayItem = tmpList.get(count);
                System.out.printf("%-5s%-35s%-45s£%-15s%s\n", count + 1, displayItem.getName(), displayItem.getDescription(), displayItem.getPrice(), displayItem.getNoInStock());
            }
            System.out.println("\n[B]Basket [Q]Back\n");

            //allow for user input
            Scanner myScanner = new Scanner(System.in);
            System.out.print("Option: ");
            inp = myScanner.nextLine();

            //check if input is int or string
            if (isInteger(inp)) {
                //value of user input shouldn't be greater than number of items in list
                if (Integer.parseInt(inp) <= tmpList.size()) {
                    //parse input and get chosen item from list
                    //add chosen item to current user's basket
                    int option = Integer.parseInt(inp);
                    Item item = tmpList.get(option - 1);
                    User.getCurrentUser().getBasket().add(item);
                } else {
                    showMessage("invalid choice");
                }
            } else {
                if (inp.toLowerCase().equals("q")) {
                    break;
                } else if (inp.toLowerCase().equals("b")) {
                    //complete order function displays users basket
                    //returns true if user completes purchase purchase of items in basket
                    if (completeOrder()) {
                        break;
                    }
                } else {
                    showMessage("invalid input");
                }
            }
        }
    }

    private boolean completeOrder() {
        String inp;
        //get current users basket
        User user = User.getCurrentUser();
        ArrayList<Item> basket = user.getBasket();

        while (true) {
            double totalCost = 0; //var for total cost of items in basket

            //display items from basket in table format
            System.out.printf("%s's Basket\n", user.getFullName());
            System.out.println("==========================================================================================================================");
            System.out.printf("%-5s%-35s%-45s%s\n", "No.", "Name", "Description", "Price");
            for (int count = 0; count < basket.size(); count++) {
                Item displayItem = basket.get(count);
                System.out.printf("%-5s%-35s%-45s£%s\n", count + 1, displayItem.getName(), displayItem.getDescription(), displayItem.getPrice());
                totalCost += displayItem.getPrice();//increment total cost for each item in basket
            }
            System.out.printf("\n%s%s\n", "total = £", totalCost);//display total cost
            System.out.println("\nSelect item To Remove From Basket\n");
            System.out.println("\n[C]Complete Order [X]Clear-basket [Q]Back\n");

            //allow for user input
            Scanner myScanner = new Scanner(System.in);
            System.out.print("Option: ");
            inp = myScanner.nextLine();

            //if user selects item then remove it from basket
            if (isInteger(inp)) {
                if (Integer.parseInt(inp) <= basket.size()) {
                    int option = Integer.parseInt(inp);
                    Item item = basket.get(option - 1);
                    basket.remove(item);
                } else {
                    showMessage("invalid choice");
                }
            } else {
                if (inp.toLowerCase().equals("q")) {
                    //return false if user quits (purchase incomplete)
                    return false;
                } else if (inp.toLowerCase().equals("x")) {
                    //return false if user clears the basket (purchase incomplete)
                    user.getBasket().clear();
                    return false;
                } else if (inp.toLowerCase().equals("c")) {
                    //return true if user completes purchase
                    //add items in basket arraylist to users ordersArrayList, save json and clear basket
                    appendOrders();
                    user.logPurchase();
                    user.getBasket().clear();
                    return true;
                } else {
                    showMessage("invalid input");
                }
            }
        }
    }

    //function to save users orders
    private void appendOrders() {
        //get users basket
        ArrayList<Item> basket = User.getCurrentUser().getBasket();

        //get time of order
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        //for each item in basket, append to users 'orderArrayList'
        for (int count = 0; count < basket.size(); count++) {
            Order order = new Order(basket.get(count).getName(), basket.get(count).getDescription(), basket.get(count).getPrice(), sdf.format(cal.getTime()));//add time as well
            User.getCurrentUser().getOrderArrayList().add(order);
        }
    }

    private void showMessage(String message) {
        System.out.println(message);
    }

}
