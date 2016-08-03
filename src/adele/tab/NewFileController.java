package adele.tab;

import adele.interfaces.IControllerViewState;
import adele.interfaces.IViewState;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import adele.Bridge;
import adele.BridgeComponent;
import adele.popup.MainSceneController;

public class NewFileController extends BridgeComponent implements IControllerViewState {

    @Override
    public void ready() {
        tabStates = new HashMap<>();
    }

    private HashMap<Tab, IViewState> tabStates;

    //  FXML bindings
    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldWidth;

    @FXML
    private TextField textFieldHeight;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private void createNewImage() {
        int width;
        int height;
        try {
            width = Integer.parseInt(textFieldWidth.getText());
            height = Integer.parseInt(textFieldHeight.getText());
        }
        catch (Exception e) {
            System.out.println("Špatně zadaná hodnota");
            System.out.println(e.getMessage());
            return;
        }
        String name = textFieldName.getText();
        if (textFieldName.getText().isEmpty()) {
            name = "Untitled";
        }
        Tab tab = (Tab) ((MainSceneController) getBridgeReference().getController(Bridge.View.MainSceneP)).getSelectetTab();
        tab.setText(textFieldName.getText());
        
        //we have to switch content of the tab, remove it's state and create new in ImageEditorController
        tab.setUserData(Bridge.View.ImageEditorT);
        removeTabState(tab);
        ImageEditorState state = (ImageEditorState)((ImageEditorController) getBridgeReference().getController(Bridge.View.ImageEditorT)).addTabState(tab);
        state.createImage(name, width, height, colorPicker.getValue());

        tab.setContent(null);
        tab.setContent(getBridgeReference().getController(Bridge.View.ImageEditorT).getViewRoot());
        ((ImageEditorController) getBridgeReference().getController(Bridge.View.ImageEditorT)).loadState(tab);
    }

    @FXML
    private void closeTab() {
        ((MainSceneController) getBridgeReference().getController(Bridge.View.MainSceneP)).closeSelectedTab();
    }

    @Override
    public IViewState addTabState(Tab tab) {
        NewFileState state = new NewFileState();
        tabStates.put(tab, state);
        return state;
    }

    @Override
    public IViewState getState(Tab tab) {
        return tabStates.get(tab);
    }

    @Override
    public void removeTabState(Tab tab) {
        tabStates.remove(tab);
    }

    @Override
    public void saveState(Tab sourceTab) {
        NewFileState newFileState = (NewFileState) tabStates.get(sourceTab);
        newFileState.name = textFieldName.getText();
        newFileState.width = textFieldWidth.getText();
        newFileState.height = textFieldHeight.getText();
    }

    @Override
    public void loadState(Tab sourceTab) {
        NewFileState newFileState = (NewFileState) tabStates.get(sourceTab);
        textFieldName.setText(newFileState.name);
        textFieldWidth.setText(newFileState.width);
        textFieldHeight.setText(newFileState.height);
    }

    @Override
    public int getNumberOfStates() {
        return tabStates.size();
    }

}
