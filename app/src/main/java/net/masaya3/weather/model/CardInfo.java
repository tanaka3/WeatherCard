package net.masaya3.weather.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by masaya3 on 2017/08/05.
 */

public class CardInfo extends RealmObject {

    @PrimaryKey
    private int cardId = 0;
    private String weather = "";
    private int temperature = 0;
    private int rainy = 0;
    private String detail = "";
    private String name = "";
    private long time = 0;
    private int type = 0;
    private String address = "";

    public long getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public long getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public long getRainy() {
        return rainy;
    }

    public void setRainy(int rainy) {
        this.rainy = rainy;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getType() {
        return type;
    }

    public void setType(int isPrint) {
        this.type = isPrint;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
