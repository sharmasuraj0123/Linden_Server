package com.linden.controllers;

import com.linden.models.accounts.User;
import com.linden.models.accounts.UserType;
import com.linden.models.content.Content;
import com.linden.models.content.Review;
import com.linden.models.content.ReviewReport;
import com.linden.services.AccountTokenService;
import com.linden.services.UserService;
import com.linden.services.VerificationService;
import com.linden.util.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Autowired
    private VerificationService verificationService;

    @Autowired
    public JavaMailSender emailSender;

    @RequestMapping(value = {"/postReview", "/postRating"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> postReview(@RequestBody Review review) {
        User user = (User) accountTokenService.getAccount(review.getToken());
        if (user != null) {

            return new ObjectStatusResponse<>(userService.postAReview(user, review), "OK");
        }
        return new ObjectStatusResponse<>(null, "Not logged in!");
    }

    @RequestMapping(value = {"/editReview/{reviewId}", "/editRating/{reviewId}"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> editReview(@PathVariable("reviewId") long reviewId, @RequestBody Review newReview) {
        User user = (User) accountTokenService.getAccount(newReview.getToken());
        if (user != null){
            return new ObjectStatusResponse<>(userService.editAReview(user, reviewId, newReview), "OK");
        }
        return new ObjectStatusResponse<>("status", "Not logged in!");
    }

    @RequestMapping(value = "/reportReview/{reviewId}", method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> reportReview(@PathVariable("reviewId") long reviewId, @RequestBody ReviewReport report) {
        User user = (User) accountTokenService.getAccount(report.getAccountToken());
        if (user != null){
            userService.reportAReview(report.getReview(), user, report);
            return new ObjectStatusResponse<>(null, "OK");
        }
        return new ObjectStatusResponse<>(null, "Not logged in!");
    }

    @RequestMapping(value = {"/deleteReview/{reviewId}", "/deleteRating/{reviewId}"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> deleteReview(@PathVariable("reviewId") long reviewId, @RequestBody Token token) {
        User user = (User) accountTokenService.getAccount(token.getToken());
        if (user != null){
            userService.deleteReview(user, reviewId);
            return new ObjectStatusResponse<>(null, "OK");
        }
        return new ObjectStatusResponse<>(null, "Not logged in!");
    }

    @RequestMapping(value = {"/getWantToSee"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> getWantToSee(@RequestBody Token token){
        User user = (User) accountTokenService.getAccount(token.getToken());
        if (user != null){
            return new ObjectStatusResponse<>(userService.getUserWantToSee(user), "OK");
        }
        return new ObjectStatusResponse<>(null, "Not logged in!");
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
        return new ObjectStatusResponse<>(null, "Not logged in!");
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
        return new ObjectStatusResponse<>(null, "Not logged in!");
    }

    @RequestMapping(value = {"/getNotInterested"}, method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> getNotInterested(@RequestBody Token token){
        User user = (User) accountTokenService.getAccount(token.getToken());
        if (user != null){
            return new ObjectStatusResponse<>(userService.getNotInterested(user), "OK");
        }
        return new ObjectStatusResponse<>(null, "Not logged in!");
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
        return new ObjectStatusResponse<>(null, "Not logged in!");
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
        return new ObjectStatusResponse<>(null, "Not logged in!");
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
        return new ObjectStatusResponse<>(null, "Not logged in!");
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
        return new ObjectStatusResponse<>(null, "User not found!");
    }

    @RequestMapping(value = {"/editCredentials"}, method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse editCredentials(@RequestBody UserCredentials userCredentials) {
        User user = (User) accountTokenService.getAccount(userCredentials.getToken());
        if(user != null) {
            userService.changeUserCredentials(user, userCredentials);
            return new StatusResponse("OK");
        }
        return new StatusResponse("Error", "Invalid user token.");
    }

    @RequestMapping(value = {"/forgotPassword"}, method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse forgotPassword(@RequestBody Token token) {
        User user = (User) accountTokenService.getAccount(token.getToken());
        if(user != null) {
            String temporaryPassword = verificationService.generateToken();
            UserCredentials userCredentials = new UserCredentials();
            userCredentials.setPassword(temporaryPassword);
            userService.changeUserCredentials(user, userCredentials);
            sendResetPasswordEmail(user, temporaryPassword);
        }
        return new StatusResponse("Error", "Invalid user token.");
    }

    private void sendResetPasswordEmail(User user, String password) {
        if(user != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Linden Password Reset");
            message.setText("Hello "+user.getFirstName()+"!\n\n" +
                    "\tHere is your new password. This new password can be used to login and change your password " +
                    "through the user menu.\n\tPassword: "+password+"\n\n" +
                    "Regards,\n" +
                    "Linden Team");
            emailSender.send(message);
        }
    }

    @RequestMapping(value = {"/deleteAccount"}, method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse deleteAccount(@RequestBody Token token) {
        User user = (User) accountTokenService.getAccount(token.getToken());
        if(user != null) {
            userService.deleteAccount(user);
            return new StatusResponse("OK");
        }
        return new StatusResponse("Error", "Invalid user token.");
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


    @RequestMapping(value = {"/applyForPromotion"}, method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse applyForPromotion(@RequestBody Token token) {
        User user = (User) accountTokenService.getAccount(token.getToken());
        if(user != null) {
            switch (user.getUserType()){
                case AUDIENCE:
                    userService.applyForPromotion(user.getId(), UserType.CRITIC);
                    break;
                case CRITIC:
                    userService.applyForPromotion(user.getId(), UserType.TOPCRITIC);
                    break;
            }
            return new StatusResponse("OK");
        }
        return new StatusResponse("Error", "Invalid user token.");
    }
}
