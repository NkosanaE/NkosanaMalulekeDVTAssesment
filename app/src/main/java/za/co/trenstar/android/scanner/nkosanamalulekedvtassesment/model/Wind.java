package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.model;

public class Wind {

    private String speed;

    private String deg;

    public Wind(String speed, String deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public String getSpeed() {
        return speed;
    }

    public String getDeg() {
        return deg;
    }
}
