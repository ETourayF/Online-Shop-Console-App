package Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Item {
    private String name, description;
    private int noInStock;
    private double price;
    private static ArrayList<Item> itemArrayList;
    private static JSONObject itemsJsonObject;

    public Item(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Item(String name, String description, int noInStock, double price) {
        this.name = name;
        this.description = description;
        this.noInStock = noInStock;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNoInStock(int noInStock) {
        this.noInStock = noInStock;
    }

    public int getNoInStock() {
        return noInStock;
    }

    public void incrementStock(int incrementAmount) {
        this.noInStock += incrementAmount;
    }

    public static ArrayList<Item> getItemArrayList() {
        return itemArrayList;
    }

    public static void setItemArrayList(ArrayList<Item> itemArrayList) {
        Item.itemArrayList = itemArrayList;
    }

    public static JSONObject getItemsJsonObject() {
        return itemsJsonObject;
    }

    public static void setItemsJsonObject(JSONObject itemsJsonObject) {
        Item.itemsJsonObject = itemsJsonObject;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", name);
            obj.put("description", description);
            obj.put("price", String.valueOf(price));
            obj.put("no.InStock", String.valueOf(noInStock));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static void loadItemFromFile() {
        //read item stock file
        itemArrayList = new ArrayList<>();
        try {
            String contents = new String((Files.readAllBytes(Paths.get("src/DataFiles/ItemStock.json"))));
            itemsJsonObject = new JSONObject(contents);
            JSONArray itemsArray = itemsJsonObject.getJSONArray("Items");
            Item item;

            for (int count = 0; count < itemsArray.length(); count++) {
                JSONObject itemObject = itemsArray.getJSONObject(count);
                item = new Item(itemObject.getString("name"),
                        itemObject.getString("description"),
                        itemObject.getInt("no.InStock"),
                        itemObject.getDouble("price"));
                itemArrayList.add(item);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveItemsToFile(ArrayList<Item> updatedList) {
        Item.setItemArrayList(updatedList);
        showMessage("Updating Stock...");
        JSONArray itemsJsonArray = new JSONArray();
        for (Item item : itemArrayList) {
            itemsJsonArray.put(item.getJSONObject());
        }

        itemsJsonObject.remove("Items");
        itemsJsonObject.put("Items", itemsJsonArray);

        try {
            FileWriter mFile = new FileWriter("src/DataFiles/ItemStock.json");
            mFile.write(itemsJsonObject.toString(4));
            mFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", noInStock=" + noInStock +
                ", price=" + price +
                '}';
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }

}