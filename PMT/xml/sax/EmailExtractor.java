/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pmt9;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Noah
 */
public class EmailExtractor extends DefaultHandler{

    private List<String> _lstEmails = new ArrayList<>(5);

    public List<String> getLstEmails(){
        return _lstEmails;
    }

    public void setLstEmails(List<String> __lstEmails){
        this._lstEmails = __lstEmails;
    }

    @Override
    public void startElement(String _uri, String _localName, String _qName, Attributes _attributes) throws SAXException{
        if(_qName.equalsIgnoreCase("Person")){
            _lstEmails.add(_attributes.getValue("Email"));
        }
    }

    public void parseXMLWithSAX(String file){
        try{
            SAXParserFactory fac = SAXParserFactory.newInstance();
            SAXParser parser = fac.newSAXParser();
            parser.parse(new File(file), this);
        }catch(ParserConfigurationException | SAXException | IOException ex){
            Logger.getLogger(EmailExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
