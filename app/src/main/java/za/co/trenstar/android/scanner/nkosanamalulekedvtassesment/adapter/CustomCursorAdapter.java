package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.R;

public class CustomCursorAdapter extends CursorAdapter {


    public CustomCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.location_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView txtLocationCity = (TextView) view.findViewById(R.id.city_location);
        // Extract properties from cursor
        String city = cursor.getString(cursor.getColumnIndexOrThrow("name"));

        // Populate fields with extracted properties
        txtLocationCity.setText(city);
    }
}