package com.linden.controllers;

import com.linden.models.accounts.User;
import com.linden.models.content.Content;
import com.linden.models.content.Review;
import com.linden.models.content.ReviewReport;
import com.linden.services.AccountTokenService;
import com.linden.services.UserService;
import com.linden.util.ObjectStatusResponse;
import com.linden.util.Token;
import com.linden.util.TokenObjectContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountTokenService accountTokenService;

    @RequestMapping(value = {"/postReview", "/postRating"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> postReview(@RequestBody Review review) {
        User user = (User) accountTokenService.getAccount(review.getToken());
        if (user != null){
            Review reviewObj = userService.postAReview(user, review);
            return new ObjectStatusResponse<>(reviewObj, "OK");
        }
        else return new ObjectStatusResponse<>("status", "Not logged in!");
    }

    @RequestMapping(value = {"/editReview/{reviewId}", "/editRating/{reviewId}"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> postReview(@PathVariable("reviewId") long reviewId, @RequestBody Review newReview) {
        User user = (User) accountTokenService.getAccount(newReview.getToken());
        if (user != null){
            userService.editAReview(user, reviewId, newReview);
            return new ObjectStatusResponse<>(newReview, "OK");
        }
        else return new ObjectStatusResponse<>("status", "Not logged in!");
    }

    @RequestMapping(value = "/reportReview/{reviewId}", method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> reportReview(@PathVariable("reviewId") long reviewId, @RequestBody ReviewReport report) {
        User user = (User) accountTokenService.getAccount(report.getAccountToken());
        if (user != null){
            userService.reportAReview(report.getReview(), user, report);
            return new ObjectStatusResponse<>("status", "OK");
        }
        else return new ObjectStatusResponse<>("status", "Not logged in!");
    }

    @RequestMapping(value = {"/deleteReview/{reviewId}", "/deleteRating/{reviewId}"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> deleteReview(@PathVariable("reviewId") long reviewId, @RequestBody Token token) {
        User user = (User) accountTokenService.getAccount(token.getToken());
        if (user != null){
            userService.deleteReview(user, reviewId);
            return new ObjectStatusResponse<>("status", "OK");
        }
        else return new ObjectStatusResponse<>("status", "Not logged in!");
    }

    @RequestMapping(value = {"/getWantToSee"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> deleteReview(@RequestBody Token token){
        User user = (User) accountTokenService.getAccount(token.getToken());
        if (user != null){
            return new ObjectStatusResponse<>(userService.getUserWantToSee(user), "OK");
        }
        else return new ObjectStatusResponse<>("status", "Not logged in!");
    }

    @RequestMapping(value = {"/addToWantToSee"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> addToWantToSee(@RequestBody TokenObjectContainer<Content> contentContainer) {
        User user = (User) accountTokenService.getAccount(contentContainer.getToken());
        if (user != null){
            Content content = contentContainer.getObj();
            // TODO: finish function
            return new ObjectStatusResponse<>("status", "OK");
        }
        else return new ObjectStatusResponse<>("status", "Not logged in!");
    }

    @RequestMapping(value="/getAllCritics", method = RequestMethod.GET)
    @ResponseBody
    public ObjectStatusResponse<?> getAllCritics(){
        ArrayList<User> critics = (ArrayList<User>) userService.getAllCritics();

        if (critics.size() != 0){
            return new ObjectStatusResponse<>(critics, "OK");
        }
        else return new ObjectStatusResponse<>("status", "No registered critics yet");
    }

    @RequestMapping(value="/getTopCritics", method = RequestMethod.GET)
    @ResponseBody
    public ObjectStatusResponse<?> getTopCritics(){
        ArrayList<User> critics = (ArrayList<User>) userService.getAllTopCritics();

        if (critics.size() != 0){
            return new ObjectStatusResponse<>(critics, "OK");
        }
        else return new ObjectStatusResponse<>("status", "No registered critics yet");
    }

}
