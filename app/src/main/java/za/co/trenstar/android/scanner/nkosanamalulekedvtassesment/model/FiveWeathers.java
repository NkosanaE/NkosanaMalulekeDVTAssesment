package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.model;

import java.util.List;

public class FiveWeathers {

    private String dt_txt;

    private Main main;
    private List<WeatherResults> weather;


    public FiveWeathers(String dt_txt, Main main, List<WeatherResults> conditions) {
        this.dt_txt = dt_txt;
        this.main = main;
        this.weather = conditions;
    }

    public String getDt_txt(){
        return dt_txt;
    }

    public Main getMain() {
        return main;
    }


    public List<WeatherResults> getWeather() {
        return weather;
    }
}
