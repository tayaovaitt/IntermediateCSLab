import java.util.List;
/**
 *  Java Coffee Co Ordering and Tracking
 *  WithFlavor: A representation of flavored syrup enum
 *  Extends the Abstract CoffeeDecorator class
 *  CS160-1001
 *  6/28/2023
 *  @author Taya Medina
 */
public class WithFlavor extends CoffeeDecorator {

    private Syrup syrupFlavor;

    public WithFlavor(Coffee c, Syrup s) {
        super(c);
        syrupFlavor = s;
    }

    @Override
    public double getCost() {
        return super.getCost() + .35;
    }

    @Override
    public List<String> getIngredients() {
        List<String> ingredients = super.getIngredients();
        ingredients.add(this.syrupFlavor + " Syrup");
        return ingredients;
    }

    @Override
    public String getIngredient(){
        return this.syrupFlavor + " Syrup";
    }

    @Override
    public String printCoffee() { return super.printCoffee() + " with " + this.syrupFlavor;
    }

}
