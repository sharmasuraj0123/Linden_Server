package com.linden.util.search.rank;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Ranker<X> implements PairingFunction<X> {

    protected PairingFunction<X> scoringFunction;

    protected Function<X, ? extends Comparable> pairTransformation;

    public Ranker() {

    }

    public Ranker(Function<X, ? extends Comparable> pairTransformation) {
        this.pairTransformation = pairTransformation;
        this.scoringFunction = content -> new Pair<>(
                content,
                pairTransformation.apply(content)
        );
    }

    public PairingFunction<X> getScoringFunction() {
        return scoringFunction;
    }

    public void setScoringFunction(PairingFunction<X> scoringFunction) {
        this.scoringFunction = scoringFunction;
    }

    public Function<X, ? extends Comparable> getPairTransformation() {
        return pairTransformation;
    }

    public void setPairTransformation(Function<X, ? extends Comparable> pairTransformation) {
        this.pairTransformation = pairTransformation;
    }

    @Override
    public Pair<X, ? extends Comparable> apply(X obj) {
        return scoringFunction.apply(obj);
    }

    public List<X> order(Collection<X> collection) {
        return order(collection, true);
    }

    public List<X> order(Collection<X> collection, boolean desc) {
        return collection.parallelStream()
                .map(this)
                .sorted((desc) ?
                        PairingFunction.COMPARATOR.reversed()
                        : PairingFunction.COMPARATOR
                )
                .map(Pair::getKey)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
