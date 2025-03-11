package com.fran.weatherdataapp.service;
import com.fran.weatherdataapp.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.url}")
    private String apiUrl;

    @Value("${openweather.api.city}")
    private String city;

    public String getCity() {
        return city;
    }

    public Double getTemperature() {
        String url = String.format("%s?q=%s&appid=%s&units=imperial", apiUrl, city, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

        if (response != null) {
            return response.getMain().getTemp();
        } else {
            return null;
        }
    }

}