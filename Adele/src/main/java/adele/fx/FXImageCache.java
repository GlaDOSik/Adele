package adele.fx;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import lombok.Getter;
import lombok.Setter;

public final class FXImageCache {
    
    private static FXImageCache singleton;
    @Getter
    private WritableImage fxImage;
    private adele.image.Image adeleImage;
    
    private FXImageCache() {}
    
    public static FXImageCache getSingletonInstance() {
        if (singleton == null) {
            singleton = new FXImageCache();
        }
        return singleton;
    }
    
    public void setAdeleImage(adele.image.Image adeleImage) {
        this.adeleImage = adeleImage;
        fxImage = new WritableImage(adeleImage.getWidth(), adeleImage.getHeight());
        adeleImage.getFrame().flattenFrame(fxImage);
    }
    
}
