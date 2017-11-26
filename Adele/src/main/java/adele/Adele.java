package adele;

import utils.Version;
import adele.controller.MainWindowController;
import javafx.application.Application;
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
        /*System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.text", "t2k");*/
        Scene scene = new Scene(mainWindowController.getWindowFrameController().getRoot());
        primaryStage.setTitle("Adele");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);        
        primaryStage.setScene(scene);       
        primaryStage.show();
        mainWindowController.getWindowFrameController().setStage(primaryStage);
    }
    
    @Override
    public void init() {
        // do not construct Scenes and Stages here
        // TODO load userfile with usersetting
        mainWindowController = (MainWindowController) MainWindowController.load(AdeleViewSource.MainWindow);
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
