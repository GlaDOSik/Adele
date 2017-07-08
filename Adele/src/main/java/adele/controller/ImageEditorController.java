/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.controller;

import adele.fx.AdeleImageView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;


public class ImageEditorController extends ViewController implements Initializable {
    
    @FXML
    private StackPane stack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AdeleImageView adeleImageView = new AdeleImageView();
        stack.getChildren().add(adeleImageView);
    }    

    @Override
    public boolean isAllowedToUseWindowFrame() {
        return true;
    }
    
}
