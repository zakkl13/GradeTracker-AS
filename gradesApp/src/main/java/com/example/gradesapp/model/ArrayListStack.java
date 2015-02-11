package com.example.gradesapp.model;

import java.util.ArrayList;
import java.util.EmptyStackException;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * // -------------------------------------------------------------------------
/**
 *
 *
 *  @author Jason
 *  @version Dec 2, 2014
 * @param <E>
 */
public class ArrayListStack<E> {
    private ArrayList<E> list;

    //~ Constructors .........................................................
    /**
     * the constructor
     */
    public ArrayListStack()
    {
        list = new ArrayList<E>();
    }


    //~ Methods ..............................................................
    /**
     * pushes the item to the top
     * @param item is an item
     */
    public void push(E item)
    {
        list.add(0, item);

    }

    /**
     * pops an item off the top
     */
    public E pop()
    {
        E item = top();
        if (list.isEmpty())
        {
            throw new EmptyStackException();
        }
        else
        {
            list.remove(0);
        }

        return item;

    }

    /**
     * top method returns top
     * @return the top
     */
    public E top()
    {
        if (list.isEmpty())
        {
            throw new EmptyStackException();
        }
        else
        {
            return list.get(0);
        }
    }

    /**
     * size
     * @return the size
     */
    public int size()
    {
        return list.size();
    }

    /**
     * indicates if the stack is empty
     * @return is empty
     */
    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    /**
     * gets the arrayList
     * @return
     */
    public ArrayList<E> getList()
    {
        return list;
    }
    /**
     * clears the list
     */
    public void clear()
    {
        list.clear();
    }

}