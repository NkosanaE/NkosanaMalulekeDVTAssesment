package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.model;

public class City {
    String id;
   String name;
   Coord coord;

    public City(String id, String name, Coord coord) {
        this.id = id;
        this.name = name;
        this.coord = coord;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}
