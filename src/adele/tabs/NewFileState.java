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
public class NewFileState implements IViewState{
    private boolean isActive = false;    
    public String name = "New File";
    public String width = "320";
    public String height = "240";     
    //TODO add bg color    

    @Override
    public boolean isActive(){
        return isActive;
    }

    @Override
    public void setActiveState(boolean state){
        isActive = state;
    }
    
}
