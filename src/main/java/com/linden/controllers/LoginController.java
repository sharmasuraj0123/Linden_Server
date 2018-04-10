package com.linden.controllers;

import com.linden.models.User;
import com.linden.services.UserService;
import com.linden.util.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse login(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody User user) {
        System.out.println("Username = "+user.getUsername());
        System.out.println("password = "+user.getPassword());
        HttpSession session = request.getSession(true);
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            User userInDatabase = userService.getUserByUsername(user.getUsername());
            if(userInDatabase == null){
                System.out.println("User not found!!!");
                return new StatusResponse("ERROR", "Not registered!");
            }
            else {
                if(userInDatabase.isVerifiedAccount()) {
                    session.setAttribute("user", userInDatabase);
                    return new StatusResponse("OK");
                }
                else{
                    return new StatusResponse("ERROR", "Account not yet verified.");
                }
            }
        } else {
            System.out.println("User found!!!");
            return new StatusResponse("ERROR", "Already logged in!");
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public StatusResponse logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        return new StatusResponse("OK", "Logged out.");
    }
}
