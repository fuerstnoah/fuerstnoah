package com.mycompany.pmt8;

import java.awt.Image;
import java.util.Objects;
import turban.utils.IGuifiable;

public class Person implements IGuifiable{

    private String _strId = "";
    private String _strName = "";
    private String _strEmail = "";
    private HSPersonellType _personelltype = null;

    public Person(String strId, String strName, HSPersonellType personelltype, String strEmail){
        _strId = strId;
        _strName = strName;
        _strEmail = strEmail;
        _personelltype = personelltype;
    }

    @Override
    public String toGuiString(){
        return _personelltype.toGuiString() + " " + _strName;
    }

    @Override
    public Image getGuiIcon(){
        return null;
    }

    public String getId(){
        return _strId;
    }

    public String getName(){
        return _strName;
    }

    public String getEmail(){
        return _strEmail;
    }

    @Override
    public int hashCode(){
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }

        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        final Person other = (Person) obj;
        if(!Objects.equals(this._strId, other._strId)){
            return false;
        }
        if(!Objects.equals(this._strName, other._strName)){
            return false;
        }
        if(!Objects.equals(this._strEmail, other._strEmail)){
            return false;
        }
        return this._personelltype == other._personelltype;
    }

    public HSPersonellType getPersonellType(){
        return _personelltype;
    }
}
