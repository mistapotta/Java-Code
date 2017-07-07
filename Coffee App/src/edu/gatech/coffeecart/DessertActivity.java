package edu.gatech.coffeecart;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.coffeecart.model.cart.CoffeeCartManager;
import edu.gatech.coffeecart.model.cart.PurchaseOrder;
import edu.gatech.coffeecart.model.util.DateUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
 * DessertActivity
 * 
 * This activity takes care of dessert actions (ordering and pre-ordering) for the CoffeeCart program.
 */
public class DessertActivity extends Activity {
	
	
	 
	List<Map<String, String>> vipList = new ArrayList<Map<String,String>>();
	SimpleAdapter simpleAdap;
	int listPosition;
	
	/**
	 * Initializes the list based on the desserts available.
	 * 
	 */
	private void initList() {

		//TODO Implement DB
		vipList.add(createVip("vip", CoffeeCartManager.getCoffeeCart().getDessertList().get(0).getName()));
		vipList.add(createVip("vip", CoffeeCartManager.getCoffeeCart().getDessertList().get(1).getName()));
	    vipList.add(createVip("vip", CoffeeCartManager.getCoffeeCart().getDessertList().get(2).getName()));
	    vipList.add(createVip("vip", CoffeeCartManager.getCoffeeCart().getDessertList().get(3).getName()));
	    //end TODO
	
		  
		}

	/**
	 * Generates a hashmap, mapping a key value to each dessert type.
	 * 
	 * @param key The key to map to a dessert.
	 * @param name The dessert to be mapped to a key value.
	 * 
	 */
	private HashMap<String, String> createVip(String key, String name) {
		    HashMap<String, String> vip = new HashMap<String, String>();
		    vip.put(key, name);
		    return vip;
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
		setContentView(R.layout.activity_dessert);
		initList();
	    ListView lv = (ListView) findViewById(R.id.dessertView);
	    simpleAdap = new SimpleAdapter(this, vipList, android.R.layout.simple_list_item_1, new String[] {"vip"}, new int[] {android.R.id.text1});
	    
	    lv.setAdapter(simpleAdap);
	    registerForContextMenu(lv);
	    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
	                                long id) {
	            TextView clickedView = (TextView) view;
	            listPosition=position;
	            Toast.makeText(DessertActivity.this, clickedView.getText() + " Nutritional Facts: 2000 Calories; Long Press on item to buy.", Toast.LENGTH_LONG).show();
	        }
	   });

	
	}
	/**
	 * The menu options after each dessert type is selected.
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
	      menu.setHeaderTitle("Options for " + map.get("vip"));
	      menu.add(1, 1, 1, "Buy");
	      menu.add(1, 2, 2, "Pre-Order");
	      
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
	    	order.addItem(CoffeeCartManager.getCoffeeCart().getDessertList().get(listPosition));			
	    	Toast.makeText(DessertActivity.this, "Added to Cart: "+CoffeeCartManager.getCoffeeCart().getDessertList().get(listPosition).getName(), Toast.LENGTH_LONG).show();
	    	return true;
	    }
	    case 2:
	    {
	    	
	    	final EditText input = new EditText(this);
	    	new AlertDialog.Builder(this)
	        .setTitle("Date")
	        .setMessage("Please enter pickup date:")
	        .setView(input)
	        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	            	//TODO DB Implement
	            	PurchaseOrder order2 = CoffeeCartManager.getCoffeeCart().getActivePurchaseOrder();
	            	try{
	            		String ordDate = input.getText().toString();
	            	    DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
	            	    Date result =  df.parse(ordDate); 
	            	    if(CoffeeCartManager.getCoffeeCart().preOrderSlotAvailable(result,CoffeeCartManager.getCoffeeCart().getDessertList().get(listPosition)))
	            	    	order2.addPreOrderItem(CoffeeCartManager.getCoffeeCart().getDessertList().get(listPosition),result);
	            	    else
	            	    	Toast.makeText(DessertActivity.this, "PreOder Slot Filled For "+ ordDate, Toast.LENGTH_LONG).show();
	            	    
	            		} catch (ParseException pe) {
	            			pe.printStackTrace();
	            		}
	            	
	            	
	            	
	    			//end TODO
	            }
	        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	                // Do nothing.
	            }
	        }).show();
	    	//order.addPreOrderItem(CoffeeCartManager.getCoffeeCart().getDessertList().get(listPosition),DateUtil.getDate(0));	
	    	//Toast.makeText(DessertActivity.this, "Added Pre Order to Cart: "+CoffeeCartManager.getCoffeeCart().getDessertList().get(listPosition).getName(), Toast.LENGTH_LONG).show();
	    	return true;
	    }
	    //end TODO	
	    default:
	    	return true;
	    }

	}
	
	/**
	 * Creates the menu for the dessert activity
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
