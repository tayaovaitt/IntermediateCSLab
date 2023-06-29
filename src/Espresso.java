import java.util.ArrayList;
import java.util.List;
/**
 *  Java Coffee Co Ordering and Tracking
 *  Espresso Class: Represents an Espresso Implementation of the Coffee Interface
 *  CS160-1001
 *  6/28/2023
 *  @author Taya Medina
 */
public class Espresso implements Coffee {
    @Override
    public double getCost() {
        return 1.75;
    }

    @Override
    public String getIngredient(){
        return "Espresso";
    }

    @Override
    public List<String> getIngredients() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("Espresso");
        return ingredients;
    }

    @Override
    public String printCoffee() {
        return "An espresso";
    }

    @Override
    public boolean isBaseDrink() {
        return true;
    }
}

