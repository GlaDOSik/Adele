/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.core;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**

 @author Ludek
 */
public class PressedHandler implements EventHandler<MouseEvent> {

    public PressedHandler(ImageEditor editorReference, StackPane stackPane, ScrollPane scrollPane){
        this.editorReference = editorReference;
        this.stackPane = stackPane;
        this.scrollPane = scrollPane;

    }

    private ImageEditor editorReference;
    private StackPane stackPane;
    private ScrollPane scrollPane;
    private Canvas activeCanvas;
    
    private static double clickPositionX;
    private static double clickPositionY;

    @Override
    public void handle(MouseEvent event){
        clickPositionX = (int) event.getScreenX();
        clickPositionY = (int) event.getScreenY();
        
        if (event.getButton() == MouseButton.PRIMARY){
            
        }
        event.consume();
    }

    public static void setClickPositionX(double clickPositionX){
        PressedHandler.clickPositionX = clickPositionX;
    }

    public static void setClickPositionY(double clickPositionY){
        PressedHandler.clickPositionY = clickPositionY;
    }

    public static double getClickPosX(){
        return clickPositionX;
    }

    public static double getClickPosY(){
        return clickPositionY;
    }
   

}
