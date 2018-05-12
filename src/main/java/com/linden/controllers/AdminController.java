package com.linden.controllers;

import com.linden.models.accounts.Admin;
import com.linden.models.accounts.User;
import com.linden.models.content.Content;
import com.linden.models.content.Movie;
import com.linden.models.content.TvShow;
import com.linden.services.AccountTokenService;
import com.linden.services.AdminService;
import com.linden.util.StatusResponse;
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

//    @RequestMapping(value = "/addMovie", method = RequestMethod.POST)
//    @ResponseBody
//    public StatusResponse addMovie(@RequestBody TokenObjectContainer<Content> contentContainer){
//
//        Admin admin = (Admin) accountTokenService.getAccount(contentContainer.getToken());
//
//        if(admin !=  null){
//            try{
//
//                Content content = contentContainer.getObj();
//                Movie movie = (Movie)content;
//
//                adminService.addMovie(movie);
//                return new StatusResponse("OK");
//            } catch (Exception e){
//                e.printStackTrace();
//                return new StatusResponse("ERROR", e.getMessage());
//            }
//        }else{
//
//        }
//
//    }

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

//    @RequestMapping(value = "/deleteMovie", method = RequestMethod.POST)
//    @ResponseBody
//    public StatusResponse deleteMovie(@RequestBody Movie movie){
//        try{
//            adminService.addMovie(movie);
//            return new StatusResponse("OK");
//        } catch (Exception e){
//            e.printStackTrace();
//            return new StatusResponse("ERROR", e.getMessage());
//        }
//    }

}
