package com.example.gradesapp;

import android.widget.TextView;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gradesapp.model.*;

// -------------------------------------------------------------------------
/**
 *  This class handles when grades are added.
 *
 *  @author Zakk Lefkowitz (zakkl13)
 *  @author Jason Barrett (jason95)
 *  @author Tanner Hudson (tannerh4)
 *  @version 2014.11.30
 */
public class AddGradesActivity
    extends ActionBarActivity implements Observer, OnItemSelectedListener
{
    private com.example.gradesapp.model.Class thisClass;
    private User clss;
    private ArrayList<Category> categories;
    private Category currentCat;
    private Assignment assmt;
    private String gradeName;

    /**
     * This method is run when a new grade is created; it sets the content
     * view and gets the intent from the saved instance state.  It also gets
     * the current class and categories, and updates the spinner.
     * @param savedInstanceState A saved state of the instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent inte = getIntent();
        Bundle b = inte.getExtras();
		if (b != null)
		{
			clss = (User) b.getParcelable("Classes");
		}
		thisClass = clss.getCurClass();

		categories = thisClass.getCats();
        updateSpinner();

        Spinner spinner = (Spinner) findViewById(R.id.categories);
        spinner.setOnItemSelectedListener(this);


    }
    // ----------------------------------------------------------
    /**
     * This method is called when the calculate button is pressed.
     * @param view The view
     */
    public void button2(View view)
    {
        //get the current category chosen
        Spinner spinner = (Spinner) findViewById(R.id.categories);
        String curCategory = (String) spinner.getSelectedItem();
        currentCat = thisClass.getCategory(curCategory);

        EditText nameG = (EditText) findViewById(R.id.gradeName); //get values
        EditText ptsRcvd = (EditText) findViewById(R.id.name);
        EditText totPts = (EditText) findViewById(R.id.totPts);

        gradeName = nameG.getText().toString(); //make Assignment obj
        String ptsRcv = ptsRcvd.getText().toString();
        String ptsTot = totPts.getText().toString();

        if (gradeName == null)
        {
        	gradeName = "NONAME";
        }

        if (!isDouble(ptsRcv))
        {
        	Toast.makeText(this, "Please enter a value for Points Recieved",
        	    Toast.LENGTH_SHORT).show();
        }
        else if (!isDouble(ptsTot))
        {
        	Toast.makeText(this, "Please enter a value for Total Points",
        	    Toast.LENGTH_SHORT).show();
        }
        else
        {
            assmt = new Assignment(gradeName, Double.parseDouble(ptsTot),
                Double.parseDouble(ptsRcv));
            currentCat.addAssmt(assmt);
            clss.saveModel(getApplicationContext());

            currentCat.setGrade();
            TextView finalGrade = (TextView) findViewById(R.id.grade);
            finalGrade.setText(String.valueOf(100*currentCat.getGrade()));

            clss.saveModel(getApplicationContext());

            Intent intent = new Intent(this, ClassDisplayActivity.class);
            intent.putExtra("Classes", clss);
            startActivity(intent);
        }


    }

    /**
     * This method determines whether or not a string represents an integer.
     * @return Returns true if the string is an integer, otherwise false
     */
    private boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    // ----------------------------------------------------------
    /**
     * This method is called when adding another grade.
     * @param view The view
     */
    public void addAnother(View view)
    {
        //get the current category chosen
        Spinner spinner = (Spinner) findViewById(R.id.categories);
        String curCategory = (String) spinner.getSelectedItem();
        currentCat = thisClass.getCategory(curCategory);

        EditText nameG = (EditText) findViewById(R.id.gradeName); //get values
        EditText ptsRcvd = (EditText) findViewById(R.id.name);
        EditText totPts = (EditText) findViewById(R.id.totPts);


        gradeName = nameG.getText().toString(); //make Assignment obj
        String ptsRcv = ptsRcvd.getText().toString();
        String ptsTot = totPts.getText().toString();
        if (gradeName == null)
        {
        	gradeName = "NONAME";
        }

        if (!isDouble(ptsRcv))
        {
        	Toast.makeText(this, "Please enter a value for Points Recieved",
        	    Toast.LENGTH_SHORT).show();
        }
        else if (!isDouble(ptsTot))
        {
        	Toast.makeText(this, "Please enter a value for Total Points",
        	    Toast.LENGTH_SHORT).show();
        }
        else
        {
            assmt = new Assignment(gradeName, Double.parseDouble(ptsTot),
                Double.parseDouble(ptsRcv));
            currentCat.addAssmt(assmt);
            clss.saveModel(getApplicationContext());

            currentCat.setGrade();
            TextView finalGrade = (TextView) findViewById(R.id.grade);
            finalGrade.setText(String.valueOf(currentCat.getGrade()));

            nameG.setText("");
            ptsRcvd.setText("");//reset them
            totPts.setText("");
        }

    }

    // ----------------------------------------------------------
    /**
     * This method takes you to the home screen.
     * @param view The view
     */
    public void home(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Classes", clss);
        startActivity(intent);
    }
    // ----------------------------------------------------------

    /**
     * This method is called when adding categories to a class.
     * @param view The view
     */
    public void addCat(View view)
    {
        Intent intent = new Intent(this, AddCategoryActivity.class);
        intent.putExtra("Classes", clss);
        startActivity(intent);
    }

    /**
     * This method is called when deleting a category.
     * @param view The view
     */
    public void delete(View view)
    {
    	Spinner spinner = (Spinner) findViewById(R.id.classSpinner);
    	String catName = (String) spinner.getSelectedItem();

    	thisClass.removeCategory(catName);
    	clss.saveModel(getApplicationContext());
    	updateSpinner();

    }

    /**
     * This method is called when clearing the assignments for a category.
     * @param view The view
     */
    public void clear(View view)
    {
    	for (Category c : categories)
        {
    		c.clearAssmt();
        }

    }

    /**
     * This method is called when the spinner is updated.
     */
    public void updateSpinner()
    {
		//Sets the spinner to display the string array of the names of the
        //classes
        Spinner spinner = (Spinner) findViewById(R.id.categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, thisClass.getCatNameArray());
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * This method handles the options menu.
     * @param menu The menu of the app
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    /**
     * This method handles when items in the options menu are selected.
     * @param item The individual item in the menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is left intentionally blank.
     */
    @Override
    public void update(Observable arg0, Object arg1)
    {
        //Do Nothing

    }

    /**
     * This method is called to set the text of the final grade text view.
     */
	@Override
	public void onItemSelected(AdapterView<?> adapt, View v, int pos,
			long arg3) {
		TextView finalGrade = (TextView) findViewById(R.id.grade);
        finalGrade.setText(String.valueOf(categories.get(pos).getGrade()));



	}

	/**
	 * This method is left intentionally blank.
	 */
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
    //Do nothing

	}
}
