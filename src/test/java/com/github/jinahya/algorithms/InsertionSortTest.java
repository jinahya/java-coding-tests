package com.github.jinahya.algorithms;

import com.github.jinahya._TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * An abstract class for testing classes implement {@link InsertionSort} interface.
 *
 * @param <T> implementation type parameter
 * @author Jin Kwon &lt;jin.kwon_at_meshkorea.net&gt;
 */
@Slf4j
abstract class InsertionSortTest<T extends InsertionSort> {

    private static Arguments getIntArrayAndIndicesArguments(final int[] array) {
        Objects.requireNonNull(array, "array is null");
        final var fromIndex = ThreadLocalRandom.current().nextInt(0, array.length + 1);
        final var toIndex = ThreadLocalRandom.current().nextInt(fromIndex, array.length + 1);
        assert fromIndex >= 0;
        assert toIndex <= array.length;
        assert fromIndex <= toIndex;
        return Arguments.of(array, fromIndex, toIndex);
    }

    private static Stream<int[]> getIntArrayStream() {
        return Stream.of(
                new int[0],
                new int[]{ThreadLocalRandom.current().nextInt()},
                IntStream.range(2, 16).map(i -> ThreadLocalRandom.current().nextInt()).toArray()
        );
    }

    private static Stream<Arguments> getIntArrayAndIndicesArgumentsStream() {
        return getIntArrayStream()
                .map(InsertionSortTest::getIntArrayAndIndicesArguments);
    }

    private static Stream<int[]> getIntArrayStreamCatastrophic() {
        return Stream.of(
                IntStream.range(0, 1048576 << 1)
                        .map(i -> ThreadLocalRandom.current().nextInt())
                        .toArray()
        );
    }

    private static Stream<Arguments> getIntArrayAndIndicesArgumentsStreamCatastrophic() {
        return getIntArrayStreamCatastrophic()
                .map(InsertionSortTest::getIntArrayAndIndicesArguments);
    }

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance for testing specified implementation class.
     *
     * @param implementationClass the implementation class to test.
     */
    InsertionSortTest(final Class<T> implementationClass) {
        super();
        this.implementationClass = Objects.requireNonNull(implementationClass, "implementationClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @DisplayName("sort(array, fromIndex, toIndex)")
    @Nested
    class SortIntArrayWithIndicesTest {

        private static Stream<Arguments> getIntArrayAndIndicesArgumentsStream() {
            return InsertionSortTest.getIntArrayAndIndicesArgumentsStream();
        }

        private static Stream<Arguments> getIntArrayAndIndicesArgumentsStreamCatastrophic() {
            return InsertionSortTest.getIntArrayAndIndicesArgumentsStream();
        }

        @DisplayName("should throw NullPointerException when array is null")
        @Test
        void _ThrowNullPointerException_ArrayIsNull() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = implementationInstance();
            final var array = (int[]) null;
            final var fromIndex = 0;
            final var toIndex = 0;
            assert fromIndex >= 0;
            assert array == null || toIndex <= array.length;
            assert fromIndex <= toIndex;
            // ----------------------------------------------------------------------------------------------- when/then
            assertThatThrownBy(() -> instance.sort(array, fromIndex, toIndex))
                    .isInstanceOf(NullPointerException.class);
        }

        @DisplayName("throw ArrayIndexOutOfBoundsException when fromIndex is less than 0")
        @Test
        void _ThrowArrayIndexOutOfBoundsException_FromIndexIsLessThan0() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = implementationInstance();
            final var array = new int[0];
            final var fromIndex = ThreadLocalRandom.current().nextInt() | Integer.MIN_VALUE;
            final var toIndex = array.length;
            assert array != null;
            assert fromIndex < 0;
            assert toIndex <= array.length;
            assert fromIndex <= toIndex;
            // ----------------------------------------------------------------------------------------------- when/then
            assertThatThrownBy(() -> instance.sort(array, fromIndex, toIndex))
                    .isInstanceOf(ArrayIndexOutOfBoundsException.class);
        }

        @DisplayName("should throw ArrayIndexOutOfBoundsException when toIndex is greater than array.length")
        @Test
        void _ThrowArrayIndexOutOfBoundsException_ToIndexIsGreaterThanArrayLength() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = implementationInstance();
            final var array = new int[0];
            final var fromIndex = 0;
            final var toIndex = ThreadLocalRandom.current().nextInt() & 0x7FFFFFFF;
            assert array != null;
            assert fromIndex >= 0;
            assert toIndex > array.length;
            assert fromIndex <= toIndex;
            // ----------------------------------------------------------------------------------------------- when/then
            assertThatThrownBy(() -> instance.sort(array, fromIndex, toIndex))
                    .isInstanceOf(ArrayIndexOutOfBoundsException.class);
        }

        @DisplayName("should throw IllegalArgumentException when fromIndex is greater than toIndex")
        @Test
        void _ThrowIllegalArgumentException_FromIndexIsGreaterThanToIndex() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = implementationInstance();
            final var array = new int[ThreadLocalRandom.current().nextInt(1, 16)];
            final var fromIndex = ThreadLocalRandom.current().nextInt(1, array.length + 1);
            final var toIndex = ThreadLocalRandom.current().nextInt(0, fromIndex);
            assert array != null;
            assert fromIndex >= 0;
            assert toIndex <= array.length;
            assert fromIndex > toIndex;
            // ----------------------------------------------------------------------------------------------- when/then
            assertThatThrownBy(() -> instance.sort(array, fromIndex, toIndex))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("should sort given range")
        @MethodSource({"getIntArrayAndIndicesArgumentsStream"})
        @ParameterizedTest
        void _ShouldSort_Range(final int[] array, final int fromIndex, final int toIndex) {
            assert array != null;
            assert fromIndex >= 0;
            assert toIndex <= array.length;
            assert fromIndex <= toIndex;
            final var copy = Arrays.copyOf(array, array.length);
            // --------------------------------------------------------------------------------------------------- given
            final var instance = implementationInstance();
            // ---------------------------------------------------------------------------------------------------- when
            assertThatCode(() -> instance.sort(array, fromIndex, toIndex))
                    .doesNotThrowAnyException();
            // ---------------------------------------------------------------------------------------------------- then
            for (int i = 0; i < fromIndex; i++) {
                assertThat(array[i])
                        .as("element at %1$d before the fromIndex(%2$d)(exclusive)", i, fromIndex)
                        .isEqualTo(copy[i]);
            }
            for (int i = toIndex; i < array.length; i++) {
                assertThat(array[i])
                        .as("element at %1$d after the toIndex(%2$d)(inclusive)", i, toIndex)
                        .isEqualTo(copy[i]);
            }
            final var range = Arrays.copyOfRange(array, fromIndex, toIndex);
            assertThat(range)
                    .as("elements between %1$d(inclusive) and %2$d(exclusive)", fromIndex, toIndex)
                    .isSorted();
        }

        @DisplayName("should sort given range (catastrophic)")
        @MethodSource({"getIntArrayAndIndicesArgumentsStreamCatastrophic"})
        @ParameterizedTest
        void _ShouldSort_RangeCatastrophic(final int[] array, final int fromIndex, final int toIndex) {
            assert array != null;
            assert fromIndex >= 0;
            assert toIndex <= array.length;
            assert fromIndex <= toIndex;
            final var copy = Arrays.copyOf(array, array.length);
            // --------------------------------------------------------------------------------------------------- given
            final var instance = implementationInstance();
            // ---------------------------------------------------------------------------------------------------- when
            assertThatCode(() -> instance.sort(array, fromIndex, toIndex))
                    .doesNotThrowAnyException();
            // ---------------------------------------------------------------------------------------------------- then
            for (int i = 0; i < fromIndex; i++) {
                assertThat(array[i])
                        .as("element at %1$d before the fromIndex(%2$d)(exclusive)", i, fromIndex)
                        .isEqualTo(copy[i]);
            }
            for (int i = toIndex; i < array.length; i++) {
                assertThat(array[i])
                        .as("element at %1$d after the toIndex(%2$d)(inclusive)", i, toIndex)
                        .isEqualTo(copy[i]);
            }
            final var range = Arrays.copyOfRange(array, fromIndex, toIndex);
            assertThat(range)
                    .as("elements between %1$d(inclusive) and %2$d(exclusive)", fromIndex, toIndex)
                    .isSorted();
        }
    }

    @DisplayName("sort(array)")
    @Nested
    class SortIntArrayTest {

        @DisplayName("should throw NullPointerException when array is null")
        @Test
        void _ThrowNullPointerException_ArrayIsNull() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = implementationInstance();
            final var array = (int[]) null;
            assert array == null;
            // ----------------------------------------------------------------------------------------------- when/then
            assertThatThrownBy(() -> instance.sort(array))
                    .as("thrown by sort((int[]) null")
                    .isInstanceOf(NullPointerException.class);
        }

        @DisplayName("should invoke sort(array, 0, array.length)")
        @Test
        void __() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = implementationSpy();
            final var array = new int[0];
            assert array != null;
            // ---------------------------------------------------------------------------------------------------- when
            assertThatCode(() -> instance.sort(array))
                    .doesNotThrowAnyException();
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).sort(array, 0, array.length);
        }
    }

    @DisplayName("sort(array, fromIndex, toIndex, comparator)")
    @Nested
    class SortObjectArrayWithIndicesAndComparatorTest {

        private static Stream<Arguments> getArrayIndicesAndComparatorArgumentsStream() {
            return _UserTestUtils.getArrayIndicesAndComparatorArgumentsStream();
        }

        private static void verify(final _User[] array, final int fromIndex, final int toIndex,
                                   final Comparator<_User> comparator,
                                   final _User[] copy) {
            assert array != null;
            assert fromIndex >= 0;
            assert toIndex <= array.length;
            assert fromIndex <= toIndex;
            assert comparator != null;
            assert copy != null;
            assert copy.length == array.length;
            // ---------------------------------------------------------------------------------------------------- then
            for (int i = 0; i < fromIndex; i++) {
                assertThat(array[i])
                        .as("element at %1$d, before the fromIndex(%2$d)(exclusive), which should be remained as same",
                            i, fromIndex)
                        .isSameAs(copy[i]);
            }
            for (int i = toIndex; i < array.length; i++) {
                assertThat(array[i])
                        .as("element at %1$d, after the toIndex(%2$d)(inclusive), which should be remained as same", i,
                            toIndex)
                        .isSameAs(copy[i]);
            }
            final var range = Arrays.copyOfRange(array, fromIndex, toIndex);
            assertThat(range)
                    .as("elements between fromIndex(%1$d)(inclusive) and toIndex(%2$d)(exclusive)", fromIndex, toIndex)
                    .isSortedAccordingTo(comparator);
        }

        @DisplayName("should throw NullPointerException when array is null")
        @Test
        void _ThrowNullPointerException_ArrayIsNull() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = implementationInstance();
            final var array = (Integer[]) null;
            final var fromIndex = 0;
            final var toIndex = 0;
            final var comparator = Comparator.<Integer>naturalOrder();
            assert array == null;
            assert fromIndex >= 0;
            assert array == null || toIndex <= array.length;
            assert fromIndex <= toIndex;
            assert comparator != null;
            // ----------------------------------------------------------------------------------------------- when/then
            assertThatThrownBy(() -> instance.sort(array, fromIndex, toIndex, comparator))
                    .isInstanceOf(NullPointerException.class);
        }

        @DisplayName("should throw ArrayIndexOutOfBoundsException when fromIndex is less than 0")
        @Test
        void _ThrowArrayIndexOutOfBoundsException_FromIndexIsLessThan0() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = implementationInstance();
            final var array = new Integer[0];
            final var fromIndex = ThreadLocalRandom.current().nextInt() | Integer.MIN_VALUE;
            final var toIndex = array.length;
            final var comparator = Comparator.<Integer>naturalOrder();
            assert array != null;
            assert fromIndex < 0;
            assert toIndex <= array.length;
            assert fromIndex <= toIndex;
            assert comparator != null;
            // ----------------------------------------------------------------------------------------------- when/then
            assertThatThrownBy(() -> instance.sort(array, fromIndex, toIndex, comparator))
                    .isInstanceOf(ArrayIndexOutOfBoundsException.class);
        }

        @DisplayName("should throw ArrayIndexOutOfBoundsException when toIndex is greater than array.length")
        @Test
        void _ThrowArrayIndexOutOfBoundsException_ToIndexIsGreaterThanArrayLength() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = implementationInstance();
            final var array = new Integer[0];
            final var fromIndex = 0;
            final var toIndex = ThreadLocalRandom.current().nextInt() & 0x7FFFFFFF;
            assert array != null;
            assert fromIndex >= 0;
            assert toIndex > array.length;
            assert fromIndex <= toIndex;
            // ----------------------------------------------------------------------------------------------- when/then
            assertThatThrownBy(() -> instance.sort(array, fromIndex, toIndex))
                    .isInstanceOf(ArrayIndexOutOfBoundsException.class);
        }

        @DisplayName("should throw IllegalArgumentException when fromIndex is greater than toIndex")
        @Test
        void _ThrowIllegalArgumentException_FromIndexIsGreaterThanToIndex() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = implementationInstance();
            final var array = new Integer[ThreadLocalRandom.current().nextInt(1, 16)];
            final var fromIndex = ThreadLocalRandom.current().nextInt(1, array.length + 1);
            final var toIndex = ThreadLocalRandom.current().nextInt(0, fromIndex);
            assert array != null;
            assert fromIndex >= 0;
            assert toIndex <= array.length;
            assert fromIndex > toIndex;
            // ----------------------------------------------------------------------------------------------- when/then
            assertThatThrownBy(() -> instance.sort(array, fromIndex, toIndex))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("should throw NullPointerException when comparator is null")
        @Test
        void _ThrowNullPointerException_ComparatorIsNull() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = implementationInstance();
            final var array = new Integer[0];
            final var fromIndex = 0;
            final var toIndex = 0;
            final var comparator = (Comparator<Integer>) null;
            assert array != null;
            assert fromIndex >= 0;
            assert toIndex <= array.length;
            assert fromIndex <= toIndex;
            assert comparator == null;
            // ----------------------------------------------------------------------------------------------- when/then
            assertThatThrownBy(() -> instance.sort(array, fromIndex, toIndex, comparator))
                    .isInstanceOf(NullPointerException.class);
        }

        @DisplayName("should sort elements in given range")
        @MethodSource({"getArrayIndicesAndComparatorArgumentsStream"})
        @ParameterizedTest
        void _ShouldSort_Range(final _User[] array, final int fromIndex, final int toIndex,
                               final Comparator<? super _User> comparator) {
            assert array != null;
            assert fromIndex >= 0;
            assert toIndex <= array.length;
            assert fromIndex <= toIndex;
            assert comparator != null;
            final var copy = Arrays.copyOf(array, array.length);
            // --------------------------------------------------------------------------------------------------- given
            final var instance = implementationInstance();
            // ---------------------------------------------------------------------------------------------------- when
            assertThatCode(() -> instance.sort(array, fromIndex, toIndex, comparator))
                    .as("sort(%1$s(%2$d), %3$d, %4$d, %5$s)", array, array.length, fromIndex, toIndex, comparator)
                    .doesNotThrowAnyException();
            // ---------------------------------------------------------------------------------------------------- then
            for (int i = 0; i < fromIndex; i++) {
                assertThat(array[i])
                        .as("element at %1$d, before the fromIndex(%2$d)(exclusive), which should be remained as same",
                            i, fromIndex)
                        .isSameAs(copy[i]);
            }
            for (int i = toIndex; i < array.length; i++) {
                assertThat(array[i])
                        .as("element at %1$d, after the toIndex(%2$d)(inclusive), which should be remained as same", i,
                            toIndex)
                        .isSameAs(copy[i]);
            }
            final var range = Arrays.copyOfRange(array, fromIndex, toIndex);
            assertThat(range)
                    .as("elements between fromIndex(%1$d)(inclusive) and toIndex(%2$d)(exclusive)", fromIndex, toIndex)
                    .isSortedAccordingTo(comparator);
        }

        @DisplayName("elements in given range should be shorted stable")
        @Test
        void _ShouldBeStable1_Range() {
            // --------------------------------------------------------------------------------------------------- given
            final var array = IntStream.range(0, 8)
                    .mapToObj(i -> new _User(i, "fixed", 0))
                    .toArray(_User[]::new);
            final var fromIndex = _TestUtils.getRandomFromIndexFromArray(array);
            final var toIndex = _TestUtils.getRandomToIndexFromArray(array, fromIndex);
            final var comparator = _User.COMPARING_NAME;
            assert array != null;
            assert fromIndex >= 0;
            assert toIndex <= array.length;
            assert fromIndex <= toIndex;
            assert comparator != null;
            final var copy = Arrays.copyOf(array, array.length);
            final var instance = implementationInstance();
            // ---------------------------------------------------------------------------------------------------- when
            assertThatCode(() -> instance.sort(array, fromIndex, toIndex, comparator))
                    .doesNotThrowAnyException();
            // ---------------------------------------------------------------------------------------------------- then
            verify(array, fromIndex, toIndex, comparator, copy);
            assertThat(array).extracting(_User::id).isSorted();
        }

        @DisplayName("elements in given range should be shorted stable")
        @Test
        void _ShouldBeStable2_Range() {
            // --------------------------------------------------------------------------------------------------- given
            final var array = IntStream.range(0, 8)
                    .mapToObj(i -> new _User(0, String.format("%019d", System.nanoTime()), 0))
                    .toArray(_User[]::new);
            final var fromIndex = _TestUtils.getRandomFromIndexFromArray(array);
            final var toIndex = _TestUtils.getRandomToIndexFromArray(array, fromIndex);
            final var comparator = _User.COMPARING_ID;
            assert array != null;
            assert fromIndex >= 0;
            assert toIndex <= array.length;
            assert fromIndex <= toIndex;
            assert comparator != null;
            final var copy = Arrays.copyOf(array, array.length);
            final var instance = implementationInstance();
            // ---------------------------------------------------------------------------------------------------- when
            assertThatCode(() -> instance.sort(array, fromIndex, toIndex, comparator))
                    .doesNotThrowAnyException();
            // ---------------------------------------------------------------------------------------------------- then
            verify(array, fromIndex, toIndex, comparator, copy);
            assertThat(array).extracting(_User::name).isSorted();
        }
    }

//    @DisplayName("sort(array, comparator)")
//    @Nested
//    class SortObjectArrayWithComparatorTest {
//
//        @DisplayName("should throw NullPointerException when array is null")
//        @Test
//        void _ThrowNullPointerException_ArrayIsNull() {
//            // --------------------------------------------------------------------------------------------------- given
//            final var instance = implementationInstance();
//            final var array = (String[]) null;
//            final var comparator = Comparator.<String>naturalOrder();
//            // ----------------------------------------------------------------------------------------------- when/then
//            assertThatThrownBy(() -> instance.sort(array, comparator))
//                    .isInstanceOf(NullPointerException.class);
//        }
//
//        @DisplayName("should throw NullPointerException when comparator is null")
//        @Test
//        void _ThrowNullPointerException_ComparatorIsNull() {
//            // --------------------------------------------------------------------------------------------------- given
//            final var instance = implementationInstance();
//            final var array = new String[0];
//            final var comparator = (Comparator<String>) null;
//            // ----------------------------------------------------------------------------------------------- when/then
//            assertThatThrownBy(() -> instance.sort(array, comparator))
//                    .isInstanceOf(NullPointerException.class);
//        }
//
//        @DisplayName("should invoke sort(array, 0, array.length, comparator)")
//        @Test
//        void _InvokeSortWithArrayAndIndices_() {
//            // --------------------------------------------------------------------------------------------------- given
//            final var instance = implementationSpy();
//            doNothing()
//                    .when(instance)
//                    .sort(any(), anyInt(), anyInt(), any());
//            final var array = new String[0];
//            @SuppressWarnings({"unchecked"})
//            final var comparator = (Comparator<? super String>) mock(Comparator.class);
//            // ---------------------------------------------------------------------------------------------------- when
//            instance.sort(array, comparator);
//            // ---------------------------------------------------------------------------------------------------- then
//            verify(instance, times(1))
//                    .sort(array, 0, array.length, comparator);
//        }
//    }
//
//    // -----------------------------------------------------------------------------------------------------------------
//    @DisplayName("sort(list, comparator)")
//    @Nested
//    class SortListWithIndicesTest {
//
//        private static Stream<Arguments> getListAndComparatorArgumentsStream() {
//            return _UserTestUtils.getListAndComparatorArgumentsStream();
//        }
//
//        private static Stream<Arguments> getListArgumentsStreamForTestingStability() {
//            return _UserTestUtils.getListAndOthersArgumentsStreamForTestingStability();
//        }
//
//        @DisplayName("should throw NullPointerException when list is null")
//        @Test
//        void _ThrowNullPointerException_ListIsNull() {
//            // --------------------------------------------------------------------------------------------------- given
//            final var instance = implementationInstance();
//            final var list = (List<String>) null;
//            final var comparator = Comparator.<String>naturalOrder();
//            // ----------------------------------------------------------------------------------------------- when/then
//            assertThatThrownBy(() -> instance.sort(list, comparator))
//                    .isInstanceOf(NullPointerException.class);
//        }
//
//        @DisplayName("should throw NullPointerException when comparator is null")
//        @Test
//        void _ThrowNullPointerException_ComparatorIsNull() {
//            // --------------------------------------------------------------------------------------------------- given
//            final var instance = implementationInstance();
//            final var list = (List<String>) null;
//            final var comparator = (Comparator<String>) null;
//            // ----------------------------------------------------------------------------------------------- when/then
//            assertThatThrownBy(() -> instance.sort(list, comparator))
//                    .isInstanceOf(NullPointerException.class);
//        }
//
//        @DisplayName("range should be shorted")
//        @MethodSource({"getListAndComparatorArgumentsStream"})
//        @ParameterizedTest
//        void _RangeShouldBeSorted_(final List<_User> list, final Comparator<? super _User> comparator) {
//            // --------------------------------------------------------------------------------------------------- given
//            assert list != null;
//            assert comparator != null;
//            final var instance = implementationInstance();
//            // ---------------------------------------------------------------------------------------------------- when
//            assertThatCode(() -> instance.sort(list, comparator))
//                    .doesNotThrowAnyException();
//            // ---------------------------------------------------------------------------------------------------- then
//            assertThat(list)
//                    .isSortedAccordingTo(comparator);
//        }
//
//        @DisplayName("range should be shorted stable")
//        @MethodSource({"getListArgumentsStreamForTestingStability"})
//        @ParameterizedTest
//        void _RangeShouldBeSortedStable_(final List<_User> list, final Comparator<? super _User> comparator,
//                                         final Consumer<? super InsertionSort> invoker,
//                                         final Supplier<? extends Void> verifier) {
//            // --------------------------------------------------------------------------------------------------- given
//            final var instance = implementationInstance();
//            // ---------------------------------------------------------------------------------------------------- when
//            invoker.accept(instance);
//            // ---------------------------------------------------------------------------------------------------- then
//            verifier.get();
//        }
//    }
//
//    @DisplayName("sort(list)")
//    @Nested
//    class SortListTest {
//
//        private static Stream<List<_User>> getListStream() {
//            return _UserTestUtils.getListStream();
//        }
//
//        private static Stream<Arguments> getListAndComparatorArgumentsStream() {
//            return _UserTestUtils.getListAndComparatorArgumentsStream();
//        }
//
//        private static Stream<Arguments> getListStreamForTestingStability() {
//            return _UserTestUtils.getListAndOthersArgumentsStreamForTestingStability();
//        }
//
//        @DisplayName("should throw NullPointerException when list is null")
//        @Test
//        void _ThrowNullPointerException_ListIsNull() {
//            // --------------------------------------------------------------------------------------------------- given
//            final var instance = implementationInstance();
//            final var list = (List<String>) null;
//            final var comparator = Comparator.<String>naturalOrder();
//            // ----------------------------------------------------------------------------------------------- when/then
//            assertThatThrownBy(() -> instance.sort(list, comparator))
//                    .isInstanceOf(NullPointerException.class);
//        }
//
//        @DisplayName("should invoke sort(list, !null)")
//        @Test
//        @SuppressWarnings({"unchecked"})
//        void _InvokeSortWithListAndComparator_() {
//            // --------------------------------------------------------------------------------------------------- given
//            final var instance = implementationSpy();
//            doNothing()
//                    .when(instance)
//                    .sort(any(List.class), notNull(Comparator.class));
//            final var list = new ArrayList<String>();
//            // ---------------------------------------------------------------------------------------------------- when
//            instance.sort(list);
//            // ---------------------------------------------------------------------------------------------------- then
//            verify(instance, times(1))
//                    .sort(same(list), notNull());
//        }
//
//        @DisplayName("range should be shorted")
//        @MethodSource({"getListStream"})
//        @ParameterizedTest
//        void _RangeShouldBeSorted_(final List<_User> list) {
//            // --------------------------------------------------------------------------------------------------- given
//            final var instance = implementationInstance();
//            // ---------------------------------------------------------------------------------------------------- when
//            assertThatCode(() -> instance.sort(list))
//                    .doesNotThrowAnyException();
//            // ---------------------------------------------------------------------------------------------------- then
//            assertThat(list)
//                    .isSorted();
//        }
//
//        @DisplayName("range should be shorted stable")
//        @MethodSource({"getListStreamForTestingStability"})
//        @ParameterizedTest
//        void _RangeShouldBeSortedStable_(final List<_User> list,
//                                         final Comparator<? super _User> comparator,
//                                         final Consumer<? super InsertionSort> invoker,
//                                         final Supplier<? extends Void> verifier) {
//            // --------------------------------------------------------------------------------------------------- given
//            final var instance = implementationInstance();
//            // ---------------------------------------------------------------------------------------------------- when
//            invoker.accept(instance);
//            // ---------------------------------------------------------------------------------------------------- then
//            verifier.get();
//        }
//    }

    // --------------------------------------------------------------------------------------------- implementationClass
    final T implementationSpy() {
        return spy(implementationInstance());
    }

    final T implementationInstance() {
        try {
            final var constructor = implementationClass.getDeclaredConstructor();
            if (!constructor.canAccess(null)) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance();
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException("failed to instantiate " + implementationClass, roe);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The class to test.
     */
    final Class<T> implementationClass;
}
