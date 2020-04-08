package kr.ac.ssu.eatgo.interfaces;

import kr.ac.ssu.eatgo.application.RestaurantService;
import kr.ac.ssu.eatgo.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() {
        try {
            //즉, 실제의 값을 관리하는것이 아니라 restaurantService를 사용하는지에 대한 TEST만 한다.
            List<Restaurant> restaurants = new ArrayList<>();
            restaurants.add(new Restaurant(1004L, "JOKER House", "Seoul"));
            given(restaurantService.getRestaurants()).willReturn(restaurants);

            mvc.perform(get("http://localhost:8080/restaurants"))
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
    public void detail() {
        try {
            Restaurant restaurant1 = new Restaurant(1004L, "JOKER House", "Seoul");
            restaurant1.addMenuItem(new MenuItem("Kimchi"));

            Restaurant restaurant2 = new Restaurant(2020L, "Cyber Food", "Seoul");

            given(restaurantService.getRestaurant(1004L)).willReturn(restaurant1);
            given(restaurantService.getRestaurant(2020L)).willReturn(restaurant2);


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
                    ));

            mvc.perform(get("http://localhost:8080/restaurants/2020"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(
                            containsString("\"id\":2020")
                    ))
                    .andExpect(content().string(
                            containsString("\"name\":\"Cyber Food\"")
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void create() {
        try {
            mvc.perform(post("/restaurants/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"Beryong\",\"address\":\"Busan\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("location","/restaurants/1234"))
                    .andExpect(content().string("{}"));

            verify(restaurantService).addRestaurant(any());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update() {
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
}