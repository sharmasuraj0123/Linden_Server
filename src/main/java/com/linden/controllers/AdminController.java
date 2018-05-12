package com.linden.controllers;

import com.linden.models.accounts.Admin;
import com.linden.models.content.Movie;
import com.linden.models.content.TvShow;
import com.linden.services.AccountTokenService;
import com.linden.services.AdminService;
import com.linden.util.StatusResponse;
import com.linden.util.Token;
import com.linden.util.TokenObjectContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AccountTokenService accountTokenService;

    @RequestMapping(value = "/addMovie", method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse addMovie(@RequestBody TokenObjectContainer<Movie> request){
        Admin admin = (Admin) accountTokenService.getAccount(request.getToken());
        if(admin != null) {
            try {
                adminService.addMovie(request.getObj());
                return new StatusResponse("OK");
            } catch (Exception e) {
                e.printStackTrace();
                return new StatusResponse("ERROR", e.getMessage());
            }
        }
        else{
            return new StatusResponse("ERROR", "Login as admin failed!");
        }
    }

    @RequestMapping(value = "/addTvShow", method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse addTvShow(@RequestBody TokenObjectContainer<TvShow> request){
        Admin admin = (Admin) accountTokenService.getAccount(request.getToken());
        if(admin != null) {
            try {
                adminService.addTvShow(request.getObj());
                return new StatusResponse("OK");
            } catch (Exception e) {
                e.printStackTrace();
                return new StatusResponse("ERROR", e.getMessage());
            }
        }
        else{
            return new StatusResponse("ERROR", "Login as admin failed!");
        }
    }

    @RequestMapping(value = "/movie/{movieId}/edit", method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse editMovie(@PathVariable("movieId") long movieId, @RequestParam TokenObjectContainer<Movie> movie) {
        Admin admin = (Admin) accountTokenService.getAccount(movie.getToken());
        if(admin != null) {

        }
        return null;
    }
}
