package adele.image.factory;

import adele.image.Image;
import adele.image.Layer;
import java.util.List;

/**
 * Template for testing purposes which holds expected results from Photoshop.
 * Uses simple color set through 5 layers with different blending and alpha
 * mixing.
 */
public class PhotoshopComplexTemplate implements ImageTemplate {

    private int cursor = 0;
    private final int[] layersColors = {0xfff7f900, 0xff2541e8, 0xffe89325, 0xff00ff72, 0xff00b4ff};
    private final float[] layersGlobalAlpha = {0.36f, 0.2f, 0.33f, 0.5f, 1.0f}; //alpha per layer for all tests

    public static final int EXPECTED_COLOR_TEST_1 = 0xff85c663;

    @Override
    public String getTemplateName() {
        return "Photoshop complex color mix test template";
    }

    @Override
    public int getWidth() {
        return 1;
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
        return 5;
    }

    @Override
    public int[] getPixels() {
        int[] pixels = new int[1];
        pixels[0] = layersColors[cursor];
        return pixels;
    }

    @Override
    public void switchTestPattern() {
        cursor = cursor > 4 ? 0 : cursor++;
    }

    @Override
    public String getImageName() {
        return "Photoshop test image";
    }

    @Override
    public int getFrameDelayInMS() {
        return 0;
    }

    /**
     * NormalBlendMode and MixAlphaBlend.
     *
     * @return configured test image
     */
    public Image getTestImage1() {
        Image image = ImageFactory.buildFromTemplate(this);
        List<Layer> layers = image.getFrames().get(0).getLayers();
        int i = 0;
        for (Layer layer : layers) {
            layer.setAlpha(layersGlobalAlpha[i]);
            i++;
        }
        return image;
    }

}
