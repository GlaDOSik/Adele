package adele.image;

import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClassUtilsTest {
    
    @Test
    public void testPacking() {
        assertEquals("Pack Color", 0xff0000ff, ColorUtils.packARGB(Color.BLUE));
        assertEquals("Pack double", 0x804d00ff, ColorUtils.packARGB(77.0, 0.0, 255.0, 0.5));
        assertEquals("Pack float", 0x9933a6d9, ColorUtils.packARGB(51.0f, 166.0f, 217.0f, 0.6f));
        assertEquals("Pack int", 0xff21569c, ColorUtils.packARGB(33, 86, 156, 255));
    }
    
    @Test
    public void testUnpacking() {
        int pixelInt = 0x80c6a316;
        int[] argb = {128, 198, 163, 22};
        assertArrayEquals("Unpack ARGB to int", argb, ColorUtils.unpackARGB(pixelInt));
        assertEquals("Unpack R to int", argb[1], ColorUtils.unpackR(pixelInt));
        assertEquals("Unpack G to int", argb[2], ColorUtils.unpackG(pixelInt));
        assertEquals("Unpack B to int", argb[3], ColorUtils.unpackB(pixelInt));
        int[] rgb = {198, 163, 22};
        assertArrayEquals("Unpack RGB to int", rgb, ColorUtils.unpackRGB(pixelInt));
        assertEquals("Unpack A to float", 0.5019608f, ColorUtils.unpackAtoFloat(0x80ffffff), 0.0001f);
        float[] argbF = {0.5019608f, 128.0f, 0.0f, 255.0f};
        float[] output = new float[4];
        assertArrayEquals("Unpack ARGB to float", argbF, ColorUtils.unpackARGBtoFloat(output, 0x808000ff), 0.0001f);
    }
    
    @Test
    public void testPackUnpack() {
        int[] expectedRGBA = {1, 2, 3, 4};
        int pixel = ColorUtils.packARGB(expectedRGBA[1], expectedRGBA[2], expectedRGBA[3], expectedRGBA[0]);
        int[] unpackedPixel = ColorUtils.unpackARGB(pixel);
        assertArrayEquals(expectedRGBA, unpackedPixel);        
    }
    
}
