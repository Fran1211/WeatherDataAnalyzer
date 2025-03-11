package com.fran.weatherdataapp.controller;
import com.fran.weatherdataapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/temperature")
    public String getTemperature() {
        Double temp = weatherService.getTemperature();
        if (temp != null) {
            return String.format("Current temperature in %s is %.2f°F", weatherService.getCity(), temp);
        } else {
            return "Temperature data not available.";
        }
    }
}
