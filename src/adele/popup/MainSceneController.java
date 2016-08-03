package adele.popup;

import adele.Bridge;
import adele.BridgeComponent;
import adele.dock.Dock;
import adele.interfaces.IControllerViewState;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainSceneController extends Popup {

    private static final int MINIMUM_WINDOW_WIDTH = 500;
    private static final int MINIMUM_WINDOW_HEIGHT = 300;

    @FXML
    private TabPane tabPane;
    @FXML
    private VBox leftDock;
    @FXML
    private VBox rightDock;
    @FXML
    private ToggleButton btnMaximize;
    @FXML
    private Button btnResize;
    @FXML
    private Button btnFullscreen;
    @FXML
    private CheckMenuItem toogleLayers;
    @FXML
    private CheckMenuItem toogleTools;
    
    @Override
    public void ready() {
        setMinimumWindowWidth(MINIMUM_WINDOW_WIDTH);
        setMinimumWindowHeight(MINIMUM_WINDOW_HEIGHT);
    }

    //If the controller is not singleton, it needs to implement IControllerViewState and work with states (IViewState)
    public Tab createTab(Bridge.View viewT, String name, boolean isSelected) {
        //New tab
        Tab newTab = new Tab(name);
        newTab.setUserData(viewT);
        BridgeComponent bridgeComponent = getBridgeReference().getController(viewT);

        //if controller implements IControllerViewState, it's not singleton and it's working with states
        if (bridgeComponent instanceof IControllerViewState) {
            newTab.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    Tab switchedTab = (Tab) event.getSource();
                    IControllerViewState controllerViewState = (IControllerViewState) getBridgeReference().getController((Bridge.View) switchedTab.getUserData());

                    if (!switchedTab.isSelected()) {
                        controllerViewState.saveState(switchedTab);
                        System.out.println("ukladam");
                    }
                    else {
                        controllerViewState.loadState(switchedTab);
                        switchedTab.setContent(null);
                        switchedTab.setContent(((BridgeComponent) controllerViewState).getViewRoot());
                        System.out.println("nahravam");
                    }

                }
            });

            newTab.setOnCloseRequest(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    //pokud stav není uložen, zkonzumovat event a vyhodit popup potvrzení
                    Tab removedTab = (Tab) event.getSource();
                    removedTab.setOnCloseRequest(null);
                    removedTab.setOnSelectionChanged(null);
                    removedTab.setContent(null);
                    IControllerViewState controllerViewState = (IControllerViewState) getBridgeReference().getController((Bridge.View) removedTab.getUserData());
                    controllerViewState.removeTabState(removedTab);
                }
            });
            ((IControllerViewState) bridgeComponent).addTabState(newTab);
        }
        else {
            newTab.setOnCloseRequest(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    Tab removedTab = (Tab) event.getSource();
                    removedTab.setOnCloseRequest(null);
                    removedTab.setContent(null);
                }
            });
        }
        newTab.setContent(bridgeComponent.getViewRoot());

        tabPane.getTabs().add(newTab);
        if (isSelected) {
            tabPane.getSelectionModel().select(newTab);
        }
        return newTab;
    }
    
    private void addDock(Dock dock){
        //při ukládání se získají informace o zobrazených docích a jejich indexech
        //při nahrání se informace použijí k 
        if (dock.getDockPlacement() == Dock.DockPlacement.Right) {
            rightDock.getChildren().add(dock.getViewRoot());   
            dock.setDockColumn(rightDock);
        }
        else {
            leftDock.getChildren().add(dock.getViewRoot());  
            dock.setDockColumn(leftDock);
        }
    }

    public void removeDock(Dock dock) {
        if (dock.getDockPlacement() == Dock.DockPlacement.Right) {
            rightDock.getChildren().remove(dock.getViewRoot());
        }
        else {
            leftDock.getChildren().remove(dock.getViewRoot());
        }       
    }

    public void addPopUp(Bridge.View viewP, String title, boolean onTop, boolean useParameterStage, Stage stage) {
        Popup popup = (Popup) getBridgeReference().getController(viewP);
        if (popup.getStage() == null) {
            if (useParameterStage) {
                popup.setStage(stage);
                popup.getStage().initStyle(StageStyle.UNDECORATED);
            }
            else {
                popup.setStage(new Stage(StageStyle.UNDECORATED));
            }

            popup.getStage().setTitle(title);
            popup.getStage().setScene(new Scene(popup.getViewRoot()));
            popup.getStage().getScene().getStylesheets().add("adele/skins/DarkDefault/DefaultSkin.css");
            popup.getStage().setWidth(((Pane) popup.getViewRoot()).getPrefWidth());
            popup.getStage().setHeight(((Pane) popup.getViewRoot()).getPrefHeight());
            popup.getStage().setFullScreenExitHint("");
            popup.getStage().setAlwaysOnTop(onTop);
            //ve fullscreenu popup zavře zbytek okna!!!
            //předělat fullscreen na maximize
        }
        popup.showPopup();
    }

    //  MENU BAR ACTIONS - FILE
    @FXML
    private void createNewFile() {
        createTab(Bridge.View.NewFileT, "New File", true);
    }

    @FXML
    private void closeActiveTab() {
        closeSelectedTab();
    }

    @FXML
    private void closeAll() {
        closeAllTabs();
    }

    @FXML
    private void debugInfo() {
        System.out.println("Number of controllers: " + getBridgeReference().getNumberOfControllers());
        System.out.println("Number of tabs: " + tabPane.getTabs().size());
    }

    //  MENU BAR ACTIONS - DOCKS
    @FXML
    private void toogleLayersDock() {
        if (toogleLayers.isSelected()) {
            addDock((Dock)getBridgeReference().getController(Bridge.View.LayersD));
        }
        else {
            removeDock((Dock)getBridgeReference().getController(Bridge.View.LayersD));
        }
    }

    @FXML
    private void toogleToolsDock() {
        if (toogleTools.isSelected()) {
            addDock((Dock)getBridgeReference().getController(Bridge.View.ToolsD));
        }
        else {
            removeDock((Dock)getBridgeReference().getController(Bridge.View.ToolsD));
        }
    }

    //  WINDOW OPERATIONS
    @FXML
    private void closeMainWindow() {
        closePopup();
    }

    @FXML
    private void maximizeMainWindow() {
        if (getStage().isMaximized()) {
            getStage().setMaximized(false);
            btnResize.setVisible(true);
            btnFullscreen.setVisible(true);

        }
        else {
            getStage().setMaximized(true);
            getStage().setHeight(Screen.getPrimary().getVisualBounds().getHeight());
            btnResize.setVisible(false);
            btnFullscreen.setVisible(false);
        }
    }

    @FXML
    private void minimizeMainWindow() {
        if (getStage().isIconified()) {
            getStage().setIconified(false);
        }
        else {
            getStage().setIconified(true);
        }
    }

    @FXML
    private void fullscreenMainWindow() {
        //TODO schovat ikonu pro zvětšení
        if (getStage().isFullScreen()) {
            getStage().setFullScreen(false);
            btnResize.setVisible(true);
            btnMaximize.setVisible(true);
        }
        else {
            getStage().setFullScreen(true);
            btnResize.setVisible(false);
            btnMaximize.setVisible(false);
        }
    }

    @FXML
    private void startMoveWindow(MouseEvent event) {
        if (!getStage().isMaximized() && !getStage().isFullScreen()) {
            moveStart(event);
        }
    }

    @FXML
    private void moveWindow(MouseEvent event) {
        if (!getStage().isMaximized() && !getStage().isFullScreen()) {
            moveDragged(event, true);
        }
    }

    @FXML
    private void resizeWindow(MouseEvent event) {
        resize(event);
    }

    // HELPERS
    public void closeSelectedTab() {
        Tab removedTab = tabPane.getSelectionModel().getSelectedItem();
        if (removedTab != null) {
            removedTab.getOnCloseRequest().handle(new Event(removedTab, removedTab, EventType.ROOT));
            tabPane.getTabs().remove(removedTab);
        }
    }

    private void closeAllTabs() {
        for (int i = 0; i < tabPane.getTabs().size(); i++) {
            tabPane.getTabs().get(i).getOnCloseRequest().handle(new Event(tabPane.getTabs().get(i), tabPane.getTabs().get(i), EventType.ROOT));
        }
        tabPane.getTabs().clear();
    }

    // GETTERS/SETTERS
    public Tab getSelectetTab() {
        return tabPane.getSelectionModel().getSelectedItem();
    }

    public int getSelectetTabIndex() {
        return tabPane.getSelectionModel().getSelectedIndex();
    }

    public TabPane getTabPane() {
        return tabPane;
    }

}
