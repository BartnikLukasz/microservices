package microservice.movie_data_service.models;

import org.springframework.data.repository.CrudRepository;

//Using CrudRepository for automatically creating functions and sending data to database
public interface MovieRepository extends CrudRepository<Movie, Integer> {

Iterable<Movie> findAllByName(String name);
Iterable<Movie> findAllByGenre(String genre);

}
