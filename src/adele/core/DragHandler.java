/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.core;

import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**

 @author Ludek
 */
public class DragHandler implements EventHandler<MouseEvent> {

    public DragHandler(ImageEditor editorReference, StackPane stackPane, ScrollPane scrollPane){
        this.editorReference = editorReference;
        this.stackPane = stackPane;
        this.scrollPane = scrollPane;
    }

    private ImageEditor editorReference;
    private StackPane stackPane;
    private ScrollPane scrollPane;

    @Override
    public void handle(MouseEvent event){
        if (event.getButton() == MouseButton.MIDDLE){
            double deltaX = (PressedHandler.getClickPosX() - event.getScreenX()) / scrollPane.getMinWidth();
            double deltaY = (PressedHandler.getClickPosY() - event.getScreenY()) / scrollPane.getMinHeight();
            scrollPane.setHvalue(scrollPane.getHvalue() + deltaX);
            scrollPane.setVvalue(scrollPane.getVvalue() + deltaY);
            PressedHandler.setClickPositionX(event.getScreenX());
            PressedHandler.setClickPositionY(event.getScreenY());
        }
        event.consume();
    }
}
