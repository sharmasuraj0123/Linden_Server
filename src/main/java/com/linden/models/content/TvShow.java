package com.linden.models.content;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class TvShow extends Content implements Serializable{

    @OneToMany
    protected List<Season> seasons;

    protected int numberOfSeasons;

    public TvShow(){
        super();
        this.contentType = ContentType.TVSHOW;
    }

    public TvShow(String name, String details, Date releaseDate, double score, Set<Cast> cast, Set<Genre> genre){
        super(name, details, releaseDate, score, cast, genre);
        this.contentType = ContentType.TVSHOW;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }
}
