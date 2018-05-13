package com.linden.services;

import com.linden.models.accounts.*;
import com.linden.models.content.*;
import com.linden.repositories.*;
import com.linden.util.UserCredentials;
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

    @Autowired
    private UserWantsToSeeRepository userWantsToSeeRepository;

    @Autowired
    private UserNotInterestedRepository userNotInterestedRepository;

    @Autowired
    private PromotionApplicationRepository promotionApplicationRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TvShowService tvShowService;

    public enum RegistrationStatus{
        OK, EMAIL_TAKEN
    }

    @Autowired
    private UserRepository userRepository;

    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

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
                    // update linden meter of the movie
                    movieService.updateLindenmeterForMovie(review,(Movie) content);
                    movieRepository.save((Movie) content);
                }
                break;
            case TVSHOW:
                content = tvShowRepository.findById(review.getContentId()).orElse(null);
                if (content != null) {
                    content.getReviews().add(review);
                    tvShowService.updateLindenmeterForTvShow(review,(TvShow) content);
                    tvShowRepository.save((TvShow) content);
                }
                break;
        }
        return review;
    }

    public Review editAReview(User user, long reviewId, Review review) {
        Optional<Review> result = reviewRepository.findById(reviewId);
        user = userRepository.findById(user.getId()).orElse(user);
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
            oldReview.setRating(review.getRating());
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
            return oldReview;
        }
        return review;
    }

    private void updateReviewList(Content content) {
        List<Review> updatedReviewList = content.getReviews().stream().map(
                review -> reviewRepository.findById(review.getId()).get()
        ).collect(Collectors.toCollection(ArrayList::new));
        content.setReviews(updatedReviewList);
    }

    public void reportAReview(Review review, User user, ReviewReport report) {
        review = reviewRepository.findById(review.getId()).orElse(review);
        user = userRepository.findById(user.getId()).orElse(user);
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
        user = userRepository.findById(user.getId()).orElse(user);
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
        if(userWantsToSeeRepository.findByUserIdAndContentId(user.getId(), content.getId()).isEmpty()) {
            user = userRepository.findById(user.getId()).orElse(user);
            UserWantsToSee wantsToSee = new UserWantsToSee();
            wantsToSee.setContentId(content.getId());
            wantsToSee.setContentType(content.getContentType());
            userWantsToSeeRepository.saveAndFlush(wantsToSee);
            updateWantToSeeList(user);
        }
    }

    public void removeFromWantToSee(User user, Content content) {
        user = userRepository.findById(user.getId()).orElse(user);
        userWantsToSeeRepository.deleteByUserIdAndContentId(user.getId(), content.getId());
        updateWantToSeeList(user);
    }

    private void updateWantToSeeList(User user) {
        List<UserWantsToSee> savedList = userWantsToSeeRepository.findByUserId(user.getId());
        user.setWantsToSee(new HashSet<>());
        savedList.forEach(user.getWantsToSee()::add);
        userRepository.saveAndFlush(user);
    }

    public ArrayList<Content> getNotInterested(User user) {
        user = userRepository.findById(user.getId()).orElse(user);
        ArrayList<Content> notInterestedList = new ArrayList<>();
        user.getNotInterested().stream().forEach(
            userNotInterested -> {
                switch (userNotInterested.getContentType()){
                    case MOVIE:
                        notInterestedList.add(
                                movieRepository.findById(userNotInterested.getContentId()).get()
                        );
                        break;
                    case TVSHOW:
                        notInterestedList.add(
                                tvShowRepository.findById(userNotInterested.getContentId()).get()
                        );
                        break;
                }
            }
        );
        return notInterestedList;
    }

    public void addToNotInterested(User user, Content content) {
        if(userNotInterestedRepository.findByUserIdAndContentId(user.getId(), content.getId()).isEmpty()) {
            user = userRepository.findById(user.getId()).orElse(user);
            UserNotInterested notInterested = new UserNotInterested();
            notInterested.setContentId(content.getId());
            notInterested.setContentType(content.getContentType());
            userNotInterestedRepository.saveAndFlush(notInterested);
            updateNotInterested(user);
        }
    }

    public void removeFromNotInterested(User user, Content content) {
        user = userRepository.findById(user.getId()).orElse(user);
        userNotInterestedRepository.deleteByUserIdAndContentId(user.getId(), content.getId());
        updateNotInterested(user);
    }

    private void updateNotInterested(User user) {
        List<UserNotInterested> savedList = userNotInterestedRepository.findByUserId(user.getId());
        user.setNotInterested(new HashSet<>());
        savedList.forEach(user.getNotInterested()::add);
        userRepository.saveAndFlush(user);
    }

    public void changeUserCredentials(User user, UserCredentials userCredentials) {
        if (userCredentials.getEmail() != null) {
            user.setEmail(userCredentials.getEmail());
        }
        if(userCredentials.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
        }
        userRepository.saveAndFlush(user);
    }

    public void deleteAccount(User user) {
        userRepository.delete(user);
    }

    public List<User> getAllCritics(){
        return userRepository.findUserByUserTypeOrUserType("CRITIC","TOPCRITIC");
    }

    public List<User> getAllTopCritics(){
        return userRepository.findUserByUserType("TOPCRITIC");
    }



    public void applyForPromotion(long userId, UserType promotionType) {
        PromotionApplication promotionApplication = new PromotionApplication();
        promotionApplication.setUserId(userId);
        promotionApplication.setPromotionType(promotionType);
        promotionApplicationRepository.save(promotionApplication);
    }
}
