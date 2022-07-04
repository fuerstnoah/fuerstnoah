/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pmt9;

import java.util.regex.Pattern;

/**
 *
 * @author Noah
 */
public class HexNum{

    public static boolean isHexNum(String text){
        return Pattern.compile("-?\\p{XDigit}+").matcher(text).matches();
    }
}
