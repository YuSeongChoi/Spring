package kr.ac.ssu.eatgo.domain;

import java.util.List;

public interface MenuItemRepository {
    List<MenuItem> findAllByRestaurantId (Long restaurantId);
}
