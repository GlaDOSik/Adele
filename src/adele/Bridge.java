
package adele;

import adele.core.ImageEditor;
import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public final class Bridge {

    public Bridge(Stage primaryStage){
        controllers = new HashMap<>(); 
        imageEditor = new ImageEditor();
        userProfile = new UserProfile();
        

        loadViewController(View.MainSceneP, "MainScene.fxml");
        loadViewController(View.NewFileT, "tabs/NewFile.fxml");
        loadViewController(View.ImageEditorT, "tabs/ImageEditor.fxml");
        loadViewController(View.LayersD, "docks/Layers.fxml");
        loadViewController(View.ToolsD, "docks/Tools.fxml");
        loadViewController(View.LayerSettingsP, "popups/LayerSettings.fxml");        
        

        ((MainSceneController) getController(View.MainSceneP)).addPopUp(View.MainSceneP, "Adele v0.4", false, true, primaryStage);
        //userProfile.load()
    }

    //MainScene + tabs, docks, popups
    public enum View {
        NewFileT, ImageEditorT,
        LayersD, ToolsD,
        MainSceneP, LayerSettingsP, ColorPickerP
    }

    //nepatří sem
    public enum DockPlacement {
        Left, Right
    }

    private final UserProfile userProfile;
    private final ImageEditor imageEditor;
    private final HashMap<View, BridgeComponent> controllers;

    private void loadViewController(View view, String pathToView){
        if (!controllers.containsKey(view)){
            FXMLLoader loader = new FXMLLoader();
            try{
                Parent root = loader.load(getClass().getResource(pathToView).openStream());
                BridgeComponent bridgeComponent = loader.getController();
                bridgeComponent.setBridgeReference(this);
                bridgeComponent.setViewRoot(root);
                controllers.put(view, bridgeComponent);
                bridgeComponent.ready();
            }
            catch (IOException ex){
                System.out.println("Došlo k chybě při načítání fxml souboru!");
                System.out.println(ex.getMessage());
            }
        }
    }

    public BridgeComponent getController(View view){
        return controllers.get(view);
    }

    public int getNumberOfControllers(){
        return controllers.size();
    }

    public ImageEditor getImageEditor(){
        return imageEditor;
    }

}
