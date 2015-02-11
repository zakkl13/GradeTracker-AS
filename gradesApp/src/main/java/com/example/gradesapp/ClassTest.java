package com.example.gradesapp;

import junit.framework.TestCase;

// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Zakk
 *  @version Dec 3, 2014
 */
public class ClassTest extends TestCase
{
    private Class cls;
    private Category cat1;
    private Category cat2;
    private Category cat3;

    public void setUp()
    {
        cls = new Class(0, true, "Class");
        cat1 = new Category(20, "cat1");
        cat2 = new Category(30, "cat2");
        cat3 = new Category(50, "cat3");
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetCategory()
    {
        cls.addCategory(cat1);
        cls.addCategory(cat2);
        cls.addCategory(cat3);

        assertEquals(cls.getCategory("cat2"), cat2);
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testRemoveCategory()
    {
        cls.addCategory(cat1);
        cls.addCategory(cat2);
        cls.addCategory(cat3);

        cls.removeCategory("cat1");

        assertEquals(cls.getCats().size(), 2);
        assertEquals(cls.getCats().get(0), cat2);
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetGrade()
    {
        cat1.addAssmt(new Assignment("assgn", 10, 10));
        cat1.addAssmt(new Assignment("assgn", 7, 10));
        cat2.addAssmt(new Assignment("assgn", 8, 10));
        cat3.addAssmt(new Assignment("assgn", 10, 10));

        cls.addCategory(cat1);
        cls.addCategory(cat2);
        cls.addCategory(cat3);

        // 17/20*20% + 8/10*30% + 10/10 * 50% = 91
        assertEquals(cls.getGrade(), 111.02941176470588);
    }
}
