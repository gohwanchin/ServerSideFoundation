package sg.edu.nus.workshop17.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    // 3 different ways to access API key in env variable

    // set OPEN_WEATHER_MAP {APIKey} in cmd
    // @Value("${open.weather.map}")
    // private String apiKey;

    // public WeatherService() {
    // apiKey = System.getenv("OPEN_WEATHER_MAP");
    // }

    // @PostConstruct
    // public void init() {
    // apiKey = System.getenv("OPEN_WEATHER_MAP");
    // }

}
