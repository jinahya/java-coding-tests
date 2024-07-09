package com.github.jinahya.algorithms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Comparator;
import java.util.Objects;

/**
 * A record has {@link #id}, {@link #name}, and {@link #age} fields.
 *
 * @param id   a value for the {@link #id} field which should be non-negative.
 * @param name a value for the {@link #name} field which should be not {@code null} nor {@link String#isBlank() blank}.
 * @param age  a value for the {@link #age} field which should be non-negative.
 * @author Jin Kwon &lt;jin.kwon_at_meshkorea.net&gt;
 */
// https://www.morling.dev/blog/enforcing-java-record-invariants-with-bean-validation/
public record _User(@PositiveOrZero int id, @NotBlank String name, @PositiveOrZero int age)
        implements Comparable<_User> {

    public static final Comparator<_User> NOT_COMPARING = (o1, o2) -> 0;

    public static final Comparator<_User> COMPARING_ID = Comparator.comparingInt(_User::id);

    public static final Comparator<_User> COMPARING_NAME = Comparator.comparing(_User::name);

    public static final Comparator<_User> COMPARING_AGE = Comparator.comparingInt(_User::age);

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance.
     *
     * @param id   a value for the {@link #id} field which should be non-negative.
     * @param name a value for the {@link #name} field which should be not {@code null} nor
     *             {@link String#isBlank() blank}.
     * @param age  a value for the {@link #age} field which should be non-negative.
     */
    public _User {
        if (id < 0) {
            throw new IllegalArgumentException("id(" + id + ") is negative");
        }
        if (Objects.requireNonNull(name, "name is null").isBlank()) {
            throw new IllegalArgumentException("name is blank");
        }
        if (age < 0) {
            throw new IllegalArgumentException("age(" + age + ") is negative");
        }
    }

    // -------------------------------------------------------------------------------------------- java.lang.Comparable
    @Override
    public int compareTo(final _User o) {
        Objects.requireNonNull(o, "o is null");
        return Integer.compare(id(), o.id());
    }
}
