package adele.tab;

import java.util.HashMap;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import adele.Bridge;
import adele.BridgeComponent;
import adele.core.AImage;
import adele.core.InputHandler;
import adele.popup.MainSceneController;
import adele.dock.LayersController;
import adele.interfaces.IControllerViewState;
import adele.interfaces.IViewState;
import javafx.scene.image.ImageView;

public class ImageEditorController extends BridgeComponent implements IControllerViewState {
    
    private NumberAxis axisHorizontal;
    private NumberAxis axisVertical;
    private HashMap<Tab, IViewState> tabStates; 
    private AImage image;
    private InputHandler handler;
    @FXML
    private ScrollPane scrollPane;
    //place for canvas/image/ruler
    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView imageView;
    
    @Override
    public void ready(){
        //TODO uživatelovo nastavení
        handler = new InputHandler(scrollPane, stackPane);
        stackPane.setStyle("-fx-background-color: #282f3d;");
        tabStates = new HashMap<>();
        ((MainSceneController) getBridgeReference().getController(Bridge.View.MainSceneP)).getTabPane().boundsInParentProperty().addListener((ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) -> {
            scrollPane.layout();
        });
        scrollPane.minWidthProperty().bind(((MainSceneController) getBridgeReference().getController(Bridge.View.MainSceneP)).getTabPane().widthProperty());
        scrollPane.minHeightProperty().bind(((MainSceneController) getBridgeReference().getController(Bridge.View.MainSceneP)).getTabPane().heightProperty().subtract(24));
        stackPane.minWidthProperty().bind(((MainSceneController) getBridgeReference().getController(Bridge.View.MainSceneP)).getTabPane().widthProperty().subtract(2));
        stackPane.minHeightProperty().bind(((MainSceneController) getBridgeReference().getController(Bridge.View.MainSceneP)).getTabPane().heightProperty().subtract(26));
        
        axisHorizontal = new NumberAxis(0, 800, 100);
        axisHorizontal.setSide(Side.BOTTOM);
        axisHorizontal.setSnapToPixel(true);
        axisHorizontal.setAnimated(false);
        
        axisVertical = new NumberAxis(-600, 0, 100);
        axisVertical.setTickLabelFormatter(new NumberAxis.DefaultFormatter(axisVertical) {
            @Override
            public String toString(Number value){
                if (value.intValue() == 0){
                    return "";
                }
                else{
                    return String.format("%7.0f", -value.doubleValue());
                }
            }
        });
        axisVertical.setSide(Side.RIGHT);
        axisVertical.setTickLabelRotation(270);
        //TODO uživatelovo nastavení (animované pravítko)
        axisVertical.setAnimated(false);
        
        stackPane.getChildren().add(axisHorizontal);
        stackPane.getChildren().add(axisVertical);
        
        stackPane.setOnScroll(handler.getScrollHandler());
        stackPane.setOnMousePressed(handler.getPressedHandler());
        stackPane.setOnMouseDragged(handler.getDragHandler());
        stackPane.setOnMouseMoved(handler.getMoveHandler());
    }
        
    private void updateAxis(){
        //TOHLE JEN DO ZOOMOVANI A PRI NACTENI
        
        double imgWidth = image.getWidth();
        double imgHeight = image.getHeight();
        
        axisHorizontal.setUpperBound(imgWidth );
        axisHorizontal.setMaxWidth((imgWidth * image.getCache().getScale()) + 2);
        axisHorizontal.setMaxHeight((imgHeight * image.getCache().getScale()) + 2);
        
        axisVertical.setLowerBound(-imgHeight);
        axisVertical.setMaxWidth((imgWidth * image.getCache().getScale()) + 2);
        axisVertical.setMaxHeight((imgHeight * image.getCache().getScale()) + 2);
    }
    
    @Override
    public IViewState addTabState(Tab tab){
        ImageEditorState state = new ImageEditorState();
        tabStates.put(tab, state);
        return state;
    }
    
    @Override
    public IViewState getState(Tab tab){
        return tabStates.get(tab);
    }
    
    @Override
    public void removeTabState(Tab tab){
        tabStates.remove(tab);
        ((LayersController) getBridgeReference().getController(Bridge.View.LayersD)).unloadDock();
    }
    
    @Override
    public void saveState(Tab sourceTab){
        ((LayersController) getBridgeReference().getController(Bridge.View.LayersD)).unloadDock();
        ImageEditorState state = (ImageEditorState) tabStates.get(sourceTab);
        state.hValue = scrollPane.getHvalue();
        state.vValue = scrollPane.getVvalue();
    }
    
    @Override
    public void loadState(Tab sourceTab){
        //změnit aktivní obrázek v InputHandler        
        ImageEditorState state = (ImageEditorState) tabStates.get(sourceTab);
        image = state.getImage();
        handler.setImage(image);
        ((LayersController) getBridgeReference().getController(Bridge.View.LayersD)).loadDock(state);
        
        imageView.setImage(image.getFXImage());
        imageView.setFitWidth(image.getWidth());
        imageView.setFitHeight(image.getHeight());
        updateAxis();        
        scrollPane.setHvalue(state.hValue);
        scrollPane.setVvalue(state.vValue);
    }
    
    @Override
    public int getNumberOfStates(){
        return tabStates.size();
    }
    
    
    public ScrollPane getScrollPane(){
        return scrollPane;
    }
    
    
}
