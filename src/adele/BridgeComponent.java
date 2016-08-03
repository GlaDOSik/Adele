
package adele;

import javafx.scene.Parent;

public abstract class BridgeComponent {
    
    private Parent root;
    private Bridge bridgeReference;
    
    public void setViewRoot(Parent viewRoot){
        root = viewRoot;
    }

    public Parent getViewRoot(){
        return root;
    }   

    public void setBridgeReference(Bridge bridgeReference){
        this.bridgeReference = bridgeReference;
    }

    public Bridge getBridgeReference(){
        return bridgeReference;
    }  
    
    //TODO přidat metodu updateUserSettings(UserProfile), která aktualizuje uživatelovo nastavení ze třídy UserProfile
    
    
    //ready is called when the bridgeComponent is in bridge
    //do not use getBridgeReference in constructor or in initialize, use it here !!
    public abstract void ready();
}
