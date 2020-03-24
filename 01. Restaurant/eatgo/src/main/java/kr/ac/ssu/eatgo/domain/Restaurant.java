package kr.ac.ssu.eatgo.domain;

public class Restaurant {


    private final String name;
    private final String address;
    private final Long id;

    public Restaurant(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Long getID() {
        return id;
    }

    public String getInformation() {
        return name + " , " + address;
    }
}
