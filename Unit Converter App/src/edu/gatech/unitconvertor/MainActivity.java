package edu.gatech.unitconvertor;
/**
 * Unit Converter - Main Activity
 * @author Tony David Potter
 * @author tpotter8@mail.gatech.edu
 * 
 * Main driver that loads other converter activities based on user's desire.
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

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//correlate button objects
		Button btnDist = (Button)findViewById(R.id.btnDist);
		Button btnTemp = (Button)findViewById(R.id.btnTemp);
		Button btnMass = (Button)findViewById(R.id.btnMass);
		//create listener for btnDist		
		btnDist.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//bring up DistanceActivity
				Intent intent = new Intent(v.getContext(), DistanceActivity.class);
				startActivityForResult(intent, 0);
				
			}
		});
		//create listener for btnTemp
		btnTemp.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//bring up TemperatureActivity
				Intent intent = new Intent(v.getContext(), TemperatureActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		//create listener for btnMass
		btnMass.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//bring up WeightActivity
				Intent intent = new Intent(v.getContext(), WeightActivity.class);
				startActivityForResult(intent, 0);
			}
		});
	}
}
