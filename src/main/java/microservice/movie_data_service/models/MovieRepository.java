package microservice.movie_data_service.models;

import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

Iterable<Movie> findAllByName(String name);
Iterable<Movie> findAllByGenre(String genre);

}
