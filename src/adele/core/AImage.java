package adele.core;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class AImage {

    private int width;
    private int height;
    private String name;
    private int selectedFrame = 0;
    private final List<AFrame> animationFrames;
    private AImageCache cache;

    public AImage(int width, int height, String name, Color fillBackgroundLayer) {
        this.width = width;
        this.height = height;
        this.name = name;

        animationFrames = new ArrayList<>();
        cache = new AImageCache(this);
        addNewFrame();
        getSelectedFrame().makeLayer("Background", true, AImage.colorToAformat(fillBackgroundLayer));
    }

    public void addNewFrame() {
        AFrame newFrame = new AFrame(this);
        if (animationFrames.size() != 0) {
            for (int i = 0; i < animationFrames.get(0).getNumberOfLayers(); i++) {
                if (animationFrames.get(0).getLayer(i).isShared()) {
                    newFrame.linkLayer(animationFrames.get(0).getLayer(i));
                }
            }
        }
        animationFrames.add(newFrame);
        if (selectedFrame < 0) {
            selectedFrame = 0;
        }
        else {
            selectedFrame = animationFrames.size() - 1;
        }
        buildCache();
    }

    public void addNewFrame(int index) {
        AFrame newFrame = new AFrame(this);
        if (animationFrames.size() != 0) {
            for (int i = 0; i < animationFrames.get(0).getNumberOfLayers(); i++) {
                if (animationFrames.get(0).getLayer(i).isShared()) {
                    newFrame.linkLayer(animationFrames.get(0).getLayer(i));
                }
            }
        }
        animationFrames.add(index, newFrame);
        if (selectedFrame < 0) {
            selectedFrame = 0;
        }
        else {
            selectedFrame = index;
        }
        buildCache();
    }

    public void deleteFrame(int index) {
        if (index >= animationFrames.size()) {
            index = animationFrames.size() - 1;
            //tady je správný index
            AFrame frameToDelete = animationFrames.get(index);
            //pokud se jedná o poslední rámeček
            if (animationFrames.size() == 1) {
                frameToDelete.removeAllLayers(true);
            }
            else {
                frameToDelete.removeAllLayers(false);
            }
            frameToDelete.prepareToGC();
            animationFrames.remove(frameToDelete);

            //pokud jsme mazali poslední rámeček, musíme vybrat předchozí
            if (selectedFrame == animationFrames.size()) {
                --selectedFrame;
            }
        }
    }

    public void selectFrame(int index) {
        if (index < animationFrames.size()) {
            selectedFrame = index;
        }
        buildCache();
    }

    public AFrame getSelectedFrame() {
        return animationFrames.get(selectedFrame);
    }

    public int getSelectedFrameIndex() {
        return selectedFrame;
    }

    public void buildCache() {
        cache.build();
    }

    public Image getFXImage() {
        return cache.getScaledStack();
    }

    public AImageCache getCache() {
        return cache;
    }

    public void linkSharedLayer(ALayer sharedLayer) {
        for (int i = 0; i < animationFrames.size(); i++) {
            if (i != selectedFrame) {
                animationFrames.get(i).linkLayer(sharedLayer);
            }
        }
    }

    public void unlinkSharedLayer(ALayer sharedLayer) {
        for (int i = 0; i < animationFrames.size(); i++) {
            if (i != selectedFrame) {
                animationFrames.get(i).unlinkLayer(sharedLayer);
            }
        }
    }

    /**
     * Transforms 4 color component RGBA to internal Aformat (32bit ARGB). Do
     * NOT use input values greater than 255 or smaller than 0. No clipping is
     * provided. Conversion is lossless.
     *
     * @param R red color component (0 - 255)
     * @param G green color component (0 - 255)
     * @param B blue color component (0 - 255)
     * @param A alpha color component (0 - 255)
     * @return
     */
    static public int colorToAformat(int R, int G, int B, int A) {
        return ((A << 24) + (R << 16) + (G << 8) + (B));
    }

    /**
     * Transforms 4 color component RGBA to internal Aformat (32bit ARGB). Do
     * NOT use input values greater than 1.0 or smaller than 0.0. No clipping is
     * provided. Conversion is lossy.
     *
     * @param R red color component (0.0f - 1.0f)
     * @param G green color component (0.0f - 1.0f)
     * @param B blue color component (0.0f - 1.0f)
     * @param A alpha color component (0.0f - 1.0f)
     * @return
     */
    static public int colorToAformat(float R, float G, float B, float A) {
        int a, r, g, b;
        a = (int) (A * 255.0f);
        r = (int) (R * 255.0f);
        g = (int) (G * 255.0f);
        b = (int) (B * 255.0f);
        return ((a << 24) + (r << 16) + (g << 8) + (b));
    }

    /**
     * Transforms 4 color component RGBA to internal Aformat (32bit ARGB). Do
     * NOT use input values greater than 1.0 or smaller than 0.0. No clipping is
     * provided. Conversion is lossy.
     *
     * @param R red color component (0.0 - 1.0)
     * @param G green color component (0.0 - 1.0)
     * @param B blue color component (0.0 - 1.0)
     * @param A alpha color component (0.0 - 1.0)
     * @return
     */
    static public int colorToAformat(double R, double G, double B, double A) {
        int a, r, g, b;
        a = (int) (A * 255.0);
        r = (int) (R * 255.0);
        g = (int) (G * 255.0);
        b = (int) (B * 255.0);
        return ((a << 24) + (r << 16) + (g << 8) + (b));
    }

    static public int colorToAformat(Color color) {
        int a, r, g, b;
        a = (int) (color.getOpacity() * 255.0);
        r = (int) (color.getRed() * 255.0);
        g = (int) (color.getGreen() * 255.0);
        b = (int) (color.getBlue() * 255.0);
        return ((a << 24) + (r << 16) + (g << 8) + (b));
    }

    static public int getRfromAformat(int Apixel) {
        return (Apixel >> 16) & 0xff;
    }

    static public int getGfromAformat(int Apixel) {
        return (Apixel >> 8) & 0xff;
    }

    static public int getBfromAformat(int Apixel) {
        return Apixel & 0xff;
    }

    static public int getAfromAformat(int Apixel) {
        return (Apixel >> 24) & 0xff;
    }

    static public float getAfromAformatF(int Apixel) {
        return ((Apixel >> 24) & 0xff) / 255.f;
    }

    static public void getRGBfromAformat(int[] outputPixels3, int Apixel) {
        outputPixels3[0] = (Apixel >> 16) & 0xff;
        outputPixels3[1] = (Apixel >> 8) & 0xff;
        outputPixels3[2] = Apixel & 0xff;
    }

    static public void getARGBfromAformat(int[] outputPixels4, int Apixel) {
        outputPixels4[0] = (Apixel >> 24) & 0xff;
        outputPixels4[1] = (Apixel >> 16) & 0xff;
        outputPixels4[2] = (Apixel >> 8) & 0xff;
        outputPixels4[3] = Apixel & 0xff;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

}
