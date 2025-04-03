package com.example;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@BenchmarkMode(Mode.AverageTime) // Вимірює середній час виконання
@OutputTimeUnit(TimeUnit.MILLISECONDS) // Одиниця вимірювання мілісекунди
@State(Scope.Thread) // Окрема копія для кожного потоку
public class Main {

    private static final int kilkict = 10000000;
    private static final int min = 1;
    private static final int max = 101;
    private static final List<Integer> numbers = new Random().ints(kilkict, min, max).boxed().collect(Collectors.toList());

    @Benchmark
    public long sumStream() {

        return numbers.stream().mapToLong(Integer::longValue).sum();
    }

    @Benchmark
    public long sumParallel() {

        return numbers.parallelStream().mapToLong(Integer::longValue).sum();
    }


    @Benchmark
    public double averageSequential() {

        return numbers.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
    }

    @Benchmark
    public double averageParallel() {
        return numbers.parallelStream().mapToDouble(Integer::doubleValue).average().orElse(0);
    }

    @Benchmark
    public double standartDeviationSequential() {
        double average = averageSequential();
        double variance = numbers.stream().mapToDouble(n -> Math.pow(n - average, 2)).average().orElse(0);
        return Math.sqrt(variance);
    }


    @Benchmark
    public double standartDeviationParallel() {
        double average = averageParallel();
        double variance = numbers.parallelStream().mapToDouble(n -> Math.pow(n - average, 2)).average().orElse(0);
        return Math.sqrt(variance);
    }

    @Benchmark
    public List<Integer> squaresStream() {
        return numbers.stream().map(n -> n * 2).collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> squaresParallel() {
        return numbers.parallelStream().map(n -> n * 2).collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> couplesvalueStream() {
        return numbers.stream().filter(n -> n % 2 == 0 && n % 3 == 0).collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> couplesvalueParallel() {
        return numbers.parallelStream().filter(n -> n % 2 == 0 && n % 3 == 0).collect(Collectors.toList());
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}