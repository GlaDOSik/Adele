package adele.core;

import java.util.ArrayList;
import java.util.List;

public class AFrame {

    private final List<ALayer> layers;
    private AImage imageReference;
    private int timeInMilliS = 0;

    public AFrame(AImage imageReference) {
        layers = new ArrayList<>();
        this.imageReference = imageReference;
    }

    public void flattenFrame(int[] inputPixelArray) {
        ALayer[] ls = getLayers();
        //int colorMix;
        float r;
        float g;
        float b;
        for (int i = 0; i < imageReference.getWidth() * imageReference.getHeight(); i++) {
            //colorMix = AImage.colorToAformat(Color.BLUEVIOLET);
            //TODO barvu alfy staticky zpřístupnit z Userfile
            r = 40.0f;
            g = 47.0f;
            b = 61.0f;
            for (int j = 0; j < ls.length; j++) {
                if (ls[j].isVisible()) {
                    int pixelI = ls[j].getPixel(i);
                    float pixelAlpha = AImage.getAfromAformatF(pixelI);
                    r = ((1.0f - pixelAlpha) * r) + (pixelAlpha * AImage.getRfromAformat(pixelI));
                    g = ((1.0f - pixelAlpha) * g) + (pixelAlpha * AImage.getGfromAformat(pixelI));
                    b = ((1.0f - pixelAlpha) * b) + (pixelAlpha * AImage.getBfromAformat(pixelI));
                }
            }
            //v colorMix je barva pixelu pro uložení do vrstvy
            inputPixelArray[i] = AImage.colorToAformat((int) r, (int) g, (int) b, 255);
        }
    }

    //přetížená ? funkce zpracuje a vrátí pouze změněné pixely (ty je třeba detekovat i pro historii změn)
    public void flattenMarkedPixels(int[] markedPixelsIndices) {

    }

    public void makeLayer(String name, boolean isShared, int AColorFormat) {
        ALayer newLayer = new ALayer(name, isShared, imageReference, AColorFormat);
        layers.add(newLayer);
        if (isShared) {
            imageReference.linkSharedLayer(newLayer);
        }
        imageReference.buildCache();
    }

    public void makeLayer(String name, boolean isShared, int index, int AColorFormat) {
        ALayer newLayer = new ALayer(name, isShared, imageReference, AColorFormat);
        if (index > layers.size()) {
            index = layers.size();
        }
        layers.add(index, newLayer);
        if (isShared) {
            imageReference.linkSharedLayer(newLayer);
        }
        imageReference.buildCache();
    }

    /**
     * Safe way of deleting layer - shared is unlinked first
     */
    public void removeLayer(int index) {
        if (layers.size() != 0) {
            if (index >= layers.size()) {
                index = layers.size() - 1;
            }
            ALayer layerToRemove = layers.get(index);
            if (layerToRemove.isShared()) {
                imageReference.unlinkSharedLayer(layerToRemove);
            }
            layerToRemove.prepareToGC();
            layers.remove(index);
        }
        imageReference.buildCache();
    }

    /**
     * Delete all layers - shared layers aren't unlinked
     * If this is called in last frame in image, we can
     * prepare to GC all shared layers too
     */
    public void removeAllLayers(boolean isThisTheLastFrame) {
        for (int i = 0; i < layers.size(); i++) {
            if (isThisTheLastFrame) {
                layers.get(i).prepareToGC();
            }
            else if (!layers.get(i).isShared()) {
                layers.get(i).prepareToGC();
            }
        }
        layers.clear();
    }

    /**
     * Link already created layer (mostly shared)
     *
     * @param layer
     */
    public void linkLayer(ALayer layer) {
        layers.add(layer);
    }

    /**
     * Unlink specified layer - it's not a safe way to delete layer!
     *
     * @param layer
     */
    public void unlinkLayer(ALayer layer) {
        layers.remove(layer);
    }

    public ALayer getLayer(int index) {
        return layers.get(index);
    }

    public ALayer[] getLayers() {
        ALayer[] ls = new ALayer[layers.size()];
        //array is inverted
        for (int i = 0; i < layers.size(); i++) {
            ls[i] = layers.get(layers.size() - 1 - i);
        }
        return ls;
    }

    public void moveLayer(ALayer layer, int index) {
        layers.remove(layer);
        layers.add(index, layer);
    }

    public int getNumberOfLayers() {
        return layers.size();
    }

    public void prepareToGC() {
        imageReference = null;
    }
}
