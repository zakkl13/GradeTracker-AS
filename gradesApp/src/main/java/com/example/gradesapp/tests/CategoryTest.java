package com.example.gradesapp.tests;

import com.example.gradesapp.model.Assignment;
import com.example.gradesapp.model.Category;

import junit.framework.TestCase;

// -------------------------------------------------------------------------
/**
 *  Cat Tst
 *
 *  @author Zakk
 *  @version Dec 3, 2014
 */
public class CategoryTest extends TestCase {

    private Category cat;


    public void setUp()
    {

        cat = new Category(100, "Test");

    }

    // ----------------------------------------------------------
    /**
     * Getweight
     */
    public void testGetWeight()
    {

        assertEquals(cat.getWeight(), 100);
    }

    /**
     * test setWeight
     */
    public void testSetWeight()
    {
        cat.setWeight(99);

        assertEquals(99, cat.getWeight());
    }
    /**
     * GetName
     */
    public void testGetName()
    {

        assertEquals(cat.getName(), "Test");
    }

    /**
     * test setWeight
     */
    public void testSetName()
    {
        cat.setName("Last");

        assertEquals("Last", cat.getName());
    }
    /**
     * get grade
     */
    public void testGetGrade()
    {

        assertEquals(0.00, cat.getGrade());
    }
    /**
     * set grade
     */
    public void testSetGrade()
    {
        Assignment assmt = new Assignment("hh1", 30, 15);
        Assignment assmt2 = new Assignment("hh1", 30, 30);

        cat.addAssmt(assmt);
        cat.addAssmt(assmt2);
        cat.setGrade();
        assertEquals(0.75, cat.getGrade());

    }
}

