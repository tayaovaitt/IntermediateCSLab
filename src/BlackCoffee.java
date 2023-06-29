import java.util.ArrayList;
import java.util.List;
/**
 *  Java Coffee Co Ordering and Tracking
 *  BlackCoffee Class: Represents a Black Coffee Implementation of the Coffee Interface
 *  CS160-1001
 *  6/28/2023
 *  @author Taya Medina
 */
public class BlackCoffee implements Coffee {
    @Override
    public double getCost() {
        return 1.00;
    }

    @Override
    public String getIngredient(){
        return "Black Coffee";
    }

    @Override
    public List<String> getIngredients() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("Black Coffee");
        return ingredients;
    }

    @Override
    public String printCoffee() {
        return "A black coffee";
    }

    @Override
    public boolean isBaseDrink() {
        return true;
    }
}
