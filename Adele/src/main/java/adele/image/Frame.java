package adele.image;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import lombok.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Frame {

    @Getter
    private final List<Layer> layers;
    @Setter
    @Getter
    private int timeInMilliS = 0;
    private final Image image;

    public Frame(Image image) {
        this.image = image;
        layers = new ArrayList<>();
    }

    /**
     * Flatten a whole frame to provided WritableImage
     *
     * @param inputPixelArray
     */
    public void flattenFrame(WritableImage image) {
        if ((int) image.getWidth() != this.image.getWidth() || (int) image.getHeight() != this.image.getHeight()) {
            return;
        }
        PixelWriter writer = image.getPixelWriter();
        Layer[] ls = layers.toArray(new Layer[0]);
        int currPixInt;
        float[] prevPix = new float[4];
        float[] currPix = new float[4];
        for (int i = 0; i < image.getWidth() * image.getHeight(); i++) {
            //TODO barvu alfy staticky zpøístupnit z Userfile
            prevPix[0] = 1.0f; //a
            prevPix[1] = 255.0f; //r
            prevPix[2] = 0.0f; //g
            prevPix[3] = 255.0f; //b            
            for (int j = ls.length - 1; j >= 0; j--) {
                if (ls[j].isVisible()) {
                    currPixInt = ls[j].getPixel(i);
                    ColorUtils.unpackARGBtoFloat(currPix, currPixInt);

                    if (j != ls.length - 1) {
                        currPix = ls[j].getBlendMode().blend(currPix, prevPix);
                    }
                    currPix[0] *= ls[j].getAlpha();
                    currPix = ls[j].getAlphaBlend().blend(currPix, prevPix);

                    float[] tempCurrPix = currPix;
                    currPix = prevPix;
                    prevPix = tempCurrPix;
                }
            }
            int width = (int) (i % image.getWidth());
            int height = (int) (i / image.getWidth());
            writer.setArgb(width, height, ColorUtils.packARGB(prevPix[1], prevPix[2], prevPix[3], 1.0f));
        }
    }

    //pøetížená ? funkce zpracuje a vrátí pouze zmìnìné pixely (ty je tøeba detekovat i pro historii zmìn)
    public void flattenFrame(int[] inputPixelArray, int[] markedPixelsIndices) {
        throw new NotImplementedException();
    }

    public int getNumberOfSharedLayers() {
        int count = 0;
        for (Layer layer : layers) {
            if (layer.isShared()) {
                count++;
            }
        }
        return count;
    }

}
