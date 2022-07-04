/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4Suite.java to edit this template
 */
package com.mycompany.lambda;

import java.util.*;
import org.junit.*;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author noah
 */
public class TestLambda{

    private final List<String> _lstSentEmails = new ArrayList<>();
    private FlexibleTreeNode<Person> tree;

    @Before
    public void setUp() throws Exception{
        tree = new FlexibleTreeNode<>(new Person("001", "Name", HSPersonellType.Dean, "abc@hs-rm.de"));
        tree.add(new FlexibleTreeNode<>(new Person("002", "Name", HSPersonellType.Dean, "abc@hs-rm.de")));
        tree.add(new FlexibleTreeNode<>(new Person("003", "Name", HSPersonellType.Student, "abc@hs-rm.de")));
    }

    @After
    public void tearDown() throws Exception{
        _lstSentEmails.clear();
    }

    @Test
    public void testForEach(){
        List<Person> persons = tree.getAllUserObjects();
        tree.forEach((node, person) -> {
            assertTrue(persons.contains(person));
            persons.remove(person);
        });
        assertTrue(persons.isEmpty());
    }

    @Test
    public void testBiConsumer(){
        List<Person> students = new ArrayList<>();
        tree.forEach((node, person) -> {
            if(person.getPersonellType().equals(HSPersonellType.Student)){
                students.add(person);
                sendMail(person.getEmail());
            }
        });
        assertTrue(students.size() == _lstSentEmails.size());
    }

    @Test
    public void testStream(){
        List<Person> persons = new ArrayList<>();
        List<Person> students = new ArrayList<>();
        tree.forEach((node, person) -> {
            persons.add(person);
            if(person.getPersonellType().equals(HSPersonellType.Student)){
                students.add(person);
            }
        });
        persons.stream().filter((person) -> person.getPersonellType().equals(HSPersonellType.Student)).forEach((student) -> sendMail(student.getEmail()));
        assertTrue(students.size() == _lstSentEmails.size());
    }

    public void sendMail(String strEmailAdress){
        _lstSentEmails.add(strEmailAdress);
    }

}
