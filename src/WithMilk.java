import java.util.List;
/**
 *  Java Coffee Co Ordering and Tracking
 *  WithMilk: A representation of the Milk customization
 *  Extends the Abstract CoffeeDecorator class
 *  CS160-1001
 *  6/28/2023
 *  @author Taya Medina
 */
public class WithMilk extends CoffeeDecorator {
    public WithMilk(Coffee c) {
        super(c);
    }

    @Override
    public double getCost() {
        return super.getCost() + .55;
    }


    @Override
    public String getIngredient(){
        return "Milk";
    }

    @Override
    public List<String> getIngredients() {
        List<String> ingredients = super.getIngredients();
        ingredients.add("Milk");
        return ingredients;
    }

    @Override
    public String printCoffee() {
        return super.printCoffee() + " with milk";
    }
}
