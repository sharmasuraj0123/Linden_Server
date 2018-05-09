package com.linden.controllers;

import com.linden.models.accounts.User;
import com.linden.models.content.Review;
import com.linden.models.content.ReviewReport;
import com.linden.services.UserService;
import com.linden.util.ObjectStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/postReview", method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> postReview(HttpServletRequest request, @RequestBody Review review) {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if (user != null){
            userService.postAReview(user, review);
            return new ObjectStatusResponse<>("status", "OK");
        }
        else return new ObjectStatusResponse<>("status", "Not logged in!");
    }

    @RequestMapping(value = "/editReview/{reviewId}", method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> postReview(HttpServletRequest request, @PathVariable("reviewId") long reviewId,
                                              @RequestBody Review newReview) {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if (user != null){
            userService.editAReview(user, reviewId, newReview);
            return new ObjectStatusResponse<>("status", "OK");
        }
        else return new ObjectStatusResponse<>("status", "Not logged in!");
    }

    @RequestMapping(value = "/reportReview/{reviewId}", method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> reportReview(HttpServletRequest request, @PathVariable("reviewId") long reviewId,
                                              @RequestBody ReviewReport report) {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if (user != null){
            userService.reportAReview(report.getReview(), user, report);
            return new ObjectStatusResponse<>("status", "OK");
        }
        else return new ObjectStatusResponse<>("status", "Not logged in!");
    }

    @RequestMapping(value = "/deleteReview/{reviewId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectStatusResponse<?> deleteReview(HttpServletRequest request, @PathVariable("reviewId") long reviewId) {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if (user != null){
            userService.deleteReview(user, reviewId);
            return new ObjectStatusResponse<>("status", "OK");
        }
        else return new ObjectStatusResponse<>("status", "Not logged in!");
    }


}
