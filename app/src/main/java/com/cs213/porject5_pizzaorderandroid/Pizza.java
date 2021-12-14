package com.cs213.porject5_pizzaorderandroid;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Abstract class of Pizza.
 *
 * @author Yuchen Wei, Denghao Sun
 */

public abstract class Pizza implements Serializable {

    public static final double PRICEPERSIZE = 2.00;
    public static final double PRICEPERTOPPING = 1.49;

    protected ArrayList<Topping> toppings = new ArrayList<Topping>();
    protected Size size;

    /**
     * Constructor of Pepperoni Object.
     * 
     * @param size the size of pepperoni pizza.
     */
    public Pizza(Size size) {
        this.size = size;
    }

//    public Pizza(Parcel in){
//        this.size = Size.valueOf(in.readString());
//        in.readList(this.toppings, Topping.class.getClassLoader());
//    }
//
//    public static final Parcelable.Creator<Pizza> CREATOR = new Parcelable.Creator<Pizza>(){
//
//        @Override
//        public Pizza createFromParcel(Parcel source) {
//            return null;
//        }
//
//        @Override
//        public Pizza[] newArray(int size) {
//            return new Pizza[0];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(size.toString());
//        dest.writeList(toppings);
//    }

    /**
     * @return the String that contains a pizza's information.
     */
    @Override
    public String toString() {
        String info = "";
        if (this instanceof Deluxe) {
            info += "Deluxe";
        } else if (this instanceof Hawaiian) {
            info += "Hawaiian";
        } else if (this instanceof Pepperoni) {
            info += "Pepperoni";
        }
        info += " pizza,";
        for (Topping t : this.toppings) {
            info += (" " + t + ",");
        }
        info += (" " + size + ",");
        info += (" $" + String.format("%.2f", this.price()));
        return info;
    }

    public String getName(){
        if (this instanceof Deluxe) {
            return "Deluxe";
        } else if (this instanceof Hawaiian) {
            return "Hawaiian";
        } else if (this instanceof Pepperoni) {
            return "Pepperoni";
        }
        return null;
    }

    /**
     * @return minimum number of toppings for each pizza.
     */
    public abstract int getMinTopping();

    /**
     * @return the price calculated of each pizza.
     */
    public abstract double price();

}
