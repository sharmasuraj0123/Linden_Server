package controllers;

import models.DataResponse;
import models.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Welcome to Linden!";
    }

    @GetMapping("/featured_movies")
    @ResponseBody
    public DataResponse getMovies(){

        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie("Kumki",4,"./images/kumki.jpeg"));
        movieList.add(new Movie("MS Dhoni",5,"./images/dhoni.jpeg"));
        movieList.add(new Movie("Arjun Reddy",5,"./images/Reddy.jpg"));
        movieList.add(new Movie("Interstellar",5,"./images/interstellar.jpeg"));
        movieList.add(new Movie("Thor Ragnarok",5,"./images/thor.jpg"));
        movieList.add(new Movie("Rush",5,"./images/rush.jpeg"));
        movieList.add(new Movie("Orient Express",2,"./images/murder.jpeg"));
        
        return new DataResponse(true, movieList.size(), movieList);
    }

}