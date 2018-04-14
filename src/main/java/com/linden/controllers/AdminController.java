package com.linden.controllers;

import com.linden.models.Movie;
import com.linden.services.AdminService;
import com.linden.util.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/addMovie", method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse addMovie(HttpServletRequest request, HttpServletResponse response,
                                   @RequestBody Movie movie){
        System.out.println("Name = "+movie.getName());
        System.out.println("Release Date = "+movie.getReleaseDate());
        System.out.println("Duration = "+movie.getDuration());
        System.out.println("Cast = "+movie.getCast());
        System.out.println("Genre = "+movie.getGenre());
        System.out.println("Score = "+movie.getScore());
        try{
            adminService.addMovie(movie);
            return new StatusResponse("OK");
        } catch (Exception e){
            e.printStackTrace();
            return new StatusResponse("ERROR", e.getMessage());
        }
    }
}
