package com.github.jinahya.algorithms;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.provider.Arguments;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
final class _UserTestUtils {

    private static _User newRandomInstance() {
        return new _User(
                ThreadLocalRandom.current().nextInt() & Integer.MAX_VALUE,
                Long.toString(ThreadLocalRandom.current().nextLong()),
                ThreadLocalRandom.current().nextInt() & 127
        );
    }

    static <T, R> R applyIndicesFromArray(final T[] array,
                                          final IntFunction<? extends IntFunction<? extends R>> function) {
        Objects.requireNonNull(array, "array is null");
        Objects.requireNonNull(function, "function is null");
        final var length = Array.getLength(array);
        final var fromIndex = ThreadLocalRandom.current().nextInt(0, length + 1);
        final var toIndex = ThreadLocalRandom.current().nextInt(fromIndex, length + 1);
        return function.apply(fromIndex).apply(toIndex);
    }

    private static <T, R> R applyIndicesFromArray(
            final Supplier<? extends T[]> supplier,
            final Function<? super T[], IntFunction<? extends IntFunction<? extends R>>> function) {
        Objects.requireNonNull(supplier, "supplier is null");
        Objects.requireNonNull(function, "function is null");
        final var array = supplier.get();
        return applyIndicesFromArray(
                array,
                fi -> ti -> function.apply(array).apply(fi).apply(ti)
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    static Stream<Comparator<_User>> getComparatorStream() {
        return Stream.of(
                _User.NOT_COMPARING,
                _User.COMPARING_ID,
                _User.COMPARING_NAME,
                _User.COMPARING_AGE
        );
    }

    static Stream<_User[]> getArrayStream() {
        return Stream.of(
                new _User[]{
                },
                new _User[]{
                        new _User(0, "Some", 0)
                },
                new _User[]{
                        new _User(1, "Jane", 1),
                        new _User(2, "John", 2)
                },
                IntStream.rangeClosed(2, 9)
                        .mapToObj(i -> newRandomInstance())
                        .toArray(_User[]::new)
        );
    }

    static Stream<Arguments> getArrayAndIndicesArgumentsStream() {
        return getArrayStream()
                .map(a -> applyIndicesFromArray(a, fi -> ti -> Arguments.of(a, fi, ti)));
    }

    static Stream<Arguments> getArrayIndicesAndComparatorArgumentsStream() {
        return getComparatorStream()
                .flatMap(c -> getArrayStream()
                        .map(a -> applyIndicesFromArray(a, fi -> ti -> Arguments.of(a, fi, ti, c))));
    }

    // -----------------------------------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------
    private _UserTestUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
