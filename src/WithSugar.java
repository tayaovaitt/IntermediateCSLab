import java.util.List;
/**
 *  Java Coffee Co Ordering and Tracking
 *  WithSugar: A representation of the Sugar customization
 *  Extends the Abstract CoffeeDecorator class
 *  CS160-1001
 *  6/28/2023
 *  @author Taya Medina
 */
public class WithSugar extends CoffeeDecorator {
    public WithSugar(Coffee c) {
        super(c);
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.15;
    }

    @Override
    public String getIngredient(){
        return "Sugar";
    }

    @Override
    public List<String> getIngredients() {
        List<String> ingredients = super.getIngredients();
        ingredients.add("Sugar");
        return ingredients;
    }

    @Override
    public String printCoffee() {
        return super.printCoffee() + " with sugar";
    }
}
