package cs325.zhang.calculator;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		displayStatistics();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void displayStatistics()
	{
		SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);		
		
		TextView tvStatisticStartedTimes = (TextView)findViewById(R.id.tvStatisticStartedTimes);
		tvStatisticStartedTimes.setText(String.valueOf(sp.getInt(CalculatorActivity.STARTED_TIMES_COUNT,0)));
		
		TextView tvStatisticTotalComputationCount = (TextView)findViewById(R.id.tvStatisticTotalComputationCount);
		tvStatisticTotalComputationCount.setText(String.valueOf(sp.getInt(CalculatorActivity.TOTAL_COMPUTATION_COUNT,0)));
		
		TextView tvStatisticSessionComputationCount = (TextView)findViewById(R.id.tvStatisticSessionComputationCount);
		tvStatisticSessionComputationCount.setText(String.valueOf(sp.getInt(CalculatorActivity.SESSION_COMPUTATION_COUNT,0)));
	}
	
}
