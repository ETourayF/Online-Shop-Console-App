package Menus;

import Objects.Item;

import java.util.ArrayList;
import java.util.Scanner;

public class StockMenu extends Menu {

    @Override
    public void listOptions() {
        String inp;
        //temporary list to keep original static list safe until changes are saved
        ArrayList<Item> tmpList = new ArrayList<>(Item.getItemArrayList());
        while (true) {
            //go through array list and display all items as options
            System.out.println("Pick an item to restock");
            System.out.println("=========================================================");
            for (int count = 0; count < tmpList.size(); count++) {
                Item displayItem = tmpList.get(count);
                System.out.printf("[%s]%s - no. in Stock: %s", count + 1, displayItem.getName(), displayItem.getNoInStock());
                System.out.println();
            }
            //option to save and also to quit
            System.out.println("[S]save [Q]Quit");
            System.out.println();

            //allow for user input
            Scanner myScanner = new Scanner(System.in);
            System.out.print("Option: ");
            inp = myScanner.nextLine();

            //check if input is int or string
            if (isInteger(inp)) {
                //value of user input shouldn't be greater than number of items in list
                if (Integer.parseInt(inp) <= tmpList.size()) {
                    //parse input and get chosen item from list
                    int option = Integer.parseInt(inp);
                    Item item = tmpList.get(option - 1);
                    //let user enter how much of the item they would like to add
                    System.out.printf("[%s] Amount to add(Q=cancel): ", item.getName());
                    inp = myScanner.nextLine();
                    if (isInteger(inp)) {
                        //increment item by provided value
                        item.incrementStock(Integer.parseInt(inp));
                    } else {
                        //cancel restock if input is q
                        if (inp.toLowerCase().equals("q")) {
                            return;
                        }
                    }
                } else {
                    showMessage("invalid choice");
                }
            } else {
                //quit (changes not saved)
                if (inp.toLowerCase().equals("q")) {
                    break;
                } else if (inp.toLowerCase().equals("s")) {
                    //save (changes are reflected in items json file)
                    Item.saveItemsToFile(tmpList);
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
