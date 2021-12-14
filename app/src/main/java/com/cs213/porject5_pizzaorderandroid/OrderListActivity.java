package com.cs213.porject5_pizzaorderandroid;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;



public class OrderListActivity extends AppCompatActivity {

    private TextView listOrderTotalString;
    private Spinner phoneNumberSpinner;
    private ListView orderListListView;

    private ArrayList<Order> orderList;
    private static final int ORDER_LIST_CODE_RESULT_OK = 5;
    private static final int ORDER_LIST_CODE_RESULT_CANCEL = 6;


    /**
     * The method to find if the given order is already in the order list. Compare
     * the given customer number with the one saved.
     *
     * @param number of the customer.
     * @return the order in the order list if it is found; otherwise, null if it is
     *         not found.
     */
    private Order findOrderInList(String number) {
        for (Order o : orderList) {
            if (o.isSameOrder(number)) {
                return o;
            }
        }
        return null;
    }

    /**
     * Update and set the price for the order total to their corresponding text filed.
     *
     * @param phoneNumberSelected phone number selected.
     */
    private void setPrices(String phoneNumberSelected){
        Order order = findOrderInList(phoneNumberSelected);
        Double price = order.getOrderTotal();
        listOrderTotalString.setText("$" + String.format("%.2f", price));
    }

    /**
     * Initialize the Spinner for choosing customer's phone number in the order list.
     */
    private void initializeSpinner(){
        ArrayList<String> numberArrayList = new ArrayList<String>();
        for (Order o : orderList) {
            numberArrayList.add(o.getOrderNumber());
        }

        ArrayAdapter<String> phoneNumberList = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, numberArrayList);
        phoneNumberSpinner.setAdapter(phoneNumberList);
        initializeListView((String) phoneNumberSpinner.getSelectedItem());
        phoneNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Update the pizza list and the price for each order selected.
             * @param parentView the Adapter.
             * @param selectedItemView the current view.
             * @param position the position of clicked item.
             * @param id the id.
             */
            @Override
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                initializeListView((String) phoneNumberSpinner.getSelectedItem());
            }

            /**
             * Do nothing when nothing is selected.
             * @param parentView the Adapter
             */
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    /**
     * Initialize the ListView for pizzas in the selected order.
     *
     * @param phoneNumberSelected phone number selected.
     */
    private void initializeListView(String phoneNumberSelected) {
        try{
            ArrayAdapter<Pizza> pizzaList = new ArrayAdapter<Pizza>(this,
                    android.R.layout.simple_list_item_1,
                    findOrderInList(phoneNumberSelected).getPizzas());
            orderListListView.setAdapter(pizzaList);
            setPrices(phoneNumberSelected);
        }catch (Exception e){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Something went wrong...\n" + e.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Activity Initialization. Find and initialize the TextView, the Spinner, and the ListView in the view.
     *
     * @param savedInstanceState for creating.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        listOrderTotalString = (TextView) findViewById(R.id.listOrderTotalString);
        phoneNumberSpinner = (Spinner) findViewById(R.id.phoneNumberSpinner);
        orderListListView = (ListView) findViewById(R.id.orderListListView);

        Intent intent = getIntent();
        orderList = (ArrayList<Order>) intent.getSerializableExtra("ORDERLIST");

        initializeSpinner();
    }

    /**
     * Override the behaviour of the action bar back button.
     *
     * @param item in the action bar.
     * @return true if home button is clicked.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return false;
    }

    /**
     * Override the behaviour of the system back button.
     */
    @Override
    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra("ORDERLIST_R", orderList);
        setResult(ORDER_LIST_CODE_RESULT_CANCEL, intent);
        finish();
    }

    /**
     * Cancel the order selected.
     *
     * @param view records the actual clicked button event.
     */
    public void onCancelOrderButton(View view) {
        Order order = findOrderInList((String) phoneNumberSpinner.getSelectedItem());
        if (orderList.size() <= 1){
            orderList.remove(order);
            Intent intent = new Intent();
            intent.putExtra("ORDERLIST_R", orderList);
            setResult(ORDER_LIST_CODE_RESULT_OK, intent);
            finish();
        }else {
            orderList.remove(order);
            initializeSpinner();
            Toast toast = Toast.makeText(getApplicationContext(), "Order: " +
                    (String) phoneNumberSpinner.getSelectedItem() + "is\n" +
                    orderList.size() + " orders left.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}