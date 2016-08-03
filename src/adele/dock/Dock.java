package adele.dock;

import adele.popup.Popup;
import adele.tab.ImageEditorState;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class Dock extends Popup {

    public enum DockPlacement {
        Left, Right
    }

    public enum DockState {
        Hidden, InDock, InWindow
    }

    private DockState dockState = DockState.Hidden;
    private VBox dockColumn = null;
    private DockPlacement dockPlacement = DockPlacement.Right;

    //each dock should have this fx ids
    //root titled pane
    @FXML
    private TitledPane paneDock;
    //hbox with resize button
    @FXML
    private HBox hboxResize;

    public abstract void loadDock(ImageEditorState state);

    public abstract void unloadDock();

    public void setDockState(DockState dockState) {
        this.dockState = dockState;

    }

    public DockState getDockState() {
        return dockState;
    }

    public void setDockPlacement(DockPlacement dockPlacement) {
        this.dockPlacement = dockPlacement;
        if (dockPlacement == DockPlacement.Left) {
            hboxResize.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            ((Button) hboxResize.getChildren().get(0)).setId("btnResizeDockRight");
            //nastavit ikonu
        }
        else {
            hboxResize.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            ((Button) hboxResize.getChildren().get(0)).setId("btnResizeDockLeft");
        }
    }

    public DockPlacement getDockPlacement() {
        return dockPlacement;
    }

    public void lockDock() {
        getViewRoot().setDisable(true);
    }

    public void unlockDock() {
        getViewRoot().setDisable(false);
    }

    public void setDockColumn(VBox dockColumn) {
        this.dockColumn = dockColumn;
    }

    @FXML
    private void resizeDock(MouseEvent event) {
        double newHeight = paneDock.getHeight() + event.getY();
        if (newHeight < 0) {
            paneDock.setPrefHeight(0.0);
        }
        //tools dock should change the height - it's determined by the number of icons
        else if (!(this instanceof ToolsController)) {
            paneDock.setPrefHeight(newHeight);
        }
        dockColumn.setPrefWidth(dockColumn.getWidth() - event.getX());
        event.consume();
    }

}
