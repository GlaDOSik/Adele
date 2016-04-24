/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.tabs;

import adele.interfaces.IViewState;

/**

 @author Ludek
 */
public class ImageEditorState implements IViewState{
    private boolean isActive = false;
    public int scale = 1;
    public double hValue = 0;
    public double vValue = 0;
    
    @Override
    public boolean isActive(){
        return isActive;
    }

    @Override
    public void setActiveState(boolean state){
        isActive = state;
    }

    
}
