package adele.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainWindowController extends ViewController implements Initializable {

    @FXML
    private TabPane tabs;
    
    
    @FXML
    private void newImagePrompt(ActionEvent event) {
        NewImageController newImagePrompt = (NewImageController) ViewController.load(AdeleViewControllerSource.NewImage);
        Tab newImageTab = new Tab("New image"); //TODO - change name
        newImagePrompt.setParentTab(newImageTab);
        newImageTab.setContent(newImagePrompt.getRoot());
        tabs.getTabs().add(newImageTab);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
    }    

    @Override
    public boolean isAllowedToUseWindowFrame() {
        return true;
    }

    
}
