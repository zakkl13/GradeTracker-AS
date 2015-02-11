package com.example.gradesapp.tests;

import com.example.gradesapp.model.*;
import com.example.gradesapp.model.Class;

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
public class ClassesTest extends TestCase {

    private User clss;
    private com.example.gradesapp.model.Class clas1;
    private Class clas2;
    private Class clas3;

    public void setUp()
    {
        clss = new User("user");

        clas1 = new Class(0, true, "clas1");
        clas2 = new Class(0, true, "clas2");
        clas3 = new Class(0, true, "clas3");

        clss.addClass(clas1);
        clss.addClass(clas2);
        clss.addClass(clas3);
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetCurClass()
    {
        clss.setCurClass("clas2");

        assertEquals(clss.getCurClass(), clas2);
    }
    /**
     * test getCurClass
     */
    public void testGetCurClassNull()
    {
        clss.setCurClass("clas10");

        assertNull(clss.getCurClass());
    }
    /**
     * test Remove Class
     */
    public void testRemoveClass()
    {
        clss.setCurClass("clas2");
        clss.deleteClass();

        assertEquals(clss.getClsArray().get(1), clas3);
        assertEquals(clss.getClsArray().size(), 2);
    }
    /**
     * test ClassNameArray
     */
    public void testClassNameArray()
    {
        String[] testEquals = new String[3];
        testEquals[0] = "clas3";
        testEquals[1] = "clas2";
        testEquals[2] = "clas1";
        assertEquals(clss.getNameArray()[0], testEquals[0]);
    }
}
