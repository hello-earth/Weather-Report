package org.huakai.weatherreport.weatherreport.ParseClass;

import java.util.List;

public class WeatherResults {
    private MyLocation location;

    private List<Daily> daily ;

    private String last_update;

    public void setLocation(MyLocation location){
        this.location = location;
    }
    public MyLocation getLocation(){
        return this.location;
    }
    public void setDaily(List<Daily> daily){
        this.daily = daily;
    }
    public List<Daily> getDaily(){
        return this.daily;
    }
    public void setLast_update(String last_update){
        this.last_update = last_update;
    }
    public String getLast_update(){
        return this.last_update;
    }

}