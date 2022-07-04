/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pmt9;

import org.junit.Test;
import turban.utils.UserMsgException;

import static org.junit.Assert.*;

/**
 *
 * @author Noah
 */
public class TestSimpleXML{

    private String filePath = "HSBib.xml";

    @Test
    public void testLoad(){
        try{
            HSBib bib = SimpleFrameworkDeAndSerializer.deserialize(HSBib.class, filePath);
            Book book = bib.getBooks().get(0);
            Person person = bib.getPersonal().get(0);
        }catch(UserMsgException ex){
            fail(ex.getMessage());
        }
    }

    @Test
    public void testSave(){
        try{
            HSBib bib = SimpleFrameworkDeAndSerializer.deserialize(HSBib.class, filePath);
            Book book = new Book("007", "Author", "Titel", 0, null);
            bib.getBooks().add(book);
            Person person = new Person(9, "Name", HSPersonellType.Student, "abc@hs-rm.de");
            bib.getPersonal().add(person);
            SimpleFrameworkDeAndSerializer.serializeToFile(bib, filePath);
            bib = SimpleFrameworkDeAndSerializer.deserialize(HSBib.class, filePath);
            assertTrue(bib.getBooks().contains(book));
            assertTrue(bib.getPersonal().contains(person));
        }catch(UserMsgException ex){
            fail(ex.getMessage());
        }
    }
}
