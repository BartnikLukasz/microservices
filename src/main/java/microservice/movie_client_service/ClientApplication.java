package microservice.movie_client_service;

import resource.ClientResources;
import resource.Movie;

import java.util.*;

public class ClientApplication {

    public static void main(String[] args) {

        ClientResources client = new ClientResources();

        Map<Movie, Integer> results;
        Movie movie = new Movie();
        movie.setName("Movie6");
        movie.setGenre("Thriller");
        client.addMovie(movie,8);

        results = client.getAllMovies();

        Set<Movie> movies = results.keySet();
        Collection<Integer> ratings = results.values();

        Iterator<Movie> itrMovie = movies.iterator();
        Iterator<Integer> itrRating = ratings.iterator();


        while (itrMovie.hasNext()) {
            System.out.println(itrMovie.next() + ", Rating: "+itrRating.next());
        }


        results = client.getAllMovies();

        movies = results.keySet();
        ratings = results.values();

        itrMovie = movies.iterator();
        itrRating = ratings.iterator();


        while (itrMovie.hasNext()) {
            System.out.println(itrMovie.next() + ", Rating: "+itrRating.next());
        }

    }

}
