package com.github.jinahya.algorithms;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * A class implements {@link InsertionSort} interface.
 */
class InsertionSortImpl
        implements InsertionSort {

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public void sort(final int[] array, final int fromIndex, final int toIndex) {
        // TODO: implement
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public <T> void sort(final List<T> list, final Comparator<? super T> comparator) {
        Objects.requireNonNull(list, "list is null");
        Objects.requireNonNull(comparator, "comparator is null");
        // TODO: implement
    }

    @Override
    public <T extends Comparable<? super T>> void sort(final List<T> list) {
        Objects.requireNonNull(list, "list is null");
        // TODO: implement
    }
}
