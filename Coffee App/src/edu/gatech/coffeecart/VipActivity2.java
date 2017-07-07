package edu.gatech.coffeecart;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.gatech.coffeecart.model.cart.CoffeeCartManager;
import edu.gatech.coffeecart.model.cart.VIPCustomer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Vip Activity 2
 * 
 * Allows creation of a VIP Account for the Coffee Cart program
 * 
 */
public class VipActivity2 extends Activity implements VipListFragment.OnHeadlineSelectedListener {

	VipListFragment firstFragment = new VipListFragment();
	/**
	 * Creates a new screen, based on the parameter savedInstanceState
	 * 
	 * @param savedInstanceState The state to be resumed when this activity is active.
	 * 
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vip_list);

        if (findViewById(R.id.fragment_container2) != null) {

            if (savedInstanceState != null) {
                return;
            }

            //VipListFragment firstFragment = new VipListFragment();

            firstFragment.setArguments(getIntent().getExtras());

            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container2, firstFragment).commit();
        }
    }

	/**
	 * Activity Fragment to be selected when a particular VIP membership component is selected.
	 * 
	 * @param position The property selected by the user.
	 * 
	 */
    public void onArticleSelected(int position) {
 
        VipEditFragment articleFrag = (VipEditFragment)
                getFragmentManager().findFragmentById(R.id.article_fragment2);

        if (articleFrag != null) {

            articleFrag.updateArticleView(position);

        } else {

            VipEditFragment newFragment = new VipEditFragment();
            Bundle args = new Bundle();
            args.putInt(VipEditFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container2, newFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }
    
	/**
	 * Creates the menu for the VIP activity
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
	 * Allows VIP login to be run when a menu item is selected.
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
		    {
		    	VipEditFragment articleFrag = (VipEditFragment)
		                getFragmentManager().findFragmentById(R.id.article_fragment2);

		        int position=-1;
				if (articleFrag != null) {

		            articleFrag.updateArticleView(position);

		        } else {

		            VipEditFragment newFragment = new VipEditFragment();
		            Bundle args = new Bundle();
		            args.putInt(VipEditFragment.ARG_POSITION, position);
		            newFragment.setArguments(args);
		            FragmentTransaction transaction = getFragmentManager().beginTransaction();

		            transaction.replace(R.id.fragment_container2, newFragment);
		            transaction.addToBackStack(null);

		            transaction.commit();
		        }
		    	return true;
		    }
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
	
	public void saveVip(View view){

	
		//TODO implement DB
		
		
    	VIPCustomer editVip = new VIPCustomer();
		editVip.setActive();
		EditText name = (EditText) this.findViewById(R.id.editName);
		editVip.setName(name.getText().toString());	
    	EditText phone = (EditText) this.findViewById(R.id.editPhoneNum);
    	editVip.setPhoneNumber(phone.getText().toString());
    	EditText birth = (EditText) this.findViewById(R.id.editDob);
		
		try{
		String dob = birth.getText().toString();
	    DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
	    Date result =  df.parse(dob); 
	    editVip.setBirthDate(result);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		TextView tvNum= (TextView) this.findViewById(R.id.textVipNum1);
		editVip.setCardNumber(tvNum.getText().toString());	
		if(tvNum.getText().toString().equals(CoffeeCartManager.getCoffeeCart().nextCardNumber())){
			CoffeeCartManager.getCoffeeCart().addVIPCustomer(editVip);
			getFragmentManager().popBackStack();
			Toast.makeText(VipActivity2.this, "VIP added: "+ name.getText().toString(), Toast.LENGTH_LONG).show();
		
		}
		else{
			CoffeeCartManager.getCoffeeCart().upadteVIPCustomer(editVip);
			getFragmentManager().popBackStack();
			Toast.makeText(VipActivity2.this, "VIP updated: "+ name.getText().toString(), Toast.LENGTH_LONG).show(); 
		}
		// end TODO
    
	}
	
	public void deleteVip(View view){
		
		//TODO DB Implement
		TextView tvNum= (TextView) this.findViewById(R.id.textVipNum1);
		if(CoffeeCartManager.getCoffeeCart().vipExists(tvNum.getText().toString())){
		EditText name = (EditText) this.findViewById(R.id.editName);
		CoffeeCartManager.getCoffeeCart().getVip(Integer.parseInt(tvNum.getText().toString())-10000).deactivate();
		getFragmentManager().popBackStack();
		Toast.makeText(VipActivity2.this, "VIP inactive: "+ name.getText().toString(), Toast.LENGTH_LONG).show();
		if(CoffeeCartManager.getCoffeeCart().orderActive()){
			CoffeeCartManager.getCoffeeCart().cancelPurchaseOrder();
			CoffeeCartManager.getCoffeeCart().startPurchaseOrder("10000");
		}
		}
		//end TODO
    }
}