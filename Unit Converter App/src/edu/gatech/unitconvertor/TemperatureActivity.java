package edu.gatech.unitconvertor;

/**
 * Unit Converter - Temperature Activity
 * @author Tony David Potter
 * @author tpotter8@mail.gatech.edu
 * 
 * Convert between degrees Celsius and Fahrenheit
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

public class TemperatureActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temperature);
		//correlate button objects
		Button btn2C = (Button)findViewById(R.id.btn2C);
		Button btn2F = (Button)findViewById(R.id.btn2F);
		//create Celsius listener
		btn2C.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//get data from textfield
				EditText txtTempField = (EditText)findViewById(R.id.txtTempField);
				double temperature = (double)(Double.parseDouble(txtTempField.getText().toString()));
				//convert to Celsius
				double newTemperature = 5.0/9.0*(temperature - 32.0);
				//get access to output label
				TextView txtOutput = (TextView)findViewById(R.id.txtOutput);
				//check if valid temp (more than Absolute Zero)
				if (newTemperature < -273.15)
					txtOutput.setText("Invalid Temperature");
				else
					txtOutput.setText(newTemperature + "");


			}
		});
		//create Fahrenheit Listener
		btn2F.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//get data from textfield
				EditText txtTempField = (EditText)findViewById(R.id.txtTempField);
				double temperature = (double)(Double.parseDouble(txtTempField.getText().toString()));
				//convert to Fahrenheit
				double newTemperature = (temperature)*9.0/5.0+32;
				//get access to output label
				TextView txtOutput = (TextView)findViewById(R.id.txtOutput);
				//check if valid temp (more than Absolute Zero)
				if (temperature < -273.15)
					txtOutput.setText("Invalid Temperature");
				else
					txtOutput.setText(newTemperature + "");

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
