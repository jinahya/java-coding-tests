package com.github.jinahya.algorithms;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * A, demonstration-purpose only, class implements {@link InsertionSort} interface.
 *
 * @author Jin Kwon &lt;jin.kwon_at_meshkorea.net&gt;
 */
class InsertionSortDemo
        implements InsertionSort {

    private static int count(final Object sortee) {
        Objects.requireNonNull(sortee, "sortee is null");
        if (sortee.getClass().isArray()) {
            return Array.getLength(sortee);
        }
        if (sortee instanceof Collection) {
            return ((Collection<?>) sortee).size();
        }
        throw new IllegalArgumentException("invalid sortee type: " + sortee);
    }

    private static void check(final Object sortee, final int fromIndex, final int toIndex) {
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
        }
        if (fromIndex < 0) {
            throw new ArrayIndexOutOfBoundsException("fromIndex(" + fromIndex + ") < 0");
        }
        final var count = count(sortee);
        if (toIndex > count) {
            throw new ArrayIndexOutOfBoundsException("toIndex(" + toIndex + ") is > sortee.count(" + count + ")");
        }
    }

    private static void check(final Object sortee, final int fromIndex, final int toIndex, final Object comparator) {
        check(sortee, fromIndex, toIndex);
        count(comparator);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public void sort(final int[] array, final int fromIndex, final int toIndex) {
        check(array, fromIndex, toIndex);
        Arrays.sort(array, fromIndex, toIndex); // ---------------------------------------------------------- ALGORITHM?
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public <T> void sort(final List<T> list, final Comparator<? super T> comparator) {
        Objects.requireNonNull(list, "list is null");
        Objects.requireNonNull(comparator, "comparator is null");
        list.sort(comparator); // --------------------------------------------------------------------------- ALGORITHM?
    }

    @Override
    public <T extends Comparable<? super T>> void sort(final List<T> list) {
        sort(list, Comparator.naturalOrder());
    }
}
