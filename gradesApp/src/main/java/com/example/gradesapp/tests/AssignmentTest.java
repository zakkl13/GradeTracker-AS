package com.example.gradesapp.tests;

import com.example.gradesapp.model.Assignment;

import junit.framework.TestCase;

// -------------------------------------------------------------------------
/**
 * tes assmt
 *
 *  @author Zakk
 *  @version Dec 3, 2014
 */
public class AssignmentTest extends TestCase {

    private Assignment assmt;

    public void setUp()
    {
        assmt = new Assignment("test", 30, 15);

    }
    /**
     * test get
     */
    public void testGetName() {
        assertEquals("test", assmt.getName());
    }

    // ----------------------------------------------------------
    /**
     * test seyt
     */
    public void testSetName() {
        assmt.setName("h1");
        assertEquals("h1", assmt.getName());
    }

    // ----------------------------------------------------------
    /**
     * test
     */
    public void testGetTotPts() {
        assertEquals(30, assmt.getTotPts());
    }

    // ----------------------------------------------------------
    /**
     * test set
     */
    public void testSetTotPts(int totPts) {
        assmt.setTotPts(45);
        assertEquals(45, assmt.getTotPts());
    }

    // ----------------------------------------------------------
    /**
     * test get
     */
    public void testGetPtsRecieved() {
        assertEquals(15, assmt.getPtsRecieved());
    }

    // ----------------------------------------------------------
    /**
     * Test set
     */
    public void testSetPtsRecieved() {
        assmt.setPtsRecieved(20);
        assertEquals(20, assmt.getPtsRecieved());
    }
    /**
     * test tostring
     */
    public void testToString()
    {
        assertEquals("test: 15 / 30", assmt.toString());
    }




}

