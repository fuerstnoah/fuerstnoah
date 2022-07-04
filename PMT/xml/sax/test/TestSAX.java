/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pmt9;

import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 *
 * @author Noah
 */
public class TestSAX{

    private EmailExtractor ex = new EmailExtractor();

    @Test
    public void testSAX(){
        ex.parseXMLWithSAX("HSBib.xml");
        List<String> emails = ex.getLstEmails();
        emails.forEach(System.out :: println);
        assertFalse(emails.isEmpty());
    }

}
