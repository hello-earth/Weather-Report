package org.huakai.weatherreport.weatherreport.ParseClass;


public class WeatherNow {
    private String text;

    private String code;

    private String temperature;

    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
    public void setTemperature(String temperature){
        this.temperature = temperature;
    }
    public String getTemperature(){
        return this.temperature;
    }

}
