package com.linden.services;

import com.linden.models.accounts.Account;
import com.linden.models.accounts.Admin;
import com.linden.models.accounts.PromotionApplication;
import com.linden.models.accounts.User;
import com.linden.models.content.Movie;
import com.linden.models.content.ReviewReport;
import com.linden.models.content.Season;
import com.linden.models.content.TvShow;
import com.linden.repositories.AdminRepository;
import com.linden.repositories.CastRepository;
import com.linden.repositories.MovieRepository;
import com.linden.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private TvShowRepository tvShowRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CastRepository castRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ReviewReportRepository reviewReportRepository;
    @Autowired
    private PromotionApplicationRepository promotionApplicationRepository;
    @Autowired
    private UserService userService;

    public Admin getAdminByEmail(String email){
        return adminRepository.findByEmail(email);
    }

    public boolean checkCredentials(Admin admin, Account databaseAccount){
        return admin.getEmail().equals(databaseAccount.getEmail())
                && passwordEncoder.matches(admin.getPassword(), databaseAccount.getPassword());
    }

    public boolean checkCredentials(Admin admin){
        return checkCredentials(admin, getAdminByEmail(admin.getEmail()));
    }

    public void addMovie(Movie movie){
        if(movie.getCast() != null) {
            movie.getCast().forEach(castRepository::save);
        }
        movieRepository.save(movie);
    }

    public void addTvShow(TvShow tvShow){
        tvShow.getCast().forEach(castRepository::save);

        for(Season season : tvShow.getSeasons()){
            season.getEpisodes().forEach(episodeRepository::save);
        }

        tvShow.getSeasons().forEach(seasonRepository::save);

        tvShowRepository.save(tvShow);
    }

    public List<ReviewReport> getReports(){
        return reviewReportRepository.findAll();
    }

    public List<PromotionApplication> getPromotionApplications() {
        return promotionApplicationRepository.findAll();
    }

    public void editMovie(long movieId, Movie newMovie) {
        // TODO: finish
        Movie oldMovie = movieRepository.findById(movieId).orElse(null);
        if(oldMovie != null) {
            // Necessary Fields
            oldMovie.setAcademyWinner(newMovie.isAcademyWinner);
            oldMovie.setDuration(newMovie.getDuration());
            oldMovie.setFeatured(newMovie.isFeatured());
            oldMovie.setRevenue(newMovie.getRevenue());
            //Unnecessary Fields
        }
    }

    @Transactional
    public void deleteReview(long reviewId) {
        if(reviewRepository.findById(reviewId).isPresent()) {
            reviewRepository.deleteById(reviewId);
        }
    }

    @Transactional
    public void deleteUser(long accountId) {
        userService.deleteAccount(userService.getUserById(accountId));
    }

    @Transactional
    public void approvePromotion(PromotionApplication promotionApplication) {
        promotionApplication = promotionApplicationRepository.findByUserId(promotionApplication.getUserId()).get(0);
        User user = userRepository.findById(promotionApplication.getUserId()).orElse(null);
        if (user != null) {
            user.setUserType(promotionApplication.getPromotionType());
            userRepository.save(user);
            promotionApplicationRepository.delete(promotionApplication);
        }
    }

    @Transactional
    public void deleteMovie(long movieId) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if(movie != null) {
            movie.getCast().stream().filter(
                cast -> movieRepository.getMoviesByCastId(cast.getId()).isEmpty()
            ).forEach(castRepository::delete);
            movieRepository.deleteMovieById(movieId);
        }
    }
}
