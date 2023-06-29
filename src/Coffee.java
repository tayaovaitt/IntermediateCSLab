import java.util.List;

public interface Coffee {

    /**
     * Returns the object's cost.
     * @return double, cost of Coffee object
     */
    public double getCost();

    /**
     * Retuns the object's ingredients.
     * @return List<String>, list of ingredients
     */
    public List<String> getIngredients();
    /**
     * Retuns the object's most recently added ingredient.
     * @return String, an ingredient
     */
    public String getIngredient();

    /**
     * Prints the contents of a coffee object in sentence form.
     * @return String,
     */
    public String printCoffee();

    /**
     * Distinguishes between drink types and drink customizations.
     * @return boolean, returns true if Black Coffee or Espresso, false if a decorator.
     */
    public boolean isBaseDrink();

}
