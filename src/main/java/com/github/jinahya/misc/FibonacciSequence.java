package com.github.jinahya.misc;

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
 */
public final class FibonacciSequence {

    public static void main(String... args) {

        final double d = 1.394232245617E+104;
        System.out.println(d);
        System.out.println(d > Long.MAX_VALUE);
    }

    public static BigInteger getFibonacciNumber(final long n) {
        if (n < 0) {
            throw new IllegalArgumentException("n(" + n + ") is negative");
        }
        // TODO: implement!
        throw new UnsupportedOperationException("not implemented yet");
    }

    private FibonacciSequence() {
        throw new AssertionError("instantiation is not allowed");
    }
}
