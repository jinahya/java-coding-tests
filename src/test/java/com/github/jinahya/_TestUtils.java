package com.github.jinahya;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Arrays;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

@Slf4j
public final class _TestUtils {

    public static int getRandomFromIndexFromArray(final Object array) {
        Objects.requireNonNull(array, "array is null");
        if (!Arrays.isArray(array)) {
            throw new IllegalArgumentException("not an array");
        }
        final var arrayLength = Array.getLength(array);
        final var fromIndex = ThreadLocalRandom.current().nextInt(0, arrayLength + 1);
        assert fromIndex >= 0;
        assert fromIndex <= arrayLength;
        return fromIndex;
    }

    public static <R> R applyRandomFromIndexFromArray(final Object array, final IntFunction<? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return function.apply(getRandomFromIndexFromArray(array));
    }

    public static int getRandomToIndexFromArray(final Object array, final int fromIndex) {
        Objects.requireNonNull(array, "array is null");
        if (fromIndex < 0) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") is negative");
        }
        final var arrayLength = Array.getLength(array);
        if (fromIndex > arrayLength) {
            throw new IllegalArgumentException(
                    "fromIndex(" + fromIndex + ") is greater than array.arrayLength(" + arrayLength + ")");
        }
        final var toIndex = ThreadLocalRandom.current().nextInt(fromIndex, arrayLength + 1);
        assert toIndex <= arrayLength;
        assert toIndex >= fromIndex;
        return toIndex;
    }

    public static <R> R applyRandomToIndexFromArray(final Object array, final int fromIndex,
                                                    final IntFunction<? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return function.apply(getRandomToIndexFromArray(array, fromIndex));
    }

    public static <R> R applyFromIndexAndToIndexFromArray(
            final Object array,
            final IntFunction<? extends IntFunction<? extends R>> function) {
        return applyRandomFromIndexFromArray(
                array,
                fi -> applyRandomToIndexFromArray(array, fi, ti -> function.apply(fi).apply(ti))
        );
    }

    public static <T, R> R applyFromIndexAndToIndexFromArray(
            final Supplier<? extends T[]> supplier,
            final Function<? super T[], IntFunction<? extends IntFunction<? extends R>>> function) {
        Objects.requireNonNull(supplier, "supplier is null");
        Objects.requireNonNull(function, "function is null");
        final var array = supplier.get();
        return applyFromIndexAndToIndexFromArray(
                array,
                fi -> ti -> function.apply(array).apply(fi).apply(ti)
        );
    }

    // -----------------------------------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------
    private _TestUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
