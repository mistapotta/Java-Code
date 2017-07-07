package edu.gatech.coffeecart;


import edu.gatech.coffeecart.model.cart.CoffeeCartManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**
 * ReportActivity2
 * 
 * This activity allows employee reports for the CoffeeCart program.
 * 
 */
public class ReportActivity2 extends Activity 
        implements HeadlinesFragment.OnHeadlineSelectedListener {

	/**
	 * Creates a new screen, based on the parameter savedInstanceState
	 * 
	 * @param savedInstanceState The state to be resumed when this activity is active.
	 * 
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_articles);

 
        if (findViewById(R.id.fragment_container) != null) {

  
            if (savedInstanceState != null) {
                return;
            }

            HeadlinesFragment firstFragment = new HeadlinesFragment();

            firstFragment.setArguments(getIntent().getExtras());

            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

	/**
	 * Activity Fragment to be selected when a particular report is selected.
	 * 
	 * @param position The report selected by the user.
	 * 
	 */
    public void onArticleSelected(int position) {
 
        ArticleFragment articleFrag = (ArticleFragment)
                getFragmentManager().findFragmentById(R.id.article_fragment);

        if (articleFrag != null) {

            articleFrag.updateArticleView(position);

        } else {

            ArticleFragment newFragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }
    
	/**
	 * Creates the menu for the report activity
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
	 * Report to be run when a menu item is selected.
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