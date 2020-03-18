package cn.com.gary.dataneo4j.api;

import cn.com.gary.dataneo4j.model.ActedIn;
import cn.com.gary.dataneo4j.model.Directed;
import cn.com.gary.dataneo4j.model.Movie;
import cn.com.gary.dataneo4j.model.Person;
import cn.com.gary.dataneo4j.service.MovieService;
import cn.com.gary.model.pojo.RestResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Movie API")
@RestController
@RequestMapping("/v1/movie")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/person")
    public RestResult addPerson(@RequestBody Person person) {
        return new RestResult(movieService.addPerson(person));
    }

    @GetMapping("/person/{id}")
    public RestResult findOnePerson(@PathVariable("id") String id) {
        return new RestResult(movieService.findOnePerson(Long.parseLong(id)));
    }

    @DeleteMapping("/person/{id}")
    public RestResult deleteOnePerson(@PathVariable("id") String id) {
        movieService.deleteOnePerson(Long.parseLong(id));
        return new RestResult("delete success");
    }

    @PostMapping("")
    public RestResult addMovie(@RequestBody Movie movie) {
        return new RestResult(movieService.addMovie(movie));
    }

    @GetMapping("/{id}")
    public RestResult findOneMovie(@PathVariable("id") String id) {
        return new RestResult(movieService.findOneMovie(Long.parseLong(id)));
    }

    @PostMapping("/directed/{personId}/{movieId}")
    public RestResult directed(@PathVariable("personId") String personId, @PathVariable("movieId") String movieId) {
        Person person = movieService.findOnePerson(Long.parseLong(personId));
        Movie movie = movieService.findOneMovie(Long.parseLong(movieId));
        Directed directed = new Directed();
        directed.setStartNode(person);
        directed.setEndNode(movie);
        return new RestResult(movieService.directed(directed));
    }

    @PostMapping("/acted-in/{personId}/{movieId}")
    public RestResult actedIn(@PathVariable("personId") String personId, @PathVariable("movieId") String movieId) {
        Person person = movieService.findOnePerson(Long.parseLong(personId));
        Movie movie = movieService.findOneMovie(Long.parseLong(movieId));
        ActedIn actedIn = new ActedIn();
        actedIn.setRoles("龙套");
        actedIn.setStartNode(person);
        actedIn.setEndNode(movie);
        return new RestResult(movieService.actedIn(actedIn));
    }
}
