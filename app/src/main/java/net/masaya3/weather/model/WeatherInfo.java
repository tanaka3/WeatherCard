package net.masaya3.weather.model;

import java.util.Date;

import io.realm.annotations.PrimaryKey;

/**
 * Created by masaya3 on 2017/08/05.
 */

public class WeatherInfo {

    @PrimaryKey
    private int temperature = 0;
    private int rainy = 0;
    private String weather = "";
    private Date time;
    private String address;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getRainy() {
        return rainy;
    }

    public void setRainy(int rainy) {
        this.rainy = rainy;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
