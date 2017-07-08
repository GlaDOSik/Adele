/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.controller;

/**
 * Controller paths for
 */
public enum AdeleViewControllerSource implements ViewControllerSource{
    WindowFrame("/fxml/utility/WindowFrame.fxml"), 
    MainWindow("/fxml/MainWindow.fxml"),
    NewImage("/fxml/tabcontent/NewImage.fxml"),
    ImageEditor("/fxml/tabcontent/ImageEditor.fxml");
    
    private final String path;
        
    private AdeleViewControllerSource(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
