import java.util.List;
/**
 *  Java Coffee Co Ordering and Tracking
 *  WithHotWater: A representation of the Hot Water customization
 *  Extends the Abstract CoffeeDecorator class
 *  CS160-1001
 *  6/28/2023
 *  @author Taya Medina
 */
public class WithHotWater extends CoffeeDecorator {
    public WithHotWater(Coffee c) {
        super(c);
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.0;
    }

    @Override
    public String getIngredient(){
        return "Hot Water";
    }

    @Override
    public List<String> getIngredients() {
        List<String> ingredients = super.getIngredients();
        ingredients.add("Hot Water");
        return ingredients;
    }

    @Override
    public String printCoffee() {
        return super.printCoffee() + " with hot water";
    }
}
