package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.model;

public class LocationObject {

    private final int id;
    private final String locationCity;
    private final String weatherInformation;

    public LocationObject(int id, String locationCity, String weatherInformation) {
        this.id = id;
        this.locationCity = locationCity;
        this.weatherInformation = weatherInformation;
    }
    public String getLocationCity() {
        return locationCity;
    }
    public String getWeatherInformation() {
        return weatherInformation;
    }
    public int getId() {
        return id;
    }
}
