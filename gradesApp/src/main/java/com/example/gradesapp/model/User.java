package com.example.gradesapp.model;

import java.lang.*;
import java.util.ArrayList;
import java.util.Observable;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import br.com.kots.mob.complex.preferences.ComplexPreferences;

/**
 *  Description of class.
 *
 *  @author Zakk Lefkowitz (zakkl13)
 *  @author Jason Barrett (jason95)
 *  @author Tanner Hudson (tannerh4)
 *  @version 2014.11.30
 */
public class User extends Observable implements Parcelable {

    private ArrayList<Class> clsArray;
    private String curClassName;
    private String userName;
    private int totCrHrs;

    /**
     * Creates the classes Object
     * @param userName
     */
    public User(String userName)
    {
        clsArray = new ArrayList<Class>();
        this.setUserName(userName);
    }

    /**
     * Updates the model from saved complex preferences
     * @param appContext
     */
    public void updateModel(Context appContext) {
        ComplexPreferences cp = ComplexPreferences.getComplexPreferences(
            appContext, "Classes", Context.MODE_PRIVATE);
        for (Class c: clsArray)
        {
            //calls the second class constructor which sets the chain in motion
            c = new Class(cp.getObject(c.getName(), Class.class), appContext);
        }
    }

    /**
     * Saves the model, sets up the chain for saving the complex preferences
     * at each level
     * @param appContext
     */
    public void saveModel(Context appContext)
    {
        ComplexPreferences cp = ComplexPreferences.getComplexPreferences(
            appContext, "Classes", Context.MODE_PRIVATE);
        //save each class (and everything inside it) then put it into the complex preferences
        for (Class c: clsArray)
        {
            c.saveClass(appContext);
            cp.putObject(c.getName(), c);
        }
        //saves the model
        cp.putObject("Model", this);
        cp.commit();
    }

    /**
     * Adds a class to the array from its sharedPreference (created in the
     * add class activity)
     * @param cls
     */
    public void addClass(Class cls)
    {
        clsArray.add(cls);
        totCrHrs += cls.getNumCrHrs();

        notifyObservers();
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void deleteClass()
    {
        Class cls = getCurClass();
        clsArray.remove(cls);
        totCrHrs -= cls.getNumCrHrs();

        notifyObservers();

    }

    /**
     * Returns a string array of the names of each class for use in the spinner
     * @return a string array of class names
     */
    public String[] getNameArray()
    {
        ArrayListStack<String> stack = new ArrayListStack<String>();
        for (Class cls: clsArray)
        {
            stack.push(cls.getName());
        }

        String[] classList = new String[stack.size()];

        int i = 0;
        while (!stack.isEmpty())
        {
            classList[i] = stack.pop();
            i++;
        }

        return classList;
    }

    /**
     * Sets the current selected class
     * @param className
     */
    public void setCurClass(String className)
    {
        curClassName = className;
    }

    /**
     * returns the current class object the corresponds to the name
     * @return a class object
     */
    public Class getCurClass()
    {
        for (int i = 0; i < clsArray.size(); i++)
        {
            if (clsArray.get(i).getName().equals(curClassName))
            {
                return clsArray.get(i);
            }
        }

        return null;
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @return
     */
    public Double getGpa()
    {
        double gpa = 0.00;
        int creditHours = 0;

        for (Class c: clsArray)
        {
            creditHours += c.getNumCrHrs();
            double thisClassGrade = c.getGrade();
            if (thisClassGrade > 94.0)
            {
                gpa += 4.0;
            }
            else if (thisClassGrade > 90.0)
            {
                gpa += 3.7;
            }
            else if (thisClassGrade > 87.0)
            {
                gpa += 3.3;
            }
            else if (thisClassGrade > 83.0)
            {
                gpa += 3.0;
            }
            else if (thisClassGrade > 80.0)
            {
                gpa += 2.7;
            }
            else if (thisClassGrade > 77.0)
            {
                gpa += 2.3;
            }
            else if (thisClassGrade > 73.0)
            {
                gpa += 2.0;
            }
            else if (thisClassGrade > 70.0)
            {
                gpa += 1.7;
            }
        }

        return gpa/ creditHours;
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @return
     */
    public ArrayList<Class> getClsArray()
    {
        return clsArray;
    }


    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public int getTotCrHrs()
    {
        return totCrHrs;
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param totCrHrs
     */
    public void setTotCrHrs(int totCrHrs)
    {
        this.totCrHrs = totCrHrs;
    }

    // ----------------------------------------------------------
    /**
     * Create a new User object.
     * @param in
     */
    protected User(Parcel in) {
        if (in.readByte() == 0x01) {
            clsArray = new ArrayList<Class>();
            in.readList(clsArray, Class.class.getClassLoader());
        } else {
            clsArray = null;
        }
        curClassName = in.readString();
        userName = in.readString();
        totCrHrs = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (clsArray == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(clsArray);
        }
        dest.writeString(curClassName);
        dest.writeString(userName);
        dest.writeInt(totCrHrs);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}