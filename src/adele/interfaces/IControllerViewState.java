package adele.interfaces;

import javafx.scene.control.Tab;

public interface IControllerViewState {

    public IViewState addTabState(Tab tab);

    public IViewState getState(Tab tab);

    public void removeTabState(Tab tab);

    public void saveState(Tab sourceTab);

    public void loadState(Tab sourceTab);

    public int getNumberOfStates();
}
