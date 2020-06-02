package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.model;


public class Weather {

//    WeatherResults weatherResults;
//
//    public Weather(WeatherResults weatherResults){
//        this.weatherResults = weatherResults;
//    }
//
//    public WeatherResults getWeatherResults() {
//        return weatherResults;
//    }

    private String dayOfWeek;
    private int weatherIcon;
    private String weatherResult;
    private String weatherResultSmall;
    public Weather(String dayOfWeek, int weatherIcon, String weatherResult) {
        this.dayOfWeek = dayOfWeek;
        this.weatherIcon = weatherIcon;
        this.weatherResult = weatherResult;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public int getWeatherIcon() {
        return weatherIcon;
    }
    public String getWeatherResult() {
        return weatherResult;
    }
}
