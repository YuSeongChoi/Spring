package kr.ac.ssu.eatgo.interfaces;

import kr.ac.ssu.eatgo.application.RestaurantService;
import kr.ac.ssu.eatgo.domain.MenuItem;
import kr.ac.ssu.eatgo.domain.Restaurant;
import kr.ac.ssu.eatgo.domain.RestaurantNotFoundException;
import kr.ac.ssu.eatgo.domain.Review;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void createWithValidData() {
        try {
            given(restaurantService.addRestaurant(any())).will(invocation -> {
                Restaurant restaurant = invocation.getArgument(0);
                return Restaurant.builder()
                        .id(1004L)
                        .name(restaurant.getName())
                        .address(restaurant.getAddress())
                        .build();
            });

            mvc.perform(post("/restaurants")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"Beryong\", \"address\": \"Busan\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("location", "/restaurants/1004"))
                    .andExpect(content().string("{}"))
                    .andDo(print());

            verify(restaurantService).addRestaurant(any());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createWithInValidData() {
        try {
            mvc.perform(post("/restaurants")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"\", \"address\": \"\"}"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void list() {
        try {
            //즉, 실제의 값을 관리하는것이 아니라 restaurantService를 사용하는지에 대한 TEST만 한다.
            List<Restaurant> restaurants = new ArrayList<>();

            given(restaurantService.getRestaurants()).willReturn(restaurants);

            mvc.perform(get("http://localhost:8080/restaurants/"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(
                            containsString("\"id\":1004")
                    ))
                    .andExpect(content().string(
                            containsString("\"name\":\"JOKER House\"")
                    ));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void detailWithExisted() {
        try {
//            Restaurant restaurant1 = new Restaurant(1004L, "JOKER House", "Seoul");
            Restaurant restaurant = Restaurant.builder()
                    .id(1004L)
                    .name("JOKER House")
                    .address("Seoul")
                    .build();
            MenuItem menuItem = MenuItem.builder()
                    .name("Kimchi")
                    .build();
            restaurant.setMenuItems(Arrays.asList(menuItem));
            Review review = Review.builder()
                    .name("JOKER")
                    .score(5)
                    .description("Great!")
                    .build();
            restaurant.setReviews(Arrays.asList(review));

            given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);


            mvc.perform(get("http://localhost:8080/restaurants/1004"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(
                            containsString("\"id\":1004")
                    ))
                    .andExpect(content().string(
                            containsString("\"name\":\"JOKER House\"")
                    ))
                    .andExpect(content().string(
                            containsString("Kimchi")
                    ))
                    .andExpect(content().string(
                            containsString("Great!")
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void detailWithNoteExisted() throws Exception {
        given(restaurantService.getRestaurant(404L))
                .willThrow(new RestaurantNotFoundException(404L));
        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }

    @Test
    public void updateWithValidData() {
        try {
            mvc.perform(patch("/restaurants/1004")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"JOKER Bar\",\"address\":\"Busan\"}"))
                    .andExpect(status().isOk());

            verify(restaurantService).updateRestaurant(1004L, "JOKER Bar", "Busan");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateWithInValidData() {
        try {
            mvc.perform(patch("/restaurants/1004")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"\",\"address\":\"\"}"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateWithoutName() {
        try {
            mvc.perform(patch("/restaurants/1004")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"\",\"address\":\"Busan\"}"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}