package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder{
    private static final String TAG = RecyclerViewHolder.class.getSimpleName();
    public TextView dayOfWeek;
    public ImageView weatherIcon;
    public TextView weatherResult;
    public RecyclerViewHolder(final View itemView) {
        super(itemView);
        dayOfWeek = (TextView)itemView.findViewById(R.id.day_of_week);
        weatherIcon = (ImageView)itemView.findViewById(R.id.weather_icon);
        weatherResult = (TextView) itemView.findViewById(R.id.weather_result);
    }
}
