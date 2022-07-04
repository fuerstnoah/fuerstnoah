package de.hsrm.ads;

public class InsertionSort extends Sort {

    @Override
    public void sort(int[] xs) {
        for (int i = xs.length - 2; i >= 0; i--) {
            if (gt(xs[i], xs[i + 1])) {
                for (int j = i; j < xs.length - 1 && gt(xs[j], xs[j + 1]); j++) {
                    swap(xs, j, j + 1);
                    if (j + 2 > xs.length - 1) {
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        InsertionSort sort = new InsertionSort();
        sort.sort(new int[]{5, 4, 3, 2, 1, 9, 6});
        sort.runSmall(100000);
    }
}
