package com.example.gradesapp;

import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.gradesapp.model.*;
import com.example.gradesapp.model.Class;

// -------------------------------------------------------------------------
/**
 *  This class handles when classes are added.
 *
 *  @author Zakk Lefkowitz (zakkl13)
 *  @author Jason Barrett (jason95)
 *  @author Tanner Hudson (tannerh4)
 *  @version 2014.11.30
 */
public class AddClassActivity extends ActionBarActivity {
	private RadioGroup RadioGroup;
	User clss;

	/**
	 * This method runs when an activity is creates.  It sets the content view
	 * and gets the intent; this is all from the saved instance state
	 * @param savedInstanceState The saved state of the instance
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_class);
		Intent inte = getIntent();
		Bundle b = inte.getExtras();
		if (b != null)
		{
			clss = (User) b.getParcelable("Classes");
		}
	}

	/**
	 * This method handles what happens when the options menu is used.
	 * @param menu The menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present
		getMenuInflater().inflate(R.menu.add_class, menu);
		return true;
	}

	/**
	 * This method handles when items in the options menu are selected.
	 * @param item The individual item in the menu
	 */
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

	/**
	 * This method handles adding classes, including the entering of text to
	 * the app's text fields as well as the selection of radio buttons.
	 * @param v The view
	 */
	public void clsAdd(View v)
	{
		//This code block checks which radio button is selected in the radio
	    //group
		RadioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		int selectedId = RadioGroup.getCheckedRadioButtonId();
		boolean passFail = false;
		if (selectedId == 0)
		{
			passFail = true;
		}

		//Get references to edit text fields
		EditText crHours = (EditText) findViewById(R.id.percent);
		EditText className = (EditText) findViewById(R.id.name);

		//Create a class object with the information from the editText fields
		com.example.gradesapp.model.Class cls = new Class(Integer.parseInt(crHours.getText().toString()),
		    passFail, className.getText().toString());

		if (classExist(cls))
		{
		    Toast.makeText(this, "You've already added this class!",
		        Toast.LENGTH_SHORT).show();
		}
		else
		{
		    clss.addClass(cls);
	        clss.saveModel(getApplicationContext());

	        //Return to main activity menu
	        Intent intent = new Intent(this, MainActivity.class);
	        intent.putExtra("Classes", clss);
	        startActivity(intent);
		}

	}

	private boolean classExist(Class cls)
	{
	    for (Class c: clss.getClsArray())
	    {
	        if (c.equals(cls))
	        {
	            return true;
	        }
	    }

	    return false;
	}
}

