package microservice.movie_data_service;

import microservice.movie_data_service.models.Movie;
import microservice.movie_data_service.models.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/movieservice")
public class MainController {

    @Autowired
    private MovieRepository movieRepository;

    @PostMapping(path="/add")
    public @ResponseBody void addMovie(@RequestParam String name, @RequestParam String genre){
        Movie movie = new Movie();
        movie.setName(name);
        movie.setGenre(genre);
        movieRepository.save(movie);
    }

    @GetMapping(path="/movies/all")
    public @ResponseBody Iterable<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    @GetMapping(path="/movies/name")
    public @ResponseBody Iterable<Movie> getByName(@RequestParam String name){
        return movieRepository.findAllByName(name);
    }

    @GetMapping(path="/movies/genre")
    public @ResponseBody Iterable<Movie> getByGenre(@RequestParam String genre){
        return movieRepository.findAllByGenre(genre);
    }

    @PutMapping(path="/movies/edit")
    public @ResponseBody void editById(@RequestParam Integer id, @RequestParam String name, @RequestParam String genre){
        Movie editedMovie = movieRepository.findById(id).get();
        editedMovie.setName(name);
        editedMovie.setGenre(genre);
        movieRepository.save(editedMovie);
    }

    @DeleteMapping(path="/movies/delete")
    public @ResponseBody void deleteById(@RequestParam Integer id){
        movieRepository.deleteById(id);
    }

}
