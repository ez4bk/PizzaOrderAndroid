package com.cs213.porject5_pizzaorderandroid;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * An object class that represents Pepperoni pizzas. Sub-class extended from
 * Pizza abstract class.
 *
 * @author Yuchen Wei, Denghao Sun
 */

public class Pepperoni extends Pizza implements Serializable {

    private static final int MINTOPPINGS = 1;
    private static final double DEFAULT_PRIZE = 8.99;

    /**
     * Constructor of Pepperoni Object.
     * 
     * @param size the size of pepperoni pizza.
     */
    public Pepperoni(Size size) {
        super(size);
        toppings.add(Topping.Pepperoni);
    }

//    public Pepperoni(Parcel in){
//        super(in);
//    }
//
//    public static final Parcelable.Creator<Pepperoni> CREATOR = new Parcelable.Creator<Pepperoni>(){
//
//        @Override
//        public Pepperoni createFromParcel(Parcel parcel) {
//            return new Pepperoni(parcel);
//        }
//
//        @Override
//        public Pepperoni[] newArray(int size) {
//            return new Pepperoni[0];
//        }
//    };
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        super.writeToParcel(dest, flags);
//    }

    /**
     * An override method that computes the price of pepperoni pizza with toppings
     * added or removed.
     * 
     * @return the computed price of pepperoni pizza.
     */
    @Override
    public double price() {
        double price = DEFAULT_PRIZE;
        price += (toppings.size() - MINTOPPINGS) * PRICEPERTOPPING + this.size.ordinal() * PRICEPERSIZE;
        return price;
    }

    /**
     * An override method that returns the minimum toppings a pepperoni pizza can
     * have.
     * 
     * @return the minimum toppings for pepperoni pizza.
     */
    @Override
    public int getMinTopping() {
        return MINTOPPINGS;
    }
}
