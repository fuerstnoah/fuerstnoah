package de.hsrm.ads;

/**
 *
 * @author Noah
 */
public class BubbleSort extends Sort {

    @Override
    public void sort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 1; j < a.length - i; j++) {
                if(lt(a[j-1],a[j]))swap(a,j-1,j);
            }
        }
    }

    public static void main(String[] args) {
        BubbleSort sort = new BubbleSort();
        sort.runSmall(1000);
    }
}
