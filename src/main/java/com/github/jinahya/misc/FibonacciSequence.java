package com.github.jinahya.misc;

import java.math.BigInteger;

/**
 * dfadfsdfsffd.
 * <math>
 * <mfrac>
 * <mn>1</mn>
 * <msqrt>
 * <mn>2</mn>
 * </msqrt>
 * </mfrac>
 * </math>
 * <p>
 * <math xmlns="http://www.w3.org/1998/Math/MathML">
 *   <mi>a</mi>
 *   <msup><mi>x</mi><mn>2</mn></msup>
 *   <mo>+</mo>
 *   <mi>b</mi>
 *   <mi>x</mi>
 *   <mo>+</mo>
 *   <mi>c</mi>
 *   <mo>=</mo>
 *   <mn>0</mn>
 * </math>
 * <p>
 *     aaa
 * <math xmlns="http://www.w3.org/1998/Math/MathML">
 *     <fn><ci>F</ci><ci>0</ci></fn>
 *     <mo>=</mo>
 *     <mi>0</mi>


 *     <mo>,</mo>
 *     <mi>F(1)</mi>
 *     <mo>=</mo>
 *     <mi>1</mi>
 *     <mo>, and</mo>
 *     <mi>F(n)</mi>
 *     <mo>=</mo>
 *     <mi>F(n-2)</mi>
 *     <mo>+</mo>
 *     <mi>F(n-1)</mi>
 *     <mi> for </mi>
 *     <mi>n</mi>
 *     <mo>></mo>
 *     <mi>1</mi>
 * </math>
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
