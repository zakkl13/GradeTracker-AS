package com.example.gradesapp;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.widget.EditText;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import java.util.Observable;
import java.util.Observer;

import br.com.kots.mob.complex.preferences.ComplexPreferences;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gradesapp.model.User;

/**
 *  Description of class.
 *
 *  @author Zakk Lefkowitz (zakkl13)
 *  @author Jason Barrett (jason95)
 *  @author Tanner Hudson (tannerh4)
 *  @version 2014.11.30
 */
public class MainActivity
    extends ActionBarActivity implements Observer
{
    private User clss;

    @Override
    /**
     * Populates the spinner with the User's classes and updates the model
     * with the classes
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		Intent inte = getIntent();
		Bundle b = inte.getExtras();
		//checks if another activity passed the model back through
		if (b != null)
		{
			clss = (User) b.getParcelable("Classes");
			updateSpinner();
		}
		else //if not then check if there is any saved date
		{	 //if so grab it and set the model, if not create a new model
			clss = null;

		    ComplexPreferences complexPreferences = ComplexPreferences.
		        getComplexPreferences(this, "Classes", MODE_PRIVATE);
		    clss = complexPreferences.getObject("Model", User.class);

		    if (clss == null)
		    {
		    	createUser();
		    }
		    else
		    {
		    	clss.updateModel(getApplicationContext());
		    	Toast.makeText(this, "Welcome Back " +
		    	            clss.getUserName() + "!", Toast.LENGTH_SHORT).show();
		    	updateSpinner();

                TextView gpa = (TextView) findViewById(R.id.gpa);
                gpa.setText(String.valueOf(clss.getGpa()));
		    }
		}


    }

    private void createUser()
    {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setView(promptsView);

        final EditText userName = (EditText) promptsView
                .findViewById(R.id.dialogUserName);

        // set dialog message
        alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("OK",
              new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    String usr = userName.getText().toString();
                    clss = new User(usr);
                }
              });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

        //Toast.makeText(this, "Welcome " +
          //      userName.getText().toString(), Toast.LENGTH_SHORT).show();
    }

	private void updateSpinner() {
		//Sets the spinner to display the string array of the names of the
	    //classes
        Spinner spinner = (Spinner) findViewById(R.id.classSpinner);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.
	        R.layout.simple_spinner_item, clss.getNameArray());
	    adapter.setDropDownViewResource(android.R.layout.
	        simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	}

    /**
     * Opens the addClass Activity
     * @param view the button
     */
    public void addClass(View view)
    {
    	Intent intent = new Intent(this, AddClassActivity.class);
        startActivity(intent);

        //putClass() into database
    }

    /**
     * Gets the selected class and passes it via Intent Extras to the Class
     * Display View
     * @param view the button
     */
    public void goToClass(View view)
    {
    	Spinner spinner = (Spinner) findViewById(R.id.classSpinner);
    	String curClass = (String) spinner.getSelectedItem();
    	clss.setCurClass(curClass);

    	Intent intent = new Intent(this, ClassDisplayActivity.class);
    	intent.putExtra("Classes", clss);
        startActivity(intent);
    }

    /**
     * deletes the current class on the spinner
     * @param view
     */
    public void deleteClass(View view)
    {
    	Spinner spinner = (Spinner) findViewById(R.id.classSpinner);
    	String curClass = (String) spinner.getSelectedItem();
    	clss.setCurClass(curClass);

    	clss.deleteClass();
        //db deleteclass

    	clss.saveModel(getApplicationContext());

    	updateSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


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


	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}
}
