package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.adapter.RecyclerViewAdapter;
import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.database.DbManager;
import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.helper.Helper;
import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.model.City;
import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.model.FiveDaysForecast;
import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.model.FiveWeathers;
import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.model.Forecast;
import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.model.LocationMapObject;
import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.model.Weather;
import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.utils.DateTimeUtils;

public class WeatherActivity extends AppCompatActivity implements LocationListener {
    private static final String TAG = WeatherActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private TextView txtLastUpdateTime;
    private TextView txTCityCountry;
    private TextView txtCurrentDate;
    private TextView txtTemperature;
    private TextView txtDescription;

    private TextView txtMinTemp;
    private TextView txtCurrentTemp;
    private TextView txtMaxTemp;

    private LocationMapObject locationMapObject;
    private LocationManager locationManager;
    private Location location;
    private final int REQUEST_LOCATION = 200;
    private String apiUrl;
    private FiveDaysForecast fiveDaysForecast;

    LinearLayout weatherLayout;
    LinearLayout weatherTempLayout;
    LinearLayout weatherFiveDayLayout;




    private boolean updateMainWeather = false;

    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        setTitle("Weather");

        dbManager = new DbManager(this);
        dbManager.open();


        weatherLayout = (LinearLayout) findViewById(R.id.layoutWeather);
        weatherTempLayout = (LinearLayout) findViewById(R.id.temperatureLayout);
        weatherFiveDayLayout = (LinearLayout) findViewById(R.id.fiveDayLayout);

        txtLastUpdateTime = (TextView)findViewById(R.id.txt_last_upadate_Time);
        txTCityCountry = (TextView)findViewById(R.id.city_country);
        txtCurrentDate = (TextView)findViewById(R.id.current_date);
        txtTemperature = (TextView)findViewById(R.id.text_temperature);
        txtDescription = (TextView)findViewById(R.id.text_weather_description);
        txtMinTemp =(TextView)findViewById(R.id.minTemp);
        txtCurrentTemp =(TextView)findViewById(R.id.currentTemp);
        txtMaxTemp =(TextView)findViewById(R.id.maxTemp);


        String lastUpdateTime = AppController.getInstance().getStringPref(Helper.PREF_KEY_LAST_UPDATE_TIME,"");

        if (!lastUpdateTime.isEmpty()){
            txtLastUpdateTime.setText("last updated: "+lastUpdateTime);
        }

        String lastLocation = AppController.getInstance().getStringPref(Helper.PREF_KEY_LAST_LOCATION,"");

        if (!lastLocation.isEmpty()){
            txTCityCountry.setText(lastLocation);
        }

        String lastTemp = AppController.getInstance().getStringPref(Helper.PREF_KEY_LAST_WEATHER_TEMP,"");
        String lastDesc = AppController.getInstance().getStringPref(Helper.PREF_KEY_LAST_WEATHER_DESC,"");

        if (!lastTemp.isEmpty()){
            txtTemperature.setText(lastTemp);
        }

        if (!lastDesc.isEmpty()){
            txtDescription.setText(lastDesc);
        }


        //get the current location
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WeatherActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            if(AppController.getInstance().getStringPref(Helper.PREF_KEY_LAST_LOCATION,"").equals("")){
                // make API call with longitude and latitude
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location!=null) {
                        apiUrl = AppController.getBaseUrl() + "lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&" + AppController.getAppID() + "&" +  AppController.getUnit();
                        getWeatherForLocation(apiUrl);
                    }else{
                        Toast.makeText(WeatherActivity.this, getString(R.string.no_location_notice), Toast.LENGTH_LONG).show();
                    }

                }
            }else{
                String storedCityName = AppController.getInstance().getStringPref(Helper.PREF_KEY_LAST_LOCATION,"");
                double lat =  Double.parseDouble(AppController.getInstance().getStringPref(Helper.PREF_KEY_LAST_LOCATION_LAT,"0"));
                double lon = Double.parseDouble(AppController.getInstance().getStringPref(Helper.PREF_KEY_LAST_LOCATION_LON,"0"));

                System.out.println("Stored city " + storedCityName);
                if(!TextUtils.isEmpty(storedCityName)){
                    String url = AppController.getBaseUrl() + "lat=" + lat + "&lon=" +lon + "&" + AppController.getAppID() + "&" + AppController.getUnit();
                   // String url =AppController.getBaseUrl()+storedCityName+"&"+AppController.getAppID()+"&" + AppController.getUnit();
                    getWeatherForLocation(url);
                }
            }
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(WeatherActivity.this, 1);
        recyclerView = (RecyclerView)findViewById(R.id.weather_daily_list);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
    }
    private void getWeatherForLocation(final String apiUrl){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                locationMapObject = gson.fromJson(response, LocationMapObject.class);
                if(locationMapObject!=null){
                    String city = locationMapObject.getName() + ", " + locationMapObject.getSys().getCountry();
                    String todayDate = getTodayDateInStringFormat();
                    float tempVal = Math.round(Math.floor(Double.parseDouble(locationMapObject.getMain().getTemp())));
                    String weatherTemp = String.valueOf((int)tempVal) + "°";


                    float minTempVal = Math.round(Math.floor(Double.parseDouble(locationMapObject.getMain().getTemp_min())));
                    String minTemp = String.valueOf((int)minTempVal) + "°";

                    float maxTempVal = Math.round(Math.floor(Double.parseDouble(locationMapObject.getMain().getTemp_max())));
                    String maxTemp = String.valueOf((int)maxTempVal) + "°";

                    String weatherDescription = Helper.capitalizeFirstLetter(locationMapObject.getWeather().get(0).getDescription());


                    weatherLayout.setBackground(getResources().getDrawable(Helper.getWeatherBackgroundResourceId(locationMapObject.getWeather().get(0).getIcon())));
                    weatherTempLayout.setBackgroundColor(getResources().getColor(Helper.getBackgroundColor(locationMapObject.getWeather().get(0).getIcon())));
                    weatherFiveDayLayout.setBackgroundColor(getResources().getColor(Helper.getBackgroundColor(locationMapObject.getWeather().get(0).getIcon())));


                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("E, d MMMM HH:MM:SS", Locale.getDefault());

                    AppController.getInstance().setStringPref(Helper.PREF_KEY_LAST_LOCATION_LON,locationMapObject.getCoord().getLon());
                    AppController.getInstance().setStringPref(Helper.PREF_KEY_LAST_UPDATE_TIME, df.format(c.getTime()));

                    AppController.getInstance().setStringPref(Helper.PREF_KEY_LAST_WEATHER_TEMP,weatherTemp);
                    AppController.getInstance().setStringPref(Helper.PREF_KEY_LAST_WEATHER_DESC,weatherDescription);
                    txtLastUpdateTime.setText("last updated: "+c.getTime());
                    //save location in database
                    if(apiUrl.contains("lat")){
                        AppController.getInstance().setStringPref(Helper.PREF_KEY_LAST_LOCATION,locationMapObject.getName());
                        AppController.getInstance().setStringPref(Helper.PREF_KEY_LAST_LOCATION_LAT,locationMapObject.getCoord().getLat());
                        AppController.getInstance().setStringPref(Helper.PREF_KEY_LAST_LOCATION_LON,locationMapObject.getCoord().getLon());






                        dbManager.insertLocation(locationMapObject.getName(),locationMapObject.getCoord().getLat(),locationMapObject.getCoord().getLon());
                    }
                    // populate View data
                    txTCityCountry.setText(Html.fromHtml(city));
                    txtCurrentDate.setText(Html.fromHtml(todayDate));
                    txtTemperature.setText(Html.fromHtml(weatherTemp));
                    txtCurrentTemp.setText(Html.fromHtml(weatherTemp));
                    txtMinTemp.setText(Html.fromHtml(minTemp));
                    txtMaxTemp.setText(Html.fromHtml(maxTemp));
                    txtDescription.setText(Html.fromHtml(weatherDescription));


                    getFiveDaysForecast(locationMapObject.getName());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error " + error.getMessage());
            }
        });
        // Add request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }
    @Override
    public void onProviderEnabled(String s) {
    }
    @Override
    public void onProviderDisabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            showGPSDisabledAlertToUser();
        }
    }
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    private String getTodayDateInStringFormat(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("E, d MMMM", Locale.getDefault());
        return df.format(c.getTime());
    }
    private void getFiveDaysForecast(final String city){
        apiUrl =AppController.getBaseFiveDayUrl()+city+"&"+AppController.getAppID()+"&"+AppController.getUnit();
        final List<Weather> daysOfTheWeek = new ArrayList<Weather>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response 5 days" + response);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Forecast forecast = gson.fromJson(response, Forecast.class);
                if (null == forecast) {
                    Toast.makeText(getApplicationContext(), "Could not get weather update for current city", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Weather refreshed", Toast.LENGTH_LONG).show();
                    int[] weekDays = new int[]{0,0,0,0,0,0,0};
                    List<FiveWeathers> weatherInfo = forecast.getList();
                    City  city = forecast.getCity();
                    for(int i = 0; i < weatherInfo.size(); i++){
                        String time = weatherInfo.get(i).getDt_txt();
                        String shortDay =DateTimeUtils.convertTimeToDay(time);
                        String temp = weatherInfo.get(i).getMain().getTemp();
                        int weatherIconId = Helper.getWeatherFiveDayForecastIconResourceId(weatherInfo.get(i).getWeather().get(0).getIcon());



                        if(DateTimeUtils.convertTimeToDay(time).equals("Mon") && weekDays[0] < 1){
                            daysOfTheWeek.add(new Weather(shortDay, weatherIconId, temp));
                            weekDays[0] = 1;
                        }
                        if(DateTimeUtils.convertTimeToDay(time).equals("Tue") && weekDays[1] < 1){
                            daysOfTheWeek.add(new Weather(shortDay, weatherIconId, temp));
                            weekDays[1] = 1;
                        }
                        if(DateTimeUtils.convertTimeToDay(time).equals("Wed") && weekDays[2] < 1){
                            daysOfTheWeek.add(new Weather(shortDay, weatherIconId, temp));
                            weekDays[2] = 1;
                        }
                        if(DateTimeUtils.convertTimeToDay(time).equals("Thu") && weekDays[3] < 1){
                            daysOfTheWeek.add(new Weather(shortDay, weatherIconId, temp));
                            weekDays[3] = 1;
                        }
                        if(DateTimeUtils.convertTimeToDay(time).equals("Fri") && weekDays[4] < 1){
                            daysOfTheWeek.add(new Weather(shortDay,weatherIconId, temp));
                            weekDays[4] = 1;
                        }
                        if(DateTimeUtils.convertTimeToDay(time).equals("Sat") && weekDays[5] < 1){
                            daysOfTheWeek.add(new Weather(shortDay,weatherIconId, temp));
                            weekDays[5] = 1;
                        }
                        if(DateTimeUtils.convertTimeToDay(time).equals("Sun") && weekDays[6] < 1){
                            daysOfTheWeek.add(new Weather(shortDay,weatherIconId, temp));
                            weekDays[6] = 1;
                        }
                        recyclerViewAdapter = new RecyclerViewAdapter(WeatherActivity.this, daysOfTheWeek);
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }

                    if (updateMainWeather){
                        apiUrl = AppController.getBaseUrl() + "lat=" +  city.getCoord().getLat() + "&lon=" + city.getCoord().getLon() + "&" + AppController.getAppID() + "&" + AppController.getUnit();
                        getWeatherForLocation(apiUrl);
                        updateMainWeather =false;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Could not get weather update for  city :" + city, Toast.LENGTH_LONG).show();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //make api call
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location!=null) {
                            apiUrl = AppController.getBaseUrl()+"lat="+location.getLatitude()+"&lon="+location.getLongitude()+"&"+AppController.getAppID()+"&"+AppController.getUnit();
                            getWeatherForLocation(apiUrl);
                        }
                    }else{
                        apiUrl =AppController.getBaseUrl()+"lat=25.7479&lon=28.2293"+"&"+AppController.getAppID()+"&"+AppController.getUnit();
                        getWeatherForLocation(apiUrl);
                    }
                }
            }else{
                Toast.makeText(WeatherActivity.this, getString(R.string.permission_notice), Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.change_location) {
            startActivityForResult(new Intent(this, ChangeLocationActivity.class),Helper.GET_LOCATION_FOR_CITY);
        }
        if (item.getItemId() == R.id.view_favorite_locations) {
            startActivity(new Intent(this, ViewLocationsActivity.class));
        }
        if (item.getItemId() == R.id.map_view_favorite_location) {
            startActivity(new Intent(this, ViewLocationsActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == Helper.GET_LOCATION_FOR_CITY) {
            updateMainWeather =true;
            String city = data.getStringExtra("City");
            getFiveDaysForecast(city);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
