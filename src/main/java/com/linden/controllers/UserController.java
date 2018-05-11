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
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        if (user != null) {
            return new ObjectStatusResponse<>(userService.postAReview(user, review), "OK");
        }
        else return new ObjectStatusResponse<>(null, "Not logged in!");
    }

    @RequestMapping(value = {"/editReview/{reviewId}", "/editRating/{reviewId}"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> editReview(@PathVariable("reviewId") long reviewId, @RequestBody Review newReview) {
        User user = (User) accountTokenService.getAccount(newReview.getToken());
        if (user != null){
            return new ObjectStatusResponse<>(userService.editAReview(user, reviewId, newReview), "OK");
        }
        else return new ObjectStatusResponse<>("status", "Not logged in!");
    }

    @RequestMapping(value = "/reportReview/{reviewId}", method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> reportReview(@PathVariable("reviewId") long reviewId, @RequestBody ReviewReport report) {
        User user = (User) accountTokenService.getAccount(report.getAccountToken());
        if (user != null){
            userService.reportAReview(report.getReview(), user, report);
            return new ObjectStatusResponse<>(null, "OK");
        }
        else return new ObjectStatusResponse<>(null, "Not logged in!");
    }

    @RequestMapping(value = {"/deleteReview/{reviewId}", "/deleteRating/{reviewId}"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> deleteReview(@PathVariable("reviewId") long reviewId, @RequestBody Token token) {
        User user = (User) accountTokenService.getAccount(token.getToken());
        if (user != null){
            userService.deleteReview(user, reviewId);
            return new ObjectStatusResponse<>(null, "OK");
        }
        else return new ObjectStatusResponse<>(null, "Not logged in!");
    }

    @RequestMapping(value = {"/getWantToSee"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> getWantToSee(@RequestBody Token token){
        User user = (User) accountTokenService.getAccount(token.getToken());
        if (user != null){
            return new ObjectStatusResponse<>(userService.getUserWantToSee(user), "OK");
        }
        else return new ObjectStatusResponse<>(null, "Not logged in!");
    }

    @RequestMapping(value = {"/addToWantToSee"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> addToWantToSee(@RequestBody TokenObjectContainer<Content> contentContainer) {
        User user = (User) accountTokenService.getAccount(contentContainer.getToken());
        if (user != null){
            Content content = contentContainer.getObj();
            userService.addToWantToSee(user, content);
            return new ObjectStatusResponse<>(null, "OK");
        }
        else return new ObjectStatusResponse<>(null, "Not logged in!");
    }

    @RequestMapping(value = {"/removeFromWantToSee"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> removeFromWantToSee(@RequestBody TokenObjectContainer<Content> contentContainer) {
        User user = (User) accountTokenService.getAccount(contentContainer.getToken());
        if (user != null){
            Content content = contentContainer.getObj();
            userService.removeFromWantToSee(user, content);
            return new ObjectStatusResponse<>(null, "OK");
        }
        else return new ObjectStatusResponse<>(null, "Not logged in!");
    }

    @RequestMapping(value = {"/getNotInterested"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> getNotInterested(@RequestBody Token token){
        User user = (User) accountTokenService.getAccount(token.getToken());
        if (user != null){
            return new ObjectStatusResponse<>(userService.getNotInterested(user), "OK");
        }
        else return new ObjectStatusResponse<>(null, "Not logged in!");
    }

    @RequestMapping(value = {"/addToNotInterested"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> addToNotInterested(@RequestBody TokenObjectContainer<Content> contentContainer) {
        User user = (User) accountTokenService.getAccount(contentContainer.getToken());
        if (user != null){
            Content content = contentContainer.getObj();
            userService.addToNotInterested(user, content);
            return new ObjectStatusResponse<>(null, "OK");
        }
        else return new ObjectStatusResponse<>(null, "Not logged in!");
    }

    @RequestMapping(value = {"/removeFromNotInterested"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> removeFromNotInterested(@RequestBody TokenObjectContainer<Content> contentContainer) {
        User user = (User) accountTokenService.getAccount(contentContainer.getToken());
        if (user != null){
            Content content = contentContainer.getObj();
            userService.removeFromNotInterested(user, content);
            return new ObjectStatusResponse<>(null, "OK");
        }
        else return new ObjectStatusResponse<>(null, "Not logged in!");
    }


    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    @ResponseBody
    public ObjectStatusResponse<?> getUserProfile(HttpServletRequest request) {
        User user = (User) accountTokenService.getAccount(request.getHeader("token"));
        if (user != null) {
            // Note:
            // Possibly call updateWantToSee(user) and updateNotInterested(user) ot make sure lists are updated.
            // They should be updated though.
            return new ObjectStatusResponse<>(user, "OK");
        }
        else return new ObjectStatusResponse<>(null, "Not logged in!");
    }

    @RequestMapping(value = {"/{userId}"}, method = RequestMethod.GET)
    @ResponseBody
    public ObjectStatusResponse<?> getUserProfile(@PathVariable("userId") long userId) {
        User user = (User) userService.getUserById(userId);
        if (user != null) {
            // Note:
            // Possibly call updateWantToSee(user) and updateNotInterested(user) ot make sure lists are updated.
            // They should be updated though.
            return new ObjectStatusResponse<>(user, "OK");
        }
        else return new ObjectStatusResponse<>(null, "Not logged in!");
    }
}
