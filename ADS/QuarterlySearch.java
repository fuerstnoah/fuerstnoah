/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsrm.ads;

/**
 *
 * @author Noah
 */
public class QuarterlySearch{

    public static boolean quarterlySearch(int[] a, int lo, int hi, int value){
        if(hi - lo >= 4){
            int m = (hi - lo) / 2;
            int q1 = m / 2;
            int q2 = (int) (m * 1.5);
            if(a[m] == value){
                return true;
            }else if(a[m] < value){
                if(a[q2] == value){
                    return true;
                }else if(a[q2] < value){
                    return quarterlySearch(a, q2, hi, value);
                }else{
                    return quarterlySearch(a, m, q2, value);
                }
            }else{
                if(a[q1] == value){
                    return true;
                }else if(a[q1] < value){
                    return quarterlySearch(a, q1, m, value);
                }else{
                    return quarterlySearch(a, lo, q1, value);
                }
            }
        }
        for(int i = lo; i <= hi; i++){
            if(a[i] == value){
                return true;
            }
        }
        return false;
    }

    public static boolean quarterlySearch(int[] a, int value){
        return quarterlySearch(a, 0, a.length - 1, value);
    }

    public static void main(String[] args){
        int[] a = new int[]{2, 5, 7, 8, 11, 15};
        System.out.println(quarterlySearch(a, 9));
    }

}
