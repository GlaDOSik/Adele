package adele.image.alphablend;

public interface AlphaBlend {
    
    public enum AlphaBlendType { AlphaMix }
    
    /**
     * Blend two colors using alpha value
     * @param p1
     * @param p2
     * @return float array of resulting blend operation 
     */
    public float[] blend(float[] p1, float[] p2);
    
    public String getName();
    
    public AlphaBlend.AlphaBlendType getBlendModeType();
    
}
