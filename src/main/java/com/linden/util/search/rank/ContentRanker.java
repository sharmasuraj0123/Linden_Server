package com.linden.util.search.rank;

import com.linden.models.Content;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple implementation of Content Ranking Algorithm.
 * By default ContentRanker will order by score field.
 * @param <X> Type of content to order
 */
public class ContentRanker<X extends Content> implements Ranker<X>{

    private Ranker<X> scoringFunction;

    public ContentRanker(){
        scoringFunction = content -> new Pair<>(content, content.getScore());
    }

    public ContentRanker(Ranker<X> scoringFunction){
        this.scoringFunction = scoringFunction;
    }

    public Ranker<X> getScoringFunction() {
        return scoringFunction;
    }

    public void setScoringFunction(Ranker<X> scoringFunction) {
        this.scoringFunction = scoringFunction;
    }

    @Override
    public Pair<X, Double> apply(X content) {
        return scoringFunction.apply(content);
    }

    public List<X> order(Collection<X> collection){
        return collection.parallelStream()
                .map(this)
                .sorted(Ranker.COMPARATOR)
                .map(Pair::getKey)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
