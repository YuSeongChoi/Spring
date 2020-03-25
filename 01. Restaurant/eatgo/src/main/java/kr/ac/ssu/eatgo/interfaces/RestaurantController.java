package kr.ac.ssu.eatgo.interfaces;

import kr.ac.ssu.eatgo.domain.Restaurant;
import kr.ac.ssu.eatgo.domain.RestaurantRepository;
import kr.ac.ssu.eatgo.domain.RestaurantRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //Spring이 직접 관리
public class RestaurantController {

    @Autowired //Spring IoC 자동연결
    private RestaurantRepository repository;

    @GetMapping("/restaurants")
    public List<Restaurant> list() {
        List<Restaurant> restaurants = repository.findAll();
        return restaurants;
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id) {
        Restaurant restaurant = repository.findById(id);
        return restaurant;
    }

}
