package com.example.gradesapp.model;

import java.util.ArrayList;

import br.com.kots.mob.complex.preferences.ComplexPreferences;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

//-------------------------------------------------------------------------
/**
 *  Creates a new class object.
 *
 *  @author Zakk Lefkowitz (zakkl13)
 *  @author Jason Barrett (jason95)
 *  @author Tanner Hudson (tannerh4)
 *  @version 2014.11.30
 */
public class Class implements Parcelable {

	private int numCrHrs;
	private String name;
	private ArrayList<Category> categories;
	private double grade;
    private long oId;

	// ----------------------------------------------------------
	/**
	 * Create a new Class object.
	 * @param numCrHrs  The number of credit hours
	 * @param name      The name of the class
	 */
	public Class(int numCrHrs, String name)
	{
		this.numCrHrs = numCrHrs;
		this.name = name;
		this.categories = new ArrayList<Category>();
		grade = 0.00;

	}

	// ----------------------------------------------------------
	/**
	 * Create a new Class object.
	 * @param cls
	 * @param appContext
	 */
	public Class(Class cls, Context appContext)
	{
		ComplexPreferences cp = ComplexPreferences.getComplexPreferences(
		    appContext, "Classes", Context.MODE_PRIVATE);

		this.numCrHrs = cls.getNumCrHrs();
		this.name = cls.getName();
		this.grade = cls.getGrade();
		this.categories = new ArrayList<Category>();
		for (Category cat: cls.getCats())
		{
			categories.add(new Category(cp.getObject(name + cat.getName(),
			    Category.class), appContext));
			Log.d("setCat", cat.getName());
		}

	}

	// ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param appContext
     */
    public void saveClass(Context appContext)
    {
    	ComplexPreferences cp = ComplexPreferences.getComplexPreferences(
    	    appContext, "Classes", Context.MODE_PRIVATE);
    	for (Category cat: categories)
    	{
    		cat.saveCategory(appContext);
    	    cp.putObject(name + cat.getName(), cat);
    	    Log.d("saveCat", cat.getName());
    	}
    	cp.commit();
    }

    // ----------------------------------------------------------
	/**
	 * Adds a category
	 * @param cat the category to add
	 */
	public void addCategory(Category cat)
	{
	    categories.add(cat);
	}

	// ----------------------------------------------------------
	/**
	 * Place a description of your method here.
	 * @param catName
	 */
	public void removeCategory(String catName)
	{
		categories.remove(getCategory(catName));

	}

	// ----------------------------------------------------------
	/**
	 * Place a description of your method here.
	 * @param asgnName
	 */
	public void deleteAssignment(String asgnName)
	{
	    for (Category c: categories)
	    {
	        for (Assignment a: c.getAssmts())
	        {
	            if (a.getName().equals(asgnName))
	            {
	                c.getAssmts().remove(a);
	                Log.d("deleted", "a.getName()");
	            }
	        }
	    }
	}

	// ----------------------------------------------------------
	/**
	 * Place a description of your method here.
	 * @param catName
	 * @return
	 */
	public Category getCategory(String catName)
	{
		for (int i = 0; i < categories.size(); i++)
		{
			if (categories.get(i).getName().equals(catName))
			{
				return categories.get(i);
			}
		}

		return null;
	}

	// ----------------------------------------------------------
	/**
	 * Place a description of your method here.
	 * @return
	 */
	public String[] getCatNameArray()
	{
	    ArrayListStack<String> stack = new ArrayListStack<String>();

	    for (Category cat: categories)
	    {
	        stack.push(cat.getName());
	    }

	    String[] catList = new String[stack.size()];
	    int i = 0;
        while (!stack.isEmpty())
        {
            catList[i] = stack.pop();
            i++;
        }

        return catList;
	}


	// ----------------------------------------------------------
	/**
	 * Uses a stack to populate the String[] for the List view of grades
	 * the stack allows for the most recent items to be at the top of the list
	 * @return a String array of Categories and their assignments
	 */
	public String[] getAssgnArray()
	{
	    ArrayListStack<String> stack = new ArrayListStack<String>();

	    for (Category cat: categories)
        {
            for (Assignment asgn: cat.getAssmts())
            {
                stack.push(asgn.getName() + asgn.toString());
            }
            stack.push(cat.toString());
        }

	    String[] assgnList = new String[stack.size()];
	    int i = 0;
	    while (!stack.isEmpty())
	    {
	        assgnList[i] = stack.pop();
	        i++;
	    }

	    return assgnList;

	}

	// ----------------------------------------------------------
    /**
     * Gets the value of the grade in the class.
     * @return Returns the grade in the class
     */
    public double getGrade()
    {
    	double totalGrade = 0.0;

    	for (Category c : categories)
        {
            c.setGrade();
            Log.d("grade", c.getGrade() + "");
            totalGrade += c.getGrade() * c.getWeight();
            Log.d("totalGrade", totalGrade + "");

        }

    	return totalGrade;
    }

    public boolean equals(Object c)
    {
        if (c instanceof Class)
        {
            Class tmp = (Class) c;
            if (tmp.getName().equals(name))
            {
                return true;
            }
        }

        return false;
    }

    //Getters and Setters
	// ----------------------------------------------------------
	/**
	 * Get the number of credit hours.
	 * @return Returns the number of credit hours
	 */
	public int getNumCrHrs() {
		return numCrHrs;
	}

	// ----------------------------------------------------------
	/**
	 * Sets the number of credit hours.
	 * @param numCrHrs The value of the number of credit hours
	 */
	public void setNumCrHrs(int numCrHrs) {
		this.numCrHrs = numCrHrs;
	}

	// ----------------------------------------------------------
	/**
	 * @return the classes DB id
	 */
	public long getId() {
		return oId;
	}

	// ----------------------------------------------------------
	/**
	 * Sets the id of the class from the database
	 * @param id the id of the class as given by the database
	 */
	public void setId(long id) {
		oId = id;
	}

	// ----------------------------------------------------------
	/**
	 * Gets the name of the class.
	 * @return Returns the name of the class
	 */
	public String getName() {
		return name;
	}

	// ----------------------------------------------------------
	/**
	 * Sets the name of the class.
	 * @param name The value of the class's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	// ----------------------------------------------------------
	/**
	 * Gets a list of all the assignments that have been input.
	 * @return Returns a list of assignments
	 */
	public ArrayList<Category> getCats() {
		return categories;
	}

	// ----------------------------------------------------------
	/**
	 * Sets the value of the grade in the class.
	 * @param grade The grade in the class
	 */
	public void setGrade(double grade)
	{
		this.grade = grade;
	}

	protected Class(Parcel in) {
        numCrHrs = in.readInt();
        name = in.readString();
        if (in.readByte() == 0x01) {
            categories = new ArrayList<Category>();
            in.readList(categories, Category.class.getClassLoader());
        } else {
            categories = null;
        }
        grade = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(numCrHrs);
        dest.writeString(name);
        if (categories == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(categories);
        }
        dest.writeDouble(grade);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Class> CREATOR = new
    Parcelable.Creator<Class>() {
        @Override
        public Class createFromParcel(Parcel in) {
            return new Class(in);
        }

        @Override
        public Class[] newArray(int size) {
            return new Class[size];
        }
    };
}