package com.linden.controllers;

import com.linden.models.content.Movie;
import com.linden.models.content.TvShow;
import com.linden.services.AdminService;
import com.linden.util.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/addMovie", method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse addMovie(@RequestBody Movie movie){
        try{
            adminService.addMovie(movie);
            return new StatusResponse("OK");
        } catch (Exception e){
            e.printStackTrace();
            return new StatusResponse("ERROR", e.getMessage());
        }
    }

    @RequestMapping(value = "/addTvShow", method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse addTvShow(@RequestBody TvShow tvShow){
        try{
            adminService.addTvShow(tvShow);
            return new StatusResponse("OK");
        } catch (Exception e){
            e.printStackTrace();
            return new StatusResponse("ERROR", e.getMessage());
        }
    }
}
