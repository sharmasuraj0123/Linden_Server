package com.linden.controllers;

import com.linden.models.User;
import com.linden.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.session.Session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Hello world!!!");
        return "hello world!!!";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> login(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody User user) {
        System.out.println("Username = "+user.getUsername());
        System.out.println("password = "+user.getPassword());
        System.out.println("Email = "+user.getEmail());
        String status = "error";
        HttpSession session = request.getSession(true);
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            session.setAttribute("user", user);
            System.out.println("User not found!!!");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            System.out.println("User found!!!");
            return new ResponseEntity<>("Already logged in!", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String status = "error";
        HttpSession session = request.getSession();
        session.invalidate();
        return new ResponseEntity<>("Logged out", HttpStatus.OK);
    }
}
