/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.core;

import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;

/**

 @author Ludek
 */
public class ScrollHandler implements EventHandler<ScrollEvent> {

    public ScrollHandler(ImageEditor editorReference, StackPane stackPane, ScrollPane scrollPane){
        this.editorReference = editorReference;
        this.stackPane = stackPane;
        this.scrollPane = scrollPane;
    }
    private ImageEditor editorReference;
    private StackPane stackPane;
    private ScrollPane scrollPane;

    @Override
    public void handle(ScrollEvent event){
        if (event.getDeltaY() < 0){
            if ((int) stackPane.getScaleX() < 8){
                stackPane.setScaleX(stackPane.getScaleX() + 1);
                stackPane.setScaleY(stackPane.getScaleY() + 1);

                scrollPane.setHvalue(event.getX() / scrollPane.getMinWidth());
                scrollPane.setVvalue(event.getY() / scrollPane.getMinHeight());
            }
        }
        else if (stackPane.getScaleX() >= 2){
            stackPane.setScaleX(stackPane.getScaleX() - 1);
            stackPane.setScaleY(stackPane.getScaleY() - 1);

            scrollPane.setHvalue(event.getX() / scrollPane.getMinWidth());
            scrollPane.setVvalue(event.getY() / scrollPane.getMinHeight());
        }
        event.consume();
    }

}
