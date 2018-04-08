package com.linden.controllers;

import com.linden.models.DataResponse;
import com.linden.models.Movie;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MovieController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception {
        model.addAttribute("accept", "text/plain");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return "Welcome to Linden!";
    }

    @GetMapping("/movies/featured")
    @ResponseBody
    public DataResponse getMovies(){

        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie("Kumki",4,"./images/kumki.jpeg"));
        movieList.add(new Movie("MS Dhoni",5,"./images/dhoni.jpeg"));
        movieList.add(new Movie("Arjun Reddy",5,"./images/Reddy.jpeg"));
        movieList.add(new Movie("Interstellar",5,"./images/interstellar.jpeg"));
        movieList.add(new Movie("Thor Ragnarok",5,"./images/thor.jpg"));
        movieList.add(new Movie("Orient Express",2,"./images/murder.jpeg"));
        movieList.add(new Movie("Rush",5,"./images/rush.jpeg"));

        return new DataResponse(true, movieList.size(), movieList);
    }

}