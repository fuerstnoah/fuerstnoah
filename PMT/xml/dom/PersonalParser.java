/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pmt8;

import java.io.File;
import java.util.*;
import java.util.logging.*;
import org.w3c.dom.*;
import turban.utils.UserMsgException;

/**
 *
 * @author Noah
 */
public class PersonalParser{

    private final List<Person> _lstPersonal = new ArrayList<>(5);

    public List<Person> getLstPersonal(){
        return _lstPersonal;
    }

    public void parsePersonal(){
        try{
            Document xml = DomParser.parseXMLtoDOM(new File("HSBib.xml").getAbsolutePath());
            traverseNodes(xml);

        }catch(UserMsgException ex){
            Logger.getLogger(PersonalParser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void traverseNodes(Node _node){
        if(_node instanceof Document){
            traverseNodes_doChildren(_node);
        }else if(_node instanceof Element e){
            traverseNodes_doAttributes(e);
            traverseNodes_doChildren(_node);
        }
    }

    private void traverseNodes_doChildren(Node _node){
        NodeList nl = _node.getChildNodes();
        for(int i = 0; i < nl.getLength(); i++){
            traverseNodes(nl.item(i));
        }
    }

    private void traverseNodes_doAttributes(Element _e){
        NamedNodeMap nodeMap = _e.getAttributes();
        if(!_e.hasAttributes() || !_e.getNodeName().equals("Person")){
            return;
        }
        Person person = new Person(nodeMap.item(1).getNodeValue(), _e.getTextContent(), HSPersonellType.valueOf(nodeMap
               .item(2).getNodeValue()), nodeMap.item(0).getNodeValue());
        _lstPersonal.add(person);
    }

}
