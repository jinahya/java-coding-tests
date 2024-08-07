package com.github.jinahya.algorithms;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * A class implements {@link InsertionSort} interface.
 */
class InsertionSortImpl
        implements InsertionSort {

    private static void swap(final Object a, final int i, final int j) {
        final Object v = Array.get(a, i);
        Array.set(a, i, Array.get(a, j));
        Array.set(a, j, v);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public void sort(final int[] array, final int fromIndex, final int toIndex) {
        Objects.requireNonNull(array, "array is null");
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") is greater than toIndex(" + toIndex + ")");
        }
        if (fromIndex < 0) {
            throw new ArrayIndexOutOfBoundsException("fromIndex(" + fromIndex + ") is less than or equal to 0");
        }
        if (toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException(
                    "toIndex(" + toIndex + ") is greater than array.length(" + array.length + ")"
            );
        }
        int temp;
        for (int i = 0; i < array.length; i++) {
            for (int j = i - 1; j > 0 && array[j - 1] > array[j]; j--) {
                swap(array, j - 1, j);
            }
        }
    }

    @Override
    public void sort(final long[] array, final int fromIndex, final int toIndex) {
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
