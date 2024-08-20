package com.github.jinahya.algorithms;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * A, demonstration-purpose only, class implements the {@link InsertionSort} interface.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class InsertionSortDemo
        implements InsertionSort {

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public void sort(final int[] array, final int fromIndex, final int toIndex) {
        Arrays.sort(array, fromIndex, toIndex); // ---------------------------------------------------------- ALGORITHM?
    }

    @Override
    public void sort(final long[] array, final int fromIndex, final int toIndex) {
        Arrays.sort(array, fromIndex, toIndex); // ---------------------------------------------------------- ALGORITHM?
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public <T> void sort(final List<T> list, final Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "comparator is null");
        list.sort(comparator); // --------------------------------------------------------------------------- ALGORITHM?
    }

    @Override
    public <T extends Comparable<? super T>> void sort(final List<T> list) {
        sort(list, Comparator.naturalOrder());
    }
}
