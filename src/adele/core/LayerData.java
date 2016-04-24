/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.core;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 *
 * @author Ludek
 */
public class LayerData {

    public LayerData(int width, int height, boolean isShared, String name) {
        layer = new Canvas(width, height);
        this.name = name;
        this.isShared = isShared;
    }
    private String name;
    private final Canvas layer;
    private boolean isShared;
    //add Blend mode?
    //private int transparency = 100;
    
    public boolean isShared(){
        return isShared;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setVisible(boolean isVisible){
        layer.setVisible(isVisible);
    }
    
    public boolean isVisible(){
        return layer.isVisible();
    }

    public Canvas getCanvas() {
        return layer;
    }
}
