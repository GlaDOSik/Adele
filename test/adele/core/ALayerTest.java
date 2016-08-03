package adele.core;

import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ALayerTest {

    private ALayer aLayer;
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private static final String NAME = "layer name";
    private boolean shared = true;

    public ALayerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        AImage image = new AImage(WIDTH, HEIGHT, NAME, Color.BLACK);
        aLayer = new ALayer("test", false, image, 0);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of writePixel method, of class ALayer.
     */
    @Test
    public void testWritePixel_3args() {
        int max = 0xffffffff;
        aLayer.writePixel(0, 0, 0);
        aLayer.writePixel(WIDTH, HEIGHT, max);
        assertEquals("min test, getPixel 2args", 0, aLayer.getPixel(0, 0));
        assertEquals("max test, getPixel 2args", max, aLayer.getPixel(WIDTH, HEIGHT));
        aLayer.writePixel(-1, -1, 0);
        aLayer.writePixel(WIDTH + 10, HEIGHT + 10, max);
        assertEquals("out of bounds min test, getPixel 2args", 0, aLayer.getPixel(0, 0));
        assertEquals("out of bounds max test, getPixel 2args", max, aLayer.getPixel(WIDTH, HEIGHT));
    }

    @Test
    public void testWritePixel_2args() {
        int max = 0xffffffff;
        aLayer.writePixel(0, 0);
        aLayer.writePixel(WIDTH * HEIGHT - 1, max);
        assertEquals("min test, getPixel 1arg", 0, aLayer.getPixel(0));
        assertEquals("max test, getPixel 1arg", max, aLayer.getPixel(WIDTH * HEIGHT - 1));
        try {
            aLayer.writePixel(-2, 0);
        } catch (Exception e) {
            assertTrue("min array out of bounds test", e instanceof ArrayIndexOutOfBoundsException);
        }
        try {
            aLayer.writePixel(WIDTH * HEIGHT, max);
        } catch (Exception e) {
            assertTrue("max array out of bounds test", e instanceof ArrayIndexOutOfBoundsException);
        }
    }

    @Test
    public void testGetPixel_2args_arrayOutOfBounds() {
        aLayer.writePixel(0, 20569);
        aLayer.writePixel(WIDTH * HEIGHT - 1, 20569);
        assertEquals("min out of bounds test", 20569, aLayer.getPixel(-1, -1));
        assertEquals("min out of bounds test", 20569, aLayer.getPixel(WIDTH+1, HEIGHT+1));
    }
    
    @Test
    public void testFillLayer(){
        int max = 0xffffffff;
        int[] expectedFill = new int[WIDTH * HEIGHT];
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            expectedFill[i] = max;
        }
        aLayer.cleanLayer(max);
        assertArrayEquals("fill test failed", expectedFill, aLayer.getRawPixelArray());
    }

}
