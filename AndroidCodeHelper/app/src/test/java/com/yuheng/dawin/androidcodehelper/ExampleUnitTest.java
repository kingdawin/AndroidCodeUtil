package com.yuheng.dawin.androidcodehelper;

import org.junit.Test;

import other.SortArithmetic;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

    }
    public void testSortArithmetic()
    {
        SortArithmetic sortArithmetic=new SortArithmetic();
        sortArithmetic.bubbleSort(new int[]{2,3,1,5,0,11,20,20});

        System.out.print("");
    }
}