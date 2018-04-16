package com.linden.models;

import javax.persistence.Entity;
import java.util.Date;
import java.util.Set;

@Entity
public class TvShow extends Content {
    public TvShow() {
        super();
    }

    public TvShow(String name, String details, Date releaseDate, double score, Set<Cast> cast, Set<Genre> genre) {
        super(name, details, releaseDate, score, cast, genre);
    }
}
