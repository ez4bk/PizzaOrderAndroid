package com.cs213.porject5_pizzaorderandroid;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.NavUtils;

import java.util.ArrayList;

/**
 * Pizza Activity that handles pizza customization for all three kinds
 * of pizza. Handle toppings addings and removings, changing size, showing
 * price, and adding the pizza to the order.
 *
 * @author Yuchen Wei, Denghao Sun
 */

public class PizzaActivity extends AppCompatActivity{

    private static final int GET_ORDER_CODE_RESULT_OK = 1;
    private static final int GET_ORDER_CODE_RESULT_CANCEL = 2;
    private Spinner sizeSpinner;
    private TextView pizzaNameTextView;
    private ListView addToppingListView;
    private ListView selectedToppingListView;
    private TextView phoneNumberString;
    private ImageView pizzaImage;
    private TextView priceString;

    private static final int MAXTOPPINGS = 7;
    private Pizza pizza;
    private Order order;
    private String pizzaNamePassed;
    private String phoneNumber;

    /**
     * Initialize the Spinner for choosing Sizes. Fill with all values of the Size
     * Enum and set the default value to Small.
     */
    private void initializeSpinner() {
        ArrayAdapter<Size> sizeList = new ArrayAdapter<Size>(this, android.R.layout.simple_list_item_1, Size.values());
        sizeSpinner.setAdapter(sizeList);
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Update the selected size to the pizza.
             *
             * @param parentView the Adapter.
             * @param selectedItemView the current view.
             * @param position the position of clicked item.
             * @param id the id.
             */
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                pizza.size = (Size) sizeSpinner.getSelectedItem();
                priceString.setText("$" + String.format("%.2f", pizza.price()));
            }

            /**
             * Update price when nothing is selected.
             * @param parentView the Adapter.
             */
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                priceString.setText(String.format("%.2f", pizza.price()));
                return;
            }

        });
    }

    /**
     * Set the click listener for selectedToppingListView.
     *
     * @param selectedToppingList to be removed.
     * @param addToppingList to be added.
     */
    private void setSelectedClickListener(ArrayAdapter selectedToppingList, ArrayAdapter addToppingList){
        selectedToppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            /**
             * Move the item on click to the other ListView.
             *
             * @param parent the Adapter.
             * @param view the current view.
             * @param position the position of clicked item.
             * @param id the id.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (pizza.toppings.size() <= pizza.getMinTopping()){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Warning!\nYou can't have less than" + pizza.getMinTopping() +
                                    " toppings.", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Topping selectedItem = (Topping) parent.getItemAtPosition(position);
                    selectedToppingList.remove(selectedItem);
                    addToppingList.add(selectedItem);
                    priceString.setText(String.format("%.2f", pizza.price()));
                }

            }
        });
    }

    /**
     * Set the click listener for addToppingListView.
     *
     * @param selectedToppingList to be added.
     * @param addToppingList to be removed.
     */
    private void setAddClickListener(ArrayAdapter selectedToppingList, ArrayAdapter addToppingList){
        addToppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            /**
             * Move the item on click to the other ListView.
             *
             * @param parent the Adapter.
             * @param view the current view.
             * @param position the position of clicked item.
             * @param id the id.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(pizza.toppings.size() >= MAXTOPPINGS){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Warning!\nYou can't have more than 7 toppings.", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Topping selectedItem = (Topping) parent.getItemAtPosition(position);
                    selectedToppingList.add(selectedItem);
                    addToppingList.remove(selectedItem);
                    priceString.setText(String.format("%.2f", pizza.price()));
                }

            }
        });
    }

    /**
     * Initialize ListViews for toppings to be added and toppings already selected.
     */
    private void initializeListView() {
        ArrayAdapter<Topping> selectedToppingList = new ArrayAdapter<Topping>(this,
                android.R.layout.simple_list_item_1, pizza.toppings);
        ArrayList<Topping> addArrayList = new ArrayList<Topping>();
        for (Topping e : Topping.values()) {
            if (!pizza.toppings.contains(e)) {
                addArrayList.add(e);
            }
        }
        ArrayAdapter<Topping> addToppingList = new ArrayAdapter<Topping>(this,
                android.R.layout.simple_list_item_1, addArrayList);
        addToppingListView.setAdapter(addToppingList);
        selectedToppingListView.setAdapter(selectedToppingList);

        setAddClickListener(selectedToppingList, addToppingList);
        setSelectedClickListener(selectedToppingList, addToppingList);
    }

    /**
     * Set the image of the pizza selected based on the instance type.
     */
    private void putImage(){
        if (pizza instanceof Deluxe)
            pizzaImage.setImageResource(R.drawable.deluxe);
        if (pizza instanceof Hawaiian)
            pizzaImage.setImageResource(R.drawable.hawaiian);
        if (pizza instanceof Pepperoni)
            pizzaImage.setImageResource(R.drawable.pepperoni);
    }

    /**
     * Activity Initialization. Find and initialize TextViews, ListViews, the Spinner, and the ImageView in the view.
     * @param savedInstanceState for creating.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);
        pizzaNameTextView = findViewById(R.id.pizzaNameTextView);
        sizeSpinner = findViewById(R.id.sizeSpinner);
        addToppingListView = findViewById(R.id.addToppingListView);
        selectedToppingListView = findViewById(R.id.selectedToppingListView);
        phoneNumberString = findViewById(R.id.phoneNumberString);
        pizzaImage = findViewById(R.id.pizzaImage);
        priceString = findViewById(R.id.priceString);

        Intent intent = getIntent();
        pizza = (Pizza) intent.getSerializableExtra("PIZZA");
        order = (Order) intent.getSerializableExtra("ORDER");
        phoneNumber = (String) intent.getStringExtra("PHONE");

        pizzaNamePassed = pizza.getName();
        phoneNumberString.setText(phoneNumber);
        pizzaNameTextView.setText(pizzaNamePassed);
        putImage();

        initializeSpinner();
        initializeListView();
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
        setResult(GET_ORDER_CODE_RESULT_CANCEL, intent);
        finish();
    }


    /**
     * Add the pizza to the order, create a new same pizza.
     * @param view records the actual clicked button event.
     */
    public void onAddToOrderButton(View view){
        order.addPizza(pizza);

        Intent intent = new Intent();
        intent.putExtra("ORDER_R", order);
        setResult(GET_ORDER_CODE_RESULT_OK, intent);
        finish();
    }


}