/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.docks;

import adele.Bridge;
import adele.BridgeComponent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author Ludek
 */
public class ToolsController extends BridgeComponent implements Initializable {
    @Override
    public void ready() {
        addToolButton(Tools.Pen);
        ((Button)flowPane.getChildren().get(0)).setId("PenActive");
        addToolButton(Tools.Eraser);
        addToolButton(Tools.Dropper);
        addToolButton(Tools.Brush);
        addToolButton(Tools.Bucket);
        addToolButton(Tools.Dither);
        addToolButton(Tools.Line);
        addToolButton(Tools.PrimaryColor);
        addToolButton(Tools.SecondaryColor);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    @FXML
    private FlowPane flowPane;

    public enum Tools {
        Pen, Dropper, Eraser, Brush, Selection, Bucket, Line, Dither,
        Zoom, PrimaryColor, SecondaryColor
    }

    public void addToolButton(Tools tool) {
        flowPane.getChildren().add(createToolButton(tool));
    }

    public Button createToolButton(Tools tool) {
        Button button = new Button("");
        button.setMinWidth(40);
        button.setMinHeight(40);
        
        button.setId(tool.toString());
        button.setUserData(tool.toString());
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Tools clickedTool = Tools.valueOf((String)((Button)event.getSource()).getUserData());
                String id = (String)((Button)event.getSource()).getUserData();
                
                if (clickedTool == Tools.PrimaryColor) {
                    //open color picker and change color
                    System.out.println("Primary color");
                } else if (clickedTool == Tools.SecondaryColor) {
                    //open color picker and change color
                    System.out.println("Secondary color");
                } else if (getBridgeReference().getImageEditor().getActiveTool() != clickedTool){
                    ((Button)event.getSource()).setId(id + "Active");
                    unselect(getBridgeReference().getImageEditor().getActiveTool());
                    getBridgeReference().getImageEditor().setActiveTool(clickedTool);
                }                
            }
        });
        //add tooltips?
        //button.setTooltip(new Tooltip("test"));
        return button;
    }
    
    private void unselect(Tools tool){
        for (int i = 0; i < flowPane.getChildren().size(); i++) {
            Button button = (Button)flowPane.getChildren().get(i);
            Tools itTool = Tools.valueOf((String)button.getUserData());
            if (tool == itTool){
                button.setId(button.getId().substring(0, button.getId().length()-6));
            }
        }
    }

    public void reloadToolsDock() {
        //nahrát znovu všechny vybrané ikony ze seznamu v user file
    }

}
