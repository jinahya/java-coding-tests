package com.github.jinahya.misc;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class Mathematics {

    // ------------------------------------------------------------------------------------------------------------- sum
    public static int sumOfAllNaturalNumbersBetweenOneAnd(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n(" + n + ") <= 0");
        }
        throw new UnsupportedOperationException("not implemented yet"); // TODO: implement!
    }

    public static long sumOfAllNaturalNumbersBetweenOneAndLong(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n(" + n + ") <= 0");
        }
        throw new UnsupportedOperationException("not implemented yet"); // TODO: implement!
    }

    // ------------------------------------------------------------------------------------------------------------- avg
    public static Optional<Double> avg(final @NotNull Iterable<@NotNull ? extends Number> elements) {
        Objects.requireNonNull(elements, "elements is null");
        throw new UnsupportedOperationException("not implemented yet"); // TODO: implement!
    }

    public static Optional<Double> avg(final @NotNull Stream<? extends Number> elements) {
        Objects.requireNonNull(elements, "elements is null");
        throw new UnsupportedOperationException("not implemented yet"); // TODO: implement!
    }

    public static Optional<Double> avg(final @NotNull IntStream elements) {
        Objects.requireNonNull(elements, "elements is null");
        throw new UnsupportedOperationException("not implemented yet"); // TODO: implement!
    }

    public static Optional<Double> avg(final @NotNull LongStream elements) {
        Objects.requireNonNull(elements, "elements is null");
        throw new UnsupportedOperationException("not implemented yet"); // TODO: implement!
    }

    // -----------------------------------------------------------------------------------------------------------------
    private Mathematics() {
        throw new AssertionError("instantiation is not allowed");
    }
}
