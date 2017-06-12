package adele.controller;

import adele.Adele;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;

/**
 * General class for controllers.
 * @author ludek
 */
public abstract class Controller {

    @Getter
    protected String name;

    @Setter
    @Getter
    private Parent root;

    @Getter
    private WindowFrameController windowFrameController;

    public void setWindowFrame(WindowFrameController windowFrame) {
        if (!allowUseWindowFrame()) {
            return;
        }
        windowFrameController = windowFrame;
    }

    /**
     * If the controller with the GUI can be inside custom window, use this
     * function to easily create custom window frame, assign it to this
     * controller and set the content of frame with root of this controller.
     *
     * Some controllers can't be inside window frame (like the window frame
     * itself) so this function in some cases does nothing.
     *
     * @param withStage if true, also create the stage, create a scene out of
     * window frame root and assign it to the scene
     */
    public void createWindowFrame(boolean withStage) {
        if (!allowUseWindowFrame()) {
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/utility/WindowFrame.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            return;
        }
        windowFrameController = fxmlLoader.getController();
        windowFrameController.setRoot(fxmlLoader.getRoot());
        windowFrameController.setContent(root);

        if (withStage) {
            Stage stage = new Stage(StageStyle.UNDECORATED);
            Scene scene = new Scene(windowFrameController.getRoot());
            stage.setScene(scene);
            windowFrameController.setStage(stage);
        }
    }

    /**
     * Can this instance of Controller use window frame?
     *
     * @return
     */
    private boolean allowUseWindowFrame() {
        if (this instanceof WindowFrameController) {
            return false;
        }
        return true;
    }
    
    public static Controller load(String pathToFXML) {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource(pathToFXML));
        try {
            loader.load();
        } catch (IOException ex) {
            // TODO - exception handling
        }
        Controller controller = loader.getController();
        controller.root = loader.getRoot();
        return controller;
    }

}
