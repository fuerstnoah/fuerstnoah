package com.mycompany.pmt9;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestRegex{

    @Test
    public void testIsHexNum(){
        assertTrue(HexNum.isHexNum("FA"));
        assertTrue(HexNum.isHexNum("-A10"));
        assertFalse(HexNum.isHexNum("G20"));
    }
}
