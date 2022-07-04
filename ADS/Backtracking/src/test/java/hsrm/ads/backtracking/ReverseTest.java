/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package hsrm.ads.backtracking;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Noah
 */
public class ReverseTest{

    @Test
    public void testReverse0(){
        String original = "Hallo", reversed = "ollaH";
        assertEquals("Not reversed", Reverse.reverse(original, 0), reversed);
    }

    @Test
    public void testReverse1(){
        String original = "Hallo", reversed = "Hallo";
        assertNotEquals("Not reversed", Reverse.reverse(original, 0), reversed);
    }

    @Test
    public void testReverse2(){
        String original = "leohortetrohoel";
        assertEquals(Reverse.reverse(original, 0), original);
    }

}
