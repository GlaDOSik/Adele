package adele.image;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@ToString
@EqualsAndHashCode
public class Image {
    
    @Getter
    private final int width;    
    @Getter
    private final int height;    
    @Setter @Getter
    private String name;
    @Getter
    private final List<Frame> frames;
    @Setter
    private int frameCursor = 0;

    public Image(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
        frames = new ArrayList<>();
    }
    
    /**
     * Add a new frame at the end of frame stack.
     */
    public void addNewFrame() {
        frames.add(new Frame(this));
    }
    
    /**
     * Add a new frame using cursor.
     */
    public void addNewFrameAtCursor() {
        frames.add(frameCursor, new Frame(this));
    }
    
    /**
     * Get a frame using cursor.
     */
    public Frame getFrame() {
        return frames.get(frameCursor);
    }

    /**
     * Delete a frame using cursor.
     */
    public void deleteFrame() {
        frames.remove(frameCursor);
    }

    
}
