
package adele.popup;

import adele.Bridge;
import adele.dock.LayersController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class LayerSettingsController extends Popup {
     
    @Override
    public void ready(){}
    
    private Pane referencePane;
    
    @FXML
    private TextField fieldName;    
    @FXML
    private CheckBox checkboxShared;
    
    
    @FXML
    private void closeLayerSettings(){
        referencePane = null;
        super.closePopup();
    }
    
    @FXML
    private void mousePressed(MouseEvent event){
        moveStart(event);
        event.consume();
    }
    
    @FXML
    private void mouseDragged(MouseEvent event){
        moveDragged(event, false);
        event.consume();
    }
    
    @FXML
    private void saveChanges(){
        ((Label) ((HBox) referencePane.getChildren().get(0)).getChildren().get(2)).setText(fieldName.getText());
        //((CheckBox)referencePane.getChildren().get(0)).setSelected(checkboxShared.isSelected());
        //změnit taky v docku        
        ((LayersController)getBridgeReference().getController(Bridge.View.LayersD)).saveLayerSettings(referencePane);
        closePopup();
        
    }
    
    public void loadPopup(){
        fieldName.setText(((Label) ((HBox) referencePane.getChildren().get(0)).getChildren().get(2)).getText());
        checkboxShared.setSelected(((CheckBox) ((HBox) referencePane.getChildren().get(0)).getChildren().get(0)).isSelected());
    }
    
    public void setPaneReference(Pane referencePane){
        this.referencePane = referencePane;
    }
     
}
