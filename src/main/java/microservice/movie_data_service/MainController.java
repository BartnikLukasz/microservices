package microservice.movie_data_service;

import microservice.movie_data_service.models.Movie;
import microservice.movie_data_service.models.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Controller
@RequestMapping(path="/movieservice")
public class MainController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    RestTemplate restTemplate;

    @PostMapping(path="/add")
    public @ResponseBody void addMovie(@RequestParam String name, @RequestParam String genre, @RequestParam String ratingS ){
        Movie movie = new Movie();
        movie.setName(name);
        movie.setGenre(genre);
        movieRepository.save(movie);

        Integer rating = Integer.valueOf(ratingS);
        String url = "http://localhost:8082/ratingservice/add";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
        map.add("id", movie.getId());
        map.add("rating", rating);

        HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);

        restTemplate.postForEntity(url,request,String.class);

    }

    @GetMapping(path="/movies/all")
    public @ResponseBody Map<Movie, Integer> getAllMovies(){
        Iterable<Movie> tempMovies =  movieRepository.findAll();
        List<Integer> tempRatings = new ArrayList<Integer>();

        String url = "http://localhost:8082/ratingservice/rating";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        tempMovies.forEach(m->{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("id", m.getId());
            HttpEntity<?> entity = new HttpEntity<>(headers);
            Integer rating =restTemplate.getForObject(builder.toUriString(), Integer.class, entity);
            tempRatings.add(rating);
        });

        List<Movie> movies = new ArrayList<>();
        tempMovies.forEach(movies::add);

        Map<Movie,Integer> movieIntegerMap = new HashMap<Movie,Integer>();

        for(int i=0; i<movies.size();i++){
            movieIntegerMap.put(movies.get(i), tempRatings.get(i));
        }

        return movieIntegerMap;

    }

    @GetMapping(path="/movies/name")
    public @ResponseBody Map<Movie, Integer> getByName(@RequestParam String name){
        Iterable<Movie> tempMovies =  movieRepository.findAllByName(name);
        List<Integer> tempRatings = new ArrayList<Integer>();

        String url = "http://localhost:8082/ratingservice/rating";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        tempMovies.forEach(m->{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("id", m.getId());
            HttpEntity<?> entity = new HttpEntity<>(headers);
            Integer rating =restTemplate.getForObject(builder.toUriString(), Integer.class, entity);
            tempRatings.add(rating);
        });

        List<Movie> movies = new ArrayList<>();
        tempMovies.forEach(movies::add);

        Map<Movie,Integer> movieIntegerMap = new HashMap<Movie,Integer>();

        for(int i=0; i<movies.size();i++){
            movieIntegerMap.put(movies.get(i), tempRatings.get(i));
        }

        return movieIntegerMap;
    }

    @GetMapping(path="/movies/genre")
    public @ResponseBody Map<Movie, Integer> getByGenre(@RequestParam String genre){
        Iterable<Movie> tempMovies =  movieRepository.findAllByGenre(genre);
        List<Integer> tempRatings = new ArrayList<Integer>();

        String url = "http://localhost:8082/ratingservice/rating";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        tempMovies.forEach(m->{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("id", m.getId());
            HttpEntity<?> entity = new HttpEntity<>(headers);
            Integer rating =restTemplate.getForObject(builder.toUriString(), Integer.class, entity);
            tempRatings.add(rating);
        });

        List<Movie> movies = new ArrayList<>();
        tempMovies.forEach(movies::add);

        Map<Movie,Integer> movieIntegerMap = new HashMap<Movie,Integer>();

        for(int i=0; i<movies.size();i++){
            movieIntegerMap.put(movies.get(i), tempRatings.get(i));
        }

        return movieIntegerMap;
    }

    @PutMapping(path="/movies/edit")
    public @ResponseBody void editById(@RequestParam Integer id, @RequestParam String name, @RequestParam String genre, @RequestParam Integer rating){
        Movie editedMovie = movieRepository.findById(id).get();
        editedMovie.setName(name);
        editedMovie.setGenre(genre);
        movieRepository.save(editedMovie);

        String url = "http://localhost:8082/ratingservice/edit";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("id", id)
                .queryParam("rating", rating);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, String.class);

    }

    @DeleteMapping(path="/movies/delete")
    public @ResponseBody void deleteById(@RequestParam Integer id){
        movieRepository.deleteById(id);

        String url = "http://localhost:8082/ratingservice/delete";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("id", id);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);

    }

}
