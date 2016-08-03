
package adele;

import adele.popup.MainSceneController;
import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public final class Bridge {

    public Bridge(Stage primaryStage){
        controllers = new HashMap<>(); 
        userProfile = new UserProfile();        

        loadViewController(View.MainSceneP, "popup/MainScene.fxml");
        loadViewController(View.NewFileT, "tab/NewFile.fxml");
        loadViewController(View.ImageEditorT, "tab/ImageEditor.fxml");
        loadViewController(View.LayersD, "dock/Layers.fxml");
        loadViewController(View.ToolsD, "dock/Tools.fxml");
        loadViewController(View.LayerSettingsP, "popup/LayerSettings.fxml");        
        
        ((MainSceneController) getController(View.MainSceneP)).addPopUp(View.MainSceneP, "Adele v0.4", false, true, primaryStage);
        //userProfile.load()
    }

    //MainScene + tabs, docks, popups
    public enum View {
        NewFileT, ImageEditorT,
        LayersD, ToolsD,
        MainSceneP, LayerSettingsP, ColorPickerP
    }    

    private final UserProfile userProfile;
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


}
