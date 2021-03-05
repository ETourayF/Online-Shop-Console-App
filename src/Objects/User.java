package Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class User {
    private String fullName;
    private static User currentUser;
    private static ArrayList<Order> orderArrayList = new ArrayList<>();
    private ArrayList<Item> basket = new ArrayList<>();
    private static JSONObject currentUserJSONObject;
    private static JSONObject usersJsonObject;
    private static int currentUserLogIndex;

    public User(String fullName, ArrayList<Order> orderArrayList) {
        this.fullName = fullName;
        this.orderArrayList = orderArrayList;
    }

    public static void setCurrentUserLogIndex(int currentUserLogIndex) {
        User.currentUserLogIndex = currentUserLogIndex;
    }

    public static void setCurrentUserJSONObject(JSONObject currentUserJSONObject) {
        User.currentUserJSONObject = currentUserJSONObject;
    }

    public static void setUsersJsonObject(JSONObject usersJsonObject) {
        User.usersJsonObject = usersJsonObject;
    }

    public ArrayList<Order> getOrderArrayList() {
        return orderArrayList;
    }

    public ArrayList<Item> getBasket() {
        return basket;
    }

    public String getFullName() {
        return fullName;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public void logPurchase() {
        showMessage("Completing purchase...");
        if (checkUserExistence(fullName)) {
            JSONArray ordersJSONArray = new JSONArray();
            for (Order order : orderArrayList) {
                ordersJSONArray.put(order.getJSONObject());
            }
            currentUserJSONObject.remove("Orders");
            currentUserJSONObject.put("Orders", ordersJSONArray);
            usersJsonObject.getJSONArray("Users").put(currentUserLogIndex, currentUserJSONObject);
            try {
                FileWriter mFile = new FileWriter("src/DataFiles/UserData.json");
                mFile.write(usersJsonObject.toString(4));
                mFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkUserExistence(String userName) {
        // parsing JSON file
        try {
            String contents = new String(
                    (
                            Files.readAllBytes(Paths.get("src/DataFiles/UserData.json"))
                    ));
            if (contents.isEmpty()) {
                appendNewUser(userName, new JSONObject(contents));
                return false;
            }
            JSONObject readObject = new JSONObject(contents);
            JSONArray tmpArray = readObject.getJSONArray("Users");

            for (int count = 0; count < tmpArray.length(); count++) {
                JSONObject userObject = tmpArray.getJSONObject(count);
                if (userObject.get("Name").equals(userName)) {
                    setCurrentUser(
                            new User(
                                    userObject.get("Name").toString(),
                                    parseOrdersFromJson(userObject.getJSONArray("Orders")))
                    );
                    setCurrentUserJSONObject(userObject);
                    setUsersJsonObject(readObject);
                    setCurrentUserLogIndex(count);
                    return true;
                }
            }
            appendNewUser(userName, readObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static ArrayList<Order> parseOrdersFromJson(JSONArray ordersJsonArray) {
        if (orderArrayList.isEmpty()) {
            ArrayList<Order> loadedOrderArrayList = new ArrayList<>();
            for (int count = 0; count < ordersJsonArray.length(); count++) {
                JSONObject itemObject = ordersJsonArray.getJSONObject(count);
                Order tmpOrder = new Order(itemObject.getString("name"), itemObject.getString("description"), itemObject.getDouble("price"), itemObject.getString("time"));
                loadedOrderArrayList.add(tmpOrder);
            }
            return loadedOrderArrayList;
        }
        return orderArrayList;
    }

    private static void appendNewUser(String userName, JSONObject jsonObj) {
        JSONArray userArray = new JSONArray();

        HashMap<Object, Object> userHashMap = new HashMap<>();
        userHashMap.put("Name", userName);
        userHashMap.put("Orders", new JSONArray());
        userArray.put(userHashMap);

        if (jsonObj.isEmpty()) {
            jsonObj.put("Users", userArray);
        } else {
            jsonObj.getJSONArray("Users").put(userHashMap);
        }

        try {
            FileWriter mFile = new FileWriter("src/DataFiles/UserData.json");
            mFile.write(jsonObj.toString(4));
            mFile.close();
            User.setCurrentUser(new User(userHashMap.get("Name").toString(), new ArrayList<>()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void authenticateUser() {
        //input user name
        Scanner input = new Scanner(System.in);
        System.out.print("insert details(Full Name): ");
        String userName = input.nextLine();
        System.out.println();

        //check for user in json file
        if (User.checkUserExistence(userName)) {
            showMessage("Welcome back, " + User.getCurrentUser().getFullName());
        } else {
            showMessage("Welcome to Big(O) Market, " + User.getCurrentUser().getFullName());
        }
    }

    public static void displayOrders() {
        if (orderArrayList.isEmpty()) {
            showMessage("No orders to display!");
            return;
        }
        System.out.println("==========================================================================================================================");
        System.out.printf("%s's Orders\n", User.getCurrentUser().fullName.toUpperCase());
        System.out.println("==========================================================================================================================");
        System.out.printf("%-5s%-35s%-45s%-15s%s\n", "No.", "Name", "Description", "Price", "Time of order");
        for (int count = 0; count < orderArrayList.size(); count++) {
            Order displayItem = orderArrayList.get(count);
            System.out.printf("%-5s%-35s%-45s£%-15s%s\n", count + 1, displayItem.getName(), displayItem.getDescription(), displayItem.getPrice(), displayItem.getTime());
        }
        System.out.println("==========================================================================================================================\n");
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                '}';
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }

}
