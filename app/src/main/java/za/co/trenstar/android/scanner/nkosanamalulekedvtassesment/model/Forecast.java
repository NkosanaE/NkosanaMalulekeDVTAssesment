package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.model;


import java.util.List;

public class Forecast {

    private List<FiveWeathers> list;

    private City city;

    public Forecast(List<FiveWeathers> list,City city) {
        this.list = list;
    }

    public List<FiveWeathers> getList() {
        return list;
    }

    public City getCity() {
        return city;
    }
}
