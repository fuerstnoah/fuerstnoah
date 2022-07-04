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
        matrix = smoothenMatrix(matrix);
        printMatrix(matrix);
    }

    private static void printMatrix(long[][] mat){
        for(long[] mat1 : mat){
            System.out.println();
            for(long l : mat1){
                System.out.println(l + " ");
            }
        }
    }

    private static long[][] smoothenMatrix(long[][] mat){
        long[][] targetMatrix = new long[mat.length][mat[0].length];
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat[i].length; j++){
                targetMatrix[i][j] = avgOfMatrix(mat, i, j);

            }
        }
        return targetMatrix;
    }

    private static long avgOfMatrix(long[][] mat, int i, int j){
        if(i == 0 && j == 0){
            return avg(mat[i][j], mat[i][j + 1], mat[i + 1][j], mat[i + 1][j + 1]);
        }else if(i == 0 && j == mat[i].length - 1){
            return avg(mat[i][j], mat[i][j - 1], mat[i + 1][j], mat[i + 1][j - 1]);
        }else if(i == 0){
            return avg(mat[i][j], mat[i][j + 1], mat[i + 1][j], mat[i + 1][j + 1], mat[i][j - 1], mat[i + 1][j - 1]);
        }else if(i == mat.length - 1 && j == 0){
            return avg(mat[i][j], mat[i][j + 1], mat[i - 1][j], mat[i - 1][j + 1]);
        }else if(j == 0){
            return avg(mat[i][j], mat[i][j + 1], mat[i + 1][j], mat[i + 1][j + 1], mat[i - 1][j], mat[i - 1][j + 1]);
        }else if(i == mat.length - 1 && j == mat[i].length - 1){
            return avg(mat[i][j], mat[i][j - 1], mat[i - 1][j], mat[i - 1][j - 1]);
        }else if(i == mat.length - 1){
            return avg(mat[i][j], mat[i][j + 1], mat[i - 1][j], mat[i - 1][j + 1], mat[i][j - 1], mat[i - 1][j - 1]);
        }else if(j == mat[i].length - 1){
            return avg(mat[i][j], mat[i][j - 1], mat[i + 1][j], mat[i + 1][j - 1], mat[i - 1][j], mat[i - 1][j - 1]);
        }else{
            return avg(mat[i][j], mat[i - 1][j - 1], mat[i - 1][j], mat[i - 1][j + 1], mat[i][j - 1], mat[i][j + 1], mat[i + 1][j - 1], mat[i + 1][j],
                    mat[i + 1][j + 1]);
        }
    }

    private static long avg(long... a){
        if(a.length == 0){
            return 0;
        }
        long avg = 0;
        for(long l : a){
            avg += l;
        }
        avg /= a.length;
        return avg;
    }

}
