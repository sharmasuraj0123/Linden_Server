package com.linden.controllers;

import com.linden.models.Movie;
import com.linden.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/search/{keyword}")
    @ResponseBody
    public Iterable<Movie> search(@PathVariable("keyword") String keyword){
        return movieRepository.findMoviesByNameContains(keyword);
    }

}