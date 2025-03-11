package com.fran.weatherdataapp.controller;
import com.fran.weatherdataapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/city")
    public ResponseEntity<String> getTemperatureForCity(@RequestParam String city) {
        Double temp = weatherService.getTemperatureForCity(city);
        if (temp != null) {
            return ResponseEntity.ok(String.format("Current temperature in %s is %.2f°F", city, temp));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Temperature data not available.");
        }
    }

    @GetMapping("/cities")
    public ResponseEntity<Map<String, Double>> getTemperaturesForCities(@RequestParam List<String> cities) {
        Map<String, Double> temperatures = weatherService.getTemperaturesForCities(cities);
        return ResponseEntity.ok(temperatures);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Double>> getAllCachedWeatherData() {
        return ResponseEntity.ok(weatherService.getCachedWeatherData());
    }

}
