package adele.benchmark;

import adele.image.Image;
import adele.image.factory.ImageFactory;
import adele.image.factory.NoiseTemplate;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class FrameBenchmarks {
    
    private Image testImage;
    private int[] output;
    
    @Setup
    public void prepare() {
        output = new int[1280*720];
        testImage = ImageFactory.buildFromTemplate(new NoiseTemplate(1280, 720, 20));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void flattenFrameBench() {
        testImage.getFrames().get(0).flattenFrame(output);
    }

}
