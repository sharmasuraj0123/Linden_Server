package com.linden.util.search.rank;

import com.linden.models.Content;

import java.util.function.Function;

/**
 * A simple implementation of Content Ranking Algorithm.
 * By default ContentRanker will order by score field.
 *
 * @param <X> Type of content to order
 */
public class ContentRanker<X extends Content> extends Ranker<X> {

    public ContentRanker() {
        this(Content::getScore);
    }

    public ContentRanker(Function<X, ? extends Comparable> pairTransformation) {
        super(pairTransformation);
    }

}
