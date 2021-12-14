package com.cs213.porject5_pizzaorderandroid;

/**
 * Create the instance of a pizza of any kind depend on the flavor selected.
 * With the given flavor, instantiate the corresponding pizza object.
 *
 * @author Yuchen Wei, Denghao Sun
 */
public class PizzaMaker {

    /**
     * Create a pizza instance with the flavor selected by the customer.
     * 
     * @param flavor the style that a pizza can have.
     * @return the created pizza.
     */
    public static Pizza createPizza(String flavor) {
        Pizza pizza = null;
        if (flavor.equals("Deluxe")) {
            pizza = new Deluxe(Size.Small);
        } else if (flavor.equals("Pepperoni")) {
            pizza = new Pepperoni(Size.Small);
        } else if (flavor.equals("Hawaiian")) {
            pizza = new Hawaiian(Size.Small);
        }
        return pizza;
    }
}
