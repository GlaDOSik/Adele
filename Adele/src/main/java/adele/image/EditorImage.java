/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.image;

import javafx.scene.image.WritableImage;
import lombok.Getter;
import lombok.Setter;

/**
 * ImageEditor container for Image which contains UID, JavaFX cache
 * (WritableImage), current frame pointer and current layer pointer.
 */
public class EditorImage {
    
    @Getter
    private String uid;
    
    @Getter
    private Image image;
    
    @Getter
    private final WritableImage fxCache;
    
    @Getter
    @Setter
    private int currentFrame = 0;
    
    @Getter
    @Setter
    private int currentLayer = 0;
    
    public EditorImage(String uid, Image image) {
        this.uid = uid;
        this.image = image;
        fxCache = new WritableImage(image.getWidth(), image.getHeight());
    }

}
