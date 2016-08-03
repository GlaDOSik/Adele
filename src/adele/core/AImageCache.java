package adele.core;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;

public class AImageCache {

    private final AImage referenceImage;
    private ALayer flatStack;
    private WritableImage scaledStack;
    private boolean isDirty = true;
    private int scale = 1;

    public AImageCache(AImage referenceImage) {
        this.referenceImage = referenceImage;
        flatStack = new ALayer("flat stack", false, referenceImage, 0);
        scaledStack = new WritableImage(referenceImage.getWidth() * scale, referenceImage.getHeight() * scale);
    }

    public void resizeCache() {
        //změní velikost flatStack a scaledStack
        flatStack = new ALayer("flat stack", false, referenceImage, 0);
        scaledStack = new WritableImage(referenceImage.getWidth() * scale, referenceImage.getHeight() * scale);
        build();
    }

    public void changeScale() {
        //změní velikost scaledStack
        scaledStack = new WritableImage(referenceImage.getWidth() * scale, referenceImage.getHeight() * scale);
        //přidat změnu měřítka
    }

    public void build() {
        //přidat scale
        //pokud neexistuje žádný rámeček (index z posledního odebrání je -1), pak se musí vytvořit alpha
        if (referenceImage.getSelectedFrameIndex() >= 0) {
            referenceImage.getSelectedFrame().flattenFrame(flatStack.getRawPixelArray());
            //NEFUNGUJE a asi ani nebude
            scaledStack.getPixelWriter().setPixels(0, 0, flatStack.getWidth() * scale, flatStack.getHeight() * scale, PixelFormat.getIntArgbInstance(), flatStack.getRawPixelArray(), 0, flatStack.getWidth());
        }
        else {
            scaledStack = null;
        }

    }

    public void update() {
        //updatovat pouze změněné pixely
    }

    public Image getScaledStack() {
        return scaledStack;
    }

    public ALayer getFlatStack() {
        return flatStack;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getScale() {
        return scale;
    }
}
