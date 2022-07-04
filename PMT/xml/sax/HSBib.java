/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pmt9;

import java.util.List;
import org.simpleframework.xml.*;

/**
 *
 * @author Noah
 */
@Root(strict = false)
public class HSBib{

    @Element(name = "HSName")
    private String name;
    @ElementList(name = "Books", entry = "Book")
    private List<Book> books;
    @ElementList(name = "Personal", entry = "Person")
    private List<Person> personal;

    public HSBib(String _name, List<Book> _books, List<Person> _personal){
        this.name = _name;
        this.books = _books;
        this.personal = _personal;
    }

    public HSBib(){
    }

    public String getName(){
        return name;
    }

    public List<Book> getBooks(){
        return books;
    }

    public List<Person> getPersonal(){
        return personal;
    }

}
