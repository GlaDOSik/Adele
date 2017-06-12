package adele.image.blendmode;

public class NormalBlendMode implements BlendMode{
    
    private final BlendModeType blendMode = BlendMode.BlendModeType.Normal;

    @Override
    public float[] blend(float[] p1, float[] p2) {
        return p1;
    }

    @Override
    public String getName() {
        return blendMode.toString();
    }

    @Override
    public BlendModeType getBlendModeType() {
        return blendMode;
    }
    
}
