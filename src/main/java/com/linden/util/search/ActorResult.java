package com.linden.util.search;

import com.linden.models.Cast;

import java.io.Serializable;

public class ActorResult implements Serializable {

    private Cast cast;

    public ActorResult(Cast cast) {
        this.cast = cast;
    }

    public Cast getCast() {
        return cast;
    }

    public void setCast(Cast cast) {
        this.cast = cast;
    }
}
