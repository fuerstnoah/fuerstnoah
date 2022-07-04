/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pmt8;

import java.util.List;
import org.junit.*;

/**
 *
 * @author Noah
 */
public class TestPersonalParser{

    @Test
    public void testParsePersonal(){
        PersonalParser parser = new PersonalParser();
        parser.parsePersonal();
        List<Person> list = parser.getLstPersonal();
        Assert.assertFalse("List is empty", list.isEmpty());
        Assert.assertEquals("Wrong ID", "001", list.get(0).getId());
    }

}
