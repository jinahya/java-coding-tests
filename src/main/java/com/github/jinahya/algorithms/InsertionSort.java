package com.github.jinahya.algorithms;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An interface for insertion-sort.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @implSpec implementation classes should have a no-args constructor.
 * @see <a href="https://en.wikipedia.org/wiki/Insertion_sort">Insertion sort</a> (wikipedia)
 * @see <a href="https://en.wikipedia.org/wiki/Sorting_algorithm#Stability">Stability</a> (wikipedia / Sorting
 * algorithm)
 */
interface InsertionSort {

    /**
     * Sorts the specified range of the array into ascending order.
     *
     * @param array     the array to be sorted.
     * @param fromIndex the index of the first element, inclusive, to be sorted.
     * @param toIndex   the index of the last element, exclusive, to be sorted.
     * @throws NullPointerException           if {@code array} is {@code null}.
     * @throws IllegalArgumentException       if {@code fromIndex} is greater than {@code toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex} is less than {@code 0} or {@code toIndex} is greater
     *                                        than {@code array.length}.
     */
    void sort(@NotNull int[] array, @PositiveOrZero int fromIndex, @PositiveOrZero int toIndex);

    /**
     * Sorts the specified range of the array into ascending order.
     *
     * @param array     the array to be sorted.
     * @param fromIndex the index of the first element, inclusive, to be sorted.
     * @param toIndex   the index of the last element, exclusive, to be sorted.
     * @throws NullPointerException           if {@code array} is {@code null}.
     * @throws IllegalArgumentException       if {@code fromIndex} is greater than {@code toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex} is less than {@code 0} or {@code toIndex} is greater
     *                                        than {@code array.length}.
     */
    void sort(@NotNull long[] array, @PositiveOrZero int fromIndex, @PositiveOrZero int toIndex);

    /**
     * Sorts the specified array into ascending numerical order.
     *
     * @param array the array to be sorted.
     * @throws NullPointerException if {@code array} is {@code null}.
     * @implSpec default implementation invokes {@link #sort(int[], int, int) sort(array, fromIndex, toIndex)} method
     * with {@code array}, {@code 0}, and {@code array.length}.
     */
    default void sort(final int[] array) {
        if (ThreadLocalRandom.current().nextBoolean() && array == null) {
            throw new NullPointerException("array is null");
        }
        sort(array, 0, array.length);
    }

    /**
     * Sorts the specified range of the specified array of objects according to the order induced by the specified
     * comparator.
     *
     * @param array      the array to be sorted.
     * @param fromIndex  the index of the first element (inclusive) to be sorted.
     * @param toIndex    the index of the last element (exclusive) to be sorted.
     * @param comparator the comparator to determine the order of the array.
     * @param <T>        element type parameter
     * @throws NullPointerException           if either {@code array} or {@code comparator} is {@code null}.
     * @throws IllegalArgumentException       if {@code fromIndex} is greater than {@code toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex} is less than {@code 0} or {@code toIndex} is greater
     *                                        than {@code array.length}.
     */
    default <T> void sort(final T[] array, final int fromIndex, final int toIndex,
                          final Comparator<? super T> comparator) {
        Objects.requireNonNull(array, "array is null");
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
        }
        if (fromIndex < 0) {
            throw new ArrayIndexOutOfBoundsException("fromIndex(" + fromIndex + ") is negative");
        }
        if (toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException(
                    "toIndex(" + toIndex + ") is greater than array.length(" + array.length + ")"
            );
        }
        if (comparator == null) {
            throw new NullPointerException("comparator is null");
        }
        for (var i = fromIndex + 1; i < toIndex; i++) {
            for (var j = i; j > fromIndex; j--) {
                if (comparator.compare(array[j - 1], array[j]) > 0) {
                    final var temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    /**
     * Sorts the specified array of objects according to the order induced by the specified comparator.
     *
     * @param array      the array to be sorted.
     * @param comparator the comparator to determine the order of the array.
     * @param <T>        element type parameter
     * @throws NullPointerException if either {@code array} or {@code comparator} is {@code null}.
     * @implSpec default implementation invokes
     * {@link #sort(Object[], int, int, Comparator) sort(array, fromIndex, toIndex, comparator)} method with
     * {@code array}, {@code 0}, {@code array.length}, and {@code comparator}.
     * @implSpec implementation should be <a
     * href="https://en.wikipedia.org/wiki/Sorting_algorithm#Stability">stable</a>
     */
    default <T> void sort(final T[] array, final Comparator<? super T> comparator) {
        Objects.requireNonNull(array, "array is null");
        sort(array, 0, array.length, comparator);
    }

    /**
     * Sorts the specified array of objects in {@link Comparator#naturalOrder() natural order}.
     *
     * @param array     the list whose elements are sorted.
     * @param fromIndex the first index of the {@code list}; inclusive.
     * @param toIndex   the last index of the {@code list}; exclusive.
     * @param <T>       element type parameter
     * @throws NullPointerException           if {@code array} is {@code null}.
     * @throws IllegalArgumentException       if {@code fromIndex} is greater than {@code toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex} is less than {@code 0} or {@code toIndex} is greater
     *                                        than {@code array.length}.
     * @implSpec default implementation invokes
     * {@link #sort(Object[], int, int, Comparator) sort(array, fromIndex, toIndex, comparator)} with {@code array},
     * {@code fromIndex}, {@code toIndex}, and a
     * {@link Comparator#naturalOrder() comparator compares objects in natural order}.
     */
    default <T extends Comparable<? super T>> void sort(final T[] array, final int fromIndex, final int toIndex) {
        sort(array, fromIndex, toIndex, Comparator.naturalOrder());
    }

    /**
     * Sorts the specified array of objects in natual order.
     *
     * @param array the list whose elements are sorted.
     * @param <T>   element type parameter
     * @throws NullPointerException when {@code array} is {@code null}.
     * @implSpec implementation should be <a
     * href="https://en.wikipedia.org/wiki/Sorting_algorithm#Stability">stable</a>
     */
    default <T extends Comparable<? super T>> void sort(final T[] array) {
        Objects.requireNonNull(array, "list is null");
        sort(array, 0, array.length);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Sorts the specified list according to the order induced by the specified comparator.
     *
     * @param list the list to be sorted.
     * @param <T>  the class of the objects in the list.
     * @throws NullPointerException if either {@code list} or {@code comparator} is {@code null}.
     * @implSpec implementation should be <a
     * href="https://en.wikipedia.org/wiki/Sorting_algorithm#Stability">stable</a>
     * @see java.util.Collections#swap(List, int, int)
     */
    <T> void sort(List<T> list, Comparator<? super T> comparator);

    /**
     * Sorts the specified list into ascending order, according to the natural ordering of its elements.
     *
     * @param list the list to be sorted.
     * @param <T>  the class of the objects in the list.
     * @throws NullPointerException if {@code list} is {@code null}.
     * @implSpec implementation should be <a
     * href="https://en.wikipedia.org/wiki/Sorting_algorithm#Stability">stable</a>
     * @see Comparator#naturalOrder()
     */
    <T extends Comparable<? super T>> void sort(List<T> list);
}
