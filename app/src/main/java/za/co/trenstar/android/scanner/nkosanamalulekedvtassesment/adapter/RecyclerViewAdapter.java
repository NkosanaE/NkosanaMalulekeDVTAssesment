package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.R;
import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.ViewHolder.RecyclerViewHolder;
import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.helper.Helper;
import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.model.Weather;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<Weather> dailyWeather;
    protected Context context;
    public RecyclerViewAdapter(Context context, List<Weather> dailyWeather) {
        this.dailyWeather = dailyWeather;
        this.context = context;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_daily_list, parent, false);
        viewHolder = new RecyclerViewHolder(layoutView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.dayOfWeek.setText(dailyWeather.get(position).getDayOfWeek());
        holder.weatherIcon.setImageResource(dailyWeather.get(position).getWeatherIcon());
        double temp = Double.parseDouble(dailyWeather.get(position).getWeatherResult());
        holder.weatherResult.setText((int)temp + "°");
    }
    @Override
    public int getItemCount() {
        return 5;
    }
}
