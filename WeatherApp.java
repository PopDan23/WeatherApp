//What I need to start: A weather API:OpenWeatherMap, Weather Underground, and AccuWeather, JDK , JavaFX, JSON parsing library
//Popular JSON parsing libraries for Java include Jackson, Gson, and JSON.simple.

//Sign up for a weather API and obtain an API key.
//
//Create a JavaFX project in your IDE.
//
//Use the weather API to retrieve weather data for a specific location using the API key.
//
//Parse the JSON data returned by the weather API using a JSON parsing library.
//
//Display the weather data in a user-friendly format using JavaFX UI components.

module Wheather {
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApp extends Application {

        private static final String API_KEY = "your_api_key_here";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";

    private Label temperatureLabel;
    private Label weatherConditionsLabel;
    private Label windSpeedLabel;

    @Override
            public void start(Stage primaryStage) {
            primaryStage.setTitle("Weather App");

    // Create labels to display weather data
        temperatureLabel = new Label();
        weatherConditionsLabel = new Label();
        windSpeedLabel = new Label();

    // Create a VBox to hold the labels
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        Label temperatureLabel = new Label("Temperature: 20°C");
        Label weatherConditionsLabel = new Label("Weather Conditions: Sunny");
        Label windSpeedLabel = new Label("Wind Speed: 5 km/h");

        vbox.getChildren().addAll(temperatureLabel, weatherConditionsLabel, windSpeedLabel);

        Scene scene = new Scene(vbox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();

    // Create a Scene with the VBox as its root node
        Scene sscene = new Scene(vbox, 300.0, 200.0);

    // Set the Scene for the Stage
        primaryStage.setScene(scene);

    // Show the Stage
        primaryStage.show();

    // Get the weather data for a specific location
        String location = "Iasi";
        WeatherData weatherData = getWeatherData(location);

    // Display the weather data on the GUI
        if (weatherData != null) {
                temperatureLabel.setText("Temperature: " + weatherData.getTemperature() + "°C");
            weatherConditionsLabel.setText("Weather Conditions: " + weatherData.getWeatherConditions());
            windSpeedLabel.setText("Wind Speed: " + weatherData.getWindSpeed() + " km/h");
} else {
        temperatureLabel.setText("Error retrieving weather data");
        }
        }

        private WeatherData getWeatherData(String location) {
        try {
        URL url = new URL(String.format(API_URL, location, API_KEY));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
        response.append(line);
        }

        reader.close();
        conn.disconnect();

        JSONObject jsonObject = new JSONObject(response.toString());
        JSONObject main = jsonObject.getJSONObject("main");
        JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);
        JSONObject wind = jsonObject.getJSONObject("wind");

        double temperature = main.getDouble("temp") - 273.15;
        String weatherConditions = weather.getString("main");
        double windSpeed = wind.getDouble("speed");

        return new WeatherData(temperature, weatherConditions, windSpeed);
        } catch (IOException | JSONException e) {
        e.printStackTrace();
        }

        return null;
        }

        public static void main(String[] args) {
        launch(args);
        }
        }
}