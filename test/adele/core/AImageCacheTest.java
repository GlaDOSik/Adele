package adele.core;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AImageCacheTest {

    private AImage image;
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    public AImageCacheTest() {
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
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBuildCache() {
        image.getSelectedFrame().makeLayer("1", false, 0);
        image.getSelectedFrame().getLayer(0).cleanLayer(0xffffffff); //alpha 7f - 127
        image.buildCache();
        assertEquals("cache is not scaled properly", image.getCache().getFlatStack().getWidth(), (int)image.getCache().getScaledStack().getWidth() / image.getCache().getScale());
        assertEquals("scaled cache is empty at x=0, y=0", 0xffffffff, image.getCache().getScaledStack().getPixelReader().getArgb(0, 0));
        assertEquals("scaled cache is empty at last position", 0xffffffff, image.getCache().getScaledStack().getPixelReader().getArgb(WIDTH - 1, HEIGHT - 1));
    }

}
