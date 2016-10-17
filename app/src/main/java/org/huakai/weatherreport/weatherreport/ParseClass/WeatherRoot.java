package org.huakai.weatherreport.weatherreport.ParseClass;

import java.util.List;


public class WeatherRoot {
    private List<WeatherResults> results ;

    public void setResults(List<WeatherResults> results){
        this.results = results;
    }
    public List<WeatherResults> getResults(){
        return this.results;
    }
}
