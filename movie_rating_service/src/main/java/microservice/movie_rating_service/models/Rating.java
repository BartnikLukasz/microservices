package microservice.movie_rating_service.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Rating {

    @Id
    Integer id;
    Integer rating;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
