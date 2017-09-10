package adele.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

public class WindowFrameController extends ViewController implements Initializable {

    @Getter
    @Setter
    private boolean snapping = false;    
    
    private Stage stage;
    
    @Setter
    private int minFrameWidth = 0;
    @Setter
    private int minFrameHeight = 0;
    
    @FXML
    private Label frameName;
    
    @FXML
    private VBox vboxContent;
    
    private int moveOffsetX;
    private int moveOffsetY;
    private int primaryStageWidth;
    private int primaryStageHeight;
    private boolean snapped = false;
    private FrameState frameState = FrameState.WINDOW;
    
    private enum FrameState {
        WINDOW, MAXIMIZE, FULLSCREEN
    }
    
    public void setStage(Stage stage) {
        // TODO - add dynamic skin changing
        stage.getScene().getStylesheets().add("skins/darkstar/Darkstar.css");
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    private void moveStart(MouseEvent event){
        moveOffsetX = (int) (stage.getX() - event.getScreenX());
        moveOffsetY = (int) (stage.getY() - event.getScreenY());
        if (!snapped){
            primaryStageWidth = (int) stage.getWidth();
            primaryStageHeight = (int) stage.getHeight();
        }
        else {
            moveOffsetX = 0;
            moveOffsetY = 0;
        }
    }

    @FXML
    private void moveDrag(MouseEvent event){
        stage.setX(event.getScreenX() + moveOffsetX);
        stage.setY(event.getScreenY() + moveOffsetY);
        if (snapping){
            snapToEdge(event, stage);
        }
    }

    @FXML
    private void resize(MouseEvent event){
        if ((int) event.getScreenX() - stage.getX() >= minFrameWidth){
            stage.setWidth(event.getScreenX() - stage.getX());
        }
        else{
            stage.setWidth(minFrameWidth);
        }

        if ((int) event.getScreenY() - stage.getY() >= minFrameHeight){
            stage.setHeight(event.getScreenY() - stage.getY());
        }
        else{
            stage.setHeight(minFrameHeight);
        }
    }
    
    @FXML
    private void minimize(ActionEvent event) {
        stage.setIconified(true);
    }
    
    @FXML
    private void maximize(ActionEvent event) {
        switch (frameState) {
            case WINDOW:
                frameState = FrameState.MAXIMIZE;
                stage.setMaximized(true);
                break;
            case MAXIMIZE:
                frameState = FrameState.FULLSCREEN;
                stage.setMaximized(false);
                stage.setFullScreen(true);
                break;
            case FULLSCREEN:
                frameState = FrameState.WINDOW;
                stage.setFullScreen(false);
                break;        
        }
    }
    
    
    private void snapToEdge(MouseEvent event, Stage stage){
        if (event.getScreenX() < 10 && event.getScreenY() > 10 && event.getScreenY() < Screen.getPrimary().getVisualBounds().getHeight() - 10){
            stage.setX(0);
            stage.setY(0);
            stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);
            stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
            snapped = true;
        }
        else if (event.getScreenX() > Screen.getPrimary().getVisualBounds().getWidth() - 10 && event.getScreenY() > 10 && event.getScreenY() < Screen.getPrimary().getVisualBounds().getHeight() - 10){
            stage.setX(Screen.getPrimary().getVisualBounds().getWidth() / 2);
            stage.setY(0);
            stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);
            stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
            snapped = true;
        }
        else if (event.getScreenY() < 10 && event.getScreenX() > 10 && event.getScreenX() < Screen.getPrimary().getVisualBounds().getWidth() - 10){
            stage.setX(0);
            stage.setY(0);
            stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
            stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight() / 2);
            snapped = true;
        }
        else if (event.getScreenY() > Screen.getPrimary().getVisualBounds().getHeight() - 10 && event.getScreenX() > 10 && event.getScreenX() < Screen.getPrimary().getVisualBounds().getWidth() - 10){
            stage.setX(0);
            stage.setY(Screen.getPrimary().getVisualBounds().getHeight() / 2);
            stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
            stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight() / 2);
            snapped = true;
        }
        else{
            stage.setWidth(primaryStageWidth);
            stage.setHeight(primaryStageHeight);
            snapped = false;
        }
    }

    public void setContent(Node root) {
        if (vboxContent.getChildren().size() == 3) {
            vboxContent.getChildren().set(1, root);
        }
        vboxContent.getChildren().add(1, root);
    }

    public void setFrameName(String name) {
        frameName.setText(name);
    }
    
    @Override
    public boolean isAllowedToUseWindowFrame() {
        return false;
    }

}
