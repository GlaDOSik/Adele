package adele.image.factory;

import adele.image.Frame;
import adele.image.Image;
import adele.image.Layer;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for easy and quick Image creation and initialization.
 */
public class ImageFactory {
    
    private static ImageFactory singleton;
    
    private int width = 0;
    private int height = 0;
    private int numberOfFrames = 0;
    private int numberOfSharedLayers = 0;
    private int numberOfLayers = 0;
    private int frameDelayInMS = 0;
    private String imageName = "Factory Image";
    
    private ImageFactory(){};
    
    public static ImageFactory getFactory() {
        if (singleton == null) {
            singleton = new ImageFactory();
        }
        return singleton;
    }
    
    public ImageFactory size(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }
    
    public ImageFactory numberOfFrames(int number) {
        numberOfFrames = number;
        return this;
    }
    
    public ImageFactory numberOfSharedLayers(int number) {
        numberOfSharedLayers = number;
        return this;
    }
    
    public ImageFactory numberOfLayers(int number) {
        numberOfLayers = number;
        return this;
    }
    
    public ImageFactory frameDelay(int timeInMiliSec) {
        frameDelayInMS = timeInMiliSec;
        return this;
    }
    
    public ImageFactory name(String name) {
        imageName = name;
        return this;
    }
    
    public Image build() {
        Image img = new Image(width, height, imageName);
        List<Layer> sharedLayers = new ArrayList<>();
        
        for (int i = 0; i < numberOfFrames; i++) {
            Frame frame = new Frame(img);            
            if (numberOfSharedLayers != 0 && sharedLayers.isEmpty()) {
                for (int j = 0; j < numberOfSharedLayers; j++) {
                    sharedLayers.add(new Layer("Shared Layer " + j+1, true, frame));
                }
            }            
            frame.setTimeInMilliS(frameDelayInMS);
            List<Layer> layers = frame.getLayers();
            layers.addAll(sharedLayers);
            for (int j = 0; j < numberOfLayers; j++) {
                layers.add(new Layer("Layer " + j+1, false, frame));
            }
            img.getFrames().add(frame);
        }
        
        singleton = null;
        return img;
    }
    
}
