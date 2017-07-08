package adele.utils;

import adele.image.EditorImage;
import adele.image.Frame;
import adele.image.Image;
import adele.image.Layer;
import java.util.UUID;

public class ImageEditorUtils {

    private ImageEditorUtils() {
    }

    /**
     * Reference orphan shared layers in all frames. Set frame and layer
     * pointers
     *
     * @param editorImage
     */
    public static void prepareForStorage(EditorImage editorImage) {
        // reference orphan shared layers
        for (Frame frame : editorImage.getImage().getFrames()) {
            for (Layer layer : frame.getLayers()) {
                addSharedLayer(layer, editorImage.getImage());
            }
        }

        // set pointers
        int framePointer = editorImage.getImage().getFrames().size() > 0 ? 0 : -1;
        editorImage.setCurrentFrame(framePointer);
        if (framePointer != -1) {
            Frame currentFrame = editorImage.getImage().getFrames().get(framePointer);
            int layerPointer = currentFrame.getLayers().size() > 0 ? 0 : -1;
            editorImage.setCurrentLayer(layerPointer);
        } else {
            editorImage.setCurrentLayer(-1);
        }

    }

    /**
     * Reference shared layer in all frames inside the image
     *
     * @param sharedLayer shared layer to be referenced
     * @param image image with frames where the layer will be referenced
     */
    public static void addSharedLayer(Layer sharedLayer, Image image) {
        if (sharedLayer.isShared()) {
            for (Frame frame : image.getFrames()) {
                if (!frame.getLayers().contains(sharedLayer)) {
                    frame.getLayers().add(sharedLayer);
                }
            }
        }
    }

    /**
     * Remove the shared layer from all frames inside the image
     *
     * @param sharedLayer shared layer to be removed
     * @param image image with frames from where the layer will be removed
     */
    public static void removeSharedLayer(Layer sharedLayer, Image image) {
        if (sharedLayer.isShared()) {
            for (Frame frame : image.getFrames()) {
                frame.getLayers().remove(sharedLayer);
            }
        }
    }

    public static String generateUID() {
        String[] uidFull = UUID.randomUUID().toString().split("-");
        String uidShort = uidFull[0].substring(0, 1)
                + uidFull[1].substring(0, 1)
                + uidFull[2].substring(0, 1)
                + uidFull[3].substring(0, 1);
        return uidShort;
    }

}
