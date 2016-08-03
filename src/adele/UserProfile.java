/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele;

/**

 @author Ludek
 */
public class UserProfile {
    
    public UserProfile(){
    //nahraje výchozí uživatelův profil/pokud neexistuje, vytvoří ho
    //vyzve uživatele, aby vybral lokaci svého profilu
    }
    
    //animované pravítko
    boolean animatedAxis = false;
    //barva okolo obrázku
    String colorAroundImage = "#282f3d";
    //skin - výchozí, nebo cesta k vlastnímu?
    
    public void loadUserProfile(){
        //JSON ?? jiný formát
        System.out.println("Loading user file");
    }
    
    public void saveUserProfile(){
        System.out.println("Saving user file");
    }
    
}
