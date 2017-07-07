package edu.gatech.coffeecart;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import edu.gatech.coffeecart.model.cart.CoffeeCartManager;
import edu.gatech.coffeecart.model.cart.PurchaseItem;
import edu.gatech.coffeecart.model.cart.VIPCustomer;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class VipEditFragment extends Fragment {
	//implements OnClickListener
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    List<Map<String, String>> purchaseList = new ArrayList<Map<String,String>>();
	SimpleAdapter simpleAdap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        View view=inflater.inflate(R.layout.vipedit_fragment, container, false);
       // Button btn = (Button) view.findViewById(R.id.saveVipBtn);
        //btn.setOnClickListener(this); 

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {

            updateArticleView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {

            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position) { 
    	//TODO DB Implement
    	if(position==-1){
    		TextView tvNum= (TextView) getActivity().findViewById(R.id.textVipNum1);
        	tvNum.setText(CoffeeCartManager.getCoffeeCart().nextCardNumber());
        	TextView tvPoints= (TextView) getActivity().findViewById(R.id.vipPoints);
        	tvPoints.setText("0/0");
        	EditText name = (EditText) getActivity().findViewById(R.id.editName);
        	name.requestFocus();
        	
    	}
    	else{
    	mCurrentPosition = position;
    	String cardNum = Integer.toString(10000+position);
    	VIPCustomer vip=CoffeeCartManager.getCoffeeCart().getVip(cardNum);
    	String gold="";
    	if(vip.isGoldLevel())
    		gold=" [GOLD]";
    	EditText name = (EditText) getActivity().findViewById(R.id.editName);
    	name.requestFocus();
    	name.setText(vip.getName());
    	TextView tvNum= (TextView) getActivity().findViewById(R.id.textVipNum1);
    	tvNum.setText(vip.getCardNumber());
    	TextView tvPoints= (TextView) getActivity().findViewById(R.id.vipPoints);
    	tvPoints.setText(vip.getPoints30Days()+" / "+vip.getPointsTotal() +gold);
    	EditText phone = (EditText) getActivity().findViewById(R.id.editPhoneNum);
    	phone.setText(vip.getPhoneNumber());
    	EditText dob = (EditText) getActivity().findViewById(R.id.editDob);
    	try{
    	SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        String db = df.format(vip.getBirthDate());
    	dob.setText(db);
    	}catch (Exception e){
    		dob.setText("");
    	}
    	
    	TextView tvPur= (TextView) getActivity().findViewById(R.id.textPurchases);
    	String purs="";
    	
    	List<PurchaseItem> purchases = vip.getPurchaseItems();
        for(PurchaseItem pItem : purchases){
            purs += (pItem.getItem().getName()+":"+ pItem.getPurchaseDate().toString() + "\n");
        }
        tvPur.setMovementMethod(new ScrollingMovementMethod());
        tvPur.setText(purs);
        
    	}
    	//end TODO
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
    /*
    public void onClick(View v) {
    	
    	
    	VIPCustomer editVip = new VIPCustomer();
		editVip.setActive();
		EditText name = (EditText) getActivity().findViewById(R.id.editName);
		editVip.setName(name.getText().toString());	
    	EditText phone = (EditText) getActivity().findViewById(R.id.editPhoneNum);
    	editVip.setPhoneNumber(phone.getText().toString());
    	EditText birth = (EditText) getActivity().findViewById(R.id.editDob);
		
		try{
		String dob = birth.getText().toString();
		//dob="12-31-2013";
	    DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
	    Date result =  df.parse(dob); 
	    editVip.setBirthDate(result);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		TextView tvNum= (TextView) getActivity().findViewById(R.id.textVipNum1);
		if(tvNum.getText().toString()==CoffeeCartManager.getCoffeeCart().nextCardNumber())
			CoffeeCartManager.getCoffeeCart().addVIPCustomer(editVip);
		else
			CoffeeCartManager.getCoffeeCart().upadteVIPCustomer(editVip);
    }
    */
    
}