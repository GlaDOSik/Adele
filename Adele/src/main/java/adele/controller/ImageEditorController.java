/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.controller;

import adele.fx.AdeleImageView;
import adele.image.EditorImage;
import adele.service.ImageEditor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;


public class ImageEditorController extends ViewController implements Initializable {
    
    private String imageUID = "";
    
    private AdeleImageView adeleImageView;
    
    @FXML
    private StackPane stack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        adeleImageView = new AdeleImageView();
        stack.getChildren().add(adeleImageView);
    }

    public void setImage(String imageUID) {
        this.imageUID = imageUID;
        EditorImage image = ImageEditor.getSingleton().getImage(imageUID);
        if (image != null) {
            adeleImageView.setImage(image.getFxCache());
        }
        else {
            // TODO - show error message
        }
    }

    @Override
    public boolean isAllowedToUseWindowFrame() {
        return true;
    }
    
}
