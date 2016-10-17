package org.huakai.weatherreport.weatherreport.ParseClass;

/**
 * Created by xinhua on 2016-10-11 0011.
 */
public class ARIResults {
    private int aqi;

    private String area;

    private int pm2_5;

    private int pm2_5_24h;

    private String position_name;

    private String primary_pollutant;

    private String quality;

    private String station_code;

    private String time_point;

    public void setAqi(int aqi){
        this.aqi = aqi;
    }
    public int getAqi(){
        return this.aqi;
    }
    public void setArea(String area){
        this.area = area;
    }
    public String getArea(){
        return this.area;
    }
    public void setPm2_5(int pm2_5){
        this.pm2_5 = pm2_5;
    }
    public int getPm2_5(){
        return this.pm2_5;
    }
    public void setPm2_5_24h(int pm2_5_24h){
        this.pm2_5_24h = pm2_5_24h;
    }
    public int getPm2_5_24h(){
        return this.pm2_5_24h;
    }
    public void setPosition_name(String position_name){
        this.position_name = position_name;
    }
    public String getPosition_name(){
        return this.position_name;
    }
    public void setPrimary_pollutant(String primary_pollutant){
        this.primary_pollutant = primary_pollutant;
    }
    public String getPrimary_pollutant(){
        return this.primary_pollutant;
    }
    public void setQuality(String quality){
        this.quality = quality;
    }
    public String getQuality(){
        return this.quality;
    }
    public void setStation_code(String station_code){
        this.station_code = station_code;
    }
    public String getStation_code(){
        return this.station_code;
    }
    public void setTime_point(String time_point){
        this.time_point = time_point;
    }
    public String getTime_point(){
        return this.time_point;
    }


}
