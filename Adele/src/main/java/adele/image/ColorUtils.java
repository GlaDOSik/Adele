
package adele.image;

import javafx.scene.paint.Color;


public class ColorUtils {
    
    /**
     * Pack 4 color component RGBA to internal pixel format (32bit ARGB). Do
     * NOT use input values greater than 255 or smaller than 0. No clipping is
     * provided. Conversion is lossless.
     *
     * @param R red color component (0 - 255)
     * @param G green color component (0 - 255)
     * @param B blue color component (0 - 255)
     * @param A alpha color component (0 - 255)
     * @return
     */
    public static int packARGB(int R, int G, int B, int A) {
        return ((A << 24) + (R << 16) + (G << 8) + (B));
    }

    /**
     * Pack 4 color component RGBA to internal pixel format (32bit ARGB).
     *
     * @param R red color component (0.0f - 255.0f)
     * @param G green color component (0.0f - 255.0f)
     * @param B blue color component (0.0f - 255.0f)
     * @param A alpha color component (0.0f - 1.0f)
     * @return
     */
    public static int packARGB(float R, float G, float B, float A) {
        int a, r, g, b;
        a = (int) (clamp(0.0f, 1.0f, A) * 255.0f + 0.5f);
        r = (int) (clamp(0.0f, 255.0f, R) + 0.5f);
        g = (int) (clamp(0.0f, 255.0f, G) + 0.5f);
        b = (int) (clamp(0.0f, 255.0f, B) + 0.5f);
        
        return ((a << 24) + (r << 16) + (g << 8) + (b));
    }

    /**
     * Pack 4 color component RGBA to internal pixel format (32bit ARGB).
     *
     * @param R red color component (0.0 - 255.0)
     * @param G green color component (0.0 - 255.0)
     * @param B blue color component (0.0 - 255.0)
     * @param A alpha color component (0.0 - 1.0)
     * @return
     */
    public static int packARGB(double R, double G, double B, double A) {
        int a, r, g, b;
        a = (int) (clamp(0.0, 1.0, A) * 255.0 + 0.5);
        r = (int) (clamp(0.0, 255.0, R) + 0.5);
        g = (int) (clamp(0.0, 255.0, G) + 0.5);
        b = (int) (clamp(0.0, 255.0, B) + 0.5);
        return ((a << 24) + (r << 16) + (g << 8) + (b));
    }

    /**
     * Pack JavaFX Color for convinient reasons to internal pixel format (32bit ARGB).
     * Conversion is lossy.
     * @param color
     * @return 
     */
    public static int packARGB(Color color) {
        int a, r, g, b;
        a = (int) (color.getOpacity() * 255.0  + 0.5);
        r = (int) (color.getRed() * 255.0  + 0.5);
        g = (int) (color.getGreen() * 255.0  + 0.5);
        b = (int) (color.getBlue() * 255.0  + 0.5);
        return ((a << 24) + (r << 16) + (g << 8) + (b));
    }

    public static int unpackR(int argb) {
        return (argb >> 16) & 0xff;
    }

    public static int unpackG(int argb) {
        return (argb >> 8) & 0xff;
    }

    public static int unpackB(int argb) {
        return argb & 0xff;
    }

    public static int unpackA(int argb) {
        return (argb >> 24) & 0xff;
    }

    public static float unpackAtoFloat(int argb) {
        return ((argb >> 24) & 0xff) / 255.0f;
    }

    /**
     * Unpack components from internal argb format
     * 
     * @param argb internal argb format
     * @return 0R 1G 2B
     */
    public static int[] unpackRGB(int argb) {
        int[] output = new int[3];
        output[0] = (argb >> 16) & 0xff;
        output[1] = (argb >> 8) & 0xff;
        output[2] = argb & 0xff;
        return output;
    }

    /**
     * Unpack components from internal argb format
     * 
     * @param argb internal argb format
     * @return 0A 1R 2G 3B
     */
    public static int[] unpackARGB(int argb) {
        int[] output = new int[4];
        output[0] = (argb >> 24) & 0xff;
        output[1] = (argb >> 16) & 0xff;
        output[2] = (argb >> 8) & 0xff;
        output[3] = argb & 0xff;
        return output;
    }
    
    /**
     * Unpack internal pixel format (32bit ARGB) to provided float array.
     * It's necessarily to provide array with lenght 4, no length check is done.
     * 
     * @param output output float array with 4 positions
     * @param argb internal pixel format
     * @return 0A (0.0 - 1.0) 1R 2R 3B (0.0 - 255.0)
     */
    public static float[] unpackARGBtoFloat(float[] output, int argb) {
        output[0] = ((argb >> 24) & 0xff) / 255.f;
        output[1] = (float) ((argb >> 16) & 0xff);
        output[2] = (float) ((argb >> 8) & 0xff);
        output[3] = (float) (argb & 0xff);
        return output;
    }
    
    public static float clamp(float min, float max, float value) {
        return Math.min(max, Math.max(min, value));
    }
    
    public static double clamp(double min, double max, double value) {
        return Math.min(max, Math.max(min, value));
    }
    
}
