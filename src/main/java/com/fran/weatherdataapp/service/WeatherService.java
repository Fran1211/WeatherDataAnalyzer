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
            weatherCache.put(city, temp);
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


    public String compareTemperatures(String city1, String city2) {
        Double temp1 = weatherCache.get(city1);
        Double temp2 = weatherCache.get(city2);

        if (temp1 == null || temp2 == null) {
            return "Temperature data not available for one or both cities.";
        }

        double difference = Math.abs(temp1 - temp2);
        if (temp1 > temp2) {
            return String.format("%s is hotter than %s by %.2f°F", city1, city2, difference);
        } else if (temp1 < temp2) {
            return String.format("%s is colder than %s by %.2f°F", city1, city2, difference);
        } else {
            return String.format("%s and %s have the same temperature: %.2f°F", city1, city2, temp1);
        }
    }

    public String getHottestCity() {
        return weatherCache.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> String.format("Hottest city: %s with %.2f°F", entry.getKey(), entry.getValue()))
                .orElse("No data available.");
    }

    public String getColdestCity() {
        return weatherCache.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(entry -> String.format("Coldest city: %s with %.2f°F", entry.getKey(), entry.getValue()))
                .orElse("No data available.");
    }

    public String getAverageTemperature() {
        if (weatherCache.isEmpty()) {
            return "No data available.";
        }

        double average = weatherCache.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        return String.format("Average temperature across %d cities: %.2f°F", weatherCache.size(), average);
    }
}