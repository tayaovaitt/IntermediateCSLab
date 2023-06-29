import java.io.*;
import java.util.*;

/**
 *  Java Coffee Co Ordering and Tracking
 *  Main Method: Builds orders and updates inventory.
 *  CS160-1001
 *  6/28/2023
 *  @author Taya Medina
 */

public class Main {
    private static Map<String, Integer> inventory = new TreeMap<String, Integer>();
    private static List<CoffeeOrder> orders = new ArrayList<CoffeeOrder>();
    private static String logFile = "OrderLog.txt";
    private static String inventoryFile = "Inventory.txt";

    /**
     * Creates a new CoffeeOrder then takes in user input to add drinks, modify the existing orders, or finalize orders.
     * @param scnr Scanner, System.in Scanner originates from main method
     * @return CoffeeOrder, a customer's order
     */
    public static CoffeeOrder buildOrder(Scanner scnr) {
        CoffeeOrder order = new CoffeeOrder();
        char usrInput = 'q';

        while (usrInput != 'x' && usrInput != 'X') {
            System.out.println("A - add coffee\nX - finalize order");
            if (order.getSize() > 0)
                System.out.println("M - modify order");
            usrInput = scnr.next().charAt(0);
            switch (usrInput) {
                case 'a':
                case 'A':
                    Coffee drink = buildCoffee(scnr);
                    if (drink != null)
                        order.addCoffee(drink);
                    break;
                case 'm':
                case 'M':
                    if (order.getSize() > 0)
                        order = modifyOrder(order, scnr);
                    else
                        System.out.println("Order is empty and cannot be modified.");
                    break;
                case 'x':
                case 'X':
                    System.out.println("*********************************************************");
                    break;
                default:
                    System.err.println("Invalid Entry");
            }
        }
        return order;
    }

    /**
     * Called in build order. Takes in user input to modify existing drinks, remove a drink from the order, or clear an order.
     * When an order is cleared, the ingredients are added back to the inventory.
     * @param order CoffeeOrder, a customer's order
     * @param scnr Scanner, System.in Scanner originates from main method
     * @return CoffeeOrder, a customers modifed order
     */
    public static CoffeeOrder modifyOrder(CoffeeOrder order, Scanner scnr) {
        System.out.println("How like to modify the order?");
        System.out.println("m - modify a drink\nd - delete a drink\nc - clear and restart order\nX - exit modifier");
        switch (scnr.next().charAt(0)) {
            case 'm':
            case 'M':
                return drinkModifier(order, drinkSelecter(order, scnr), scnr);
            case 'd':
            case 'D':
                if (!order.getCoffees().isEmpty())
                    return drinkDeleter(order, drinkSelecter(order, scnr));
                else
                    System.err.println("Order is empty.");
            case 'c':
            case 'C':
                for (Coffee drink:order.getCoffees()) {
                    addDrinkToInventory(drink);
                }
                return new CoffeeOrder();
            case 'x':
            case 'X':
                return order;
            default:
                System.err.println("Invalid Entry. Order has not been modified.");
                return order;
        }
    }

    /**
     * Called in modifyOrder when a drink will be deleted or an order has multiple drinks and a single drink needs to be modified
     * Prints drinks with item numbers, takes item number from user input, returns drink represented by item number.
     * @param order CoffeeOrder, a customer's order
     * @param scnr Scanner, System.in Scanner originates from main method
     * @return Coffee, a drink selected by the user
     */
    public static Coffee drinkSelecter(CoffeeOrder order, Scanner scnr) {
        if (order.getSize() > 1) {
            System.out.println(order.printDrinksForSelection());
            int orderSize = order.getSize();

            try {
                int userSelection = scnr.nextInt();

                if (userSelection > 0 && userSelection <= orderSize)
                    return order.getCoffees().get(userSelection - 1);

                System.err.println("Invalid Entry. Please select 1 - " + orderSize);
                return drinkSelecter(order, scnr);
            } catch (InputMismatchException e) {
                System.err.println("Invalid Entry. Please select 1 - " + orderSize);
                scnr.nextLine();
                return drinkSelecter(order, scnr);
            }
        } else {
            return order.getCoffees().get(0);
        }

    }

    /**
     * Called in modifyOrder. Takes in user input to add or remove decorators from a drink.
     * @param order CoffeeOrder, a customer's order
     * @param drink Coffee, a drink within CoffeeOrder
     * @param scnr Scanner, System.in Scanner originates from main method
     * @return CoffeeOrder, an order with the modified drink
     */
    public static CoffeeOrder drinkModifier(CoffeeOrder order, Coffee drink, Scanner scnr) {
        System.out.println("How would you like to modify " + drink.printCoffee());
        System.out.println("a - add a customization");
        if (drink.getIngredients().size() > 1)
            System.out.println("r - remove a customization");
        System.out.println("X - exit modifier");
        char userInput = scnr.next().charAt(0);
        switch (userInput) {
            case 'a':
            case 'A':
                order.replaceDrink(drink, decorateCoffee(drink, scnr));
                System.out.println("Addition Successful");
                return order;
            case 'r':
            case 'R':
                order.replaceDrink(drink, removeSelector(drink, scnr));
                return order;
            case 'x':
            case 'X':
                return order;
            default:
                System.err.println("Invalid Entry.");
                return order;
        }
    }

    /**
     * Takes in user input and calls deDecorate to return a drink with the user selected customization removed.
     * @param drink Coffee, a drink within CoffeeOrder
     * @param scnr Scanner, System.in Scanner originates from main method
     * @return Coffee, the coffee passed in without the decorator removed
     */
    public static Coffee removeSelector(Coffee drink, Scanner scnr) {
        List<String> ingredients = drink.getIngredients();
        System.out.println("What would you like to remove?");
        List<Character> validEntries = new ArrayList<>();
        if (ingredients.contains("Whipped Cream")) {
            System.out.println("w - whipped cream");
            validEntries.add('w');
            validEntries.add('W');
        }
        if (ingredients.contains("Sugar")) {
            System.out.println("s - sugar");
            validEntries.add('s');
            validEntries.add('S');
        }
        if (ingredients.contains("Milk")) {
            System.out.println("m - milk");
            validEntries.add('m');
            validEntries.add('M');
        }
        if (ingredients.contains("Hot Water")) {
            System.out.println("h - hot water");
            validEntries.add('h');
            validEntries.add('H');
        }
        if (ingredients.contains("VANILLA Syrup")) {
            System.out.println("v - vanilla");
            validEntries.add('v');
            validEntries.add('V');
        }
        if (ingredients.contains("CARAMEL Syrup")) {
            System.out.println("c - caramel");
            validEntries.add('c');
            validEntries.add('C');
        }
        if (ingredients.contains("MOCHA Syrup")) {
            System.out.println("o - mocha");
            validEntries.add('o');
            validEntries.add('O');
        }
        System.out.println("x - exit removal");
        validEntries.add('x');
        validEntries.add('X');

        char userInput = scnr.next().charAt(0);

        if (validEntries.contains(userInput)) {
            String itemToRemove;
            switch (userInput) {
                case 'w':
                case 'W':
                    return deDecorate(drink, "Whipped Cream");
                case 's':
                case 'S':
                    return deDecorate(drink, "Sugar");
                case 'm':
                case 'M':
                    return deDecorate(drink, "Milk");
                case 'h':
                case 'H':
                    return deDecorate(drink, "Hot Water");
                case 'v':
                case 'V':
                    return deDecorate(drink, "VANILLA Syrup");
                case 'c':
                case 'C':
                    return deDecorate(drink, "CARAMEL Syrup");
                case 'o':
                case 'O':
                    return deDecorate(drink, "MOCHA Syrup");
                case 'x':
                case 'X':
                    return drink;
                default:
                    break;
            }
        }
        System.err.println("Invalid Entry. The drink was not modified.");
        return drink;
    }

    /**
     * A helper method that transforms a Coffee object into a list of ingredients, removes the specified ingredient from the list,
     * creates a drink from the modified ingredient list, and returns the new drink
     * @param drink Coffee, a drink within a CoffeeOrder
     * @param ingredientToRemove String, passed in from removeSelector that matches an entry in inventoryFile
     * @return Coffee, the coffee passed in without the decorator removed
     */
    public static Coffee deDecorate(Coffee drink, String ingredientToRemove) {
        List<String> ingredients = drink.getIngredients();
        ingredients.remove(ingredientToRemove);
        addToInventory(ingredientToRemove);
        return ingredientsToDrink(ingredients);
    }

    /**
     * Transforms a list of Coffee ingredients into a Coffee objects containing those ingredients.
     * @param ingredients List<String>, a list of drink ingredients
     * @return Coffee, a Coffee object that exactly contains ingredients in the String list
     */
    public static Coffee ingredientsToDrink(List<String> ingredients) {
        Coffee drink;
        if (ingredients.get(0).equals("Black Coffee"))
            drink = new BlackCoffee();
        else {
            drink = new Espresso();
        }
        for (String ingredient : ingredients) {
            switch (ingredient) {
                case "Whipped Cream":
                    drink = new WithWhippedCream(drink);
                    break;
                case "Sugar":
                    drink = new WithSugar(drink);
                    break;
                case "Milk":
                    drink = new WithMilk(drink);
                    break;
                case "Hot Water":
                    drink = new WithHotWater(drink);
                    break;
                case "VANILLA Syrup":
                    drink = new WithFlavor(drink, Syrup.VANILLA);
                    break;
                case "CARAMEL Syrup":
                    drink = new WithFlavor(drink, Syrup.CARAMEL);
                    break;
                case "MOCHA Syrup":
                    drink = new WithFlavor(drink, Syrup.MOCHA);
                    break;
            }
        }
        return drink;
    }

    /**
     * Returns the coffee order passed in without the drink passed in
     * @param order CoffeeOrder, a customer's order
     * @param drink Coffee, a drink within order
     * @return CoffeeOrder, the coffee order passed in without the drink passed in
     */
    public static CoffeeOrder drinkDeleter(CoffeeOrder order, Coffee drink) {
        order.removeDrink(drink);
        System.out.println(drink.printCoffee() + " deleted.");
        addDrinkToInventory(drink);
        return order;
    }

    /**
     * Returns a new non-decorator Coffee Object as specified by user input
     * @param scnr Scanner, System.in Scanner originates from main method
     * @return Coffee, either a BlackCoffee or Espresso
     */
    public static Coffee buildCoffee(Scanner scnr) {
        Coffee drink = null;
        while (drink == null) {
            System.out.println("B - black coffee\nE - espresso\nX - exit drink builder");
            switch (scnr.next().charAt(0)) {
                case 'b':
                case 'B':
                    drink = new BlackCoffee();
                    break;
                case 'e':
                case 'E':
                    drink = new Espresso();
                    break;
                case 'x':
                case 'X':
                    return null;
                default:
                    System.err.println("Invalid entry.");
            }
            if (drink != null && !removeIfInInventory(drink.getIngredients().get(0))) {//checks to  make sure the drink is in inventory
                drink = null;
            }
        }
        drink = decorateCoffee(drink, scnr);
        return drink;
    }

    /**
     * buildCoffee helper. Adds Flavor, Hot Water, Milk, Sugar, Whipped Cream as specified by user input
     * @param drink Coffee, a drink within a CoffeeOrder
     * @param scnr Scanner, System.in Scanner originates from main method
     * @return Coffee, a decorated coffee object
     */
    public static Coffee decorateCoffee(Coffee drink, Scanner scnr) {
        Coffee beverage = drink;
        char usrInput = 'q';
        String prompt = "What would you like to add?\nx - no additions. add coffee to order. \nf - add a flavor\nh - add hot water\nm - add milk\ns - add sugar\nw - add whipped cream\n";
        while (usrInput != 'x' && usrInput != 'X') {
            System.out.println(prompt);
            usrInput = scnr.next().charAt(0);
            switch (usrInput) {
                case 'f'://adds flavor
                case 'F':
                    beverage = addFlavor(beverage, scnr);
                    break;
                case 'h': //adds hot water
                case 'H':
                    if (removeIfInInventory("Hot Water")) {
                        beverage = new WithHotWater(beverage);
                        System.out.println("Hot water added.");
                    }
                    break;
                case 'm': //adds milk
                case 'M':
                    if (removeIfInInventory("Milk")) {
                        beverage = new WithMilk(beverage);
                        System.out.println("Milk added.");
                    }
                    break;
                case 's'://adds sugar
                case 'S':
                    if (removeIfInInventory("Sugar")) {
                        beverage = new WithSugar(beverage);
                        System.out.println("Sugar added.");
                    }
                    break;
                case 'w'://adds whipped cream
                case 'W':
                    if (removeIfInInventory("Whipped Cream")) {
                        System.out.println("Whipped cream added.");
                        beverage = new WithWhippedCream(beverage);
                    }
                    break;
                case 'x':
                case 'X':
                    System.out.println("Drink added to order.");
                    break;
                default:
                    System.err.println("Invalid entry.");
            }
        }
        return beverage;
    }

    /**
     * Called in decorateCoffee when a flavor is added. Adds a syrup as specified by user input
     * @param drink Coffee, a drink within a CoffeeOrder
     * @param scnr Scanner, System.in Scanner originates from main method
     * @return Coffee, a Coffee objected decorated with a syrup
     */
    public static Coffee addFlavor(Coffee drink, Scanner scnr) {
        Coffee beverage = drink;
        char usrInput = 'q';
        String prompt = "What flavor would you like to add?\nc - caramel\nv - vanilla\nm - mocha\n";
        System.out.println(prompt);
        usrInput = scnr.next().charAt(0);
        switch (usrInput) {
            case 'c'://adds Caramel
            case 'C':
                if (removeIfInInventory("CARAMEL Syrup")) {
                    beverage = new WithFlavor(beverage, Syrup.CARAMEL);
                    System.out.println("Caramel added.");
                }
                break;
            case 'v'://adds Vanilla
            case 'V':
                if (removeIfInInventory("VANILLA Syrup")) {
                    System.out.println("Vanilla added.");
                    beverage = new WithFlavor(beverage, Syrup.VANILLA);
                }
                break;
            case 'm'://adds Mocha
            case 'M':
                if (removeIfInInventory("MOCHA Syrup")) {
                    System.out.println("Mocha added.");
                    beverage = new WithFlavor(beverage, Syrup.MOCHA);
                }
                break;
            default:
                System.err.println("Invalid entry. No syrup was added.");
        }
        return beverage;
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        char usrInput = 'q';
        inventory = readInventory();
        do {
            System.out.println("A - add order\n" +
                    "R - reload inventory\n" +
                    "I - update inventory\n" +
                    "O - update order log\n" +
                    "X - exit program\n");
            usrInput = scnr.next().charAt(0);
            switch (usrInput) {
                case 'a':
                case 'A'://creates a new order
                    System.out.println("New Order Created.");
                    CoffeeOrder order = buildOrder(scnr);
                    orders.add(order);
                    System.out.println(order.printOrder());
                    System.out.println("*********************************************************");
                    break;
                case 'r':
                case 'R'://reloads inventory
                    inventory = readInventory();
                    System.out.println(inventory);
                    break;
                case 'i':
                case 'I'://updates inventory
                    writeInventory();
                    break;
                case 'o':
                case 'O'://updates order log
                    writeOrderLog();
                    break;
                case 'x':
                case 'X'://exits program
                    writeOrderLog();
                    orders = new ArrayList<CoffeeOrder>();
                    writeInventory();
                    break;
                default:
                    System.err.println("Invalid Entry");
            }

        } while (usrInput != 'x' && usrInput != 'X'); //terminates when user enters 'X' or 'x'
    }

    /**
     * Reads a txt file where each line contains an inventory item's name, "=", and the available quantity of the item.
     * Parses the read file into a <String, Integer> map.
     * @param filePath String, indicates the file path of the txt file to be read
     * @return Map<String, Integer>, Contains a map of inventory items and their quantities.
     */
    private static Map<String, Integer> readInventory(String filePath) {
        Map<String, Integer> invMap = new TreeMap<>();

        try (BufferedReader bRead = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            String nextLine;
            while ((nextLine = bRead.readLine()) != null) {
                String[] invLine = nextLine.split("=");
                invMap.put(invLine[0].strip(), Integer.parseInt(invLine[1].strip()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return invMap;
    }

    //given no filepath, readInventory is called with variable: inventoryFile
    private static Map<String, Integer> readInventory() {
        return readInventory(inventoryFile);
    }

    /**
     * References inventory to update the txt file at filepath so that
     * each line contains an inventory item's name, "=", and the available quantity of the item.
     * @param filePath String, indicates the file path of the txt file to be written
     */
    private static void writeInventory(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {

            for (Map.Entry<String, Integer> item : inventory.entrySet()) {
                writer.write(item.getKey() + "=" + item.getValue() + "\n");
            }
            writer.flush();
            System.out.println("Inventory updated.");
        } catch (IOException e) {
            System.err.println("Error writing to file " + filePath);
        }
    }

    //given no filepath, writeInventory is called with variable: inventoryFile
    private static void writeInventory() {
        writeInventory(inventoryFile);
    }

    //writes the orders string to the given file path.

    /**
     * Saves .printOrder() data for each method in order at the given filepath
     * @param filePath String, indicates the file path of the txt file to be written
     */
    private static void writeOrderLog(String filePath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (CoffeeOrder order : orders) {
                writer.write(order.printOrder());
            }
            writer.flush();
            System.out.println("Order log updated.");
        } catch (IOException e) {
            System.err.println("Error writing to file " + filePath);
        }
    }

    //given no filepath, writeOrderLog is called with logfile
    private static void writeOrderLog() {
        writeOrderLog(logFile);
    }

    /**
     * Adds 1 of the specified ingredient to the inventory count
     * @param ingredient String, string must match entry in inventoryFile
     */
    private static void addToInventory(String ingredient){
        inventory.put(ingredient,(inventory.get(ingredient)+1));
    }

    /**
     * Iterates through the ingredients of Coffee object and adds 1 of each ingredient to the inventory count
     * @param drink Coffee, being removed from an order in another method and its ingredients should be added back to the inventory
     */
    private static void addDrinkToInventory(Coffee drink){
        List<String> ingredients = drink.getIngredients();
        for (String ingredient:ingredients){
            addToInventory(ingredient);
        }
    }

    /**
     * Calls and returns the ouput of isInInventory. Reduces inventory of the called item by one if available.
     * @param i String, an ingredient that is being searched for and decremented in inventory
     * @return boolean, Returns true if available and false if not in inventory.
     */
    private static boolean removeIfInInventory(String i){
        boolean inInventory = isInInventory(i);
        if (inInventory)
            inventory.put(i,(inventory.get(i)-1));
        return inInventory;
    }

    /**
     * Checks if item is in inventory.
     * @param i String, an ingredient that is being searched for in inventory
     * @return boolean, Returns true if available and false if not in inventory.
     */
    private static boolean isInInventory(String i){
        int numLeft = inventory.getOrDefault(i,0);
        if (numLeft >0)
            return true;
        else {
            System.err.println(i + " is not currently available.");
            return false;
        }
    }


}