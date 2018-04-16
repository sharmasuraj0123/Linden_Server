package com.linden.util.search.rank;

import javafx.util.Pair;

import java.util.Comparator;
import java.util.function.Function;

/**
 * Functional interface used to define a ranking (a.k.a scoring function) for each
 * object. This interface must map an object to a key value pair consisting of
 * (object, value). With the only requirement that value must extend Comparable.
 * <p>
 * Functional diagram:
 * (object) -> (object, value)
 */
@FunctionalInterface
public interface PairingFunction<X> extends Function<X, Pair<X, ? extends Comparable>> {
    Comparator<Pair<?, ? extends Comparable>> COMPARATOR = Comparator.comparing(Pair::getValue);

    Pair<X, ? extends Comparable> apply(X obj);
}
