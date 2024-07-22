package com.github.jinahya.misc;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigInteger;

/**
 * A class generates <a href="#definition">Fibonacci sequence</a>.
 *
 * <h2 id="definition">Fibonacci sequence</h2>
 * <math xmlns="http://www.w3.org/1998/Math/MathML">
 * <mrow>
 * <msub><mi>F</mi><mn>0</mn></msub><mo>=</mo><mi>0</mi>
 * <mtext>,&ThickSpace;</mtext>
 * </mrow>
 * <mrow>
 * <msub><mi>F</mi><mn>1</mn></msub><mo>=</mo><mi>1</mi>
 * <mtext>,&ThickSpace;and&ThickSpace;</mtext>
 * <msub><mi>F</mi><mn>n</mn></msub><mo>=</mo><msub><mi>F</mi><mn>n-1</mn></msub>
 * <mo>+</mo>
 * <msub><mi>F</mi><mn>n-2</mn></msub>
 * <mtext>&NewLine;for</mtext><mi>n</mi><mo>></mo><mi>1</mi>
 * </mrow>
 * </math>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://r-knott.surrey.ac.uk/Fibonacci/fibtable.html">The first 300 Fibonacci numbers, completely
 * factorised</a>
 */
public final class FibonacciSequence {

    static final int MAX_N = 47; // 2971215073

    static final int MAX_N_LONG = 87; // 679891637638612258

    static final int MAX_N_BIG = 127; // 155576970220531065681649693

    private static int requirePositiveOrZero(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n(" + n + ") is negative");
        }
        return n;
    }

    public static @PositiveOrZero long getFibonacciNumber(final @Max(MAX_N) @PositiveOrZero int n) {
        if (requirePositiveOrZero(n) > MAX_N) {
            throw new IllegalArgumentException("n(" + n + ") is greater than " + MAX_N);
        }
        // TODO: implement!
        throw new UnsupportedOperationException("not implemented yet");
    }

    public static @PositiveOrZero long getFibonacciNumberLong(final @Max(MAX_N_LONG) @PositiveOrZero int n) {
        if (requirePositiveOrZero(n) > MAX_N_LONG) {
            throw new IllegalArgumentException("n(" + n + ") is greater than " + MAX_N_LONG);
        }
        // TODO: implement!
        throw new UnsupportedOperationException("not implemented yet");
    }

    public static BigInteger getFibonacciNumberBig(final @PositiveOrZero int n) {
        requirePositiveOrZero(n);
        // TODO: implement!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------
    private FibonacciSequence() {
        throw new AssertionError("instantiation is not allowed");
    }
}
