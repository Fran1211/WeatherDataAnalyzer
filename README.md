Base URL:
http://localhost:8080/weather

Get Temperature for a single city:
GET /weather/city?city={cityName}
Example: GET /weather/city?city=Miami

For multiple cities:
GET /weather/cities?cities={city1},{city2},{city3}
Example: GET /weather/cities?cities=Miami,New York,Los Angeles

Get all cached weather data:
GET /weather/all

Compare temperatures between two cities:
GET /weather/compare?city1={cityName1}&city2={cityName2}
Example: GET /weather/compare?city1=Miami&city2=New York

Get the Hottest/Coldest City from cached data:
GET /weather/hottest
GET /weather/coldest

Get average temperature from cached data:
GET /weather/average

