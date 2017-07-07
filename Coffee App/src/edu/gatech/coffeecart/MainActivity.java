package edu.gatech.coffeecart;

import java.util.List;

import edu.gatech.coffeecart.model.cart.CoffeeCart;
import edu.gatech.coffeecart.model.cart.CoffeeCartManager;
import edu.gatech.coffeecart.model.cart.PurchaseItem;
import edu.gatech.coffeecart.model.cart.PurchaseOrder;
import edu.gatech.coffeecart.model.util.DateUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * MainActivity
 * 
 * This activity orchestrates activities between the elements of the CoffeeCart program.
 * 
 */

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "edu.gatech.coffeeapp.MESSAGE";
	
	private void initCart() {
	  
	}
	/**
	 * Creates a new screen, based on the parameter savedInstanceState
	 * 
	 * @param savedInstanceState The state to be resumed when this activity is active.
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(CoffeeCartManager.getCoffeeCart().empty())
			CoffeeCartManager.getCoffeeCart().createPurchases();
		setContentView(R.layout.activity_main);
		//TODO DB Implement
		if(CoffeeCartManager.getCoffeeCart().orderActive()){
			TextView vip = (TextView) findViewById(R.id.mainVipName);	
			String cName= CoffeeCartManager.getCoffeeCart().getActiveCustomer().getName();
			vip.setText(cName);
			String purs="";
			List<PurchaseItem> pList=CoffeeCartManager.getCoffeeCart().getActivePurchaseOrder().getPurchaseItems();
			for(PurchaseItem pItem : pList){
				
	            purs += (pItem.getItem().getName()+":"+ pItem.getCost().toString() + "\n");
	        }
			TextView pl = (TextView) findViewById(R.id.mainCoffeeList);	
			pl.setMovementMethod(new ScrollingMovementMethod());
			pl.setText(purs);
			
		}
		//end TODO	

		
	}
	
	
	/**
	 * Sends a reminder message to user to use the menu buttons at the bottom instead of ones
	 * on the screen.
	 * 
	 * @param view The current view of the activity.
	 * 
	 */
	
	public void loginVip(View view){
		final EditText input = new EditText(this);
    	new AlertDialog.Builder(this)
        .setTitle("VIP Login")
        .setMessage("Please enter VIP number:")
        .setView(input)
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	//TODO DB Implement
            	String vipNum =input.getText().toString();
            	CoffeeCartManager.getCoffeeCart().cancelPurchaseOrder();
            	
            	if(CoffeeCartManager.getCoffeeCart().vipExists(vipNum) && CoffeeCartManager.getCoffeeCart().vipIsActive(vipNum)){
            		CoffeeCartManager.getCoffeeCart().startPurchaseOrder(vipNum);
            	}
            	else
            	{
            		Toast.makeText(MainActivity.this, "VIP: "+vipNum+" Inactive - Using Non VIP", Toast.LENGTH_LONG).show();
            		CoffeeCartManager.getCoffeeCart().startPurchaseOrder("10000");	
            	}
            	TextView vip = (TextView) findViewById(R.id.mainVipName);	
    			String cName= CoffeeCartManager.getCoffeeCart().getActiveCustomer().getName();
    			vip.setText(cName);
    			//end TODO
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do nothing.
            }
        }).show();
	}
	public void emptyCart(View view){
		if(CoffeeCartManager.getCoffeeCart().orderActive()){
			//TODO DB Implement
			String vipNo=CoffeeCartManager.getCoffeeCart().getActivePurchaseOrder().getCustomer().getCardNumber();
	    	CoffeeCartManager.getCoffeeCart().cancelPurchaseOrder();
	    	TextView cL = (TextView) findViewById(R.id.mainCoffeeList);	
			cL.setText("");
	    	Toast.makeText(MainActivity.this, "Cart Empty!", Toast.LENGTH_LONG).show();
	    	CoffeeCartManager.getCoffeeCart().startPurchaseOrder(vipNo);
	    	}
		//end TODO
	}
	public void checkout(View view){
		//TODO DB Implement
		if(CoffeeCartManager.getCoffeeCart().orderActive()){
			String vipNo=CoffeeCartManager.getCoffeeCart().getActivePurchaseOrder().getCustomer().getCardNumber();
			String purs="";
			List<PurchaseItem> pList=CoffeeCartManager.getCoffeeCart().getActivePurchaseOrder().getPurchaseItems();
			for(PurchaseItem pItem : pList){
				
	            purs += (pItem.getItem().getName()+":"+ pItem.getCost().toString() + "\n");
	        }
			
	    	CoffeeCartManager.getCoffeeCart().completePurchaseOrder();
	    	Toast.makeText(MainActivity.this, "Purchased: "+purs, Toast.LENGTH_LONG).show();
	    	TextView cL = (TextView) findViewById(R.id.mainCoffeeList);	
			cL.setText("");
	    	CoffeeCartManager.getCoffeeCart().startPurchaseOrder(vipNo);
	    	}
		//end TODO
	}
	
	public void editCart(View view){
		//TODO DB Implement
		if(!CoffeeCartManager.getCoffeeCart().orderActive())
			CoffeeCartManager.getCoffeeCart().startPurchaseOrder("10000");
		//end TODO
		Intent intent;
		intent = new Intent(this, CoffeeActivity.class);
    	startActivity(intent);
 
		//Toast.makeText(MainActivity.this, "Advanced Cart Edit Not Implemented In This Release.", Toast.LENGTH_LONG).show();
	}
	/**
	 * Creates the menu for the main activity
	 * 
	 * @return Success or failure of creating the menu.
	 * 
	 * @param menu The menu to be created.
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.vip_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	/**
	 * Activities to be run when a menu item is selected.
	 * 
	 * @return Success or failure in executing item.
	 * @param item The item selected by the user.
	 * 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//TODO DB Implement
		if(!CoffeeCartManager.getCoffeeCart().orderActive())
			CoffeeCartManager.getCoffeeCart().startPurchaseOrder("10000");
		//end TODO	
		    	
			
		Intent intent;
	    switch (item.getItemId()) {
	    
	    case R.id.action_login:
	    	intent = new Intent(this, VipActivity2.class);
	    	startActivity(intent);
	    	return true;
	    case R.id.action_coffee:
	    	intent = new Intent(this, CoffeeActivity.class);
	    	startActivity(intent);
	    	return true;
	    case R.id.action_dessert:
	    	intent = new Intent(this, DessertActivity.class);
	    	startActivity(intent);
	    	return true;		
	    case R.id.action_report:
	    	intent = new Intent(this, ReportActivity2.class);
	    	startActivity(intent);
	    	return true;
	    default:
	    	return super.onOptionsItemSelected(item);
	    }
	}
	
}
