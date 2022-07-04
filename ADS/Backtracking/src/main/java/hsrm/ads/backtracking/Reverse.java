/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hsrm.ads.backtracking;

/**
 *
 * @author Noah
 */
public class Reverse{

    /**
     *
     * @param str String
     * @param pos current position in String, 0 at first call
     *
     * @return reversed String
     */
    public static String reverse(String str, int pos){
        if(str.length() / 2 == pos){
            return str;
        }
        str = swap(str, pos, str.length() - pos - 1);
        return reverse(str, pos + 1);
    }

    public static void main(String[] args){
        if(args.length > 0){
            String arg = "";
            for(String arg1 : args){
                arg += " " + arg1;
            }
            System.out.println(reverse(arg, 0));
        }else{
            System.out.println(reverse("Hallo", 0));
        }
    }

    /**
     *
     * @param str
     * @param i   position of first char
     * @param j   position of scnd char
     *
     * @return String with swapped chars
     */
    private static String swap(String str, int i, int j){
        char[] chars = str.toCharArray();
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
        return new String(chars);
    }
}
