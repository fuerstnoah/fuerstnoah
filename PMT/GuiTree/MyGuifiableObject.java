/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pmt7;

import java.awt.Image;
import turban.utils.IGuifiable;

/**
 *
 * @author noah
 */
public class MyGuifiableObject implements IGuifiable{

    private final String _guiString;

    public MyGuifiableObject(String guiString){
        _guiString = guiString;
    }

    @Override
    public String toGuiString(){
        return _guiString;
    }

    @Override
    public Image getGuiIcon(){
        return null;
    }

}
