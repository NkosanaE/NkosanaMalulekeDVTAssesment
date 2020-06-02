package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.helper;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.R;

public class Helper {

    public static final String PREFS_TAG = "prefs";

    public static final String PREF_KEY_LAST_LOCATION = "LAST_LOCATION";
    public static final String PREF_KEY_LAST_LOCATION_LAT = "LAST_LOCATION_LAT";
    public static final String PREF_KEY_LAST_LOCATION_LON = "LAST_LOCATION_LON";
    public static final String PREF_KEY_LAST_UPDATE_TIME = "LAST_UPDATE_TIME";

    public static final String PREF_KEY_LAST_WEATHER_TEMP = "LAST_WEATHER_TEMP";
    public static final String PREF_KEY_LAST_WEATHER_DESC = "LAST_WEATHER_DESC";


    public static final int GET_LOCATION_FOR_CITY =100;

    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }



    public static int getWeatherBackgroundResourceId(String icon){

        if (icon.equals("01d") || icon.equals("01n")) { // clear sky
            return R.drawable.forest_sunny;

        } else if (icon.equals("02d") || icon.equals("02n")) { //few clouds
            return R.drawable.forest_cloudy;
        } else if (icon.equals("03d") || icon.equals("03n")) { // scattered clouds
            return R.drawable.forest_cloudy;
        } else if (icon.equals("04d") || icon.equals("04n")) { //broken clouds
            return R.drawable.forest_cloudy;

        } else if (icon.equals("09d") || icon.equals("09n")) {  //shower rain
            return R.drawable.forest_rainy;

        } else if (icon.equals("10d") || icon.equals("10n")) { //rain
            return R.drawable.forest_rainy;

        } else if (icon.equals("11d") || icon.equals("11n")) { //thunderstorm
            return R.drawable.forest_rainy;

        } else if (icon.equals("13d") || icon.equals("13n")) { //snow
            return R.drawable.forest_rainy;

        } else if (icon.equals("50d") || icon.equals("50n")) { //mist
            return R.drawable.forest_rainy;

        } else {
            return R.drawable.forest_sunny;
        }

    }



    public static int getWeatherFiveDayForecastIconResourceId(String icon){


        if (icon.equals("01d") || icon.equals("01n")) { // clear sky
            return R.drawable.clear_medium;

        } else if (icon.equals("02d") || icon.equals("02n")) { //few clouds
            return R.drawable.partlysunny_medium;
        } else if (icon.equals("03d") || icon.equals("03n")) { // scattered clouds
            return R.drawable.partlysunny_medium;
        } else if (icon.equals("04d") || icon.equals("04n")) { //broken clouds
            return R.drawable.partlysunny_medium;

        } else if (icon.equals("09d") || icon.equals("09n")) {  //shower rain
            return R.drawable.rain_medium;

        } else if (icon.equals("10d") || icon.equals("10n")) { //rain
            return R.drawable.rain_medium;

        } else if (icon.equals("11d") || icon.equals("11n")) { //thunderstorm
            return R.drawable.rain_medium;

        } else if (icon.equals("13d") || icon.equals("13n")) { //snow
            return R.drawable.rain_medium;

        } else if (icon.equals("50d") || icon.equals("50n")) { //mist
            return R.drawable.rain_medium;

        } else {
            return R.drawable.clear_medium;
        }

    }

    public static int getBackgroundColor(String icon){

        if (icon.equals("01d") || icon.equals("01n")) { // clear sky
            return R.color.sunny;

        } else if (icon.equals("02d") || icon.equals("02n")) { //few clouds
            return R.color.cloudy;
        } else if (icon.equals("03d") || icon.equals("03n")) { // scattered clouds
            return R.color.cloudy;
        } else if (icon.equals("04d") || icon.equals("04n")) { //broken clouds
            return R.color.cloudy;

        } else if (icon.equals("09d") || icon.equals("09n")) {  //shower rain
            return R.color.rainy;

        } else if (icon.equals("10d") || icon.equals("10n")) { //rain
            return R.color.rainy;

        } else if (icon.equals("11d") || icon.equals("11n")) { //thunderstorm
            return R.color.rainy;

        } else if (icon.equals("13d") || icon.equals("13n")) { //snow
            return R.color.rainy;

        } else if (icon.equals("50d") || icon.equals("50n")) { //mist
            return R.color.rainy;

        } else {
            return R.color.sunny;
        }

    }



}
