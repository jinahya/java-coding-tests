package com.github.jinahya.misc;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class Mathematics {

    // ------------------------------------------------------------------------------------------------------------- avg
    public static double avg(final @NotNull Iterable<@NotNull ? extends Number> elements) {
        Objects.requireNonNull(elements, "elements is null");
        throw new UnsupportedOperationException("not implemented yet");
        // TODO: implement!
    }

    public static double avg(final @NotNull Stream<? extends Number> elements) {
        Objects.requireNonNull(elements, "elements is null");
        throw new UnsupportedOperationException("not implemented yet");
        // TODO: implement!
    }

    public static double avg(final @NotNull LongStream elements) {
        Objects.requireNonNull(elements, "elements is null");
        throw new UnsupportedOperationException("not implemented yet");
        // TODO: implement!
    }

    public static double avg(final @NotNull IntStream elements) {
        Objects.requireNonNull(elements, "elements is null");
        throw new UnsupportedOperationException("not implemented yet");
        // TODO: implement!
    }

    // -----------------------------------------------------------------------------------------------------------------
    private Mathematics() {
        throw new AssertionError("instantiation is not allowed");
    }
}
