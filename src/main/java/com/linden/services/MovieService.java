package com.linden.services;

import com.linden.models.content.*;
import com.linden.repositories.CastRepository;
import com.linden.repositories.MovieRepository;
import com.linden.util.search.rank.ContentRanker;
import com.linden.util.search.rank.Ranker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CastRepository castRepository;

    public Movie findById(long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.orElse(null);
    }

    public List<Movie> searchMovie(String keywords){
        return searchMovie(keywords, new ContentRanker<Movie>(), true);
    }

    public List<Movie> searchMovie(Function<Movie, ? extends  Comparable> rankingFunction) {
        return searchMovie(rankingFunction, true);
    }

    public List<Movie> searchMovie(Function<Movie, ? extends  Comparable> rankingFunction, boolean desc) {
        Ranker<Movie> ranker = new Ranker<>(rankingFunction);
        return ranker.order(movieRepository.findAll());
    }

    public List<Movie> searchMovie(String keywords,
                                   Function<Movie, ? extends Comparable> rankingFunction){
        return searchMovie(keywords, new ContentRanker<>(rankingFunction), true);
    }

    public List<Movie> searchMovie(String keywords,
                                   Function<Movie, ? extends Comparable> rankingFunction,
                                   boolean desc){
        return searchMovie(keywords, new ContentRanker<>(rankingFunction), desc);
    }

    public List<Movie> searchMovie(String keywords,
                                   ContentRanker<Movie> ranker,
                                   boolean desc){
        List<Movie> movies = movieRepository.findMoviesByNameContains(keywords);
        // Return sorted list based on ranker supplied
        return  ranker.order(movies, desc);
    }

    public List<Movie> getFeaturedMovies(){
        return movieRepository.findByIsFeaturedTrue();
    }

    public Collection<Movie> getOpeningThisWeek(){
        return movieRepository.findByMovieType(MovieType.COMING_SOON).parallelStream().filter(
            movie -> {
                Duration duration = Duration.between(movie.getReleaseDate().toInstant(), Instant.now());
                System.out.println(duration.toDays());
                return duration.toDays() <= 7;
            }
        ).collect(Collectors.toCollection(HashSet::new));
    }

    public Collection<Movie> getOpeningThisWeek(int limit){
        return movieRepository.findByMovieType(MovieType.COMING_SOON).parallelStream().filter(
                movie -> {
                    Duration duration = Duration.between(movie.getReleaseDate().toInstant(), Instant.now());
                    System.out.println(duration.toDays());
                    return duration.toDays() <= 7;
                }
        ).limit(limit).collect(Collectors.toCollection(HashSet::new));
    }

    public List<Movie> getHighestRated() {
        return movieRepository.findByOrderByScoreDesc();
    }

    public Movie getMovie(long movieId){
        Optional<Movie> movie = movieRepository.findById(movieId);
        System.out.println(movie);
        return movie.orElse(null);
    }

    public List<Movie> getUpcomingMovies(Date today){
        List<Movie> movies = movieRepository.getMovieByReleaseDateAfter(today);
        return movies;
    }

    public List<Movie> getMoviesByCastId(long castId){
        List<Movie> movies = new ArrayList<>();
        Cast cast = castRepository.getCastById(castId);
        List<Cast> duplicates = castRepository.getCastsByFirstNameAndLastName(cast.getFirstName(),cast.getLastName());

        for(Cast tempCast: duplicates){
            List<Movie> tempMovies = movieRepository.getMoviesByCastId(tempCast.getId());
            movies.addAll(tempMovies);
        }

        return movies;
    }

    public List<Movie> getTopBoxOffice(){

        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.with(firstDayOfYear());
        Date date = java.sql.Date.valueOf(firstDay);

        List<Movie> movies = movieRepository.findByReleaseDateAfterOrderByBoxOfficeDesc(date);
        return movies;
    }

    public List<Movie> getAcademyAwardWinner(){
        List<Movie> movies = movieRepository.findMoviesByIsAcademyWinnerTrue();
        return movies;
    }

    public void git updateLindenmeterForMovie(Review rev, Movie movie){

        int rating = rev.getRating();
        List<Review> reviews = movie.getReviews();

        int criticReviewCount = 0;
        for(Review review: reviews){
            if(review.getReviewType().equals(ReviewType.CRITIC) || review.getReviewType().equals(ReviewType.TOP_CRITIC)){
                 criticReviewCount++;
            }
        }

        if(rev.getReviewType().equals(ReviewType.AUDIENCE)){
            double newRating = 0;
            int audienceReviewCount= reviews.size()-criticReviewCount;
            if(rating > 3){
                newRating = (100+(movie.getScore()*criticReviewCount))/(audienceReviewCount+1);
            }else{
                newRating = ((movie.getScore()*criticReviewCount))/(audienceReviewCount+1);
            }

            movie.setScore(newRating);

        }else{
            double newRating = 0;

            if(rating > 3){
                newRating = (100+(movie.getLindenMeter()*criticReviewCount))/(criticReviewCount+1);
            }else{
                newRating = ((movie.getLindenMeter()*criticReviewCount))/(criticReviewCount+1);
            }

            movie.setLindenMeter(newRating);
        }


    }
}
