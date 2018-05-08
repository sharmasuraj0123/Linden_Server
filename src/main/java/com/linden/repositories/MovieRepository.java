package com.linden.repositories;

import com.linden.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findMoviesByNameContains(String keywords);

    Movie getMovieById(long id);

    List<Movie> getMovieByReleaseDateAfter(Date today);
}
