/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.interfaces;

import javafx.scene.control.Tab;

/**

 @author Ludek
 */
public interface IControllerViewState {

    public void addTabState(Tab tab);

    public IViewState getState(Tab tab);

    public void removeTabState(Tab tab);

    public void saveState(Tab sourceTab);

    public void loadState(Tab sourceTab);

    public int getNumberOfStates();
}
