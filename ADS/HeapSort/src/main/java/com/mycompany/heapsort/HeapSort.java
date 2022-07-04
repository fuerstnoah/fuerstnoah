/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.heapsort;

/**
 *
 * @author Noah
 */
public class HeapSort{

    /**
     * Implementation eines Heapsorts
     *
     * @param arr zu sortierendes Feld
     */
    public static void heapSort(String[] arr){
        int n = arr.length;
        buildHeap(arr);
        for(int i = n - 1; i >= 0; i--){
            swap(0, i, arr);
            heapify(i, 0, arr);
        }
    }

    /**
     * @deprecated Replaced by fillArrayNew(String [])
     *
     * @param arr
     */
    @Deprecated
    public static void fillArray(String[] arr){
        for(int i = 0; i < arr.length; i++){
            arr[i] = genRandomString(16);
        }
    }

    /**
     * Füllt Feld mit Strings
     *
     * @param arr Feld von Strings
     */
    public static void fillArrayNew(String[] arr){
        for(String str : arr){
            str = genRandomString(16);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args){
        printBenchmarks();
    }

    /**
     * Feld wird so umstrukturiert, dass die Heap-Bedingung erfüllt ist
     *
     * @param arr zu sortierendes Feld
     */
    private static void buildHeap(String[] arr){
        int n = arr.length;
        for(int i = n / 2 - 1; i >= 0; i--){
            heapify(n, i, arr);
        }
    }

    /**
     * Sortiert Heap, stellt Heap-Bedingung her
     *
     * @param arr zu sortierendes Feld
     * @param n   Länge des Heaps
     * @param i   Aktueller Index
     */
    private static void heapify(int n, int i, String[] arr){
        int largest = i;
        int childLeft = 2 * i + 1;
        int childRight = 2 * i + 2;

        //setzt largest = childLeft wenn größer als Root
        boolean isLeftChildLargerThanParent = n > childLeft && arr[childLeft]
                .compareTo(arr[largest]) > 0;
        if(isLeftChildLargerThanParent){
            largest = childLeft;
        }

        //setzt largest = childRight wenn größer als Root/childLeft
        boolean isRightChildLargerThanLargest = n > childRight && arr[childRight]
                .compareTo(arr[largest]) > 0;
        if(isRightChildLargerThanLargest){
            largest = childRight;
        }

        //Überprüft ob largest Root ist, wenn nicht swap largest mit Root und neuer rekursiver Aufruf
        boolean isLargestRoot = largest != i;
        if(isLargestRoot){
            swap(i, largest, arr);
            heapify(n, largest, arr);
        }
    }

    /**
     * Vertauscht zwei Strings in einem Feld
     *
     * @param arr Feld von Strings
     * @param a   Index
     * @param b   Index
     */
    private static void swap(int a, int b, String[] arr){
        String temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * Generiert ein Zufälligen String
     *
     * @param len Länge des Strings
     *
     * @return Random generierten String
     */
    private static String genRandomString(int len){
        String str = "";
        for(int i = 0; i < len; i++){
            char c = (char) ((Math.random() * 95) + 31);
            str += c;
        }
        return str;
    }

    /**
     * Gibt Ergebnisse der Benchmarks aus
     */
    private static void printBenchmarks(){
        int[] n = new int[]{100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000, 2000000, 4000000, 8000000};
        for(int i : n){
            System.out.println(
                    "Laufzeit für n = " + i + " beträgt: " + benchmark(i) + "ms");
            System.out.println(
                    "Laufzeit für n = " + i + " beträgt: " + preciseBenchmark(i,
                            3) + "ms");
        }

    }

    /**
     * Misst die Durchschnittliche Laufzeit von mehreren Durchläufen
     *
     * @param n      Länge des Feldes
     * @param factor Anzahl der Durchläufe
     *
     * @return Durchschnittliche Laufzeit
     */
    private static long preciseBenchmark(int n, int factor){
        long result = 0;
        for(int i = 0; i < factor; i++){
            result += benchmark(n);
        }
        return result / factor;
    }

    /**
     * Misst die Laufzeit für einen Durchlauf
     *
     * @param n Länge des Feldes
     *
     * @return Laufzeit
     */
    private static long benchmark(int n){
        String[] arr = new String[n];
        fillArrayNew(arr);
        long start = System.nanoTime();
        heapSort(arr);
        long end = System.nanoTime();
        return (end - start) / 1000000;
    }

}
