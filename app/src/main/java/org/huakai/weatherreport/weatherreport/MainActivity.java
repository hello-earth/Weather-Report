package org.huakai.weatherreport.weatherreport;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.huakai.weatherreport.weatherreport.ParseClass.ARIResults;
import org.huakai.weatherreport.weatherreport.ParseClass.Daily;
import org.huakai.weatherreport.weatherreport.ParseClass.WeatherNowRoot;
import org.huakai.weatherreport.weatherreport.ParseClass.WeatherResults;
import org.huakai.weatherreport.weatherreport.ParseClass.WeatherRoot;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String ccity = "beijing";
    private final static String WNOWURL="https://api.thinkpage.cn/v3/weather/now.json?key=2thfum3vo3o2ir64&location=%s&language=zh-Hans&unit=c";
    private final static String WURL="https://api.thinkpage.cn/v3/weather/daily.json?key=2thfum3vo3o2ir64&location=%s&language=zh-Hans&unit=c&start=1&days=5";
    private final static String AURL="http://www.pm25.in/api/querys/pm2_5.json?city=%s&token=5j1znBVAsnSf5xQyNQyq";
    private  final static String WWURL="http://wthrcdn.etouch.cn/weather_mini?city=北京";
    private TextView city;
    private TextView humidity;
    private TextView last_update;
    private TextView wtext;
    private TextView temperature;
    private TextView aQuality;
    private TextView pm25_value;

    private TextView first_date;
    private TextView second_date;
    private TextView third_date;
    private TextView first_temperature;
    private TextView second_temperature;
    private TextView third_temperature;
    private TextView first_text_day;
    private TextView second_text_day;
    private TextView third_text_day;
    private TextView first_wind_scale;
    private TextView second_wind_scale;
    private TextView third_wind_scale;

    private ImageView nowImg;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if(checkNetStatus()){
            initOK();
//            request(WWURL);
            request(String.format(WNOWURL,ccity));
        }else{
            Toast.makeText(this,"没有网络请稍后再试",Toast.LENGTH_SHORT).show();
        }
    }

    private  void initView(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

//        toolbar.setNavigationIcon(R.mipmap.toolbar_city_home);//设置导航栏图标
//        toolbar.setLogo(R.mipmap.ic_launcher);//设置app logo
        toolbar.setTitle("");//设置主标题

        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_search) {
                    Intent intent = new Intent(MainActivity.this, PickCityActivity.class);
                    Bundle bundle=new Bundle();
                    String city="北京";
                    bundle.putString("city", city);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,0);
                } else if (menuItemId == R.id.action_refresh) {
                    if(checkNetStatus()){
                        request(String.format(WNOWURL,ccity));
                    }else{
                        Toast.makeText(MainActivity.this,"没有网络请稍后再试",Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });

        city = (TextView)findViewById(R.id.cityname);
        humidity = (TextView)findViewById(R.id.humidity);
        last_update = (TextView)findViewById(R.id.last_update);
        temperature = (TextView)findViewById(R.id.temperature);
        wtext = (TextView)findViewById(R.id.wtext);
        pm25_value = (TextView)findViewById(R.id.pm25_value);
        aQuality = (TextView)findViewById(R.id.pm25_quality);

        first_date= (TextView)findViewById(R.id.first_date);
        second_date= (TextView)findViewById(R.id.second_date);
        third_date= (TextView)findViewById(R.id.third_date);
        first_temperature = (TextView)findViewById(R.id.first_temperature);
        second_temperature = (TextView)findViewById(R.id.second_temperature);
        third_temperature = (TextView)findViewById(R.id.third_temperature);
        first_text_day = (TextView)findViewById(R.id.first_text_day);
        second_text_day = (TextView)findViewById(R.id.second_text_day);
        third_text_day = (TextView)findViewById(R.id.third_text_day);
        first_wind_scale = (TextView)findViewById(R.id.first_wind_scale);
        second_wind_scale = (TextView)findViewById(R.id.second_wind_scale);
        third_wind_scale = (TextView)findViewById(R.id.third_wind_scale);

        nowImg = (ImageView)findViewById(R.id.now_img);
        img1 = (ImageView)findViewById(R.id.first_img);
        img2 = (ImageView)findViewById(R.id.second_img);
        img3 = (ImageView)findViewById(R.id.third_img);

    }

    private boolean checkNetStatus(){
        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE)  return true;
        return false;
    }

    private void initOK(){
        OkGo.init(getApplication());
    }

    private void request(final String url){
        System.out.println(url);
        OkGo.get(url).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        String reurl =  call.request().url().toString();
                        if(reurl.equals(String.format(WNOWURL,ccity))){
                            onWeatherDataChanged(parseWeatherResult(s,WeatherNowRoot.class));
                            request(String.format(AURL,ccity));
                        }
                        else if(reurl.equals(String.format(WURL,ccity))) {
                            onWeatherDataChanged(parseWeatherResult(s,WeatherRoot.class));
                        }
                        else if(reurl.equals(String.format(AURL,ccity))) {
                            List<ARIResults> aresult = parseWeatherResult(s, new TypeToken<List<ARIResults>>(){}.getType());
                            if(aresult!=null && aresult.size()>0)onWeatherDataChanged(aresult.get(0));
                            request(String.format(WURL,ccity));
                        }
                    }
                });
    }

    //此处应更新三天数据，但是API网站只返回2天，所以，不更新第三天数据
    private void onWeatherDataChanged(WeatherRoot result){
        if(result==null || result.getResults().size()==0)
            return;
        List<Daily> dresult = result.getResults().get(0).getDaily();
        first_date.setText(dresult.get(0).getDate().substring(8)+"日");
        second_date.setText(dresult.get(1).getDate().substring(8)+"日");
//        third_date.setText(dresult.get(2).getDate().substring(8)+"日");
        first_temperature.setText(dresult.get(0).getLow()+"℃~"+dresult.get(0).getHigh()+"℃");
        second_temperature.setText(dresult.get(1).getLow()+"℃~"+dresult.get(1).getHigh()+"℃");
//        third_temperature.setText(dresult.get(2).getLow()+"℃~"+dresult.get(2).getHigh()+"℃");
        first_text_day.setText(dresult.get(0).getText_day());
        second_text_day.setText(dresult.get(1).getText_day());
//        third_text_day.setText(dresult.get(2).getText_day());
        first_wind_scale.setText("风力"+dresult.get(0).getWind_scale()+"级");
        second_wind_scale.setText("风力"+dresult.get(1).getWind_scale()+"级");
//        third_wind_scale.setText("风力"+dresult.get(2).getWind_scale()+"级");

        int resID = getResources().getIdentifier(ImageDatamap.dMap.get(dresult.get(0).getText_day()), "mipmap", "org.huakai.weatherreport.weatherreport");
        Drawable image = getResources().getDrawable(resID);
        img1.setImageDrawable(image);

        resID = getResources().getIdentifier(ImageDatamap.dMap.get(dresult.get(1).getText_day()), "mipmap", "org.huakai.weatherreport.weatherreport");
        image = getResources().getDrawable(resID);
        img2.setImageDrawable(image);

//        resID = getResources().getIdentifier(ImageDatamap.dMap.get(dresult.get(2).getText_day()), "mipmap", "org.huakai.weatherreport.weatherreport");
//        image = getResources().getDrawable(resID);
//        img3.setImageDrawable(image);
    }

    private void onWeatherDataChanged(WeatherNowRoot result){
        if(result==null || result.getResults().size()==0)
            return;
        city.setText(result.getResults().get(0).getLocation().getName());
        last_update.setText(result.getResults().get(0).getLast_update().substring(11,16));
        wtext.setText(result.getResults().get(0).getNow().getText());
        temperature.setText(result.getResults().get(0).getNow().getTemperature());
        int resID = getResources().getIdentifier(ImageDatamap.dMap.get(result.getResults().get(0).getNow().getText()), "mipmap", "org.huakai.weatherreport.weatherreport");
        Drawable image = getResources().getDrawable(resID);
        nowImg.setImageDrawable(image);
        ((TextView)findViewById(R.id.toolbar_title)).setText(result.getResults().get(0).getLocation().getName()+"天气");
    }

    private void onWeatherDataChanged(ARIResults result){
        if(result==null || "".equals(result.getAqi()))
            return;
        aQuality.setText(result.getQuality());
        pm25_value.setText(String.valueOf(result.getPm2_5()));
    }


    private <T> T parseWeatherResult(String jString, Class<T> cls){
        Gson gson = new Gson();
        try{
            return (T)gson.fromJson(jString, cls);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    private <T> T parseWeatherResult(String json, Type typeOfT){
        Gson gson = new Gson();
        try{
            return (T)gson.fromJson(json, typeOfT);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0){
            Bundle b=data.getExtras(); //data为B中回传的Intent
            ccity=b.getString("city");//str即为回传的值
            if(checkNetStatus()){
                request(String.format(WNOWURL,ccity));
            }else{
                Toast.makeText(MainActivity.this,"没有网络请稍后再试",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
//		/* ShareActionProvider配置 */
//        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu
//                .findItem(R.id.action_share));
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/*");
//        mShareActionProvider.setShareIntent(intent);
        return super.onCreateOptionsMenu(menu);
    }
}
