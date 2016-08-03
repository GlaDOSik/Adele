package adele.core;

import javafx.geometry.Rectangle2D;

public class ALayer {

    private AImage imageReference;
    private String name;
    private boolean visibility = true;
    private int[] pixels;
    private int width;
    private int height;
    private boolean isShared = false;

    public ALayer(String name, boolean isShared, AImage imageReference, int AColorFormat) {
        this.imageReference = imageReference;
        width = imageReference.getWidth();
        height = imageReference.getHeight();
        pixels = new int[width * height];
        this.name = name;
        this.isShared = isShared;
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = AColorFormat;
        }
    }

    /**
     * Writes a pixel information at specified position. X and Y values are
     * clamped to the range [0, width/height - 1]
     *
     * @param x - position on axis X (indexed from 0 to width-1)
     * @param y - position on axis Y (indexed from 0 to height-1)
     * @param colorAformat - value of the pixel in Adele bit format
     */
    public void writePixel(int x, int y, int colorAformat) {
        pixels[Math.max(0, Math.min(height - 1, y)) * width + Math.max(0, Math.min(width - 1, x))] = colorAformat;
    }

    /**
     * Writes a pixel information at specified index.
     *
     * @param index - index of pixel in 1D array
     * @param colorAformat - value of the pixel in Adele bit format
     */
    public void writePixel(int index, int colorAformat) {
        pixels[index] = colorAformat;
    }

    /**
     * Gets a pixel in Adele color format. X and Y values are clamped to the
     * range [0, width/height - 1]
     *
     * @param x - position on axis X (indexed from 0 to width-1)
     * @param y - position on axis Y (indexed from 0 to height-1)
     * @return int - color in Adele format
     */
    public int getPixel(int x, int y) {
        return pixels[Math.max(0, Math.min(height - 1, y)) * width + Math.max(0, Math.min(width - 1, x))];
    }

    /**
     * Gets a pixel in Adele color format.
     *
     * @param index - index of pixel in 1D array
     * @return int - color in Adele format
     */
    public int getPixel(int index) {
        return pixels[index];
    }

    public int[] getRawPixelArray() {
        return pixels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVisible(boolean isVisible) {
        visibility = isVisible;
        imageReference.buildCache();
    }

    public boolean isVisible() {
        return visibility;
    }

    public void setShared(boolean isShared) {
        if (isShared == false) {
            imageReference.unlinkSharedLayer(this);
        }
        else {
            imageReference.linkSharedLayer(this);
        }
        this.isShared = isShared;
    }

    public boolean isShared() {
        return isShared;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Fills the whole layer with specified color. Old values are rewritten.
     *
     * @param colorAformat - value of the pixel in Adele format
     */
    public void cleanLayer(int colorAformat) {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = colorAformat;
        }
        imageReference.buildCache();
    }

    //zvětší a vrátí vrstvu - používá clipping obdélník
    public void scaleLayer(int targetWidth, int targetHeight, Rectangle2D clippingRect) {

    }

    public void scaleLayer(int targetWidth, int targetHeight) {

    }

    public void resize() {

    }
    //metody pracující nad jednou vrstvou (draw, fill, atd.)

    public void prepareToGC() {
        imageReference = null;
    }
}
