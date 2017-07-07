package edu.gatech.coffeecart;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.coffeecart.model.cart.CoffeeCartManager;
import edu.gatech.coffeecart.model.cart.PurchaseOrder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * CoffeeActivity
 * 
 * This activity takes care of coffee actions (ordering and refills) for the CoffeeCart program.
 * 
 */
public class CoffeeActivity extends Activity {
	
	
	 
	List<Map<String, String>> coffeeList = new ArrayList<Map<String,String>>();
	SimpleAdapter simpleAdap;
	int listPosition;
	
	/**
	 * Initializes the list with the coffee flavors available in the shop.
	 * 
	 */
	private void initList() {
		//TODO DB Implement
		coffeeList.add(createVip("coff", CoffeeCartManager.getCoffeeCart().getCoffeeList().get(0).getName()));
		coffeeList.add(createVip("coff", CoffeeCartManager.getCoffeeCart().getCoffeeList().get(1).getName()));
	    coffeeList.add(createVip("coff", CoffeeCartManager.getCoffeeCart().getCoffeeList().get(2).getName()));
	    coffeeList.add(createVip("coff", CoffeeCartManager.getCoffeeCart().getCoffeeList().get(3).getName()));
	    coffeeList.add(createVip("coff", CoffeeCartManager.getCoffeeCart().getCoffeeList().get(4).getName()));
	    //End TODO
		}
	/**
	 * Generates a hashmap, mapping a key value to each coffee type.
	 * 
	 * @param key The key to map to a coffee flavor.
	 * @param name The coffee flavor to be mapped to a key value.
	 * 
	 */
	private HashMap<String, String> createVip(String key, String name) {
		    HashMap<String, String> coff = new HashMap<String, String>();
		    coff.put(key, name);
		    return coff;
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
		setContentView(R.layout.activity_coffee);
		initList();
	    ListView lv = (ListView) findViewById(R.id.coffeeView);
	    simpleAdap = new SimpleAdapter(this, coffeeList, android.R.layout.simple_list_item_1, new String[] {"coff"}, new int[] {android.R.id.text1});
	    
	    lv.setAdapter(simpleAdap);
	    registerForContextMenu(lv);
	    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
	                                long id) {
	            TextView clickedView = (TextView) view;
	            listPosition=position;
	            Toast.makeText(CoffeeActivity.this, clickedView.getText() + " Nutritional Facts: 1000 Calories; Long Press on item to buy.", Toast.LENGTH_LONG).show();
	        }
	   });

	}
	
	/**
	 * The menu options after each flavor of coffee is selected.
	 * 
	 * @param menu The menu to add elements to.
	 * @param v The current screen view.
	 * @param menuInfo The current ContextMenuInfo item for pop up messages.
	 * 
	 */
	@Override
	 public void onCreateContextMenu(ContextMenu menu, View v,
	          ContextMenuInfo menuInfo) {
	      super.onCreateContextMenu(menu, v, menuInfo);
	      AdapterContextMenuInfo mInfo = (AdapterContextMenuInfo) menuInfo;
	      HashMap map =  (HashMap) simpleAdap.getItem(mInfo.position);
	      listPosition=mInfo.position;
	      menu.setHeaderTitle("Options for " + map.get("coff"));
	      menu.add(1, 1, 1, "Buy");
	      menu.add(1, 2, 2, "Refill");
	 
	      
	  }
	
	/**
	 * Actions that need to take place when a menu item is selected.
	 * 
	 * @return Success or failure when selecting a menu item.
	 * @param item The menu item chosen.
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    int itemId = item.getItemId();
	  //TODO DB Implement
	    PurchaseOrder order = CoffeeCartManager.getCoffeeCart().getActivePurchaseOrder();
	    
	    switch (itemId) {

	    case 1:	
	    {
	    	order.addItem(CoffeeCartManager.getCoffeeCart().getCoffeeList().get(listPosition));			
	    	Toast.makeText(CoffeeActivity.this, "Added to Cart: "+CoffeeCartManager.getCoffeeCart().getCoffeeList().get(listPosition).getName(), Toast.LENGTH_LONG).show();
	    	return true;
	    }
	    case 2:
	    {
	    	order.addRefill(CoffeeCartManager.getCoffeeCart().getCoffeeList().get(listPosition));	
	    	Toast.makeText(CoffeeActivity.this, "Added Refill to Cart: "+CoffeeCartManager.getCoffeeCart().getCoffeeList().get(listPosition).getName(), Toast.LENGTH_LONG).show();
	    	return true;
	    }
	    	
	    default:
	    	return true;
	    }
	    //end TODO
	}
	
	/**
	 * Creates the menu for the coffee activity
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