package adele.image.factory;

import adele.image.Frame;
import adele.image.Image;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ImageFactoryTest {

    private ImageFactory factory;

    @Before
    public void prepareFactory() {
        factory = new ImageFactory();
    }

    @Test
    public void testFactoryBuild() {
        Image image = factory.size(20, 15)
                .name("factory img")
                .frameDelay(33)
                .numberOfFrames(2)
                .numberOfSharedLayers(3)
                .numberOfLayers(5)
                .build();
        Assert.assertEquals("Image name", "factory img", image.getName());
        Assert.assertEquals("Image width", 20, image.getWidth());
        Assert.assertEquals("Image height", 15, image.getHeight());
        Assert.assertEquals("Image frames", 2, image.getFrames().size());
        for (Frame frame : image.getFrames()) {
           Assert.assertEquals("Number of shared layers", 3, frame.getNumberOfSharedLayers()); 
           Assert.assertEquals("Number of all layers", 8, image.getFrames().get(0).getLayers().size());
        }
       
    }

}
