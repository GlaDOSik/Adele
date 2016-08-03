package adele.dock;

import adele.tab.ImageEditorState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class ToolsController extends Dock {
    
     @FXML
    private FlowPane flowPane;
    private static PaintTool activeTool = null;
    private Button activeButton = null;

    @Override
    public void ready() {
        setDockPlacement(DockPlacement.Left);
        loadDock(null);
    }   

    @Override
    public void loadDock(ImageEditorState state) {
        addToolButton(PaintTool.Pen);
        addToolButton(PaintTool.Eraser);
        addToolButton(PaintTool.Dropper);
        //addToolButton(PaintTool.Brush);
        //addToolButton(PaintTool.Bucket);
        //addToolButton(PaintTool.Dither);
        //addToolButton(PaintTool.Line);
        addToolButton(PaintTool.PrimaryColor);
        addToolButton(PaintTool.SecondaryColor);
    }

    @Override
    public void unloadDock() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public enum PaintTool {
        Pen, Dropper, Eraser, Brush, Selection, Bucket, Line, Dither,
        Zoom, PrimaryColor, SecondaryColor
    }

    public void addToolButton(PaintTool tool) {
        Button button = new Button("");
        button.setMinWidth(40);
        button.setMinHeight(40);

        button.setId(tool.toString());
        button.setUserData(tool);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PaintTool clickedTool = (PaintTool) ((Button) event.getSource()).getUserData();

                if (clickedTool == PaintTool.PrimaryColor) {
                    System.out.println("open color picker");
                }
                else if (clickedTool == PaintTool.SecondaryColor) {
                    System.out.println("open color picker");
                }
                else if (activeButton != (Button) event.getSource()) {
                    ((Button) event.getSource()).setId(clickedTool.toString() + "Active");
                    activeTool = (PaintTool) ((Button) event.getSource()).getUserData();
                    if (activeButton != null) {
                        activeButton.setId(activeButton.getId().substring(0, activeButton.getId().length() - 6));
                    }
                    activeButton = (Button) event.getSource();
                }
            }
        });
        flowPane.getChildren().add(button);
    }

    private void unselect() {
        activeButton.setId(activeButton.getId().substring(0, activeButton.getId().length() - 6));
    }
    
    public static PaintTool getActiveTool(){
        return activeTool;
    }

}
