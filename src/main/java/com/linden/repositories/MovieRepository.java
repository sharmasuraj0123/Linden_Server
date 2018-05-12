package com.linden.repositories;

import com.linden.models.content.Movie;
import com.linden.models.content.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.Date;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findMoviesByNameContains(String keywords);

    List<Movie> findByMovieType(MovieType movieType);

    List<Movie> findByIsFeaturedTrue();

    List<Movie> findByOrderByBoxOfficeDesc();

    List<Movie> findByOrderByScoreDesc();

    List<Movie> getMovieByReleaseDateAfter(Date today);

    List<Movie> getMoviesByCastId(long castId);

    void deleteMovieById(long id);

    List<Movie> findByReleaseDateAfterOrderByBoxOfficeDesc(Date firstOfYear);

    List<Movie> findMoviesByIsAcademyWinnerTrue();

    @Query(value = "update movie m set m.linden_meter=?1 where m.id = ?2", nativeQuery = true)
    void setMovieLindenMeter(double rating, long id);
}
