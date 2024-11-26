package com.meraprojects.weatherapplication;

public class WeatherRVModel {
    private String time;
   private String temperature;
    private String condition;
    private String windspeed;
    private String icon;
    public WeatherRVModel(String time,String temperature,String condition,String windspeed,String icon){
        this.time=time;
        this.temperature=temperature;
        this.condition=condition;
        this.windspeed=windspeed;
        this.icon=icon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
