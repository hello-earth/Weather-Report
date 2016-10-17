package org.huakai.weatherreport.weatherreport.ParseClass;


import java.util.List;

public class WeatherNowRoot {
    private List<WeatherNowResults> results ;

    public void setResults(List<WeatherNowResults> results){
        this.results = results;
    }
    public List<WeatherNowResults> getResults(){
        return this.results;
    }
}
