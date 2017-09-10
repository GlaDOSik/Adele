package adele.service;

import adele.image.EditorImage;
import adele.image.Image;
import adele.utils.ImageEditorUtils;
import java.util.HashMap;

/**
 * ImageEditor service modifies the Image class. It manages the internal state
 * of the image (shared layers, fx cache, frame and layer pointers) and modifies
 * the pixel data.
 */
public class ImageEditor {

    private static ImageEditor singleton;

    private final HashMap<String, EditorImage> storage;

    private ImageEditor() {
        storage = new HashMap<>();
    }

    /**
     * Stores the provided image in editor. Unique id is generated for the image
     * so two same images can be opened in editor under two different ids.
     *
     * The image is checked if it contains shared layers and if so, shared
     * layers are referenced in each layer. FX image cache is populated and
     * pointers to current frame and layer are set.
     *
     * @param image
     * @return unique id of image in editor
     */
    public String storeImage(Image image) {
        String uid;
        do {
            uid = ImageEditorUtils.generateUID();
        }
        while(storage.containsKey(uid));
        
        EditorImage editorImage = new EditorImage(uid, image);
        ImageEditorUtils.prepareForStorage(editorImage);
        storage.put(uid, editorImage);

        return uid;
    }
    
    public EditorImage getImage(String imageUID) {
        return storage.get(imageUID);
    }

    public static ImageEditor getSingleton() {
        if (singleton == null) {
            singleton = new ImageEditor();
        }
        return singleton;
    }

}
