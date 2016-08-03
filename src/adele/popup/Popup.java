
package adele.popup;

import adele.BridgeComponent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public abstract class Popup extends BridgeComponent {

    private int minimumWindowWidth = 500;
    private int minimumWindowHeight = 300;
    private int moveOffsetX;
    private int moveOffsetY;
    private int primaryStageWidth;
    private int primaryStageHeight;
    private Stage stage;
    private boolean isClosed = true;

    public void closePopup(){
        isClosed = true;
        stage.close();
    }

    public void moveStart(MouseEvent event){
        moveOffsetX = (int) (stage.getX() - event.getScreenX());
        moveOffsetY = (int) (stage.getY() - event.getScreenY());
        primaryStageWidth = (int) stage.getWidth();
        primaryStageHeight = (int) stage.getHeight();
    }

    public void moveDragged(MouseEvent event, boolean snapping){
        stage.setX(event.getScreenX() + moveOffsetX);
        stage.setY(event.getScreenY() + moveOffsetY);
        if (snapping){
            snapToEdge(event);
        }
    }

    public void resize(MouseEvent event){
        if ((int) event.getScreenX() - stage.getX() >= minimumWindowWidth){
            stage.setWidth(event.getScreenX() - stage.getX());
        }
        else{
            stage.setWidth(minimumWindowWidth);
        }

        if ((int) event.getScreenY() - stage.getY() >= minimumWindowHeight){
            stage.setHeight(event.getScreenY() - stage.getY());
        }
        else{
            stage.setHeight(minimumWindowHeight);
        }
    }

    private void snapToEdge(MouseEvent event){
        if (event.getScreenX() < 10 && event.getScreenY() > 10 && event.getScreenY() < Screen.getPrimary().getVisualBounds().getHeight() - 10){
            stage.setX(0);
            stage.setY(0);
            stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);
            stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
        }
        else if (event.getScreenX() > Screen.getPrimary().getVisualBounds().getWidth() - 10 && event.getScreenY() > 10 && event.getScreenY() < Screen.getPrimary().getVisualBounds().getHeight() - 10){
            stage.setX(Screen.getPrimary().getVisualBounds().getWidth() / 2);
            stage.setY(0);
            stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);
            stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
        }
        else if (event.getScreenY() < 10 && event.getScreenX() > 10 && event.getScreenX() < Screen.getPrimary().getVisualBounds().getWidth() - 10){
            stage.setX(0);
            stage.setY(0);
            stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
            stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight() / 2);
        }
        else if (event.getScreenY() > Screen.getPrimary().getVisualBounds().getHeight() - 10 && event.getScreenX() > 10 && event.getScreenX() < Screen.getPrimary().getVisualBounds().getWidth() - 10){
            stage.setX(0);
            stage.setY(Screen.getPrimary().getVisualBounds().getHeight() / 2);
            stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
            stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight() / 2);
        }
        else{
            stage.setWidth(primaryStageWidth);
            stage.setHeight(primaryStageHeight);
        }
    }
    
    public void showPopup(){
        isClosed = false;
        stage.show();
    }
    
    public boolean isClosed(){
        return isClosed;
    }

    public Stage getStage(){
        return stage;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setMinimumWindowWidth(int minimumWindowWidth){
        this.minimumWindowWidth = minimumWindowWidth;
    }

    public void setMinimumWindowHeight(int minimumWindowHeight){
        this.minimumWindowHeight = minimumWindowHeight;
    }
}
