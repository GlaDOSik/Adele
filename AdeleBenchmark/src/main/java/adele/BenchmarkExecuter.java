package adele;

import adele.benchmark.ColorUtilsBenchmarks;
import adele.benchmark.FrameBenchmarks;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.WarmupMode;

public class BenchmarkExecuter {

    public static void main(String[] args) throws RunnerException {
        
        Options frameBenchOpts = new OptionsBuilder().include(FrameBenchmarks.class.getSimpleName())
                .forks(1)                
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MICROSECONDS)
                .warmupIterations(7)
                .measurementIterations(10)
                .build();
        Options colorUtilsfOpts = new OptionsBuilder().include(ColorUtilsBenchmarks.class.getSimpleName())
                .forks(1)                
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MICROSECONDS)
                .warmupIterations(5)
                .measurementIterations(10)
                .build();
        
        Runner test1 = new Runner(frameBenchOpts);
        Runner test2 = new Runner(colorUtilsfOpts);
        
        test1.run();
        test2.run();
    }

}
