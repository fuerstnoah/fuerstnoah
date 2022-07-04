package de.hsrm.ads;

import java.util.Arrays;

/**
 *
 * @author Noah
 */
public class SelectionSort extends Sort {

    @Override
    public void sort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int currentPos = i;
            for (int j = i + 1; j < a.length; j++) {
                if (lt(a[j], a[currentPos]))currentPos = j;
            }
            swap(a, currentPos, i);
        }
        System.out.println(Arrays.toString(a));
    }

    public static void main(String[] args) {
        SelectionSort sort = new SelectionSort();
        sort.runSmall(1000);
    }
}
