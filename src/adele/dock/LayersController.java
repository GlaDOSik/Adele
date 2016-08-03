package adele.dock;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import adele.Bridge;
import adele.popup.MainSceneController;
import adele.popup.LayerSettingsController;
import adele.tab.ImageEditorState;
import adele.core.AImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class LayersController extends Dock {

    private static int selectedLayerIdx = 0;
    private AImage image;
    ObservableList<Pane> listOfLayerItems = FXCollections.observableArrayList();
    private int lastDraggedCellIndex = 0;
    private boolean isDragged = false;
    @FXML
    Label frameLabel;
    @FXML
    private ListView layersContainer;
   
    @Override
    public void ready() {
        setDockPlacement(DockPlacement.Right);
        layersContainer.setItems(listOfLayerItems);
        //list view don't want to respect max height !!!
        layersContainer.setPrefHeight(4000);
    }
    
    @Override
    public void loadDock(ImageEditorState state) {
        image = state.getImage();
        //get frame(0) - 0 předělat na právě vybraný snímek v docku
        int fln = image.getSelectedFrame().getNumberOfLayers();
        for (int i = 0; i < fln; i++) {
            String name = image.getSelectedFrame().getLayer(i).getName();
            boolean isShared = image.getSelectedFrame().getLayer(i).isShared();
            boolean isVisible = image.getSelectedFrame().getLayer(i).isVisible();
            addLayerItem(isShared, name, isVisible);
        }
        frameLabel.setText(String.format("%d", image.getSelectedFrameIndex()));
        unlockDock();
    }

    @Override
    public void unloadDock() {
        listOfLayerItems.clear();
        frameLabel.setText("");
        lockDock();
    }

    @FXML
    private void addFrameLayer() {
        String frameLayerName = "New Layer";
        image.getSelectedFrame().makeLayer(frameLayerName, false, 0, AImage.colorToAformat(Color.AQUA));
        addLayerItem(false, frameLayerName, true);
        System.out.println("Pocet vrstev: " + image.getSelectedFrame().getNumberOfLayers());
    }

    @FXML
    private void addSharedLayer() {
        String sharedLayerName = "New Shared Layer";
        image.getSelectedFrame().makeLayer(sharedLayerName, true, 0, AImage.colorToAformat(Color.GREENYELLOW));
        addLayerItem(true, sharedLayerName, true);
        System.out.println("Pocet vrstev: " + image.getSelectedFrame().getNumberOfLayers());
    }

    @FXML
    private void deleteLayer() {
        int selectedLayer = layersContainer.getSelectionModel().getSelectedIndex();
        listOfLayerItems.remove(selectedLayer);
        image.getSelectedFrame().removeLayer(selectedLayer);
        System.out.println("Pocet vrstev: " + image.getSelectedFrame().getNumberOfLayers());
    }

    private void addLayerItem(boolean isShared, String layerName, boolean isVisible) {
        Pane item;
        Label lblName;
        CheckBox chbShared;
        CheckBox chbVisible;
        try {
            item = FXMLLoader.load(getClass().getResource("LayerItem.fxml"));
        }
        catch (IOException ex) {
            Logger.getLogger(LayersController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        chbShared = ((CheckBox) ((HBox) item.getChildren().get(0)).getChildren().get(0));
        chbVisible = ((CheckBox) ((HBox) item.getChildren().get(0)).getChildren().get(1));
        lblName = ((Label) ((HBox) item.getChildren().get(0)).getChildren().get(2));

        lblName.setText(layerName);
        chbVisible.setSelected(isVisible);
        if (isShared) {
            chbShared.setSelected(true);
        }
        item.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if(event.getClickCount() == 1){       
                        //without this line, the selection is updated after the call getSelectedIndex, thus the selectedLayerIdx is wrong
                        layersContainer.getSelectionModel().select(event.getSource());
                        selectedLayerIdx = layersContainer.getSelectionModel().getSelectedIndex();    
                    }
                    else if (event.getClickCount() == 2) {
                        ((LayerSettingsController) getBridgeReference().getController(Bridge.View.LayerSettingsP)).setPaneReference((Pane) event.getSource());
                        ((LayerSettingsController) getBridgeReference().getController(Bridge.View.LayerSettingsP)).loadPopup();
                        ((MainSceneController) getBridgeReference().getController(Bridge.View.MainSceneP)).addPopUp(Bridge.View.LayerSettingsP, "Layer settings", true, false, null);
                    }
                }
            }
        });

        item.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int repIndex = layersContainer.getSelectionModel().getSelectedIndex() + (int) Math.floor(event.getY() / item.getHeight());
                if (repIndex < listOfLayerItems.size() && repIndex >= 0 && repIndex != layersContainer.getSelectionModel().getSelectedIndex()) {
                    listOfLayerItems.get(lastDraggedCellIndex).setId("layerItemNormal");
                    listOfLayerItems.get(repIndex).setId("layerItemReplaced");
                    lastDraggedCellIndex = repIndex;
                    isDragged = true;
                }
            }
        });

        item.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isDragged) {
                    listOfLayerItems.get(lastDraggedCellIndex).setId("layerItemNormal");
                    int selectedIndex = layersContainer.getSelectionModel().getSelectedIndex();
                    Pane p = listOfLayerItems.get(selectedIndex);
                    listOfLayerItems.remove(selectedIndex);
                    listOfLayerItems.add(lastDraggedCellIndex, p);

                    image.getSelectedFrame().moveLayer(image.getSelectedFrame().getLayer(selectedIndex), lastDraggedCellIndex);
                    layersContainer.getSelectionModel().select(lastDraggedCellIndex);
                    image.buildCache();
                    isDragged = false;
                }
            }
        });

        chbVisible.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane pane = (Pane) ((CheckBox) event.getSource()).getParent().getParent();
                int index = listOfLayerItems.indexOf(pane);

                if (((CheckBox) event.getSource()).isSelected()) {
                    image.getSelectedFrame().getLayer(index).setVisible(true);
                }
                else {
                    image.getSelectedFrame().getLayer(index).setVisible(false);
                }
            }
        });

        listOfLayerItems.add(0, item);
        layersContainer.getSelectionModel().select(item);
    }

    public void saveLayerSettings(Pane pane) {
        int index = listOfLayerItems.indexOf(pane);
        image.getSelectedFrame().getLayer(index).setName(((Label) ((HBox) pane.getChildren().get(0)).getChildren().get(2)).getText());
        //přidat shared
    }
    
    public int getPaneIndex(Pane pane) {
        return listOfLayerItems.indexOf(pane);
    }
    
    public static int getSelectedLayerIdx(){
        return selectedLayerIdx;
    }
    

}
