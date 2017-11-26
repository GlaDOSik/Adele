package adele;

import utils.controller.ViewSource;

/**
 * View paths of Adele image editor
 */
public enum AdeleViewSource implements ViewSource{
    WindowFrame("/fxml/utility/WindowFrame.fxml"), 
    MainWindow("/fxml/MainWindow.fxml"),
    NewImage("/fxml/tabcontent/NewImage.fxml"),
    ImageEditor("/fxml/tabcontent/ImageEditor.fxml");
    
    private final String path;
        
    private AdeleViewSource(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getName() {
        return toString();
    }
}
