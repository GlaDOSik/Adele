package adele.core;

import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class AFrameTest {
    
    private AFrame frame;
    private AImage image;
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    
    public AFrameTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        image = new AImage(WIDTH, HEIGHT, "test image", Color.AQUA);
        frame = new AFrame(image);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAddLayer_3args() {
        
         frame.makeLayer("1", false, 0);
         frame.makeLayer("2", false, 0);
         frame.makeLayer("3", false, 0);
         frame.makeLayer("4", false, 2, 0);
         frame.makeLayer("5", false, 50, 0);
         assertEquals("1", frame.getLayer(0).getName());
         assertEquals("2", frame.getLayer(1).getName());
         assertEquals("wrong add order", "4", frame.getLayer(2).getName());
         assertEquals("3", frame.getLayer(3).getName());
         assertEquals("wrong out of bounds add", "5", frame.getLayer(4).getName());
    }
    
    @Test
    public void testRemoveLayer(){
        frame.makeLayer("1", false, 0);
        frame.makeLayer("2", false, 0);
        frame.makeLayer("3", false, 0);
        frame.removeLayer(1);
        assertEquals("wrong remove order", "3", frame.getLayer(1).getName());
        frame.removeLayer(1);
        frame.removeLayer(50);
        assertEquals("wrong out of bounds remove", 0, frame.getNumberOfLayers());
    }
    
    @Test
    public void testFlattenFrame(){
        frame.makeLayer("1", false, 0);
        frame.makeLayer("2", false, 0);
        frame.makeLayer("3", false, 0);
        frame.makeLayer("4", false, 0);
        frame.getLayer(1).cleanLayer(0xff000000); //alpha ff - 255
        frame.getLayer(0).cleanLayer(0x7fffffff); //alpha 7f - 127     
        int[] colorMix = new int[WIDTH * HEIGHT];
        frame.flattenFrame(colorMix);
        assertEquals("inaccurate 100 % + 50 % mix", 0xff7f7f7f, colorMix[0]);
        frame.getLayer(3).cleanLayer(0xff000000); //alpha ff - 255
        frame.getLayer(2).cleanLayer(0x40ffffff); //alpha 40 - 64
        frame.getLayer(1).cleanLayer(0x80ffffff); //alpha 80 - 128
        frame.getLayer(0).cleanLayer(0x40ffffff); //alpha 40 - 64
        frame.flattenFrame(colorMix);
        assertEquals("inaccurate complex mix", 0xffb7b7b7, colorMix[0]);
        frame.getLayer(0).cleanLayer(0xff161616); //alpha 40 - 64
        frame.flattenFrame(colorMix);
        assertEquals("last layer is not opaque", 0xff161616, colorMix[0]);
    }

    
}
