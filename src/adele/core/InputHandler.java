package adele.core;

import adele.dock.LayersController;
import adele.dock.ToolsController;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;

public class InputHandler {

    private ScrollPane scrollPane;
    private StackPane stackPane;
    int clickPositionX, clickPositionY;
    int xOnLayer, yOnLayer;
    private AImage image;

    public InputHandler(ScrollPane scrollPane, StackPane stackPane) {
        this.scrollPane = scrollPane;
        this.stackPane = stackPane;
    }

    /**
     * Process the drag event (pan function, draw line, selection, etc.)
     */
    public EventHandler<MouseEvent> getDragHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.MIDDLE) {
                    double deltaX = (clickPositionX - event.getScreenX()) / scrollPane.getMinWidth();
                    double deltaY = (clickPositionY - event.getScreenY()) / scrollPane.getMinHeight();
                    scrollPane.setHvalue(scrollPane.getHvalue() + deltaX);
                    scrollPane.setVvalue(scrollPane.getVvalue() + deltaY);
                    clickPositionX = (int) event.getScreenX();
                    clickPositionY = (int) event.getScreenY();
                }
                event.consume();
            }
        };
    }

    /**
     * Process pressed event (save position, draw 1 pixel, fill, etc.)
     */
    public EventHandler<MouseEvent> getPressedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.MIDDLE) {
                    clickPositionX = (int) event.getScreenX();
                    clickPositionY = (int) event.getScreenY();
                }
                else if (event.getButton() == MouseButton.PRIMARY) {
                    //eventX - ((stackPane width - image width)/2) (without scale
                    xOnLayer = (int) (event.getX() - ((((StackPane) event.getSource()).getWidth() - image.getWidth()) / 2));
                    yOnLayer = (int) (event.getY() - ((((StackPane) event.getSource()).getHeight() - image.getHeight()) / 2));
                    
                    if (ToolsController.getActiveTool() == ToolsController.PaintTool.Pen){
                        image.getSelectedFrame().getLayer(LayersController.getSelectedLayerIdx()).writePixel(xOnLayer, yOnLayer, 0);
                        //build only changed pixels !!!
                        image.buildCache();
                    }
                }
                event.consume();
            }
        };
    }

    /**
     * Process move event above the image (rendering on utility layer)
     */
    public EventHandler<MouseEvent> getMoveHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                event.consume();
            }
        };
    }

    /**
     * Process scroll event (zoom function, brush resize)
     */
    public EventHandler<ScrollEvent> getScrollHandler() {
        return new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() < 0) {
                    if ((int) stackPane.getScaleX() < 8) {
                        stackPane.setScaleX(stackPane.getScaleX() + 1);
                        stackPane.setScaleY(stackPane.getScaleY() + 1);
                        scrollPane.setHvalue(event.getX() / scrollPane.getMinWidth());
                        scrollPane.setVvalue(event.getY() / scrollPane.getMinHeight());
                    }
                }
                else if (stackPane.getScaleX() >= 2) {
                    stackPane.setScaleX(stackPane.getScaleX() - 1);
                    stackPane.setScaleY(stackPane.getScaleY() - 1);
                    scrollPane.setHvalue(event.getX() / scrollPane.getMinWidth());
                    scrollPane.setVvalue(event.getY() / scrollPane.getMinHeight());
                }
                event.consume();
            }
        };
    }

    public void setImage(AImage image) {
        this.image = image;
    }

}
