package adele.image;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

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

    public Image(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
        frames = new ArrayList<>();
    }
    
    
}
