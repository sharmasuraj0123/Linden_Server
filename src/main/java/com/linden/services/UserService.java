package com.linden.services;

import com.linden.models.accounts.Account;
import com.linden.models.accounts.User;
import com.linden.models.accounts.UserType;
import com.linden.models.accounts.UserWantsToSee;
import com.linden.models.content.*;
import com.linden.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TvShowRepository tvShowRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewReportRepository reviewReportRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public enum RegistrationStatus{
        OK, EMAIL_TAKEN
    }

    @Autowired
    private UserRepository userRepository;

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User getUserByType(UserType userType){
        return userRepository.findByUserType(userType);
    }

    public boolean checkCredentials(User user, Account databaseAccount){
        return user.getEmail().equals(databaseAccount.getEmail())
                && passwordEncoder.matches(user.getPassword(), databaseAccount.getPassword());
    }

    public boolean checkCredentials(User user){
        return checkCredentials(user, getUserByEmail(user.getEmail()));
    }

    @Transactional
    public RegistrationStatus registerUser(User user){
        user.setVerifiedAccount(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (getUserByEmail(user.getEmail()) != null || adminService.getAdminByEmail(user.getEmail()) != null) {
            return RegistrationStatus.EMAIL_TAKEN;
        }
        else {
            if(user.getUserType() == UserType.GUEST) {
                user.setUserType(UserType.AUDIENCE);
            }
            userRepository.save(user);
            return RegistrationStatus.OK;
        }
    }

    public Review postAReview(User user, Review review){
        review.setPostedBy(user);
        review.setDate(Date.from(Instant.now()));
        switch (user.getUserType()) {
            default:
            case AUDIENCE:
                review.setReviewType(ReviewType.AUDIENCE);
                break;
            case CRITIC:
                review.setReviewType(ReviewType.CRITIC);
                break;
            case TOPCRITIC:
                review.setReviewType(ReviewType.TOP_CRITIC);
                break;
        }
        reviewRepository.saveAndFlush(review);
        Content content;
        switch (review.getContentType()) {
            case MOVIE:
                content = movieRepository.findById(review.getContentId()).orElse(null);
                if (content != null) {
                    content.getReviews().add(review);
                    movieRepository.save((Movie) content);
                }
                break;
            case TVSHOW:
                content = tvShowRepository.findById(review.getContentId()).orElse(null);
                if (content != null) {
                    content.getReviews().add(review);
                    tvShowRepository.save((TvShow) content);
                }
                break;
        }
        return review;
    }

    public void editAReview(User user, long reviewId, Review review) {
        Optional<Review> result = reviewRepository.findById(reviewId);
        user = userRepository.findById(user.getAccountId()).orElse(user);
        if (result.isPresent() && result.get().getPostedBy().equals(user)) {
            Review oldReview = result.get();
            switch (user.getUserType()) {
                default:
                case AUDIENCE:
                    oldReview.setReviewType(ReviewType.AUDIENCE);
                    break;
                case CRITIC:
                    oldReview.setReviewType(ReviewType.CRITIC);
                    break;
                case TOPCRITIC:
                    oldReview.setReviewType(ReviewType.TOP_CRITIC);
                    break;
            }
            oldReview.setDetails(review.getDetails());
            oldReview.setDate(Date.from(Instant.now()));
            reviewRepository.saveAndFlush(oldReview);
            Content content;
            switch (review.getContentType()) {
                case MOVIE:
                    content = movieRepository.findById(review.getContentId()).orElse(null);
                    if (content != null) {
                        updateReviewList(content);
                        movieRepository.save((Movie) content);
                    }
                    break;
                case TVSHOW:
                    content = tvShowRepository.findById(review.getContentId()).orElse(null);
                    if (content != null) {
                        updateReviewList(content);
                        tvShowRepository.save((TvShow) content);
                    }
                    break;
            }
        }
    }

    private void updateReviewList(Content content) {
        List<Review> updatedReviewList = content.getReviews().stream().map(
                review -> reviewRepository.findById(review.getId()).get()
        ).collect(Collectors.toCollection(ArrayList::new));
        content.setReviews(updatedReviewList);
    }

    public void reportAReview(Review review, User user, ReviewReport report) {
        review = reviewRepository.findById(review.getId()).orElse(review);
        user = userRepository.findById(user.getAccountId()).orElse(user);
        report.setReview(review);
        report.setReportedBy(user);
        reviewReportRepository.save(report);
    }

    public void deleteReview(User user, long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review != null && review.getPostedBy().equals(user)) {
            Content content;
            switch (review.getContentType()) {
                case MOVIE:
                    content = movieRepository.findById(review.getContentId()).orElse(null);
                    if (content != null) {
                        updateReviewList(content);
                        content.getReviews().remove(review);
                        movieRepository.save((Movie) content);
                    }
                    break;
                case TVSHOW:
                    content = tvShowRepository.findById(review.getContentId()).orElse(null);
                    if (content != null) {
                        updateReviewList(content);
                        content.getReviews().remove(review);
                        tvShowRepository.save((TvShow) content);
                    }
                    break;
            }
            reviewRepository.delete(review);
        }
    }

    public ArrayList<Content> getUserWantToSee(User user) {
        user = userRepository.findById(user.getAccountId()).orElse(user);
        ArrayList<Content> wantToSeeList = new ArrayList<>();
        user.getWantsToSee().stream().forEach(
                userWantsToSee -> {
                    switch (userWantsToSee.getContentType()){
                        case MOVIE:
                            wantToSeeList.add(
                                movieRepository.findById(userWantsToSee.getContentId()).get()
                            );
                            break;
                        case TVSHOW:
                            wantToSeeList.add(
                                tvShowRepository.findById(userWantsToSee.getContentId()).get()
                            );
                            break;
                    }
                }
        );
        return wantToSeeList;
    }

    public void addToWantToSee(User user, Content content) {
        user = userRepository.findById(user.getAccountId()).orElse(user);

    }
}
