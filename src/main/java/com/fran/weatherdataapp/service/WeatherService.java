package com.fran.weatherdataapp.service;
import com.fran.weatherdataapp.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    private final Map<String, Double> weatherCache = new HashMap<>();


    public Double getTemperatureForCity(String city) {
        String url = String.format("%s?q=%s&appid=%s&units=imperial", apiUrl, city, apiKey);
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

        if (response != null && response.getMain() != null) {
            Double temp = response.getMain().getTemp();
            weatherCache.put(city, temp);  // Cache result
            return temp;
        }
        return null;
    }

    public Map<String, Double> getTemperaturesForCities(List<String> cities) {
        Map<String, Double> results = new HashMap<>();
        for (String city : cities) {
            Double temp = getTemperatureForCity(city);
            if (temp != null) {
                results.put(city, temp);
            }
        }
        return results;
    }

    public Map<String, Double> getCachedWeatherData() {
        return weatherCache;
    }
}