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
//    static Stream<Arguments> getArrayArgumentsStreamForTestingStability() {
//        return Stream.<Arguments>of(
//                applyFromIndexAndToIndexFromArray(
//                        () -> {
//                            return IntStream.range(0, 8)
//                                    .mapToObj(i -> new User(i, "fixed", 0))
//                                    .toArray(User[]::new);
//                        },
//                        a -> fi -> ti -> {
//                            final var comparator = User.COMPARING_NAME;
//                            final Consumer<? super InsertionSort> invoker = i -> {
//                                assertThatCode(() -> i.sort(a, fi, ti, comparator))
//                                        .doesNotThrowAnyException();
//                            };
//                            final Supplier<? extends Void> verifier = () -> {
//                                final var range = Arrays.copyOfRange(a, fi, ti);
//                                final var idStream = Arrays.stream(range).mapToInt(v -> v == null ? 0 : v.id);
//                                assertThat(idStream).isSorted();
//                                return null;
//                            };
//                            return Arguments.of(a, fi, ti, comparator, invoker, verifier);
//                        }
//                ),
//                applyFromIndexAndToIndexFromArray(
//                        () -> {
//                            return IntStream.range(0, 8)
//                                    .mapToObj(i -> new User(i, "fixed", i))
//                                    .toArray(User[]::new);
//                        },
//                        a -> fi -> ti -> {
//                            final var comparator = User.COMPARING_NAME.thenComparing(User.COMPARING_AGE);
//                            final Consumer<? super InsertionSort> invoker = i -> {
//                                assertThatCode(() -> i.sort(a, fi, ti, comparator))
//                                        .doesNotThrowAnyException();
//                            };
//                            final Supplier<Void> verifier = () -> {
//                                final var range = Arrays.copyOfRange(a, fi, ti);
//                                final var idStream = Arrays.stream(range).mapToInt(v -> v == null ? 0 : v.id);
//                                assertThat(idStream).isSorted();
//                                return null;
//                            };
//                            return Arguments.of(a, fi, ti, comparator, invoker, verifier);
//                        }
//                ),
//                applyFromIndexAndToIndexFromArray(
//                        () -> {
//                            return IntStream.range(0, 8)
//                                    .mapToObj(i -> new User(i, "fixed", i))
//                                    .toArray(User[]::new);
//                        },
//                        a -> fi -> ti -> {
//                            final var comparator = User.COMPARING_AGE.thenComparing(User.COMPARING_NAME);
//                            final Consumer<? super InsertionSort> invoker = i -> {
//                                assertThatCode(() -> i.sort(a, fi, ti, comparator))
//                                        .doesNotThrowAnyException();
//                            };
//                            final Supplier<Void> verifier = () -> {
//                                final var range = Arrays.copyOfRange(a, fi, ti);
//                                final var idStream = Arrays.stream(range).mapToInt(v -> v == null ? 0 : v.id);
//                                assertThat(idStream).isSorted();
//                                return null;
//                            };
//                            return Arguments.of(a, fi, ti, comparator, invoker, verifier);
//                        }
//                )
//        );
//    }
//
//    private static List<User> list(final User[] array) {
//        return Arrays.stream(array)
//                .collect(
//                        Collectors.toCollection(
//                                () -> ThreadLocalRandom.current().nextBoolean()
//                                        ? new ArrayList<>() : new LinkedList<>()
//                        )
//                );
//    }
//
//    static Stream<List<User>> getListStream(final Comparator<? super User> comparator) {
//        return getArrayStream(comparator)
//                .map(_UserTestUtils::list);
//    }
//
//    static Stream<Arguments> getListAndComparatorArgumentsStream(final Comparator<? super User> comparator) {
//        return getListStream(comparator)
//                .map(l -> {
//                    return Arguments.of(l, comparator);
//                });
//    }
//
//    static Stream<Arguments> getListAndOthersArgumentsStreamForTestingStability() {
//        return getArrayArgumentsStreamForTestingStability().map(a -> {
//            // org.junit.jupiter.params.aggregator.DefaultArgumentsAccessor
//            final var got = a.get();
//            final var array = (User[]) got[0];
//            final var fromIndex = (int) got[1];
//            final var toIndex = (int) got[2];
//            @SuppressWarnings({"unchecked"})
//            final var comparator = (Comparator<? super User>) got[3];
//            final var list = list(array).subList(fromIndex, toIndex);
//            final Consumer<? super InsertionSort> invoker = i -> i.sort(list, comparator);
//            final Supplier<? extends Void> verifier = () -> {
//                assertThat(list).isSortedAccordingTo(comparator);
//                return null;
//            };
//            return Arguments.of(list, comparator, invoker, verifier);
//        });
//    }

//    static Stream<User[]> getArrayStreamCatastrophic(final Comparator<? super User> comparator) {
//        Objects.requireNonNull(comparator, "comparator is null");
//        return Stream.of(
//                new User[]{
//                },
//                new User[]{
//                        new User(0, "John", 0, comparator)
//                },
//                new User[]{
//                        new User(0, "John", 0, comparator),
//                        new User(1, "Jane", 1, comparator)
//                },
//                IntStream.rangeClosed(2, 9)
//                        .mapToObj(i -> new User(i, Integer.toString(i), i, comparator))
//                        .toArray(User[]::new)
//        );
//    }

    // -----------------------------------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------
    private _UserTestUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
