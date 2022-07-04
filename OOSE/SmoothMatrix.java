/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Noah
 */
public class SmoothMatrix{

    public static void main(String[] args){
        long[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        printMatrix(matrix);
        matrix = glaetteMatrix(matrix);
        printMatrix(matrix);
    }

    static void printMatrix(long[][] mat){
        for(long[] mat1 : mat){
            System.out.println();
            for(int j = 0; j < mat1.length; j ++){
                System.out.print(mat1[j] + " ");
            }
        }
    }

    static long[][] glaetteMatrix(long[][] mat){
        long[][] zielM = new long[mat.length][mat[0].length];

        for(int i = 0; i < mat.length; i ++){
            for(int j = 0; j < mat[i].length; j ++){
                zielM[i][j] = durchschnitt(mat, i, j);

            }

        }
        return zielM;
    }

    static long durchschnitt(long[][] mat, int i, int j){
        long durchschnitt;
        if(i == 0 && j == 0){
            durchschnitt = (mat[i][j] + mat[i][j + 1] + mat[i + 1][j] + mat[i + 1][j + 1]) / 4;
        }else if(i == 0 && j == mat[i].length - 1){
            durchschnitt = (mat[i][j] + mat[i][j - 1] + mat[i + 1][j] + mat[i + 1][j - 1]) / 4;
        }else if(i == 0){
            durchschnitt = (mat[i][j] + mat[i][j + 1] + mat[i + 1][j] + mat[i + 1][j + 1] + mat[i][j - 1] + mat[i + 1][j - 1]) / 6;
        }else if(i == mat.length - 1 && j == 0){
            durchschnitt = (mat[i][j] + mat[i][j + 1] + mat[i - 1][j] + mat[i - 1][j + 1]) / 4;
        }else if(j == 0){
            durchschnitt = (mat[i][j] + mat[i][j + 1] + mat[i + 1][j] + mat[i + 1][j + 1] + mat[i - 1][j] + mat[i - 1][j + 1]) / 6;
        }else if(i == mat.length - 1 && j == mat[i].length - 1){
            durchschnitt = (mat[i][j] + mat[i][j - 1] + mat[i - 1][j] + mat[i - 1][j - 1]) / 4;
        }else if(i == mat.length - 1){
            durchschnitt = (mat[i][j] + mat[i][j + 1] + mat[i - 1][j] + mat[i - 1][j + 1] + mat[i][j - 1] + mat[i - 1][j - 1]) / 6;
        }else if(j == mat[i].length - 1){
            durchschnitt = (mat[i][j] + mat[i][j - 1] + mat[i + 1][j] + mat[i + 1][j - 1] + mat[i - 1][j] + mat[i - 1][j - 1]) / 6;
        }else{
            durchschnitt = (mat[i][j] + mat[i - 1][j - 1] + mat[i - 1][j] + mat[i - 1][j + 1] + mat[i][j - 1] + mat[i][j + 1] + mat[i + 1][j - 1] + mat[i + 1][j] + mat[i + 1][j + 1]) / 9;
        }

        return durchschnitt;

    }

}
