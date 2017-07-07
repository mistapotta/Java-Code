package edu.gatech.unitconvertor;

/**
 * Unit Converter - Distance Activity
 * @author Tony David Potter
 * @author tpotter8@mail.gatech.edu
 * 
 * Activity that converts between km and mi.
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

public class DistanceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distance);
		//correlate button objects
		Button btn2km = (Button)findViewById(R.id.btn2C);
		Button btn2mi = (Button)findViewById(R.id.btn2F);
		//create kilometer listener
		btn2km.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//get data from textfield
				EditText txtTempField = (EditText)findViewById(R.id.txtTempField);
				double distance = (double)(Double.parseDouble(txtTempField.getText().toString()));
				//convert to kilometers
				double conversionFactor = 1.60934;
				double newDistance = distance * conversionFactor;
				//output result
				TextView txtOutput = (TextView)findViewById(R.id.txtOutput);
				txtOutput.setText(newDistance + "");

			}
		});
		//create mile listener
		btn2mi.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//get data from textfield
				EditText txtTempField = (EditText)findViewById(R.id.txtTempField);
				double distance = (double)(Double.parseDouble(txtTempField.getText().toString()));
				//convert to miles
				double conversionFactor = 1.60934;
				double newDistance = distance / conversionFactor;
				//output result
				TextView txtOutput = (TextView)findViewById(R.id.txtOutput);
				txtOutput.setText(newDistance + "");
			}
		});
		//button to return to main activity
		Button btnReturn = (Button)findViewById(R.id.btnReturn);
		//create return listener
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
