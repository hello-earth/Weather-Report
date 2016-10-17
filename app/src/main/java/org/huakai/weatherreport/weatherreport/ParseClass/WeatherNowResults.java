package org.huakai.weatherreport.weatherreport.ParseClass;

public class WeatherNowResults {
    private MyLocation location;

    private WeatherNow now;

    private String last_update;

    public void setLocation(MyLocation location){
        this.location = location;
    }
    public MyLocation getLocation(){
        return this.location;
    }
    public void setNow(WeatherNow now){
        this.now = now;
    }
    public WeatherNow getNow(){
        return this.now;
    }
    public void setLast_update(String last_update){
        this.last_update = last_update;
    }
    public String getLast_update(){
        return this.last_update;
    }
}
