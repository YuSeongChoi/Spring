package kr.ac.ssu.eatgo.interfaces;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc; 

    @Test
    public void list() {
        try {
            mvc.perform(get("/restaurants"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(
                            containsString("\"id\":1004")
                    ))
                    .andExpect(content().string(
                            containsString("\"name\":\"Bob zip\"")
                    ));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void detail() {
        try {
            mvc.perform(get("restaurants/1004"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(
                            containsString("\"id\":1004")
                    ))
                    .andExpect(content().string(
                            containsString("\"name\":\"Bob zip\"")
                    ));

            mvc.perform(get("restaurants/2020"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(
                            containsString("\"id\":2020")
                    ))
                    .andExpect(content().string(
                            containsString("\"name\":\"Bob zip\"")
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}