package com.linden.controllers;

import com.linden.models.accounts.Admin;
import com.linden.models.content.Movie;
import com.linden.models.content.TvShow;
import com.linden.services.AccountTokenService;
import com.linden.services.AdminService;
import com.linden.util.StatusResponse;
import com.linden.util.ContentContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AccountTokenService accountTokenService;

    @RequestMapping(value = "/addMovie", method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse addMovie(@RequestBody ContentContainer request){
        Admin admin = (Admin) accountTokenService.getAccount(request.getToken());
        if(admin != null) {
            try {
                adminService.addMovie((Movie)request.getContent());
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
    public StatusResponse addTvShow(@RequestBody ContentContainer request){
        Admin admin = (Admin) accountTokenService.getAccount(request.getToken());
        if(admin != null) {
            try {
                adminService.addTvShow((TvShow)request.getContent());
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

    @RequestMapping(value = "/viewReports", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, ?> viewReports(HttpServletRequest request) {
        Admin admin = (Admin) accountTokenService.getAccount(request.getHeader("token"));
        if(admin != null) {
            HashMap<String, List<?>> response = new HashMap<>();
            response.put("reports", adminService.getReports());
            return response;
        }
        else {
            HashMap<String, String> response = new HashMap<>();
            response.put("status", "OK");
            return response;
        }
    }

    @RequestMapping(value = "/movie/{movieId}/edit", method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse editMovie(@PathVariable("movieId") long movieId, @RequestParam ContentContainer movie) {
        Admin admin = (Admin) accountTokenService.getAccount(movie.getToken());
        if(admin != null) {

        }
        return null;
    }


}
