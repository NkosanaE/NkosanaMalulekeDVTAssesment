package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static String baseUrl ;
    private static String baseFiveDayUrl ;
    private static String appID ;
    private static String unit;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        baseUrl = getResources().getString(R.string.api_base_url);
        baseFiveDayUrl = getResources().getString(R.string.api_5day_url);
        appID = getResources().getString(R.string.open_weather_api_key);
        unit = getResources().getString(R.string.unit);

        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getBaseFiveDayUrl() {
        return baseFiveDayUrl;
    }

    public static String getAppID() {
        return appID;
    }

    public static String getUnit() {
        return unit;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    public String getStringPref(String key_val, String def_val) {
        SharedPreferences pref = getSharedPreferences("pref_" + key_val, MODE_PRIVATE);
        return pref.getString(key_val, def_val);
    }

    public void setStringPref(String key_val, String val) {
        SharedPreferences pref = getSharedPreferences("pref_" + key_val, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.clear();
        prefEditor.putString(key_val, val);
        prefEditor.commit();
    }

}