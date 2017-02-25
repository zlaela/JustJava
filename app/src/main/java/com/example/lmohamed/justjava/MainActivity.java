package com.example.lmohamed.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    int quantity = 92;
    int price = 5;
    String priceMessage = "Tasty Coffee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);


        TextView orderSummaryTextView = (TextView) findViewById(
                R.id.order_summary_text_view);
        orderSummaryTextView.setText("Tasty coffee is one tap away!");
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity <= 99){
        quantity = quantity + 1; }
        else {
            quantity = 100;
            Toast.makeText(this, "Only 100 cups of coffee can be ordered", Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity !=0) {
        quantity = quantity - 1;
        display(quantity); }
        else {
            quantity = 0;
            Toast.makeText(this, "You must order at least 1 coffee", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method determiens if Add Whipped Cream
     * @return whippedCream (true/false Boolean value)
     */
    public Boolean hasWhippedCream() {
        CheckBox wantsWhippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        Boolean whippedCream = wantsWhippedCream.isChecked();
        return whippedCream;
    }


    /**
     * This method determiens if Add Whipped Cream
     * @return chocolateTopping (true/false Boolean value)
     */
    public Boolean hasChocolateTopping() {
        CheckBox wantsChocolateTopping = (CheckBox) findViewById(R.id.chocolate_topping);
        Boolean chocolateTopping = wantsChocolateTopping.isChecked();
        return chocolateTopping;
    }

    /**
     * This method asks for the person's name
     * @return customerName
     */
    public String customerName() {
        EditText guestName = (EditText) findViewById(R.id.customer_name);
        String name = guestName.getText().toString();
        return name;
    }
    /**
     * This method calculates the price of the coffee
     * @return totalCost
     */
    public int calculatePrice() {
        int totalCost = quantity * price;

        return totalCost;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String name = customerName();
        int price = calculatePrice();
        boolean whipped = hasWhippedCream();
        boolean chocolate = hasChocolateTopping();

        String message = createOrderSummary(price, whipped, chocolate, name);
        // displayMessage(message);

        /**
         * create instance of intent
         */
        Intent intent = new Intent(Intent.ACTION_SEND);
        //intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_subject, name));
        intent.setType("text/plain");

        if (intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        } else {
            Toast.makeText(this, "You must order at least 1 coffee", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method creates the order summary
     * @param cost of order
     * @param whipped is whether the customer wants whipped cream, or not
     * @return priceMessage, the final order message
     */
    private String createOrderSummary(int cost, boolean whipped, boolean chocolate, String name) {
        priceMessage = getString(R.string.order_summary_name); // name fills in the placeholder %s in strings.xml
        priceMessage += "\n" +getString(R.string.whipped_cream) + whipped;
        priceMessage += "\n" +getString(R.string.chocolate_topping) + chocolate;
        priceMessage += "\n" +getString(R.string.quantity) + quantity;
        priceMessage += "\n" +getString(R.string.total) + cost ;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price value on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        // orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(message));
        orderSummaryTextView.setText(message);
    }


    /**
     * This method logs selected actions
     * @param view
     */
    public void printToLogs(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        Boolean hasWhippedCream = whippedCream.isChecked();
        Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);
    }
}
