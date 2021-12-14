package com.cs213.porject5_pizzaorderandroid;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

/**
 * Main Activity that handles ordering functions. Handles pizza selection,
 * customer phone number verification, and order manipulations.
 *
 * @author Yuchen Wei, Denghao Sun
 */

public class MainActivity extends AppCompatActivity {

    private TextView phoneNumberTextView;
    private Button pizzaButton;

    private Pizza pizza = null;
    private String pizzaName;
    private String customerNumber;
    private ArrayList<Order> orderList = new ArrayList<Order>();
    private static Order order;
    private static final int GET_ORDER_CODE = 1;
    private static final int GET_ORDER_BACK = 2;
    private static final int PLACE_ORDER_CODE = 3;
    private static final int PLACE_ORDER_BACK = 4;
    private static final int ORDER_LIST_CODE = 5;
    private static final int ORDER_LIST_BACK = 6;
    private static final int NULL_SIZE = 0;

    /**
     * Activity Initialization. Find and initialize the TextView in the view for phone number.
     *
     * @param savedInstanceState for creating.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneNumberTextView = findViewById(R.id.phoneNumberText);
    }

    /**
     * Showing a toast message.
     *
     * @param alertTitle title of the toast message.
     * @param alertMessage detailed content of the toast message.
     */
    private void showToast(String alertTitle, String alertMessage) {
        Toast toast = Toast.makeText(getApplicationContext(),
                alertTitle + "\n" + alertMessage, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Check if a new order is needed to be created.
     *
     * @param number input to be checked.
     * @return true if a different number is entered, false otherwise.
     */
    private boolean checkNewOrder(String number) {
        if (order != null) {
            if (order.isSameOrder(number)) {
                return false;
            }
            showToast("Warning!",
                    "A different phone number is entered.\n" +
                            "The previous order is suspend.\nNew order number: " + number);
        }
        return true;
    }

    /**
     * Check if the phone number input is valid. Only non-empty and integer parsable
     * string is allowed.
     *
     * @param number string input to be checked.
     * @return true if the number is valid, false otherwise.
     */
    private boolean checkPhoneNumber(String number) {
        try {
            if (number.isEmpty()) {
                throw new NullPointerException("Empty Phone Number!");
            } else {
                try {
                    Long.parseLong(number);
                } catch (NumberFormatException e) {
                    showToast("Warning!",
                            "Invalid input: " + number + "\nPlease enter numbers only.");
                    return false;
                }
                customerNumber = number;
            }
        } catch (NullPointerException e) {
            showToast("Warning!", "Please enter a phone number.");
            return false;
        }
        return true;
    }

    /**
     * Place order to the order list.
     *
     * @param order to be placed.
     */
    private void placeOrder(Order order) {
        if (order.getPizzas().size() == NULL_SIZE){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Order has no pizzas!\n" +
                            "Please order at least on pizza.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        for (Order o : orderList) {
            if (o.isSameOrder(order.getOrderNumber())) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Order already exists!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
        }
        orderList.add(order);
        Toast toast = Toast.makeText(getApplicationContext(),
                "Order Placed!", Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Create an instance of pizza according to the type of pizza button pressed and
     * start customizing. Open up the window for customization.
     * @param view records the actual clicked button event.
     */
    public void onPizzaButton(View view){
        boolean flag = true;
        flag = checkPhoneNumber(phoneNumberTextView.getText().toString());

        pizzaButton = (Button) view;
        pizzaName = pizzaButton.getText().toString();
        pizza = PizzaMaker.createPizza(pizzaName);

        if (flag) {
            if (checkNewOrder(customerNumber)) {
                order = new Order(customerNumber);
            }
            Intent intent = new Intent(this, PizzaActivity.class);
            intent.putExtra("PIZZA", pizza);
            intent.putExtra("ORDER", order);
            intent.putExtra("PHONE", customerNumber);
            startActivityForResult(intent, GET_ORDER_CODE);

        }
    }

    /**
     * Open up the window for manipulating the current order.
     *
     * @param view records the actual clicked button event.
     */
    public void onCurrentOrderButton(View view){
        if (order == null || order.getPizzas().isEmpty()) {
            showToast("Warning!",
                    "Empty Order!\n" +
                            "Type in phone number and select a pizza to create an order.");
        }else {
            Intent intent = new Intent(this, CurrentOrderActivity.class);
            intent.putExtra("ORDER", order);
            startActivityForResult(intent, PLACE_ORDER_CODE);
        }
    }

    /**
     * Open up the window for manipulating the order list.
     *
     * @param view records the actual clicked button event.
     */
    public void onOrderListButton(View view){
        if (orderList == null || orderList.isEmpty()){
            showToast("Warning!",
                    "No Order Placed!\nGo to Current Order icon to place an order.");
        }else {
            Intent intent = new Intent(this, OrderListActivity.class);
            intent.putExtra("ORDERLIST", orderList);
            startActivityForResult(intent, ORDER_LIST_CODE);
        }
    }

    /**
     * Dealing with results from PizzaActivity depending on the result code.
     * Pass back the result order to the Main Activity.
     *
     * @param resultCode GET_ORDER_CODE for success, GET_ORDER_BACK for doing nothing when the back button is pressed.
     * @param data the result data passed back.
     */
    private void orderResultCode(int resultCode, Intent data){
        if (resultCode == GET_ORDER_CODE) {
            order = (Order) data.getSerializableExtra("ORDER_R");
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Pizza added to order. Add another one!", Toast.LENGTH_SHORT);
            toast.show();
        } else if(resultCode == GET_ORDER_BACK){
            order = (Order) data.getSerializableExtra("ORDER_R");
        }else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Order fails. Please try again...", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Dealing with results from CurrentOrderActivity depending on the result code.
     * Pass back the result order to the Main Activity.
     *
     * @param resultCode PLACE_ORDER_CODE for success, PLACE_ORDER_BACK for doing nothing when the back button is pressed.
     * @param data the result data passed back.
     */
    private void placeOrderResultCode(int resultCode, Intent data){
        if (resultCode == PLACE_ORDER_CODE) {
            order = (Order) data.getSerializableExtra("ORDER_R");
            placeOrder(order);
        }else if(resultCode == PLACE_ORDER_BACK){
            order = (Order) data.getSerializableExtra("ORDER_R");
        }else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Invalid Order!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Dealing with results from OrderListActivity depending on the result code.
     * Pass back the result orderList to the Main Activity.
     *
     * @param resultCode ORDER_LIST_CODE for success, ORDER_LIST_BACK for doing nothing when the back button is pressed.
     * @param data the result data passed back.
     */
    private void cancelOrderResultCode(int resultCode, Intent data){
        if (resultCode == ORDER_LIST_CODE) {
            orderList = (ArrayList<Order>) data.getSerializableExtra("ORDERLIST_R");
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Order is canceled.\n 0 orders in the list.", Toast.LENGTH_SHORT);
            toast.show();
        }else if(resultCode == ORDER_LIST_BACK){
            orderList = (ArrayList<Order>) data.getSerializableExtra("ORDERLIST_R");
        }else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Cancellation Fails...", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Dealing with result from other activities depending on the request code and result code.
     *
     * @param requestCode code for success.
     * @param resultCode actual code returned from other activities.
     * @param data the result data passed back.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_ORDER_CODE){
            orderResultCode(resultCode, data);
        }else if (requestCode == PLACE_ORDER_CODE) {
            placeOrderResultCode(resultCode, data);
        }else if (requestCode == ORDER_LIST_CODE){
            cancelOrderResultCode(resultCode, data);
        }
    }
}