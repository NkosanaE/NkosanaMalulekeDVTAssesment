package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ListJsonObject {

    @SerializedName("id")
    private String _id;
    @SerializedName("name")
    private String name;
    @SerializedName("country")
    private String country;
    @SerializedName("coord")
    private Coordinates coord;
    public ListJsonObject(String _id, String name, String country, Coordinates coord) {
        this._id = _id;
        this.name = name;
        this.country = country;
        this.coord = coord;
    }
    public String get_id() {
        return _id;
    }
    public String getName() {
        return name;
    }
    public String getCountry() {
        return country;
    }
    public Coordinates getCoord() {
        return coord;
    }
}
