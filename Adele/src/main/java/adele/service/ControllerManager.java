package adele.service;

import adele.controller.Controller;
import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ControllerManager {

    private static ControllerManager singleton;
    private final HashMap<String, Controller> map = new HashMap<>();
    private static final Logger logger = LogManager.getLogger();

    private ControllerManager() {
    }

    public static ControllerManager getSingletonInstance() {
        if (singleton == null) {
            singleton = new ControllerManager();
        }
        return singleton;
    }

    /**
     * Adds controller from the initialized FXMLLoader to the manager. Also sets
     * the controller reference to the root from FXMLLoader. The function is
     * using addController(Controller controller) internally.
     *
     * @param loader FMXLLoader with non-null controller and root
     */
    public void addController(FXMLLoader loader) {
        Controller controller = loader.getController();
        try {
            controller.setRoot(loader.load());
        } catch (IOException | NullPointerException ex) {
            logger.error("Unable to load view controller or view.");
            return;
        }
        addController(controller);
    }

    /**
     * Adds controller to the manager
     *
     * @param controller controller with setted root
     */
    public void addController(Controller controller) {
        map.put(controller.getName(), controller);
    }

    public void getController(String controllerName) {
        map.get(controllerName);
    }

    public void removeController(String controllerName) {
        map.remove(controllerName);
    }

}
