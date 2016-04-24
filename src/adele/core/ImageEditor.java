/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.core;

import adele.docks.ToolsController;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;

/**

 @author Ludek
 */
public class ImageEditor {
    public ImageEditor(){
        openImages = new HashMap<>();
    }    
    private final HashMap<Tab,ImageData> openImages;
    private ImageData activeImage;    
    private ToolsController.Tools activeTool = ToolsController.Tools.Pen;
   
    
    
    /* GENERAL FUNCTIONS FOR SIMPLE DATA MANIPULATION */    
    public void createNewImage(String name, int width, int height, Color bgColor, Tab linkedTab){
        ImageData tempImage = new ImageData(name, width, height);
        tempImage.addFrame();
        tempImage.getFrame().addNewLayer(true, "Background");
        tempImage.getFrame().getLayer(0).getCanvas().getGraphicsContext2D().setFill(bgColor);
        tempImage.getFrame().getLayer(0).getCanvas().getGraphicsContext2D().fillRect(0, 0, tempImage.getWidth(), tempImage.getHeight());
        openImages.put(linkedTab, tempImage);
        activeImage = tempImage;
    }
    
    public void removeImage(Tab linkedTab){
        openImages.remove(linkedTab);
    }
    
    public void removeAll(){
        openImages.clear();
    }
    
    public ImageData getImage(Tab linkedTab){
        return openImages.get(linkedTab);
    }
    
    public ImageData getActiveImage(){
        return activeImage;
    }
    
    public void changeActiveImage(Tab linkedTab){
        activeImage = openImages.get(linkedTab);
    }
    
    public int getNumberOfImages(){
        return openImages.size();
    }
    
    //This should be called when we select a layer, it will prepare it for the drawing operations
    public void prepareActiveLayer(int index){
        //activeCanvas = activeImage.getFrame().getLayer(index).getCanvas();
        
        //možná WritablePixelFormat
    }
    

    public ToolsController.Tools getActiveTool() {
        return activeTool;
    }

    public void setActiveTool(ToolsController.Tools activeTool) {
        this.activeTool = activeTool;
    }
    
    
}
