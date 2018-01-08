package br.com.mertins.se.ca.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mertins
 */
public class GenericTest {
    
    public GenericTest() {
    }
    
    /**
     * Test of removeExtensionFile method, of class Generic.
     */
    @Test
    public void testRemoveExtensionFile() {
        assertEquals("teste", Generic.removeExtensionFile("teste"));
        assertEquals("teste", Generic.removeExtensionFile("teste.png"));
    }

   
}
