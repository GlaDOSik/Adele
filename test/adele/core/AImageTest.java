/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.core;

import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ludek
 */
public class AImageTest {
    
    private AImage image;
    private final static int WIDTH = 100;
    private final static int HEIGHT = 100;
    private final static String NAME = "test image";
    
    public AImageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        image = new AImage(WIDTH, HEIGHT, NAME, Color.AQUA);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testColorToAformat_4args_int() {
        int pixel = AImage.colorToAformat(255, 0, 255, 0);
        assertEquals(0x00ff00ff, pixel);
    }
    
    @Test
    public void testColorToAformat_4args_float() {
        int pixel = AImage.colorToAformat(1.0f, 0.0f, 1.0f, 0.0f);
        assertEquals(0x00ff00ff, pixel);
    }
    
    @Test
    public void testColorToAformat_4args_double() {
        int pixel = AImage.colorToAformat(1.0, 0.0, 1.0, 0.0);
        assertEquals(0x00ff00ff, pixel);
    }
    
    @Test
    public void testColorToAformat_1arg() {
        int pixel = AImage.colorToAformat(Color.YELLOWGREEN);
        assertEquals(0xff9acd32, pixel);
    }
    
    @Test
    public void testGetAfromAformat(){
        int pixel = 0x7f000000;
        assertEquals(127, AImage.getAfromAformat(pixel));
    }
    
    @Test
    public void testGetRfromAformat(){
        int pixel = 0x007f0000;
        assertEquals(127, AImage.getRfromAformat(pixel));
    }
    
    @Test
    public void testGetGfromAformat(){
        int pixel = 0x00007f00;
        assertEquals(127, AImage.getGfromAformat(pixel));
    }
    
    @Test
    public void testGetBfromAformat(){
        int pixel = 0x0000007f;
        assertEquals(127, AImage.getBfromAformat(pixel));
    }
    
    @Test
    public void testGetAfromAformatF(){
        int pixel = 0x7f000000;
        assertTrue(Math.abs(AImage.getAfromAformatF(pixel) - 0.5f) < 0.01f);
    }
    
    @Test
    public void testGetRGBfromAformat(){
        int[] pixelsRGB = new int[]{127, 255, 32};
        int[] outputPixels = new int[3];
        int pixel = 0x007fff20;
        AImage.getRGBfromAformat(outputPixels, pixel);
        assertArrayEquals(pixelsRGB, outputPixels);
    }
    
    @Test
    public void testGetARGBfromAformat(){
        int[] pixelsRGB = new int[]{255, 127, 255, 32};
        int[] outputPixels = new int[4];
        int pixel = 0xff7fff20;
        AImage.getARGBfromAformat(outputPixels, pixel);
        assertArrayEquals(pixelsRGB, outputPixels);
    }
    

}
