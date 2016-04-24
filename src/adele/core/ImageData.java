/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.core;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;



/**

 @author Ludek
 */
public class ImageData {
    
    public ImageData(String name, int width, int height){
        frames = new ArrayList<>();
        this.name = name;
        this.width = width;
        this.height = height;
    }
    
    private String name;
    private final int width;
    private final int height;
    private final ArrayList<FrameData> frames;
    private FrameData activeFrame;
  
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    
    /* FRAMES */
    
    public void addFrame(){
        //při přidání vybrat frame 0 jestli existuje a přidat všechny sdílené vrstvy
        FrameData newFrameData = new FrameData(this);
        if (frames.size() > 0){
            for (int i = 0; i < frames.get(0).getNumberOfLayers(); i++){
                if(frames.get(0).getLayer(i).isShared()){
                    newFrameData.linkLayer(frames.get(0).getLayer(i));
                }
            }
        }
        activeFrame = newFrameData;
        frames.add(newFrameData);
    }
    
    public FrameData getFrame(){
        return activeFrame;
    }
    
    public void removeFrame(){
        //najít vybrat snímek po mazání
        //vybrat nový aktivní snímek (na základě GUI pro přepínání, jen tam to jde mazat)
        frames.remove(activeFrame);
    }
    
    public void changeActiveFrame(int index){
        activeFrame = frames.get(index);
    }
    
    public int getActiveFrameIndex(){
        return frames.indexOf(activeFrame);
    }
    
    public int getNumberOfFrames(){
        return frames.size();
    }
    
}
