package com.mycompany.heapsort;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Arrays;
import org.junit.*;

/**
 *
 * @author Noah
 */
public class TestHeapSort{

    private String[] unsorted, sorted;

    /**
     * sets up Arrays, prints unsorted Array before tests
     */
    @Before
    public void setUp(){
        unsorted = new String[20];
        HeapSort.fillArrayNew(unsorted);
        sorted = new String[unsorted.length];
        System.arraycopy(unsorted, 0, sorted, 0, unsorted.length);
        Arrays.sort(sorted);
        System.out.println("Unsortiert:");
        System.out.println(Arrays.toString(unsorted));
    }

    /**
     * prints Array after tests
     */
    @After
    public void printArray(){
        System.out.println("\nSortiert:");
        System.out.println(Arrays.toString(unsorted));
    }

    /**
     * Checks if Heapsort works
     */
    @Test
    public void testHeapSort(){
        HeapSort.heapSort(unsorted);
        Assert.assertArrayEquals("Array is unsorted", sorted, unsorted);
    }
}
