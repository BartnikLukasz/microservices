package microservice.movie_client_service;

import resource.ClientResources;
import resource.Movie;

import java.util.Arrays;

public class ClientApplication {

    public static void main(String[] args) {

        ClientResources client = new ClientResources();

        Arrays.stream(client.getAllMovies())
                .forEach(m -> {
                    String s = m.getId() + ", " + m.getName() + ", " + m.getGenre();
                    s = ClientResources.decodeResponse(s);
                    System.out.println(s);
                });

        client.deleteMovieById(8);

        Arrays.stream(client.getAllMovies())
                .forEach(m -> {
                    String s = m.getId() + ", " + m.getName() + ", " + m.getGenre();
                    s = ClientResources.decodeResponse(s);
                    System.out.println(s);
                });

    }

}
