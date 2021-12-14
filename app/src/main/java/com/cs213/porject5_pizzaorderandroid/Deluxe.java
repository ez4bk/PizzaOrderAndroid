package com.cs213.porject5_pizzaorderandroid;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


/**
 * An object class that represents Deluxe pizzas. Sub-class extended from Pizza
 * abstract class.
 *
 * @author Yuchen Wei, Denghao Sun
 */

public class Deluxe extends Pizza implements Serializable {

    private static final int MINTOPPINGS = 5;
    private static final double DEFAULT_PRIZE = 12.99;

    /**
     * Constructor of Deluxe Object.
     * 
     * @param size the size of deluxe pizza.
     */
    public Deluxe(Size size) {
        super(size);
        toppings.add(Topping.Onion);
        toppings.add(Topping.Pepperoni);
        toppings.add(Topping.GreenPepper);
        toppings.add(Topping.Mushroom);
        toppings.add(Topping.Sausage);
    }

//    public Deluxe(Parcel in){
//        super(in);
//    }
//
//    public static final Parcelable.Creator<Deluxe> CREATOR = new Parcelable.Creator<Deluxe>(){
//
//        @Override
//        public Deluxe createFromParcel(Parcel parcel) {
//            return new Deluxe(parcel);
//        }
//
//        @Override
//        public Deluxe[] newArray(int size) {
//            return new Deluxe[0];
//        }
//    };
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        super.writeToParcel(dest, flags);
//    }

    /**
     * An override method that computes the price of deluxe pizza with toppings
     * added or removed.
     * 
     * @return the computed price of deluxe pizza.
     */
    @Override
    public double price() {
        double price = DEFAULT_PRIZE;
        price += (toppings.size() - MINTOPPINGS) * PRICEPERTOPPING + this.size.ordinal() * PRICEPERSIZE;
        return price;
    }

    /**
     * An override method that returns the minimum toppings a deluxe pizza can have.
     * 
     * @return the minimum toppings for deluxe pizza.
     */
    @Override
    public int getMinTopping() {
        return MINTOPPINGS;
    }
}
