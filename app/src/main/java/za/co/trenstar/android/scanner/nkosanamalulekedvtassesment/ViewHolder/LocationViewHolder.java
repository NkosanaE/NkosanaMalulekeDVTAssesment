package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.ViewHolder;

import android.graphics.Color;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.R;
import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.model.LocationObject;

public class LocationViewHolder extends RecyclerView.ViewHolder{
    private static final String TAG = LocationViewHolder.class.getSimpleName();
    public TextView locationCity;
    public LocationViewHolder(final View itemView, final List<LocationObject> locationObject) {
        super(itemView);
        locationCity = (TextView) itemView.findViewById(R.id.city_location);
    }
}
