package adele.image.factory;

import adele.image.Frame;
import adele.image.Image;
import adele.image.Layer;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;

/**
 * Creates images based on templates (for example, basic template creates an image with one frame and one layer)
 * Other templates creates testing images for tests (not exposed in editor) or templates exposed in editor
 * @author ludek
 */
public class ImageFactory {
    
    private int width = 0;
    private int height = 0;
    private int numberOfFrames = 0;
    private int numberOfSharedLayers = 0;
    private int numberOfLayers = 0;
    private int frameDelayInMS = 0;
    private String imageName = "Factory Image";
    
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
    
    public static Image buildFromTemplate(ImageTemplate template) {
        Image img = new Image(template.getWidth(), template.getHeight(), template.getImageName());
        List<Layer> sharedLayers = new ArrayList<>();
        for (int i = 0; i < template.getNumberOfSharedLayers(); i++) {
            Layer sharedLayer = new Layer("Shared Layer " + i+1, true, img);
            if (template.getPixels() != null && template.getWidth() * template.getHeight() == template.getPixels().length) {
                int[] pixels = sharedLayer.getPixels();
                for (int j = 0; j < template.getPixels().length; j++) {
                    pixels[j] = template.getPixels()[j];
                }
                template.switchTestPattern();
            }
        }
        for (int i = 0; i < template.getNumberOfFrames(); i++) {
            Frame frame = new Frame(img);
            frame.setTimeInMilliS(template.getFrameDelayInMS());
            List<Layer> layers = frame.getLayers();
            layers.addAll(sharedLayers);
            for (int j = 0; j < template.getNumberOfLayers(); j++) {
                Layer layer = new Layer("Layer " + j+1, false, img);
                if (template.getPixels() != null && template.getWidth() * template.getHeight() == template.getPixels().length) {                    
                    int[] pixels = layer.getPixels();
                    for (int k = 0; k < template.getPixels().length; k++) {
                        pixels[k] = template.getPixels()[k];
                    }
                    template.switchTestPattern();
                }
                frame.getLayers().add(layer);
            }
            img.getFrames().add(frame);
        }        
        return img;
    }
    
    public Image build() {
        Image img = new Image(width, height, imageName);
        List<Layer> sharedLayers = new ArrayList<>();
        for (int i = 0; i < numberOfSharedLayers; i++) {
            sharedLayers.add(new Layer("Shared Layer " + i+1, true, img));
        }
        for (int i = 0; i < numberOfFrames; i++) {
            Frame frame = new Frame(img);
            frame.setTimeInMilliS(frameDelayInMS);
            List<Layer> layers = frame.getLayers();
            layers.addAll(sharedLayers);
            for (int j = 0; j < numberOfLayers; j++) {
                layers.add(new Layer("Layer " + j+1, false, img));
            }
            img.getFrames().add(frame);
        }        
        return img;
    }
    
}
