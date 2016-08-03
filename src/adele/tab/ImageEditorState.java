package adele.tab;

import adele.interfaces.IViewState;
import adele.core.AImage;
import javafx.scene.paint.Color;


public class ImageEditorState implements IViewState{
    
    public double hValue = 0;
    public double vValue = 0;
    private AImage image;
    public int scale = 1;   
    
    public void createImage(String name, int width, int height, Color fillBackground){
        image = new AImage(width, height, name, fillBackground);
    }
    
    public AImage getImage(){
        return image;
    }
    
}
