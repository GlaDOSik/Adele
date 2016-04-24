package adele.tabs;

import java.util.HashMap;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import adele.Bridge;
import adele.BridgeComponent;
import adele.MainSceneController;
import adele.core.DragHandler;
import adele.core.MoveHandler;
import adele.core.PressedHandler;
import adele.docks.LayersController;
import adele.interfaces.IControllerViewState;
import adele.interfaces.IViewState;
import adele.core.ScrollHandler;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javax.swing.JPanel;

public class ImageEditorController extends BridgeComponent implements IControllerViewState {
    
    @Override
    public void ready(){
        stckpaneEditorBG.setStyle("-fx-background-color: #282f3d;");
        tabStates = new HashMap<>();
        ((MainSceneController) getBridgeReference().getController(Bridge.View.MainSceneP)).getTabPane().boundsInParentProperty().addListener((ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) -> {
            scrp.layout();
        });
        scrp.minWidthProperty().bind(((MainSceneController) getBridgeReference().getController(Bridge.View.MainSceneP)).getTabPane().widthProperty());
        scrp.minHeightProperty().bind(((MainSceneController) getBridgeReference().getController(Bridge.View.MainSceneP)).getTabPane().heightProperty().subtract(24));
        stckpaneEditorBG.minWidthProperty().bind(((MainSceneController) getBridgeReference().getController(Bridge.View.MainSceneP)).getTabPane().widthProperty().subtract(2));
        stckpaneEditorBG.minHeightProperty().bind(((MainSceneController) getBridgeReference().getController(Bridge.View.MainSceneP)).getTabPane().heightProperty().subtract(26));
        
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
        axisVertical.setAnimated(false);
        /*
        inputScrollStackHandler = new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event){
                if (event.getDeltaY() < 0){
                    if ((int) stckpaneEditorBG.getScaleX() < 8){
                        stckpaneEditorBG.setScaleX(stckpaneEditorBG.getScaleX() + 1);
                        stckpaneEditorBG.setScaleY(stckpaneEditorBG.getScaleY() + 1);

                        scrp.setHvalue(event.getX() / scrp.getMinWidth());
                        scrp.setVvalue(event.getY() / scrp.getMinHeight());
                    }
                }
                else if (stckpaneEditorBG.getScaleX() >= 2){
                    stckpaneEditorBG.setScaleX(stckpaneEditorBG.getScaleX() - 1);
                    stckpaneEditorBG.setScaleY(stckpaneEditorBG.getScaleY() - 1);

                    scrp.setHvalue(event.getX() / scrp.getMinWidth());
                    scrp.setVvalue(event.getY() / scrp.getMinHeight());
                }
                event.consume();
            }
        };*/

 /* inputDragStackHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                if (event.getButton() == MouseButton.MIDDLE){
                    double deltaX = (panLastX - event.getScreenX()) / scrp.getMinWidth();
                    double deltaY = (panLastY - event.getScreenY()) / scrp.getMinHeight();
                    scrp.setHvalue(scrp.getHvalue() + deltaX);
                    scrp.setVvalue(scrp.getVvalue() + deltaY);
                    panLastX = event.getScreenX();
                    panLastY = event.getScreenY();
                }
                event.consume();
            }
        };*/

 /* inputPressStackHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                if (event.getButton() == MouseButton.MIDDLE){
                    panLastX = event.getScreenX();
                    panLastY = event.getScreenY();
                }
                event.consume();
            }
        };     */
        scrollHandler = new ScrollHandler(getBridgeReference().getImageEditor(), stckpaneEditorBG, scrp);
        pressedHandler = new PressedHandler(getBridgeReference().getImageEditor(), stckpaneEditorBG, scrp);
        dragHandler = new DragHandler(getBridgeReference().getImageEditor(), stckpaneEditorBG, scrp);
        moveHandler = new MoveHandler(inputDrawOverlay, getBridgeReference().getImageEditor());
        
        stckpaneEditorBG.setOnScroll(scrollHandler);
        stckpaneEditorBG.setOnMousePressed(pressedHandler);
        stckpaneEditorBG.setOnMouseDragged(dragHandler);
        
    }
    
    private NumberAxis axisHorizontal;
    private NumberAxis axisVertical;
    private HashMap<Tab, IViewState> tabStates;
    private Canvas inputDrawOverlay;    
    private ScrollHandler scrollHandler;
    private PressedHandler pressedHandler;
    private DragHandler dragHandler;
    private MoveHandler moveHandler;
    private EventHandler<ScrollEvent> inputScrollStackHandler;
    private EventHandler<MouseEvent> inputDragStackHandler;
    private EventHandler<MouseEvent> inputPressStackHandler;
    
    private double panLastX, panLastY;
    
    @FXML
    private ScrollPane scrp;
    @FXML
    private StackPane stckpaneEditorBG;
    
    
    public void linkLayers(){
        //bude se používat při přepínání stavu tabu, takže nejprve vyčistí stackpane,
        // pak přidá osy a input canvas, pak přidá vrstvy obrázku a seřadí je fcí sortLayers()
        stckpaneEditorBG.getChildren().clear();

        //musí přidat všechny sdílené vrstvy a ty z AKTIVNÍHO SNÍMKU
        for (int i = getBridgeReference().getImageEditor().getActiveImage().getFrame().getNumberOfLayers() - 1; i >= 0; i--){
            stckpaneEditorBG.getChildren().add(getBridgeReference().getImageEditor().getActiveImage().getFrame().getLayer(i).getCanvas());
        }
        stckpaneEditorBG.getChildren().add(axisVertical);
        stckpaneEditorBG.getChildren().add(axisHorizontal);

        //vstupní vrstva
        inputDrawOverlay = new Canvas(getBridgeReference().getImageEditor().getActiveImage().getWidth(), getBridgeReference().getImageEditor().getActiveImage().getHeight());
        inputDrawOverlay.setOnMouseMoved(new MoveHandler(inputDrawOverlay, getBridgeReference().getImageEditor()));
        
        inputDrawOverlay.getGraphicsContext2D().setLineWidth(1);
        inputDrawOverlay.getGraphicsContext2D().strokeLine(0.5, 0.5, 100.5, 100.5);
        inputDrawOverlay.getGraphicsContext2D().strokeLine(0.5, 51.5, 100.5, 51.5);
        
        stckpaneEditorBG.getChildren().add(inputDrawOverlay);
    }
    
    public void linkLayer(Canvas linkedCanvas){
        //přidá jednu vrstvu a seřadí je
        //stckpaneEditorBG.getChildren().add(linkedCanvas);
    }
    
    private void updateAxis(){
        //TOHLE JEN DO ZOOMOVANI A PRI NACTENI
        double imgWidth = getBridgeReference().getImageEditor().getActiveImage().getWidth();
        double imgHeight = getBridgeReference().getImageEditor().getActiveImage().getHeight();
        
        axisHorizontal.setUpperBound(imgWidth);
        axisHorizontal.setMaxWidth(imgWidth + 2);
        axisHorizontal.setMaxHeight(imgHeight + 2);
        
        axisVertical.setLowerBound(-imgHeight);
        axisVertical.setMaxWidth(imgWidth + 2);
        axisVertical.setMaxHeight(imgHeight + 2);
    }
    
    @Override
    public void addTabState(Tab tab){
        tabStates.put(tab, new ImageEditorState());
    }
    
    @Override
    public IViewState getState(Tab tab){
        return tabStates.get(tab);
    }
    
    @Override
    public void removeTabState(Tab tab){
        tabStates.remove(tab);
        ((LayersController) getBridgeReference().getController(Bridge.View.LayersD)).cleanDock();
        if (tabStates.size() == 0){
            stckpaneEditorBG.setScaleX(1);
            stckpaneEditorBG.setScaleY(1);
        }
    }
    
    @Override
    public void saveState(Tab sourceTab){
        ((LayersController) getBridgeReference().getController(Bridge.View.LayersD)).cleanDock();
        ImageEditorState state = (ImageEditorState) tabStates.get(sourceTab);
        state.scale = (int) stckpaneEditorBG.getScaleX();
        state.hValue = scrp.getHvalue();
        state.vValue = scrp.getVvalue();
    }
    
    @Override
    public void loadState(Tab sourceTab){
        getBridgeReference().getImageEditor().changeActiveImage(sourceTab);
        linkLayers();
        updateAxis();
        ((LayersController) getBridgeReference().getController(Bridge.View.LayersD)).loadLayersDock();
        ImageEditorState state = (ImageEditorState) tabStates.get(sourceTab);
        stckpaneEditorBG.setScaleX(state.scale);
        stckpaneEditorBG.setScaleY(state.scale);
        scrp.setHvalue(state.hValue);
        scrp.setVvalue(state.vValue);
    }
    
    @Override
    public int getNumberOfStates(){
        return tabStates.size();
    }
    
    public Canvas getInputDrawOverlay(){
        return inputDrawOverlay;
    }
    
    public StackPane getStackPane(){
        return stckpaneEditorBG;
    }
    
    public ScrollPane getScrollPane(){
        return scrp;
    }
}
