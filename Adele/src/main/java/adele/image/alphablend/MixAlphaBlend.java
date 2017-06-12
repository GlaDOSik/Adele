/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.image.alphablend;

/**
 *
 * @author ludek
 */
public class MixAlphaBlend implements AlphaBlend {
    
    private AlphaBlendType alphaBlendType = AlphaBlendType.AlphaMix;

    @Override
    public float[] blend(float[] p1, float[] p2) {
        p1[1] = ((1.0f - p1[0]) * p2[1]) + (p1[0] * p1[1]);
        p1[2] = ((1.0f - p1[0]) * p2[2]) + (p1[0] * p1[2]);
        p1[3] = ((1.0f - p1[0]) * p2[3]) + (p1[0] * p1[3]);
        return p1;
    }

    @Override
    public String getName() {
        return alphaBlendType.toString();
    }

    @Override
    public AlphaBlendType getBlendModeType() {
        return alphaBlendType;
    }
    
}
