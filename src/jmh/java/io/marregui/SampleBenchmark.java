package io.marregui;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class SampleBenchmark {
    @Benchmark
    public void fibClassic(Blackhole bh) {
        bh.consume(fibClassic(30));
    }

    @Benchmark
    public void fibTailRec(Blackhole bh) {
        bh.consume(tailRecFib(30));
    }

    @Benchmark
    public void fibLoop(Blackhole bh) {
        bh.consume(fibStdLoop(30));
    }


    private static long fibClassic(int n) {
        if (n < 2) {
            return n;
        }
        return fibClassic(n - 1) + fibClassic(n - 2);
    }

    private static long tailRecFib(int n) {
        return tailRecFib(n, 0, 1);
    }

    private static int tailRecFib(int n, int a, int b) {
        if (n == 0) {
            return a;
        }
        if (n == 1) {
            return b;
        }
        return tailRecFib(n - 1, b, a + b);
    }

    private static long fibStdLoop(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        long fi = 1;
        long fj = 1;
        long tmp;
        for (int i = 2; i <= n; i++) {
            tmp = fi + fj;
            fi = fj;
            fj = tmp;

        }
        return fi;
    }
}