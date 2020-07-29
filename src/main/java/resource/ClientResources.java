package resource;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

//Class with functions responsible for sending requests to first microservice
public class ClientResources {

    public String addMovie(Movie movie, Integer rating){
        String url = "http://localhost:8081/movieservice/add";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("name", movie.getName());
        map.add("genre",movie.getGenre());
        map.add("ratingS", rating.toString());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url,request,String.class);

        return response.getStatusCode().toString();
    }

    public Map<Movie,Integer> getAllMovies(){
        String url = "http://localhost:8081/movieservice/movies/all";

        RestTemplate restTemplate = new RestTemplate();

        Map<Movie,Integer> result = new HashMap<>();

        return restTemplate.getForObject(url,result.getClass());

    }

    public Map<Movie,Integer> getMoviesByName(String name){
        String url = "http://localhost:8081/movieservice/movies/name";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("name", name);
        HttpEntity<?> entity = new HttpEntity<>(headers);


        RestTemplate restTemplate = new RestTemplate();
        Map<Movie,Integer> result = new HashMap<>();

        return restTemplate.getForObject(builder.toUriString(),result.getClass(),entity);

    }

    public Map<Movie,Integer> getMoviesByGenre(String genre){
        String url = "http://localhost:8081/movieservice/movies/genre";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("genre", genre);
        HttpEntity<?> entity = new HttpEntity<>(headers);


        RestTemplate restTemplate = new RestTemplate();
        Map<Movie,Integer> result = new HashMap<>();

        return restTemplate.getForObject(builder.toUriString(),result.getClass(),entity);

    }

    public String editMovieById(Integer id, String name, String genre, Integer rating){
        String url = "http://localhost:8081/movieservice/movies/edit";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("id", id)
                .queryParam("name", name)
                .queryParam("genre", genre)
                .queryParam("rating", rating);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, String.class);

        return response.toString();

    }

    public String deleteMovieById(Integer id){
        String url = "http://localhost:8081/movieservice/movies/delete";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("id", id);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class);

        return  response.toString();
    }


    public static String decodeResponse(String response) {
        try {
            return URLDecoder.decode(response, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
