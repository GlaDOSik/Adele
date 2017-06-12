package adele.image.factory;

import adele.image.ColorUtils;

public class NoiseTemplate implements ImageTemplate{
    
    private int width = 320;
    private int height = 240;
    private int numberOfLayers = 1;
    private final String name = "Random noise";
    private int[] pixels;
    
    public NoiseTemplate() {
        pixels = new int[width * height];
        fillRandomPixels();
    }
    
    public NoiseTemplate(int width, int height) {
        pixels = new int[width * height];
        this.width = width;
        this.height = height;
        fillRandomPixels();
    }
    
    public NoiseTemplate(int width, int height, int numberOfLayers) {
        pixels = new int[width * height];
        this.width = width;
        this.height = height;
        this.numberOfLayers = numberOfLayers;
        fillRandomPixels();
    }

    @Override
    public String getTemplateName() {
        return name + " template";
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getNumberOfFrames() {
        return 1;
    }

    @Override
    public int getNumberOfSharedLayers() {
        return numberOfLayers;
    }

    @Override
    public int getNumberOfLayers() {
        return 0;
    }

    @Override
    public int[] getPixels() {
        return pixels;
    }

    @Override
    public void switchTestPattern() {
        fillRandomPixels();
    }

    @Override
    public String getImageName() {
        return name + " image";
    }

    @Override
    public int getFrameDelayInMS() {
        return 0;
    }
    
    private void fillRandomPixels() {
        for (int i = 0; i < width * height; i++) {
            pixels[i] = ColorUtils.packARGB(Math.random() * 255.0, Math.random() * 255.0, Math.random() * 255.0, Math.random());
        }
    }
    
}
