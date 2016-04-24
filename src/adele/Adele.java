/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**

 @author Luděk Novotný
 */
public class Adele extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Bridge bridge = new Bridge(stage);    }

    /**
     @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
