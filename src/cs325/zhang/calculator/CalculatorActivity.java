package cs325.zhang.calculator;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatorActivity extends Activity {
	
	public static final String TOTAL_COMPUTATION_COUNT = "TotalComputationCount";
	public static final String SESSION_COMPUTATION_COUNT = "SessionComputationCount";
	public static final String STARTED_TIMES_COUNT = "StartedTimesCount";
	
	private static final String STATE_VALUE1 = "value1";
	private static final String STATE_VALUE2 = "value2";
	private static final String STATE_RESULT = "result";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);

		if ( savedInstanceState != null )
		{
			// load instance state
			
			EditText etValue1 = (EditText)findViewById(R.id.etValue1);
			EditText etValue2 = (EditText)findViewById(R.id.etValue2);
			TextView tvResult = (TextView)findViewById(R.id.tvResult);
			String a = savedInstanceState.getString(STATE_VALUE1);
			
			etValue1.setText(a);
			etValue2.setText(savedInstanceState.getString(STATE_VALUE2) + "0");
			tvResult.setText(savedInstanceState.getString(STATE_RESULT) + "0");

		}
		else
		{
			// new instance
			saveStartedTimesCount();
		}

		
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculator, menu);
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
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		EditText etValue1 = (EditText)findViewById(R.id.etValue1);
		EditText etValue2 = (EditText)findViewById(R.id.etValue2);
		TextView tvResult = (TextView)findViewById(R.id.tvResult);
		savedInstanceState.putString(STATE_VALUE1,etValue1.getText().toString());
		savedInstanceState.putString(STATE_VALUE2,etValue2.getText().toString());
		savedInstanceState.putString(STATE_RESULT,tvResult.getText().toString());
		super.onSaveInstanceState(savedInstanceState);
	}
	
	public void plus(View view)
	{
		calculate(view, '+');
	}
	
	public void minus(View view)
	{
		calculate(view, '-');		
	}

	public void multiply(View view)
	{
		calculate(view, '*');		
	}

	public void divide(View view)
	{
		calculate(view, '/');		
	}
	
	public void showAboutActivity(View view)
	{
		startActivity(new Intent(this, AboutActivity.class));
	}
	
	private void calculate(View view, char operator)
	{
		float value1;
		float value2;
		float result = 0;
		EditText etValue1 = (EditText)findViewById(R.id.etValue1);
		EditText etValue2 = (EditText)findViewById(R.id.etValue2);
		TextView tvResult = (TextView)findViewById(R.id.tvResult);
		try
		{
			value1 = Float.valueOf(etValue1.getText().toString());
			value2 = Float.valueOf(etValue2.getText().toString());
			if ( value2 != 0 )
			{
				switch ( operator)
				{
					case '+':
						result = value1 + value2;
						break;

					case '-':
						result = value1 - value2;
						break;

					case '*':
						result = value1 * value2;
						break;

					case '/':
						result = value1 / value2;
						break;
				}
				tvResult.setText(new DecimalFormat("#.##").format(result));

				saveComputationCount();
			}
			else
			{
				// value 2 is zero
				showErrorDialog(getString(R.string.error_dialog_message_value2_is_zero));
				tvResult.setText("NaN");
			}

		}
		catch (NumberFormatException  ex) // value1 or value2 is not a number
		{	
			showErrorDialog(getString(R.string.error_dialog_message_value_invalid));
		}
	}

	private void showErrorDialog(String message)
	{
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CalculatorActivity.this);
 
		// set title
		alertDialogBuilder.setTitle(R.string.error_dialog_title);
 
		// set dialog message
		alertDialogBuilder
			.setMessage(message)
			.setPositiveButton(getString(R.string.error_dialog_positive_button), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {  
		                dialog.cancel();
					}  
				  });
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
 
		// show it
		alertDialog.show();
		
	}

	private void saveComputationCount()
	{
		SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = sp.edit();
		
		int totalComputationCount = sp.getInt(TOTAL_COMPUTATION_COUNT,0);
		totalComputationCount++;
		editor.putInt(TOTAL_COMPUTATION_COUNT, totalComputationCount);
		
		int sessionComputationCount = sp.getInt(SESSION_COMPUTATION_COUNT,0);
		sessionComputationCount++;
		editor.putInt(SESSION_COMPUTATION_COUNT, sessionComputationCount);
		
		editor.commit();
	}
	
	private void saveStartedTimesCount()
	{
		SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = sp.edit();
		
		int startedTimesCount = sp.getInt(STARTED_TIMES_COUNT,0);
		startedTimesCount++;
		editor.putInt(STARTED_TIMES_COUNT, startedTimesCount);
		
		editor.putInt(SESSION_COMPUTATION_COUNT, 0); // initialize session computation count with 0
		
		editor.commit();
	}
}
