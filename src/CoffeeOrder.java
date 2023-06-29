import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 *  Java Coffee Co Ordering and Tracking
 *  CoffeeOrder: A representation of a customer's order comprised of a list of Coffees
 *  CS160-1001
 *  6/28/2023
 *  @author Taya Medina
 */
public class CoffeeOrder {
    private List<Coffee> coffees;
    private LocalDateTime orderDate;

    public CoffeeOrder () {
        this.coffees = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
    }

    public CoffeeOrder (LocalDateTime ldt) {
        this.coffees = new ArrayList<>();
        this.orderDate = ldt;
    }

    /**
     * Adds a Coffee object to CoffeeOrder's coffees
     * @param c Coffee, drink to add to order
     */
    public void addCoffee(Coffee c) {
        this.coffees.add(c);
    }

    public List<Coffee> getCoffees() {
        return coffees;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public double getTotal() {
        double orderCost = 0;
        for (Coffee coffee:coffees) {
           orderCost += coffee.getCost();
        }
        return orderCost;
    }

    private String getFormattedDateTime(){ //helper method
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mma");
        return orderDate.format(dtf);
    }

    /**
     * Formats and prints coffee order for order summaries and order log appending
     * @return String, coffee order info
     */
    public String printOrder() {
        StringBuilder orderString = new StringBuilder("ORDER RECEIPT\n");
        orderString.append("Timestamp: " + getFormattedDateTime() + "\n");
        int itemNum = 1;
        for (Coffee coffee:coffees) {
            orderString.append(String.format("Item %d: %s - %.2f\n",itemNum, coffee.printCoffee(),coffee.getCost()));
            itemNum ++;
        }
        orderString.append(String.format("TOTAL = %.2f\n",this.getTotal()));
        return orderString.toString();
    }

    /**
     * Formats and prints coffee order for user  selection
     * @return String, coffee order info
     */
    public String printDrinksForSelection() {
        StringBuilder orderString = new StringBuilder("Select Drink Number:\n");
        int itemNum = 1;
        for (Coffee coffee:coffees) {
            orderString.append(String.format("%d: %s\n",itemNum, coffee.printCoffee()));
            itemNum ++;
        }
        return orderString.toString();
    }

    public int getSize(){
        int itemNum = 0;
        for (Coffee coffee:coffees) {
            itemNum ++;
        }
        return itemNum;
    }

    /**
     * Removes a drink from coffees.
     * @param drink Coffee, drink to be removed from coffees
     */
    public void removeDrink(Coffee drink){
        for (Coffee beverage:this.coffees) {
            if(beverage.equals(drink)) {
                coffees.remove(beverage);
                return;
            }
        }
    }

    /**
     * Replaces currentDrink with newDrink in coffees.
     * @param currentDrink Coffee drink to be replaced.
     * @param newDrink Coffee drink to replace current drink with.
     */
    public void replaceDrink(Coffee currentDrink, Coffee newDrink){
        for (int i = 0; i < coffees.size() ; i++) {
            if(coffees.get(i).equals(currentDrink)) {
                coffees.set(i,newDrink);
                return;
            }
        }
    }

}
