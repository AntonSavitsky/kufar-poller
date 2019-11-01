package kufar.service;

public class PropertyService {

    public String getProperty(String key) {
        return System.getenv(key);
    }
}
