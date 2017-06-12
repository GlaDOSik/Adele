package adele.image.factory;

public interface ImageTemplate {
    
    public String getTemplateName();
    
    public int getWidth();
    
    public int getHeight();
    
    public int getNumberOfFrames();
    
    public int getNumberOfSharedLayers();
    
    public int getNumberOfLayers();
    
    public int[] getPixels();
    
    /**
     * Switch test pattern of pixels from getPixels()
     */
    public void switchTestPattern();
            
    public String getImageName();
    
    public int getFrameDelayInMS();
}
