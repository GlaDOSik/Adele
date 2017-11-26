package adele.controller;

import adele.AdeleViewSource;
import adele.image.Image;
import adele.image.factory.ImageFactory;
import adele.service.ImageEditor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import utils.controller.AbstractController;

/**
 * FXML Controller class
 *
 * @author ludek
 */
public class NewImageController extends AbstractController implements Initializable {

    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 3000;

    @FXML
    private TextField txtFldName;
    private String name;

    @FXML
    private TextField txtFldWidth;
    private int widht;

    @FXML
    private TextField txtFldHeight;
    private int height;

    @FXML
    private TextField txtFldFrames;
    private int frames;

    @Setter
    @Getter
    private Tab parentTab;

    @FXML
    private void createImage(ActionEvent event) {
        System.out.println("Create image");
        if (!validateInput()) {
            System.out.println("Input not valid");
            return;
        }
        ImageEditorController newImageEditor = (ImageEditorController) AbstractController.load(AdeleViewSource.ImageEditor);
        Image newImage = ImageFactory.getFactory().size(widht, height).name(name).numberOfSharedLayers(1).numberOfFrames(1).build();
        String imageUID = ImageEditor.getSingleton().storeImage(newImage);
        newImageEditor.setImage(imageUID);
        parentTab.setText(name);
        parentTab.setContent(newImageEditor.getRoot());
    }

    @FXML
    private void cancelImage(ActionEvent event) {
        parentTab.setContent(null);
        parentTab.getTabPane().getTabs().remove(parentTab);
    }

    private boolean validateInput() {
        boolean isInputValid = true;

        name = txtFldName.getText();
        if (Strings.isEmpty(name)) {
            //add to error message
            isInputValid = false;
        }

        String width = txtFldWidth.getText();
        try {
            this.widht = Integer.parseInt(width);
            if (this.widht < MIN_SIZE || this.widht > MAX_SIZE) {
                throw new IllegalArgumentException("Invalid image width");
            }
        } catch (NumberFormatException ex) {
            //add to error message
            isInputValid = false;
        } catch (IllegalArgumentException ex) {
            //add to error message
            isInputValid = false;
        }

        String height = txtFldHeight.getText();
        try {
            this.height = Integer.parseInt(height);
            if (this.height < MIN_SIZE || this.height > MAX_SIZE) {
                throw new IllegalArgumentException("Invalid image height");
            }
        } catch (NumberFormatException ex) {
            //add to error message
            isInputValid = false;
        } catch (IllegalArgumentException ex) {
            //add to error message
            isInputValid = false;
        }

        String frames = txtFldFrames.getText();
        try {
            this.frames = Integer.parseInt(frames);
            if (this.frames < 0) {
                throw new IllegalArgumentException("Invalid number of frames");
            }
        } catch (NumberFormatException ex) {
            //add to error message
            isInputValid = false;
        } catch (IllegalArgumentException ex) {
            //add to error message
            isInputValid = false;
        }

        return isInputValid;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public boolean isAllowedToUseWindowFrame() {
        return false;
    }

}
