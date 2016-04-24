/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.core;

import adele.Bridge;
import adele.docks.ToolsController;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**

 @author Ludek
 */
public class MoveHandler implements EventHandler<MouseEvent> {

    public MoveHandler(Canvas inputDrawOverlay, ImageEditor editorReference){
        this.inputDrawOverlay = inputDrawOverlay;
        this.editorReference = editorReference;
    }

    

    private Canvas inputDrawOverlay;
    private ImageEditor editorReference;

    @Override
    public void handle(MouseEvent event){
        if (editorReference.getActiveTool() == ToolsController.Tools.Pen){
            inputDrawOverlay.getGraphicsContext2D().getPixelWriter().setColor((int) event.getX(), (int) event.getY(), Color.BLACK);

        }
    }
    
    
}
