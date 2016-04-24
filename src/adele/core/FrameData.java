/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.core;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**

 @author Ludek
 */
public class FrameData {

    public FrameData(ImageData linkImg){
        layers = new ArrayList<>();
        this.linkImg = linkImg;
        duration = 300;
    }
    private ImageData linkImg;
    private final ArrayList<LayerData> layers;
    private int duration;

    public void addNewLayer(boolean isShared, String name){
        LayerData newLayer = new LayerData(linkImg.getWidth(), linkImg.getHeight(), isShared, name);
        //je sdílená, projít snímky, pokud není tento, svázat sdílenou
        if (isShared){
            for (int i = 0; i < linkImg.getNumberOfFrames(); i++){
                if (linkImg.getFrame() != this){
                    linkImg.getFrame().linkLayer(newLayer);
                }
            }
        }
        //VYBARVIT PRO TESTOVÁNÍ, POTOM SMAZAT
        if (isShared){
            newLayer.getCanvas().getGraphicsContext2D().setFill(Color.CHOCOLATE);
            newLayer.getCanvas().getGraphicsContext2D().fillRect(0, 0, linkImg.getWidth(), linkImg.getHeight());
        }
        else{
            newLayer.getCanvas().getGraphicsContext2D().setFill(Color.AQUA);
            newLayer.getCanvas().getGraphicsContext2D().fillRect(0, 0, linkImg.getWidth(), linkImg.getHeight());
        }
        layers.add(newLayer);
    }

    public void linkLayer(LayerData sharedLayer){
        layers.add(sharedLayer);
        System.out.println("Propojuji sdilenou vrstvu");
    }
    
    public void linkLayer(LayerData sharedLayer, int index){
        layers.add(index, sharedLayer);
        System.out.println("Propojuji sdilenou vrstvu");
    }

    public LayerData getLayer(int index){
        return layers.get(index);
    }

    public int getNumberOfLayers(){
        return layers.size();
    }

    public void deleteLayerTestShared(int index){
        //projít ostatní snímky a pokud to není tento, smazat sdílenou vrstvu
        LayerData deletedLayer = layers.get(index);
        if (deletedLayer.isShared()){
            for (int i = 0; i < linkImg.getNumberOfFrames(); i++){
                if (linkImg.getFrame() != this){
                    linkImg.getFrame().deleteLayer(deletedLayer);
                }
            }
        }
        layers.remove(deletedLayer);
    }

    public void deleteLayer(LayerData layerToDelete){
        layers.remove(layerToDelete);
    }

    public int getDuration(){
        return duration;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

}
