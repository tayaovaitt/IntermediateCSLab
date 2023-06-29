import java.util.ArrayList;
import java.util.List;
/**
 *  Java Coffee Co Ordering and Tracking
 *  WithWhippedCream: A representation of the Whipped Cream customization
 *  Extends the Abstract CoffeeDecorator class
 *  CS160-1001
 *  6/28/2023
 *  @author Taya Medina
 */
public class WithWhippedCream extends CoffeeDecorator {
    public WithWhippedCream(Coffee c) {
        super(c);
    }

    @Override
    public double getCost() {

        return super.getCost() + .25;
    }

    @Override
    public String getIngredient(){
        return "Whipped Cream";
    }

    @Override
    public List<String> getIngredients() {
        List<String> ingredients = super.getIngredients();
        ingredients.add("Whipped Cream");
        return ingredients;
    }

    @Override
    public String printCoffee() {

        return super.printCoffee() + " with whipped cream";
    }
}
