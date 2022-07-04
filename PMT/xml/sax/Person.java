package com.mycompany.pmt9;

import java.util.Objects;
import org.simpleframework.xml.*;
import org.simpleframework.xml.core.Validate;

@Root(strict = false)
public class Person{

    @Attribute(name = "ID")
    private int id;
    @Text
    private String _strName = "";
    @Attribute(name = "Email")
    private String _strEmail = "";
    @Attribute(name = "Type")
    private String type = null;
    @Transient
    private HSPersonellType hsType = null;

    public Person(int _id, String strName, HSPersonellType personelltype, String strEmail){
        id = _id;
        _strName = strName;
        _strEmail = strEmail;
        hsType = personelltype;
    }

    public Person(){
    }

    @Validate
    public void validate(){
        hsType = HSPersonellType.valueOf(type);
    }

    public void setHsType(HSPersonellType _hsType){
        this.hsType = _hsType;
        type = _hsType.toString();
    }

    public int getId(){
        return id;
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
        if(id == other.getId()){
            return false;
        }
        if(!Objects.equals(this._strName, other._strName)){
            return false;
        }
        if(!Objects.equals(this._strEmail, other._strEmail)){
            return false;
        }
        return this.hsType == other.hsType;
    }

    public HSPersonellType getPersonellType(){
        return hsType;
    }
}
