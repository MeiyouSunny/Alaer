package com.alaer.lib.api.bean;

import java.util.ArrayList;
import java.util.List;

public class Province {

    public String name;
    public int code;
    public List<City> citys = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }

    public static class City {
        public String name;
        public int code;
        public List<Region> regions = new ArrayList<>();

        @Override
        public String toString() {
            return name;
        }
    }

    public static class Region {
        public String name;
        public int code;

        @Override
        public String toString() {
            return name;
        }
    }

    public static class Entity {
        public String name;
        public int code;

    }

}
