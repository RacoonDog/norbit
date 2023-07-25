package io.github.racoondog.orbit;

import io.github.racoondog.BenchmarkEvent;
import io.github.racoondog.Constants;
import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.listeners.ConsumerListener;
import meteordevelopment.orbit.listeners.IListener;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = Constants.WARMUP_ITERATIONS, time = Constants.WARMUP_TIME)
@Measurement(iterations = Constants.MEASUREMENT_ITERATIONS, time = Constants.MEASUREMENT_TIME)
@Fork(value = Constants.MEASUREMENT_FORKS, warmups = Constants.WARMUP_FORKS)
public class Consumer_Subscribe {
    private EventBus orbit;

    @Setup
    public void setup() {
        orbit = new EventBus();
    }

    @Benchmark
    public void bench() {
        for (int i = 0; i < Constants.ITERATIONS; i++) {
            IListener listener = new ConsumerListener<BenchmarkEvent>(BenchmarkEvent.class, event -> event.blackhole().consume(Integer.bitCount(Integer.parseInt("123"))));
            orbit.subscribe(listener);
            orbit.unsubscribe(listener);
        }
    }
}