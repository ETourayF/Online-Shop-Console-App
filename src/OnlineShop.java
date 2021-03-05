import Menus.MainMenu;
import Menus.Menu;
import Objects.Item;
import Objects.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.*;

import static Objects.Item.*;


public class OnlineShop {
    public static void main(String[] args) {
        //initialise the program and open up the main menu
        initialise();
        new MainMenu().listOptions();
    }

    private static void initialise() {
        //check if items json file exists -> if not create one -> if one exists then check if empty -> if empty then initialise it with some items
        File itemsFile = new File("src/DataFiles/ItemStock.json");
        if (itemsFile.length() == 0) {
            ArrayList<Item> initialItemList = new ArrayList<>();
            Item.setItemArrayList(new ArrayList<>());
            Item.setItemsJsonObject(new JSONObject());
            initialItemList.add(new Item("Assasins creed: Valhalla", "3rd person action adventure game", 10, 52.00));
            initialItemList.add(new Item("Call Of Duty: Cold War", "1st person shooter", 3, 60.00));
            initialItemList.add(new Item("Ghost Of Tsushima", "3rd person action adventure/melee", 7, 35.00));
            saveItemsToFile(initialItemList);
        } else {
            //otherwise load list of existing items
            loadItemFromFile();
        }

        //check if user json file exists -> if exists, check if empty -> if empty, initialise with empty "users" array
        File usersFile = new File("src/DataFiles/UserData.json");
        if (usersFile.length() == 0) {
            JSONObject userJsonObject = new JSONObject();
            userJsonObject.put("Users", new JSONArray());
            try {
                FileWriter mFile = new FileWriter("src/DataFiles/UserData.json");
                mFile.write(userJsonObject.toString(4));
                mFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}