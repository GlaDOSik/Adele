package adele.image.blendmode;

public interface BlendMode {
    
    public enum BlendModeType { Normal, Add, Multiply }
    
    /**
     * Blends two pixels in float arrays.
     * @param p1 float array of top pixel
     * @param p2 float array of bottom pixel
     * @return float array of resulting blend operation
     */
    public float[] blend(float[] p1, float[] p2);
    
    /**
     * Get te name of this blend mode for the UI
     * @return 
     */
    public String getName();
    
    public BlendModeType getBlendModeType();
    
}
