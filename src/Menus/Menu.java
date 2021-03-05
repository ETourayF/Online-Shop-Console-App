package Menus;

public abstract class Menu {
    //abstract method
    //all menus must list options (and allow for input)
    public abstract void listOptions();

    //function to check if user input is an integer
    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
