package adele.tabs;

import adele.interfaces.IControllerViewState;
import adele.interfaces.IViewState;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import adele.Bridge;
import adele.BridgeComponent;
import adele.MainSceneController;

public class NewFileController extends BridgeComponent implements IControllerViewState {

    @Override
    public void ready(){
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
    private void createNewImage(){
        if (textFieldName.getText().isEmpty()){
            textFieldName.setText("Untitled");
        }
        Tab tab = (Tab) ((MainSceneController) getBridgeReference().getController(Bridge.View.MainSceneP)).getSelectetTab();
        tab.setText(textFieldName.getText());
        tab.setUserData(Bridge.View.ImageEditorT);
        
        removeTabState(tab);
        ((ImageEditorController)getBridgeReference().getController(Bridge.View.ImageEditorT)).addTabState(tab);
        ((IControllerViewState)getBridgeReference().getController(Bridge.View.ImageEditorT)).getState(tab).setActiveState(true);
        try{
            int width = Integer.parseInt(textFieldWidth.getText());
            int height = Integer.parseInt(textFieldHeight.getText());
            getBridgeReference().getImageEditor().createNewImage(textFieldName.getText(), width, height, colorPicker.getValue(), tab);
            tab.setContent(null);
            tab.setContent(getBridgeReference().getController(Bridge.View.ImageEditorT).getViewRoot());
            ((ImageEditorController)getBridgeReference().getController(Bridge.View.ImageEditorT)).loadState(tab);
        }
        catch (Exception e){
            System.out.println("Špatně zadaná hodnota");
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void closeTab(){
        ((MainSceneController) getBridgeReference().getController(Bridge.View.MainSceneP)).closeSelectedTab();
    }

    @Override
    public void addTabState(Tab tab){
        tabStates.put(tab, new NewFileState());
    }

    @Override
    public IViewState getState(Tab tab){
        return tabStates.get(tab);
    }

    @Override
    public void removeTabState(Tab tab){
        tabStates.remove(tab);
    }

    @Override
    public void saveState(Tab sourceTab){
        NewFileState newFileState = (NewFileState) tabStates.get(sourceTab);
        newFileState.name = textFieldName.getText();
        newFileState.width = textFieldWidth.getText();
        newFileState.height = textFieldHeight.getText();
    }

    @Override
    public void loadState(Tab sourceTab){
        NewFileState newFileState = (NewFileState) tabStates.get(sourceTab);
        textFieldName.setText(newFileState.name);
        textFieldWidth.setText(newFileState.width);
        textFieldHeight.setText(newFileState.height);
    }

    @Override
    public int getNumberOfStates(){
        return tabStates.size();
    }

}
