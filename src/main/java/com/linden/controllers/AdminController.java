package com.linden.controllers;

import com.linden.models.Movie;
import com.linden.services.AdminService;
import com.linden.util.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    public void addMovie(HttpServletRequest request, HttpServletResponse response,
                            @RequestBody Movie movie){

        adminService.addMovie(movie);


    }
}
