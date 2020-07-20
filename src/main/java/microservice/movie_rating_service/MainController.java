package microservice.movie_rating_service;

import microservice.movie_rating_service.models.Rating;
import microservice.movie_rating_service.models.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/ratingservice")
public class MainController {

    @Autowired
    private RatingRepository ratingRepository;

    @PostMapping(path="/add")
    public @ResponseBody void addRating(@RequestParam Integer id, @RequestParam Integer rating){
        Rating r = new Rating();
        r.setId(id);
        r.setRating(rating);
        ratingRepository.save(r);
    }

    @GetMapping(path="/rating")
    public @ResponseBody Integer getRating(@RequestParam Integer id){
        return ratingRepository.findById(id).get().getRating();
    }

    @PutMapping(path="/edit")
    public @ResponseBody void editRating(@RequestParam Integer id, @RequestParam Integer rating){
        Rating r = ratingRepository.findById(id).get();
        r.setRating(rating);
        ratingRepository.save(r);
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody void delete(@RequestParam Integer id){
        ratingRepository.deleteById(id);
    }

}
