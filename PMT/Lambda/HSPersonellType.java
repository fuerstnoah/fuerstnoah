/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.lambda;

import java.awt.Image;
import turban.utils.IGuifiable;

/**
 *
 * @author Noah
 */
public enum HSPersonellType implements IGuifiable{
    Student("Student", true, false, false, false),
    Professor("Professor", false, true, false, false),
    Tutor("Tutor", false, true, false, false),
    President("Präsident", false, true, true, false),
    Dean("Dekan", false, true, true, false),
    ITAdmin("IT Admin", false, false, false, true),
    Secretary("Sekretariat", false, false, false, true);

    private final String _guiString;
    private final boolean _receivesLessons;
    private final boolean _givesLessons;
    private final boolean _hasOrgResp;
    private final boolean _isAdminStaff;

    private HSPersonellType(String guiString, boolean receivesLessons, boolean givesLessons, boolean hasOrgResp, boolean isAdminStaff){
        _guiString = guiString;
        _receivesLessons = receivesLessons;
        _givesLessons = givesLessons;
        _hasOrgResp = hasOrgResp;
        _isAdminStaff = isAdminStaff;
    }

    public boolean receivesLessons(){
        return _receivesLessons;
    }

    public boolean givesLessons(){
        return _givesLessons;
    }

    public boolean hasOrgResp(){
        return _hasOrgResp;
    }

    public boolean isAdminStaff(){
        return _isAdminStaff;
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
