package adele;

import adele.utils.Version;
import adele.controller.MainWindowController;
import adele.controller.WindowFrameController;
import adele.service.ControllerManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Adele extends Application {
    
    private static Version adeleVersion;
    private MainWindowController mainWindowController;    
    
    public static void main(String[] args) {
        launch(args);
    }    

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(mainWindowController.getWindowFrameController().getRoot());
        mainWindowController.getWindowFrameController().setStage(primaryStage);
        
        primaryStage.setTitle("Adele");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);        
        primaryStage.setScene(scene);       
        primaryStage.show();
    }
    
    @Override
    public void init() {
        // do not construct Scenes and Stages here
        // TODO load userfile with usersetting
        mainWindowController = (MainWindowController) MainWindowController.load("/fxml/MainWindow.fxml");
        mainWindowController.createWindowFrame(false);
        mainWindowController.getWindowFrameController().setSnapping(true);
    }
    
    @Override
    public void stop() {
        //TODO - save progress
    }
    
    /**
     * Get the version of Adele application. The version is fetched from Maven
     * pom file and it is available only from jar file.
     *
     * @return
     */
    public static Version getAdeleVersion() {
        if (adeleVersion == null) {
            adeleVersion = Version.fromString(Adele.class.getPackage().getImplementationVersion());
        }
        return adeleVersion; // TODO - implement fallback for testing
    }
}
