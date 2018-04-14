package com.linden.util.search.rank;

import javafx.util.Pair;

import java.util.Comparator;
import java.util.function.Function;

/**
 * Functional interface used to define a ranking (a.k.a scoring function) for each
 * movie. This interface must map an object to a key value pair consisting of
 * (object, rank). With the only requirement that order must extend Comparable.
 *
 * Functional diagram:
 * (object) -> (object, rank)
 */
@FunctionalInterface
public interface Ranker<X> extends Function<X, Pair<X, ? extends Comparable>> {
    Comparator<Pair<?, ? extends Comparable>> COMPARATOR = Comparator.comparing(Pair::getValue);
    Pair<X, Double> apply(X obj);
}
