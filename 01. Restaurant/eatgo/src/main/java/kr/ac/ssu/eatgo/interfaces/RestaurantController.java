package kr.ac.ssu.eatgo.interfaces;

import kr.ac.ssu.eatgo.application.RestaurantService;
import kr.ac.ssu.eatgo.domain.MenuItem;
import kr.ac.ssu.eatgo.domain.MenuItemRepository;
import kr.ac.ssu.eatgo.domain.Restaurant;
import kr.ac.ssu.eatgo.domain.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //Spring이 직접 관리
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        return restaurants;
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        // 기본 정보 + 메뉴 정보

//        Restaurant restaurant = restaurantRepository.findById(id);
//
//        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
//        restaurant.setMenuItems(menuItems);

        return restaurant;
    }

}
