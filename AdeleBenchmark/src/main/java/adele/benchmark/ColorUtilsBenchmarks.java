/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.benchmark;

import adele.image.ColorUtils;
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
public class ColorUtilsBenchmarks {
    
    private int[] output;
    
    @Setup
    public void prepare() {
        output = new int[640 * 480];
    }
    
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void packFloatARGB() {
        for (int i = 0; i < output.length; i++) {
            output[i] = ColorUtils.packARGB(123.0f, 85.0, 222.0f, 0.523f);
        }
    }
    
}
