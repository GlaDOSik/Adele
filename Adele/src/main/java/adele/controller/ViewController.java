package adele.controller;

import adele.Adele;
import java.io.IOException;
import java.nio.file.InvalidPathException;
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
 *
 * @author ludek
 */
public abstract class ViewController {

    @Setter
    @Getter
    private Parent root;

    @Getter
    private WindowFrameController windowFrameController;

    public void setWindowFrame(WindowFrameController windowFrame) {
        if (!isAllowedToUseWindowFrame()) {
            return;
        }
        windowFrameController = windowFrame;
    }

    /**
     * Create a custom window frame and insert the root view of this controller
     * to it. Functionality is restricted by abstract method
     * isAllowedToUseWindowFrame.
     *
     * The custom window frame has to be inserted in the Stage. A new Stage with
     * Scene from window frame can be created by argument withStage. Stage has
     * to be shown manually. Keep in mind Stages and Scenes can't be created in
     * JavaFX's Application.init().
     *
     * @param withStage create a stage and scene for window frame with view
     */
    public void createWindowFrame(boolean withStage) {
        if (!isAllowedToUseWindowFrame()) {
            return;
        }
        windowFrameController = (WindowFrameController) WindowFrameController.load(AdeleViewControllerSource.WindowFrame);
        windowFrameController.setContent(root);

        if (withStage) {
            Stage stage = new Stage(StageStyle.UNDECORATED);
            Scene scene = new Scene(windowFrameController.getRoot());
            stage.setScene(scene);
            windowFrameController.setStage(stage);
        }
    }

    /**
     * Can the subclass of ViewController use WindowFrame?
     *
     * @return
     */
    public abstract boolean isAllowedToUseWindowFrame();

    public static ViewController load(ViewControllerSource viewControllerSource) {
        FXMLLoader loader = getLoader(viewControllerSource);
        ViewController controller = loader.getController();
        if (controller == null) {
            throw new IllegalArgumentException("Provided controller doesn't exist");
        }
        controller.root = loader.getRoot();
        return controller;
    }

    public static Parent loadView(ViewControllerSource viewControllerSource) {
        try {
            return FXMLLoader.load(ViewController.class.getResource(viewControllerSource.getPath()));
        } catch (IOException ex) {
            throw new IllegalArgumentException("Provided path is not valid");
        }
    }

    private static FXMLLoader getLoader(ViewControllerSource viewControllerSource) {
        if (viewControllerSource == null || viewControllerSource.getPath().isEmpty()) {
            throw new IllegalArgumentException("Path is null or empty");
        }
        FXMLLoader loader = new FXMLLoader(Adele.class.getResource(viewControllerSource.getPath()));
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Provided path is not valid");
        }
        return loader;
    }

}
