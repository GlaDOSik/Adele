package adele.image;

import adele.image.alphablend.AlphaBlend;
import adele.image.alphablend.MixAlphaBlend;
import adele.image.blendmode.BlendMode;
import adele.image.blendmode.NormalBlendMode;
import lombok.*;

public class Layer {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private boolean isVisible = true;

    @Getter
    @Setter
    private boolean isShared = false;

    @Getter
    @Setter
    private BlendMode blendMode;

    @Getter
    @Setter
    private AlphaBlend alphaBlend;

    @Getter
    @Setter
    private float alpha = 1.0f;

    @Getter
    private final int[] pixels;

    /**
     * Shared layers have reference to the first frame they were added to. They
     * are in fact inside every frame so having reference to all frames would be
     * pointless.
     */
    private final Frame parentFrame;

    public Layer(String name, boolean isShared, Frame parentFrame) {
        this.parentFrame = parentFrame;
        this.name = name;
        this.isShared = isShared;
        blendMode = new NormalBlendMode();
        alphaBlend = new MixAlphaBlend();
        pixels = new int[parentFrame.getParentImage().getWidth() * parentFrame.getParentImage().getHeight()];
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    /**
     * Write a pixel information at specified position. X and Y values are
     * clamped to the range [0, width/height - 1]. This is a safe but slower
     * write operation.
     *
     * @param x position on axis X (indexed from 0 to width-1)
     * @param y position on axis Y (indexed from 0 to height-1)
     * @param colorAformat value of the pixel in Adele bit format
     */
    public void writePixel(int x, int y, int colorAformat) {
        //TODO ? - test the performance and add width/height as local variable if necessary
        Image pi = parentFrame.getParentImage();
        pixels[Math.max(0, Math.min(pi.getHeight() - 1, y)) * pi.getWidth() + Math.max(0, Math.min(pi.getWidth() - 1, x))] = colorAformat;
    }

    /**
     * Write a pixel information at specified index in 1D array of pixels. This
     * is unsafe but faster write operation. Can throw
     * IndexOutOfBoundsException.
     *
     * @param index index of pixel in 1D array
     * @param colorAformat value of the pixel in Adele bit format
     */
    public void writePixel(int index, int colorAformat) {
        pixels[index] = colorAformat;
    }

    /**
     * Get a pixel in Adele color format. X and Y values are clamped to the
     * range [0, width/height - 1]. This is a safe but slower read operation.
     *
     * @param x position on axis X (indexed from 0 to width-1)
     * @param y position on axis Y (indexed from 0 to height-1)
     * @return int color in Adele format
     */
    public int getPixel(int x, int y) {
        Image pi = parentFrame.getParentImage();
        return pixels[Math.max(0, Math.min(pi.getHeight() - 1, y)) * pi.getWidth() + Math.max(0, Math.min(pi.getWidth() - 1, x))];
    }

    /**
     * Get a pixel in Adele color format. This is unsafe but faster read
     * operation. Can throw IndexOutOfBoundsException.
     *
     * @param index index of pixel in 1D array
     * @return int color in Adele format
     */
    public int getPixel(int index) {
        return pixels[index];
    }

    /**
     * Fill the whole layer with specified color. Old values are rewritten.
     *
     * @param colorAformat value of the pixel in Adele format
     */
    public void cleanLayer(int colorAformat) {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = colorAformat;
        }
    }
    
}
