package com.cs213.porject5_pizzaorderandroid;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class CurrentOrderActivity extends AppCompatActivity {

    private TextView currentPhoneNumberString;
    private TextView subtotalString;
    private TextView salesTaxString;
    private TextView orderTotalString;
    private ListView pizzaListView;

    private Order order;
    private double subTotalNumber = 0;
    private double taxTotalNumber = 0;
    private double orderTotalNumber = 0;
    private static final int PLACE_ORDER_CODE_RESULT_OK = 3;
    private static final int PLACE_ORDER_CODE_RESULT_BACK = 4;

    /**
     * Update and set the prices for sub-total, sales tax, and order total to their
     * corresponding text field.
     */
    private void setPrices(){
        subTotalNumber = order.getSubtotal();
        subtotalString.setText("$" + String.format("%.2f", subTotalNumber));

        taxTotalNumber = order.getTax();
        salesTaxString.setText("$" + String.format("%.2f", taxTotalNumber));

        orderTotalNumber = order.getOrderTotal();
        orderTotalString.setText("$" + String.format("%.2f", orderTotalNumber));
    }

    /**
     * Initialize the ListView that displays pizzas in the order.
     */
    private void initializePizzaListView(){
        try{
            ArrayAdapter<Pizza> pizzaList = new ArrayAdapter<Pizza>(this,
                    android.R.layout.simple_list_item_1, order.getPizzas());
            pizzaListView.setAdapter(pizzaList);
            pizzaListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                /**
                 * Defines the long click method for removing a pizza.
                 * @param parent the Adapter.
                 * @param view the current view.
                 * @param position the position of clicked item.
                 * @param id the id.
                 * @return
                 */
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        Pizza pizzaToRemove = (Pizza) parent.getItemAtPosition(position);
                        pizzaList.remove(pizzaToRemove);
                        setPrices();
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Pizza Removed", Toast.LENGTH_SHORT);
                        toast.show();
                        return true;
                    }catch (Exception e){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Pizza Removal Fails...\n" + e.toString(), Toast.LENGTH_SHORT);
                        toast.show();
                        return false;
                    }
                }

            });
        }catch (Exception e){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Something went wrong...\n" + e.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    /**
     * Activity Initialization. Find and initialize TextViews and the ListView in the view.
     * @param savedInstanceState for creating.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);

        currentPhoneNumberString = (TextView) findViewById(R.id.currentPhoneNumberString);
        subtotalString = (TextView) findViewById(R.id.subtotalString);
        salesTaxString = (TextView) findViewById(R.id.salesTaxString);
        orderTotalString = (TextView) findViewById(R.id.orderTotalString);
        pizzaListView = (ListView) findViewById(R.id.pizzaListView);

        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("ORDER");


        currentPhoneNumberString.setText(order.getOrderNumber());
        setPrices();
        initializePizzaListView();
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
        intent.putExtra("ORDER_R", order);
        setResult(PLACE_ORDER_CODE_RESULT_BACK, intent);
        finish();
    }

    /**
     * PLace the order to the order list.
     *
     * @param view records the actual clicked button event.
     */
    public void onPlaceOrderButton(View view) {
        try{
            Intent intent = new Intent();
            intent.putExtra("ORDER_R", order);
            setResult(PLACE_ORDER_CODE_RESULT_OK, intent);
            finish();
        }catch (Exception e){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Invalid Order!\n" + e.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}