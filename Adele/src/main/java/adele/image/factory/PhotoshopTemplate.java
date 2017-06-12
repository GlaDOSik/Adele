package adele.image.factory;

import adele.image.Image;
import adele.image.Layer;
import java.util.List;

/**
 * Template for testing purposes which holds expected results from Photoshop.
 * Uses expanded color set through 2 layers with different blending and alpha
 * mixing
 */
public class PhotoshopTemplate implements ImageTemplate {

    private boolean isTop = true;
    private final int[] layerTop    = {0xffffffff, 0xff000000, 0x80ffffff, 0xff00ff00, 0xff00ff00, 0xfffcff0e, 0xffff9000, 0xff8136ce, 0xff412a56, 0xffed3073};
    private final int[] layerBottom = {0xff000000, 0xffffffff, 0xff000000, 0xffff00ff, 0xffff0000, 0xff3da1cf, 0xff50527d, 0xff68de7c, 0xff536245, 0x805082bf};

    private final float[] layersGlobalAlpha = {0.5f, 0.24f}; //alpha for top layer per test
    public static final int[] EXPECTED_COLOR_TEST_1 = {0xff808080, 0xff7f7f7f, 0xff404040, 0xff7f807f, 0xff7f8000, 0xff9dd06e, 0xffa8713e, 0xff758aa5, 0xff4a464e, 0xffb93dc4};
    public static final int[] EXPECTED_COLOR_TEST_2 = {0xff3d3d3d, 0xffc2c2c2, 0xff1f1f1f, 0xffc23dc2, 0xffc23d00, 0xff6bb7a1, 0xff7a615f, 0xff6eb690, 0xff4f5549, 0xffb03fd2};

    @Override
    public String getTemplateName() {
        return "Photoshop color mix test template";
    }

    @Override
    public int getWidth() {
        return 10;
    }

    @Override
    public int getHeight() {
        return 1;
    }

    @Override
    public int getNumberOfFrames() {
        return 1;
    }

    @Override
    public int getNumberOfSharedLayers() {
        return 0;
    }

    @Override
    public int getNumberOfLayers() {
        return 2;
    }

    @Override
    public int[] getPixels() {
        return isTop ? layerTop : layerBottom;
    }

    @Override
    public void switchTestPattern() {
        isTop = !isTop;
    }

    @Override
    public String getImageName() {
        return "Photoshop test image";
    }

    @Override
    public int getFrameDelayInMS() {
        return 0;
    }

    public Image getTestImage1() {
        Image image = ImageFactory.buildFromTemplate(this);
        List<Layer> layers = image.getFrames().get(0).getLayers();
        layers.get(0).setAlpha(layersGlobalAlpha[0]);
        return image;
    }
    
    public Image getTestImage2() {
        Image image = ImageFactory.buildFromTemplate(this);
        List<Layer> layers = image.getFrames().get(0).getLayers();
        layers.get(0).setAlpha(layersGlobalAlpha[1]);
        return image;
    }

}
