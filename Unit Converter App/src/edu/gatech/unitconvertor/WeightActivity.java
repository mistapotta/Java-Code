package edu.gatech.unitconvertor;
/**
 * Unit Converter - Weight Activity
 * @author Tony David Potter
 * @author tpotter8@mail.gatech.edu
 * 
 * Converts between kilograms and lbs, or returns to main activity
 */

import edu.gatech.Assignment3.R;
import edu.gatech.Assignment3.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WeightActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weight);
	
		//create buttons for activity
		Button btn2kg = (Button)findViewById(R.id.btn2C);
		Button btn2lb = (Button)findViewById(R.id.btn2F);
		
		//create listener for kg converter
		btn2kg.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//get data from textfield
				EditText txtTempField = (EditText)findViewById(R.id.txtTempField);
				double mass = (double)(Double.parseDouble(txtTempField.getText().toString()));
				//convert to kg.
				double conversionFactor = 2.20462;
				double newMass = mass / conversionFactor;
				//output result
				TextView txtOutput = (TextView)findViewById(R.id.txtOutput);
				txtOutput.setText(newMass + "");
			}
		});
	
		//create listener for lb converter
		btn2lb.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//get data from textfield
				EditText txtTempField = (EditText)findViewById(R.id.txtTempField);
				double mass = (double)(Double.parseDouble(txtTempField.getText().toString()));
				//convert to lb.
				double conversionFactor = 2.20462;
				double newMass = mass * conversionFactor;
				//output result
				TextView txtOutput = (TextView)findViewById(R.id.txtOutput);
				txtOutput.setText(newMass + "");
				
			}	
		});	
		
		//button to return to main activity
		Button btnReturn = (Button)findViewById(R.id.btnReturn);
		//create listener for return button
		btnReturn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//bring up MainActivity
				Intent intent = new Intent(v.getContext(), MainActivity.class);
				startActivityForResult(intent, 0);

			}
		});
	}
}
