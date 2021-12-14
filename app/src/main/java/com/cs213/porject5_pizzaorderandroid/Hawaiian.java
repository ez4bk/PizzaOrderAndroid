package com.cs213.porject5_pizzaorderandroid;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * An object class that represents Hawaiian pizzas. Sub-class extended from
 * Pizza abstract class.
 *
 * @author Yuchen Wei, Denghao Sun
 */

public class Hawaiian extends Pizza implements Serializable {

    private static final int MINTOPPINGS = 2;
    private static final double DEFAULT_PRIZE = 10.99;

    /**
     * Constructor of Hawaiian Object.
     * 
     * @param size the size of hawaiian pizza.
     */
    public Hawaiian(Size size) {
        super(size);
        toppings.add(Topping.Pineapple);
        toppings.add(Topping.Ham);
    }

//    public Hawaiian(Parcel in){
//        super(in);
//    }
//
//    public static final Parcelable.Creator<Hawaiian> CREATOR = new Parcelable.Creator<Hawaiian>(){
//
//        @Override
//        public Hawaiian createFromParcel(Parcel parcel) {
//            return new Hawaiian(parcel);
//        }
//
//        @Override
//        public Hawaiian[] newArray(int size) {
//            return new Hawaiian[0];
//        }
//    };
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        super.writeToParcel(dest, flags);
//    }

    /**
     * An override method that computes the price of hawaiian pizza with toppings
     * added or removed.
     * 
     * @return the computed price of hawaiian pizza.
     */
    @Override
    public double price() {
        double price = DEFAULT_PRIZE;
        price += (toppings.size() - MINTOPPINGS) * PRICEPERTOPPING + this.size.ordinal() * PRICEPERSIZE;
        return price;
    }

    /**
     * An override method that returns the minimum toppings a hawaiian pizza can
     * have.
     * 
     * @return the minimum toppings for hawaiian pizza.
     */
    @Override
    public int getMinTopping() {
        return MINTOPPINGS;
    }
}
