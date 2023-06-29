import java.util.List;
/**
 *  Java Coffee Co Ordering and Tracking
 *  Coffee Decorator: An abstract class that implements the Coffee interface.
 *  CS160-1001
 *  6/28/2023
 *  @author Taya Medina
 */
public abstract class CoffeeDecorator implements Coffee {
    private Coffee coffee;

    public CoffeeDecorator(Coffee c) {
        coffee = c;
    }
    @Override
    public double getCost() {
        return coffee.getCost();
    }
    @Override
    public List<String> getIngredients() {
        return coffee.getIngredients();
    }
    @Override
    public String printCoffee() {
        return coffee.printCoffee();
    }

    @Override
    public boolean isBaseDrink() {
        return false;
    }
}
