/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pmt9;

import java.util.Date;
import org.simpleframework.xml.*;

/**
 *
 * @author Noah
 */
@Root(strict = false)
public class Book{

    @Attribute(name = "ID")
    private String id;
    @Attribute(name = "Author")
    private String author;
    @Text
    private String title;
    @Attribute(name = "LentBy", required = false)
    private int lentBy;
    @Attribute(name = "ReturnDate", required = false)
    private Date returnDate;

    public Book(String _id, String _author, String _title, int _lentBy, Date _returnDate){
        this.id = _id;
        this.author = _author;
        this.title = _title;
        this.lentBy = _lentBy;
        this.returnDate = _returnDate;
    }

    public Book(){
    }

    public String getId(){
        return id;
    }

    public String getAuthor(){
        return author;
    }

    public String getTitle(){
        return title;
    }

    public int getLentBy(){
        return lentBy;
    }

    public Date getReturnDate(){
        return returnDate;
    }

}
