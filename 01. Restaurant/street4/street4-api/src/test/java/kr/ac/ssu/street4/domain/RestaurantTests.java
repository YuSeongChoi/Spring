package kr.ac.ssu.street4.domain;

import org.junit.jupiter.api.Test;


class RestaurantTests {

    @Test
    public void creation() {
        Restaurant restaurant = new Restaurant("Bob zip", "");
    }

    @Test
    public void information() {
        Restaurant restaurant = new Restaurant("Bob zip", "Seoul");
    }
}