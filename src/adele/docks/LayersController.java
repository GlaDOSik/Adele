package adele.docks;

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
import adele.BridgeComponent;
import adele.MainSceneController;
import adele.core.LayerData;
import adele.popups.LayerSettingsController;
import adele.tabs.ImageEditorController;

public class LayersController extends BridgeComponent {

    @Override
    public void ready(){
        layersContainer.setItems(listOfLayerItems);
    }

    ObservableList<Pane> listOfLayerItems = FXCollections.observableArrayList();
    private int lastDraggedCellIndex = 0;
    private boolean draggStoped = false;

    @FXML
    Label frameLabel;
    @FXML
    private ListView layersContainer;

    @FXML
    private void addFrameLayer(){
        String frameLayerName = "New Frame Layer";
        getBridgeReference().getImageEditor().getActiveImage().getFrame().addNewLayer(false, frameLayerName);
        addLayer(false, frameLayerName, true);
        System.out.println("Pocet vrstev: " + getBridgeReference().getImageEditor().getActiveImage().getFrame().getNumberOfLayers());
    }

    @FXML
    private void addSharedLayer(){
        String sharedLayerName = "New Shared Layer";
        getBridgeReference().getImageEditor().getActiveImage().getFrame().addNewLayer(true, sharedLayerName);
        addLayer(true, sharedLayerName, true);
        System.out.println("Pocet vrstev: " + getBridgeReference().getImageEditor().getActiveImage().getFrame().getNumberOfLayers());
    }

    @FXML
    private void deleteLayer(){
        int selectedLayer = layersContainer.getSelectionModel().getSelectedIndex();
        listOfLayerItems.remove(selectedLayer);
        getBridgeReference().getImageEditor().getActiveImage().getFrame().deleteLayerTestShared(selectedLayer);
        ((ImageEditorController) getBridgeReference().getController(Bridge.View.ImageEditorT)).linkLayers();
        System.out.println("Pocet vrstev: " + getBridgeReference().getImageEditor().getActiveImage().getFrame().getNumberOfLayers());
    }

    private void addLayer(boolean isShared, boolean isVisible){
        addLayer(isShared, "New Layer", isVisible);
    }

    private void addLayer(boolean isShared, String layerName, boolean isVisible){
        try{
            Pane item = FXMLLoader.load(getClass().getResource("LayerItem.fxml"));
            ((Label) item.getChildren().get(2)).setText(layerName);
            ((CheckBox) item.getChildren().get(1)).setSelected(isVisible);
            if (isShared){
                ((CheckBox) ((Pane) item).getChildren().get(0)).setSelected(true);
            }
            item.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event){
                    if (event.getButton().equals(MouseButton.PRIMARY)){
                        if (event.getClickCount() == 2){
                            ((LayerSettingsController) getBridgeReference().getController(Bridge.View.LayerSettingsP)).setPaneReference((Pane) event.getSource());
                            ((LayerSettingsController) getBridgeReference().getController(Bridge.View.LayerSettingsP)).loadPopup();
                            ((MainSceneController) getBridgeReference().getController(Bridge.View.MainSceneP)).addPopUp(Bridge.View.LayerSettingsP, "Layer settings", true, false, null);
                        }
                    }
                }
            });

            item.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event){
                    int repIndex = layersContainer.getSelectionModel().getSelectedIndex() + (int) Math.floor(event.getY() / 25.0);
                    if (repIndex < listOfLayerItems.size() && repIndex >= 0 && repIndex != layersContainer.getSelectionModel().getSelectedIndex()){
                        listOfLayerItems.get(lastDraggedCellIndex).setId("layerItemNormal");
                        listOfLayerItems.get(repIndex).setId("layerItemReplaced");
                        lastDraggedCellIndex = repIndex;
                        draggStoped = true;
                    }
                }
            });

            item.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event){
                    if (draggStoped){
                        listOfLayerItems.get(lastDraggedCellIndex).setId("layerItemNormal");
                        int selectedIndex = layersContainer.getSelectionModel().getSelectedIndex();
                        Pane p = listOfLayerItems.get(selectedIndex);
                        listOfLayerItems.remove(selectedIndex);
                        listOfLayerItems.add(lastDraggedCellIndex, p);

                        LayerData layer = getBridgeReference().getImageEditor().getActiveImage().getFrame().getLayer(selectedIndex);
                        getBridgeReference().getImageEditor().getActiveImage().getFrame().deleteLayer(layer);
                        getBridgeReference().getImageEditor().getActiveImage().getFrame().linkLayer(layer, lastDraggedCellIndex);

                        layersContainer.getSelectionModel().select(lastDraggedCellIndex);
                        draggStoped = false;

                        //PŘEDĚLAT JEN NA POSUN JEDNÉ
                        ((ImageEditorController) getBridgeReference().getController(Bridge.View.ImageEditorT)).linkLayers();
                    }
                }
            });

            ((CheckBox) item.getChildren().get(1)).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event){
                    Pane pane = (Pane) ((CheckBox) event.getSource()).getParent();
                    int index = listOfLayerItems.indexOf(pane);

                    if (((CheckBox) event.getSource()).isSelected()){
                        getBridgeReference().getImageEditor().getActiveImage().getFrame().getLayer(index).setVisible(true);
                    }
                    else{
                        getBridgeReference().getImageEditor().getActiveImage().getFrame().getLayer(index).setVisible(false);
                    }
                }
            });

            listOfLayerItems.add(item);
            layersContainer.getSelectionModel().select(item);
            //ZBYTEČNĚ PROPOJUJE VŠECHNY, UPRAVIT NA LINK LAYER
            ((ImageEditorController) getBridgeReference().getController(Bridge.View.ImageEditorT)).linkLayers();
        }
        catch (IOException ex){
            Logger.getLogger(LayersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadLayersDock(){
        listOfLayerItems.clear();
        //get frame(0) - 0 předělat na právě vybraný snímek v docku
        int fln = getBridgeReference().getImageEditor().getActiveImage().getFrame().getNumberOfLayers();
        for (int i = 0; i < fln; i++){
            String name = getBridgeReference().getImageEditor().getActiveImage().getFrame().getLayer(i).getName();
            boolean isShared = getBridgeReference().getImageEditor().getActiveImage().getFrame().getLayer(i).isShared();
            boolean isVisible = getBridgeReference().getImageEditor().getActiveImage().getFrame().getLayer(i).isVisible();
            addLayer(isShared, name, isVisible);
        }
        frameLabel.setText(String.format("%d", getBridgeReference().getImageEditor().getActiveImage().getActiveFrameIndex()));
        getViewRoot().setDisable(false);
    }

    public void cleanDock(){
        listOfLayerItems.clear();
        frameLabel.setText("");
        getViewRoot().setDisable(true);
    }

    public int getPaneIndex(Pane pane){
        return listOfLayerItems.indexOf(pane);
    }

    public void saveLayerSettings(Pane pane){
        int index = listOfLayerItems.indexOf(pane);
        getBridgeReference().getImageEditor().getActiveImage().getFrame().getLayer(index).setName(((Label) pane.getChildren().get(2)).getText());
        //přidat shared
    }

}
